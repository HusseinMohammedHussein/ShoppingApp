package com.e.commerce.util

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import com.e.commerce.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

// Created by Hussein_Mohammad on 5/12/2021.
class Validations constructor(var context: Context) : TextWatcher {

    private lateinit var view: View

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
    }


    fun validateEditText(text: String, textInputLayout: TextInputLayout): Boolean {
        val isValid: Boolean = Patterns.EMAIL_ADDRESS.matcher(text).matches()
        var isEmpty: TextInputEditText = TextInputEditText(context)
        if (isEmpty.text!!.trim().isEmpty()) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error = "Required"
            return false
        } else if (isValid) {
            textInputLayout.isCounterEnabled = true
            textInputLayout.error = "Email Invalid"
        }
        return true
    }

}