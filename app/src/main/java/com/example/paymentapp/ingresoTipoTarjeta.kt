package com.example.paymentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.paymentapp.databinding.ActivityIngresoTipoTarjetaBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Objects

class ingresoTipoTarjeta : AppCompatActivity() {

    private var public_key: String = "444a9ef5-8a6b-429f-abdf-587639155d88"

    private lateinit var binding: ActivityIngresoTipoTarjetaBinding

    private var data: MutableMap<String,String> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIngresoTipoTarjetaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val objetoIntent : Intent = intent
        val monto = objetoIntent.getStringExtra("monto")
        val textViewMonto = binding.montoPrevio
        val btnIrBanco = binding.irBanco

        if (monto != null){
            textViewMonto.text = "$ $monto"
            data["monto"] = monto
        }

        btnIrBanco.setOnClickListener {
            if (data["grupo"]?.isNotEmpty() == true){
                startActivity(Intent(this, ingresoBanco::class.java, )
                    .putExtra("monto",data["monto"])
                    .putExtra("grupo",data["grupo"]))
            }else {
                mostrarError("Debes seleccionar el grupo de tu  tarjeta")
            }
        }
        obtenerTipoTarjeta()
    }



    private fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.mercadopago.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun obtenerTipoTarjeta() {
        CoroutineScope(Dispatchers.IO).launch {
            val llamada = getRetrofit().create(APIService::class.java)
                .obtenerTipoDeTarjetas("payment_methods?public_key=$public_key")

            runOnUiThread {
                if (llamada.isSuccessful){
                    val tipoTarjeta = llamada.body()?.map {
                        val nuevoObj: Map<String, String> = mapOf("tipo" to it.id,
                            "imagen" to it.secure_thumbnail)
                        return@map nuevoObj
                    } ?: emptyList()

                    val adapter = adaptadorTipoTarjeta(this@ingresoTipoTarjeta,tipoTarjeta)

                    val spinner = binding.spinnerTipoTarjeta
                    spinner.adapter = adapter
                    spinner.onItemSelectedListener = object:
                        AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            data["grupo"] = tipoTarjeta[position]["tipo"] ?: ""
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