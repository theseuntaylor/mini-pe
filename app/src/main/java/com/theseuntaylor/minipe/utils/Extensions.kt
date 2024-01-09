package com.theseuntaylor.minipe.utils

import android.util.Patterns

fun String.passwordValidator() = this.isNotEmpty() && this.length > 4

fun String.usernameValidator() =
    this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()