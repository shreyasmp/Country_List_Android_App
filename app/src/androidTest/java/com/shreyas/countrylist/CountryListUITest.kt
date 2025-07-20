package com.shreyas.countrylist

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.shreyas.countrylist.data.CountryUiState
import com.shreyas.countrylist.ui.theme.CountryListTheme
import com.shreyas.countrylist.viewmodel.CountryViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class CountryListUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingStateShowsLoadingText() {
        // Given
        val mockViewModel = mockk<CountryViewModel>()
        every { mockViewModel.uiState } returns CountryUiState(isLoading = true)

        // When
        composeTestRule.setContent {
            CountryListTheme {
                CountryList(paddingValues = PaddingValues(), viewModel = mockViewModel)
            }
        }

        // Then
        composeTestRule.onNodeWithText("Loading Countries List...").assertIsDisplayed()
    }

    @Test
    fun errorStateShowsErrorText() {
        // Given
        val mockViewModel = mockk<CountryViewModel>()
        every { mockViewModel.uiState } returns CountryUiState(error = "Failed to load countries")

        // When
        composeTestRule.setContent {
            CountryListTheme {
                CountryList(paddingValues = PaddingValues(), viewModel = mockViewModel)
            }
        }

        // Then
        composeTestRule.onNodeWithText("Failed to load countries").assertIsDisplayed()
    }

    @Test
    fun successStateShowsCountryList() {
        // Given
        val mockViewModel = mockk<CountryViewModel>()
        val countries = listOf(
            CountriesQuery.Country("US", "United States"),
            CountriesQuery.Country("CA", "Canada")
        )
        every { mockViewModel.uiState } returns CountryUiState(countries = countries)

        // When
        composeTestRule.setContent {
            CountryListTheme {
                CountryList(paddingValues = PaddingValues(), viewModel = mockViewModel)
            }
        }

        // Then
        composeTestRule.onNodeWithText("United States  (US)").assertIsDisplayed()
        composeTestRule.onNodeWithText("Canada  (CA)").assertIsDisplayed()
    }
}