package com.example.testcomposaapplication

sealed class AppState {
    object Empty : AppState()
    data class Feature1(val text: String) : AppState()
    data class Feature2(val text: String) : AppState()
}