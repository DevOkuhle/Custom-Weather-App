package com.example.customweatherapp.data.model.weatherTermsGlossary

import kotlinx.serialization.Serializable

@Serializable
data class Glossary(
    var definition: String = "",
    var term: String = ""
)