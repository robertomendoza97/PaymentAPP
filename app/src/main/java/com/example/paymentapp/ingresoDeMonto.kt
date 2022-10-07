package com.example.paymentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.paymentapp.databinding.ActivityHomeBinding
import com.example.paymentapp.databinding.ActivityIngresoDeMontoBinding

class ingresoDeMonto : AppCompatActivity() {

    private lateinit var binding: ActivityIngresoDeMontoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIngresoDeMontoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textMonto = binding.editTextMonto
        val btnSiguiente = binding.irMedioDePago
        btnSiguiente.setOnClickListener {


                if(textMonto.text.isNotEmpty()) {
                    if (textMonto?.text?.toString()?.toInt()!! < 150000){
                        startActivity(Intent(this, ingresoTipoTarjeta::class.java)
                            .putExtra("monto", textMonto.text.toString()))
                    }else{
                        mostrarMsj("El monto maximo es: 1.500.000")
                    }
                }else{
                    mostrarMsj("El monto es obligatorio")
                }
        }
    }

    private fun mostrarMsj(msj: String){
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show()
    }
}