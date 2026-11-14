package com.univesp.calculadoraveiculos

data class SimulationResult(
    val valorVeiculo: Double,
    val valorEntrada: Double,
    val valorFinanciado: Double,
    val jurosMensal: Double,
    val parcelaFinanciamento: Double,
    val totalFinanciamento: Double,
    val totalCompra: Double,
    val diaria: Double,
    val custoMensalLocacao: Double,
    val totalLocacao: Double,
    val totalDiasLocacao: Int,
    val diferenca: Double,
    val economiaAbsoluta: Double,
    val indicacao: String,
    val numParcelas: Int,
    val numMeses: Int
)
