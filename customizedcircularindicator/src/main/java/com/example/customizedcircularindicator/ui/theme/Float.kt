package com.example.customizedcircularindicator.ui.theme

import java.text.DecimalFormat


fun Float.retainDecimal(number: Int): String {
    val format = DecimalFormat("0.${"#".repeat(number)}")
    return format.format(this)
}