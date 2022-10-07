package com.example.paymentapp

data class RespuestaTipoTarjeta(
    val id: String,
    val name: String,
    val payment_type_id: String,
    val status: String,
    val secure_thumbnail: String,
    val thumbnail: String,
    val deferred_capture: String,
    val settings: List<Any>,
    val additional_info_needed: List<Any>,
    val min_allowed_amount: Int,
    val max_allowed_amount: Int,
    val accreditation_time: Int,
    val financial_institutions: List<Any>,
    val processing_modes: List<String>
    )

data class RespuestaBancos(
    val id: String,
    val name: String,
    val secure_thumbnail: String,
    val thumbnail: String,
    val processing_mode: String,
    val merchant_account_id: String?,
    val status: String
)

data class RespuestaCuotas(
    val payment_method_id: String,
    val payment_type_id: String,
    val issuer: Map<String,String>,
    val processing_mode: String,
    val merchant_account_id: String?,
    val payer_costs: List<Payer_costs>
    )

data class Payer_costs (
    val installments: Int,
    val installment_rate: Double,
    val discount_rate: Int,
    val reimbursement_rate: Any,
    val labels: List<String>,
    val installment_rate_collector: List<String>,
    val min_allowed_amount: Int,
    val mix_allowed_amount: Int,
    val recommended_message: String,
    val installment_amount: Double,
    val total_amount: Double,
    val payment_method_option_id: String

        )