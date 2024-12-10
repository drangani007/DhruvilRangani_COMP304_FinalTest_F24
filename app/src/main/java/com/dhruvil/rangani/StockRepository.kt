package com.dhruvil.rangani

class StockRepository(private val stockDao: StockDao) {
    fun getStockSymbols() = stockDao.getStockSymbols()
    suspend fun insertStock(stock: StockInfo) = stockDao.insertStock(stock)
    suspend fun getStockBySymbol(symbol: String) = stockDao.getStockBySymbol(symbol)
}