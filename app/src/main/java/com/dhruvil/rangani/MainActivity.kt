package com.dhruvil.rangani

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.dhruvil.rangani.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "stock_database"
        ).build()
        val repository = StockRepository(database.stockDao())
        val viewModel: StockViewModel = StockViewModel(repository)

        setContent {
            AppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainScreen(viewModel = viewModel) { stockSymbol ->
                        val intent = Intent(this, DisplayActivity::class.java)
                        intent.putExtra("stockSymbol", stockSymbol)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: StockViewModel, onStockSelected: (String) -> Unit) {
    var stockSymbol by remember { mutableStateOf("") }
    var companyName by remember { mutableStateOf("") }
    var stockQuote by remember { mutableStateOf("") }

    // Collect stock symbols using collectAsState from the ViewModel
    val stockSymbols by viewModel.stockSymbols.collectAsState()

    // Fetch the stock symbols initially when the screen is displayed
    LaunchedEffect(Unit) {
        viewModel.fetchStockSymbols()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextField(value = stockSymbol, onValueChange = { stockSymbol = it }, label = { Text("Stock Symbol") })
        TextField(value = companyName, onValueChange = { companyName = it }, label = { Text("Company Name") })
        TextField(value = stockQuote, onValueChange = { stockQuote = it }, label = { Text("Stock Quote") })
        Button(onClick = {
            // Insert stock info and fetch updated symbols
            viewModel.insertStock(StockInfo(stockSymbol, companyName, stockQuote.toDouble()))
            viewModel.fetchStockSymbols() // Refresh stock symbols after insertion
        }) {
            Text("Add Stock")
        }

        // Display the list of stock symbols
        LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 16.dp)) {
            items(stockSymbols) { symbol ->
                TextButton(onClick = { onStockSelected(symbol) }) {
                    Text(symbol)
                }
            }
        }
    }
}
