package com.shreyas.countrylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shreyas.countrylist.ui.theme.CountryListTheme
import com.shreyas.countrylist.viewmodel.CountryViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CountryListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Replace Greeting with CountryList
                    CountryList(innerPadding)

                }
            }
        }
    }
}

@Composable
fun CountryList(
    paddingValues: PaddingValues,
    viewModel: CountryViewModel = hiltViewModel()
) {
    // Access the uiState from the viewModel
    val uiState = viewModel.uiState

    LazyColumn(
        modifier = Modifier.padding(
            top = 32.dp,
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp
        ),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        if (uiState.isLoading) {
            item {
                Text("Loading Countries List...")
            }
        } else if (uiState.error != null) {
            item {
                Text(
                    text = uiState.error ?: "Unknown Error",
                    color = MaterialTheme.colorScheme.error,
                )
            }
        } else {
            items(uiState.countries) { country ->
                Text(
                    text = "${country.name}  (${country.code})",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(2.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CountryListTheme {
        CountryList(paddingValues = PaddingValues())
    }
}