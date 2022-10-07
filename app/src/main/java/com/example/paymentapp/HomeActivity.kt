package com.example.paymentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import com.example.paymentapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var contUltimoPago: FrameLayout? = null
    private lateinit var saldo: TextView
    //private val app = applicationContext as RecargoApp

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_PaymentAPP)
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        saldo = binding.saldo
        val botonAgregarSaldo = binding.btnCargaSaldo
        val botonHistorial = binding.btnHistorial
        contUltimoPago = binding.ContainerUltimoCargo

        botonAgregarSaldo.setOnClickListener {
            val intent: Intent = Intent(this, ingresoDeMonto::class.java )
            startActivity(intent)
        }

        botonHistorial.setOnClickListener {
            mostrarMsj("Mostrar historial")
        }

        //cargarRecargos()
    }

    override fun onResume() {
        val textVacio = TextView(this)
        textVacio.text = "No has realizado niguna carga"
        contUltimoPago?.addView(textVacio)

        val saldoPrev = saldo.text
        val objetoIntent : Intent = intent
        val monto = objetoIntent.getStringExtra("monto") ?: "0"
        val grupoPrev = objetoIntent.getStringExtra("grupo") ?: "S/N"
        val nombreBanco = objetoIntent.getStringExtra("nombreBanco") ?: "S/N"
        val msj = objetoIntent.getStringExtra("resumen")
            ?.replace(")","")
            ?.split(" (") ?: listOf("S/N","S/N")

        if (monto.toInt() > 0){
            contUltimoPago?.removeAllViews()
            val nuevoSaldo = saldoPrev.toString().toInt()  + monto.toInt()
            saldo.text = nuevoSaldo.toString()
            println(msj[1])
            val fragment = FragmentResult(monto,grupoPrev,nombreBanco,msj[0],msj[1])
            supportFragmentManager
                .beginTransaction()
                .add(R.id.ContainerUltimoCargo, fragment).commit()
            mostrarMsj("Monto cargado exitosamente")

        }



        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    private fun mostrarMsj(msj: String){
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show()
    }

    /*private fun cargarRecargos() {
            lifecycleScope.launch {
                val recargo = app.room.recargoDao().obtenerUltimo()
                println(recargo)
            }



    }*/

}

