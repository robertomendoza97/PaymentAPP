package com.example.paymentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import com.example.paymentapp.databinding.ActivityIngresoCuotasBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ingresoCuotas : AppCompatActivity() {

    private lateinit var binding: ActivityIngresoCuotasBinding
    private var public_key: String = "444a9ef5-8a6b-429f-abdf-587639155d88"
    private var data: MutableMap<String,String> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIngresoCuotasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val objetoIntent : Intent = intent
        val monto = objetoIntent.getStringExtra("monto")
        val grupoPrev = objetoIntent.getStringExtra("grupo")
        val nombreBanco = objetoIntent.getStringExtra("nombreBanco")
        val idBanco = objetoIntent.getStringExtra("idBanco")
        val bundle = Bundle()
        bundle.putString("monto",monto)
        bundle.putString("grupo",grupoPrev)
        bundle.putString("nombreBanco",nombreBanco)

        val textViewMonto = binding.montoPrevio
        val textGrupo = binding.tipoTarjetaPrevio
        val btnconfirmar = binding.confirmar
        val textBanco = binding.bancoPrevio

        textViewMonto.text = monto
        textGrupo.text = grupoPrev
        textBanco.text = nombreBanco

        btnconfirmar.setOnClickListener {
            if (data["resumen"]?.isNotEmpty() == true ) {
                bundle.putString("resumen",data["resumen"])
                startActivity(Intent(this, HomeActivity::class.java)
                    .putExtras(bundle).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )
                finish()
            } else {
                mostrarError("Las cuotas son obliatorias")
            }
        }

        obtenerCuotas(grupoPrev, monto, idBanco)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.mercadopago.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun obtenerCuotas(tipoTarjeta: String?,monto:String?,idBanco:String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val llamada = getRetrofit().create(APIService::class.java)
                .obtenerCuotas(
                    "payment_methods/installments?public_key=$public_key" +
                            "&amount=$monto&payment_method_id=$tipoTarjeta" +
                            "&issuer.id=$idBanco")

            runOnUiThread {
                if (llamada.isSuccessful){
                    val objResp = llamada.body()?.get(0)
                    var payerCosts = objResp?.payer_costs?.map {
                        val nuevoObj: Map<String, String> = mapOf(
                            "msj" to it.recommended_message
                        )
                        return@map nuevoObj
                    } ?: emptyList()

                    val adapter = adaptadorCuotas(this@ingresoCuotas,payerCosts)

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
                            data["resumen"] = payerCosts[position]["msj"] ?: ""
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