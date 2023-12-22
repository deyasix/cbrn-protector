package ua.nure.cbrnprotector.utills

import androidx.core.text.isDigitsOnly

fun String.toIntArray(): IntArray? {
    if (isNullOrBlank()) return null
    val string = split(",")
    var values = intArrayOf()
    string.forEach {
        if (it.isDigitsOnly()) values += it.toInt() else throw Exception()
    }
    return values
}