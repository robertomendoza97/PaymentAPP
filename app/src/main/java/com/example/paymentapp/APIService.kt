package com.example.paymentapp

import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {

    @GET
    suspend fun obtenerTipoDeTarjetas(@Url url: String):Response<List<RespuestaTipoTarjeta>>

    @GET
    suspend fun obtenerBancos(@Url url: String ) :Response<List<RespuestaBancos>>

    @GET
    suspend fun obtenerCuotas(@Url url: String) : Response<List<RespuestaCuotas>>
}