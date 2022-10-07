package com.example.paymentapp

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.paymentapp.databinding.FragmentResultBinding


class FragmentResult(val monto: String,
                     var grupo: String ,
                     var banco: String,
                     var cuotas: String ,
                     var total: String
                     ) : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val montoView = view.findViewById<TextView>(R.id.montoFinal)
        val grupoView = view.findViewById<TextView>(R.id.grupoFinal)
        val bancoView = view.findViewById<TextView>(R.id.bancoFinal)
        val cuotaView = view.findViewById<TextView>(R.id.cuotasFinal)
        val totalView = view.findViewById<TextView>(R.id.total)

        montoView.text = monto
        grupoView.text = grupo
        bancoView.text = banco
        cuotaView.text = cuotas
        totalView.text = total
    }

}