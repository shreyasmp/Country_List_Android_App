package com.shreyas.countrylist.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloNetworkException
import com.shreyas.countrylist.CountriesQuery
import com.shreyas.countrylist.CountriesQuery.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor() : ViewModel() {
    private val apolloClient = ApolloClient.Builder()
        .serverUrl("http://10.0.2.2:8080/graphql") // for local server from emulator
        .build()

    var countries by mutableStateOf<List<Country>>(emptyList())
        private set

    init {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    apolloClient.query(CountriesQuery()).execute()
                }
                countries = response.data?.countries?.map {
                    Country(code = it?.code ?: "", name = it?.name ?: "")
                } ?: emptyList()
            } catch (e: ApolloNetworkException) {
                Log.e("CountryViewModel", "Network error: ${e.message}", e)
            } catch (e: Exception) {
                Log.e("CountryViewModel", "Unexpected error: ${e.message}", e)
            }
        }
    }
}