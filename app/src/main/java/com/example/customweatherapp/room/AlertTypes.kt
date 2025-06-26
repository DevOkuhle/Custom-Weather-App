package com.example.customweatherapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alert_types")
data class AlertTypes(
    @PrimaryKey(autoGenerate = true)
    var identifier: Int = 0,
    var alertType: String = ""
)