package com.example.orthodoxy_in_ukraine.presentation.calendar

data class Holyday(
    val name :String,
    val date : Int,
    val month :Int,
    val sizeOfHolyday :Int,
    val isBigHolyday :Int,
    val description: String
) {
}