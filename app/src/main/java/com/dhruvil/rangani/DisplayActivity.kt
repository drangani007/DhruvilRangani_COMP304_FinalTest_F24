package com.dhruvil.rangani

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.dhruvil.rangani.ui.theme.AppTheme
import kotlinx.coroutines.launch

class DisplayActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val stockSymbol = intent.getStringExtra("stockSymbol") ?: ""
        val database = AppDatabase.getInstance(applicationContext)
        val repository = StockRepository(database.stockDao())

        lifecycleScope.launch {
            val stockInfo = repository.getStockBySymbol(stockSymbol)
            setContent {
                AppTheme {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        DisplayScreen(stockInfo) {
                            finish()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DisplayScreen(stockInfo: StockInfo, onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Stock Symbol: ${stockInfo.stockSymbol}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Company Name: ${stockInfo.companyName}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Stock Quote: ${stockInfo.stockQuote}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onBack() }) {
            Text("Back")
        }
    }
}