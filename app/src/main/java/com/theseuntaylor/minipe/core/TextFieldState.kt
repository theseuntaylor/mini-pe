package com.theseuntaylor.minipe.core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.theseuntaylor.minipe.R

open class TextFieldState(
    private val validator: (String) -> Boolean,
    private val errorFor: String? = null,
) {

    var text: String by mutableStateOf("")
    private var isFocusedDirty: Boolean by mutableStateOf(false)
    private var isFocused: Boolean by mutableStateOf(false)
    private var displayErrors: Boolean by mutableStateOf(false)
    var isPasswordVisible: Boolean by mutableStateOf(false)

    open val isValid: Boolean
        get() = validator(text)

    fun onFocusChange(focused: Boolean) {
        isFocused = focused
        if (focused) isFocusedDirty = true
    }

    fun enableShowErrors() {
        if (isFocusedDirty) {
            displayErrors = true
        }
    }

    fun showErrors() = !isValid && displayErrors

    open fun getError(): String? {
        return if (showErrors()) {
            errorFor
        } else {
            null
        }
    }

    fun showTrailingIcon(): Int {
        return if (isPasswordVisible) {
            R.drawable.ic_visibility_off
        } else {
            R.drawable.ic_visibility_on
        }
    }

    fun showTrailingIconDescription(): Int {
        return if (isPasswordVisible) {
            R.string.showPassword
        } else {
            R.string.hidePassword
        }
    }

}