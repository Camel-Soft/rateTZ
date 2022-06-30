package com.camelsoft.ratetz._data.db

import androidx.room.*

@Dao
interface CurrencyDao {
    @Query("SELECT CurrencyEntity.currency FROM CurrencyEntity")
    fun getAll(): List<CurrencyEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(currencyEntity: CurrencyEntity)

    @Delete
    fun delete(vararg currencyEntity: CurrencyEntity)
}