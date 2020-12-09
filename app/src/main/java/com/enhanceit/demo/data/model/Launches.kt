package com.enhanceit.demo.data.model

data class Launches(
    val links: Links,
    val staticFireDateUtc: String = "",
    val static_fire_date_unix: Int? = 0,
    val success: Boolean? = false,
    val name: String = ""
)