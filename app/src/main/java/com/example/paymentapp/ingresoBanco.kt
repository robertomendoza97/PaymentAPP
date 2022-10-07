package com.example.paymentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Toast
import com.example.paymentapp.databinding.ActivityIngresoBancoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ingresoBanco : AppCompatActivity() {

    private lateinit var binding: ActivityIngresoBancoBinding
    private var public_key: String = "444a9ef5-8a6b-429f-abdf-587639155d88"
    private var data: MutableMap<String,String> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIngresoBancoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val objetoIntent : Intent = intent
        val monto = objetoIntent.getStringExtra("monto")
        val grupoPrev = objetoIntent.getStringExtra("grupo")
        val textViewMonto = binding.montoPrevio
        val textGrupo = binding.tipoTarjetaPrevio
        val btnIrCuotas: Button = binding.irCuotas

        textViewMonto.text = "$ $monto"
        textGrupo.text = grupoPrev

        val bundle = Bundle()
        bundle.putString("monto",monto)
        bundle.putString("grupo",grupoPrev)

        btnIrCuotas.setOnClickListener {

            if (data["idBanco"]?.isNotEmpty() == true && data["nombreBanco"]?.isNotEmpty() == true ) {
                bundle.putString("nombreBanco",data["nombreBanco"])
                bundle.putString("idBanco",data["idBanco"])
                startActivity(Intent(this, ingresoCuotas::class.java)
                    .putExtras(bundle)
                )
            } else {
                mostrarError("El banco es obligatorio")
            }
        }

        obtenerBanco(grupoPrev)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.mercadopago.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun obtenerBanco(id: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val llamada = getRetrofit().create(APIService::class.java)
                .obtenerBancos(
                    "payment_methods/card_issuers?public_key=$public_key&payment_method_id=$id")

            runOnUiThread {
                if (llamada.isSuccessful){
                    val tipoBanco = llamada.body()?.map {
                        val nuevoObj: Map<String, String> = mapOf("tipo" to it.name,
                            "imagen" to it.secure_thumbnail, "id" to it.id)
                        return@map nuevoObj
                    } ?: emptyList()

                    val adapter = adaptadorTipoTarjeta(this@ingresoBanco,tipoBanco)

                    val spinner = binding.spinner
                    spinner.adapter = adapter
                    spinner.onItemSelectedListener = object:
                        AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            data["nombreBanco"] = tipoBanco[position]["tipo"] ?: ""
                            data["idBanco"] = tipoBanco[position]["id"] ?: ""
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }

                    }
                } else {
                    mostrarError("ha ocurrido un error")
                }
            }

        }
    }

    private fun mostrarError(msj: String){
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show()
    }
}