package com.shreyas.countrylist.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.exception.ApolloNetworkException
import com.shreyas.countrylist.data.CountryUiState
import com.shreyas.countrylist.repository.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val repository: CountryRepository,
) : ViewModel() {

    var uiState by mutableStateOf(CountryUiState())
        private set

    init {
        fetchCountries()
    }

    fun fetchCountries() {
        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true, error = null
            )

            repository.getCountries()
                .onSuccess { countries ->
                    uiState = uiState.copy(
                        countries = countries,
                        isLoading = false,
                    )
                }
                .onFailure { exception ->
                    val errorMessage = when (exception) {
                        is ApolloNetworkException -> "Network Error: Check your connection"
                        else -> "Error loading countries: ${exception.message}"
                    }
                    uiState = uiState.copy(
                        isLoading = false,
                        error = errorMessage,
                    )
                }
        }
    }
}