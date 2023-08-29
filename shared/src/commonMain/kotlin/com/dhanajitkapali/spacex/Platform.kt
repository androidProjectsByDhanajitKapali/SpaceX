package com.dhanajitkapali.spacex

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform