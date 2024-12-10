package com.dhruvil.rangani

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStock(stock: StockInfo)

    @Query("SELECT stockSymbol FROM stock_info")
    fun getStockSymbols(): Flow<List<String>>

    @Query("SELECT * FROM stock_info WHERE stockSymbol = :symbol")
    suspend fun getStockBySymbol(symbol: String): StockInfo
}