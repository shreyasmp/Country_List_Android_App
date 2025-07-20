package com.shreyas.countrylist.data

import com.shreyas.countrylist.CountriesQuery.Country

data class CountryUiState(
    val countries: List<Country> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
