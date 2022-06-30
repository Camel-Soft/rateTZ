package com.camelsoft.ratetz._data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyEntity(
    @PrimaryKey(autoGenerate = false)
    val currency: String,
)