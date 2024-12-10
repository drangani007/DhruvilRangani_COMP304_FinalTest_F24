package com.dhruvil.rangani

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StockViewModel(private val repository: StockRepository) : ViewModel() {

    private val _stockSymbols = MutableStateFlow<List<String>>(emptyList())
    val stockSymbols: StateFlow<List<String>> get() = _stockSymbols

    fun insertStock(stock: StockInfo) {
        viewModelScope.launch {
            repository.insertStock(stock)
        }
    }

    fun fetchStockSymbols() {
        viewModelScope.launch {
            repository.getStockSymbols().collect { symbols ->
                _stockSymbols.value = symbols
            }
        }
    }
}