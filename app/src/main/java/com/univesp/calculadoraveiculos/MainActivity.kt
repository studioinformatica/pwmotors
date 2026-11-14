package com.univesp.calculadoraveiculos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.univesp.calculadoraveiculos.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.abs
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val jurosMap = mapOf(
        "moto" to mapOf("popular" to 0.010, "medio" to 0.015, "luxo" to 0.020),
        "carro" to mapOf("popular" to 0.015, "medio" to 0.020, "luxo" to 0.025),
        "caminhao" to mapOf("popular" to 0.020, "medio" to 0.025, "luxo" to 0.030)
    )

    private val ptBrLocale = Locale("pt", "BR")
    private val currencyFormat = NumberFormat.getCurrencyInstance(ptBrLocale)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinners()
        setupMasks()
        setupListeners()
    }

    private fun setupSpinners() {
        val tipos = arrayOf("Carro", "Moto", "Caminhão")
        binding.spinnerTipo.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tipos)

        val categorias = arrayOf("Popular", "Médio", "Luxo")
        binding.spinnerCategoria.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categorias)
    }

    private fun setupMasks() {
        binding.editValorVeiculo.addTextChangedListener(CurrencyTextWatcher(binding.editValorVeiculo))
        binding.editValorEntrada.addTextChangedListener(CurrencyTextWatcher(binding.editValorEntrada))
    }

    private fun setupListeners() {
        binding.btnCalcular.setOnClickListener {
            calcularSimulacao()
        }
        binding.btnLimpar.setOnClickListener {
            limparCampos()
        }
        binding.btnCompartilhar.setOnClickListener {
            compartilharResultado()
        }
    }

    private fun calcularSimulacao() {
        val valorVeiculo = CurrencyTextWatcher.parseCurrencyValue(binding.editValorVeiculo.text.toString())
        val valorEntrada = CurrencyTextWatcher.parseCurrencyValue(binding.editValorEntrada.text.toString())

        val numParcelas = binding.editParcelas.text.toString().toIntOrNull()
        val numMeses = binding.editMesesUso.text.toString().toIntOrNull()
        val numDiasPorMes = binding.editDiasPorMes.text.toString().toIntOrNull()

        if (valorVeiculo <= 0.0) {
            Toast.makeText(this, "Informe o valor do veículo.", Toast.LENGTH_SHORT).show()
            return
        }
        if (valorEntrada >= valorVeiculo) {
            Toast.makeText(this, "A entrada deve ser menor que o valor do veículo.", Toast.LENGTH_SHORT).show()
            return
        }
        if (numParcelas == null || numParcelas < 1 || numParcelas > 120) {
            Toast.makeText(this, "Informe de 1 a 120 parcelas.", Toast.LENGTH_SHORT).show()
            return
        }
        if (numMeses == null || numMeses < 1 || numMeses > 120) {
            Toast.makeText(this, "Informe de 1 a 120 meses.", Toast.LENGTH_SHORT).show()
            return
        }
        if (numDiasPorMes == null || numDiasPorMes < 1 || numDiasPorMes > 31) {
            Toast.makeText(this, "Informe entre 1 e 31 dias por mês.", Toast.LENGTH_SHORT).show()
            return
        }

        val tipoKey = when (binding.spinnerTipo.selectedItemPosition) {
            0 -> "carro"
            1 -> "moto"
            else -> "caminhao"
        }
        val catKey = when (binding.spinnerCategoria.selectedItemPosition) {
            0 -> "popular"
            1 -> "medio"
            else -> "luxo"
        }

        val jurosMensal = jurosMap[tipoKey]?.get(catKey) ?: 0.015
        val valorFinanciado = valorVeiculo - valorEntrada

        var parcelaFinanciamento = valorFinanciado
        if (valorFinanciado > 0.0) {
            parcelaFinanciamento = (valorFinanciado * (1.0 + jurosMensal).pow(numParcelas.toDouble()) * jurosMensal) /
                    ((1.0 + jurosMensal).pow(numParcelas.toDouble()) - 1.0)
        }

        val totalFinanciamento = parcelaFinanciamento * numParcelas
        val totalCompra = valorEntrada + totalFinanciamento

        var diaria = valorVeiculo * 0.0015
        if (catKey == "medio") diaria *= 1.2
        if (catKey == "luxo") diaria *= 1.5

        val totalDiasLocacao = numMeses * numDiasPorMes
        val custoMensalLocacao = diaria * numDiasPorMes
        val totalLocacao = diaria * totalDiasLocacao

        val diferenca = totalCompra - totalLocacao
        val economiaAbsoluta = abs(diferenca)

        val indicacao = when {
            diferenca < 0.0 -> "Na simulação, a AQUISIÇÃO apresenta menor custo total."
            diferenca > 0.0 -> "Na simulação, a LOCAÇÃO apresenta menor custo total."
            else -> "Na simulação, os custos totais são EQUIVALENTES."
        }

        val txtFinanciadoVal = currencyFormat.format(valorFinanciado)
        val txtTaxaVal = String.format(Locale.getDefault(), "%.2f", jurosMensal * 100)
        val txtParcelaVal = currencyFormat.format(parcelaFinanciamento)
        val txtTotalCompraVal = currencyFormat.format(totalCompra)
        val txtDiariaVal = currencyFormat.format(diaria)
        val txtCustoMensalVal = currencyFormat.format(custoMensalLocacao)
        val txtTotalLocacaoVal = currencyFormat.format(totalLocacao)
        val txtEconomiaVal = currencyFormat.format(economiaAbsoluta)

        binding.txtFinanciado.text = "Valor Financiado: $txtFinanciadoVal"
        binding.txtTaxaJuros.text = "Taxa mensal estimada: $txtTaxaVal%"
        binding.txtParcela.text = "Parcela Mensal: $txtParcelaVal"
        binding.txtTotalCompra.text = "Custo Total da Compra: $txtTotalCompraVal"

        binding.txtDiaria.text = "Diária Estimada: $txtDiariaVal"
        binding.txtCustoMensalLocacao.text = "Custo Mensal Locação: $txtCustoMensalVal"
        binding.txtTotalLocacao.text = "Custo Total Locação: $txtTotalLocacaoVal"

        binding.txtIndicacao.text = indicacao
        binding.txtEconomia.text = "Diferença Absoluta: $txtEconomiaVal"

        // Exibe o card de resultado e rola a tela suavemente para baixo
        binding.cardResultado.visibility = View.VISIBLE
        binding.scrollMain.post {
            binding.scrollMain.smoothScrollTo(0, binding.cardResultado.top)
        }
    }

    private fun limparCampos() {
        binding.editValorVeiculo.setText("")
        binding.editValorEntrada.setText("")
        binding.editParcelas.setText("")
        binding.editMesesUso.setText("")
        binding.editDiasPorMes.setText("30")
        binding.cardResultado.visibility = View.GONE
        binding.scrollMain.post {
            binding.scrollMain.smoothScrollTo(0, 0)
        }
    }

    private fun compartilharResultado() {
        val totalCompraText = binding.txtTotalCompra.text.toString()
        val totalLocacaoText = binding.txtTotalLocacao.text.toString()
        val indicacaoText = binding.txtIndicacao.text.toString()
        val economiaText = binding.txtEconomia.text.toString()

        val textShare = "Relatório de Viabilidade Financeira - PW Motors\n\n" +
                "$totalCompraText\n" +
                "$totalLocacaoText\n\n" +
                "Conclusão:\n" +
                "$indicacaoText\n" +
                economiaText

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Simulação PW Motors - Viabilidade Financeira")
            putExtra(Intent.EXTRA_TEXT, textShare)
        }
        startActivity(Intent.createChooser(shareIntent, "Compartilhar Relatório"))
    }
}