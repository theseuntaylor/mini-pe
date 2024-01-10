package com.theseuntaylor.minipe.utils

import android.util.Patterns
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun String.passwordValidator() = this.isNotEmpty() && this.length > 4

fun String.usernameValidator() =
    this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.returnDate(): String {
    val parsedDate = LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
    return parsedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
}
fun String.returnTime(): String {
    val parsedDate = LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
    return parsedDate.format(DateTimeFormatter.ofPattern("HH:mm"))
}