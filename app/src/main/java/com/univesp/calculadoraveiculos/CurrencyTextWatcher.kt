package com.univesp.calculadoraveiculos

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.lang.ref.WeakReference
import java.text.NumberFormat
import java.util.Locale

class CurrencyTextWatcher(editText: EditText) : TextWatcher {
    private val editTextRef: WeakReference<EditText> = WeakReference(editText)
    private var isUpdating = false

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        val editText = editTextRef.get() ?: return
        if (isUpdating) return

        isUpdating = true

        val cleanString = s.toString().replace("[^0-9]".toRegex(), "")

        if (cleanString.isEmpty()) {
            editText.setText("")
            isUpdating = false
            return
        }

        try {
            val parsed = cleanString.toDouble() / 100.0
            val formatted = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(parsed)

            editText.setText(formatted)
            editText.setSelection(formatted.length)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        isUpdating = false
    }

    companion object {
        fun parseCurrencyValue(formattedText: String): Double {
            val cleanString = formattedText.replace("[^0-9]".toRegex(), "")
            if (cleanString.isEmpty()) return 0.0
            return cleanString.toDouble() / 100.0
        }
    }
}
