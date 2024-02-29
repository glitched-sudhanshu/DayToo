package com.example.daytoo.models

data class GalleryData(
    val url: String,
    val type: String,
    val title: String
)

enum class Location(name: String){
    AUTO("auto"),
    PARK("park"),
    CAFE("cafe"),
}

fun String.toLocation() : Location{
    return when(this)
    {
        "auto" -> Location.AUTO
        "park" -> Location.PARK
        "cafe" -> Location.CAFE
        else -> Location.AUTO
    }
}