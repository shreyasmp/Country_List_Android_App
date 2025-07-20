package com.shreyas.countrylist.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.apollographql.apollo3.exception.ApolloNetworkException
import com.shreyas.countrylist.CountriesQuery
import com.shreyas.countrylist.repository.CountryRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CountryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var repository: CountryRepository
    private lateinit var viewModel: CountryViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchCountries should update UI state with countries when successful`() = runTest {
        // Given
        val mockCountries = listOf(
            CountriesQuery.Country("US", "United States"),
            CountriesQuery.Country("CA", "Canada")
        )
        coEvery { repository.getCountries() } returns Result.success(mockCountries)

        // When
        viewModel = CountryViewModel(repository)

        // Then
        assertEquals(false, viewModel.uiState.isLoading)
        assertEquals(mockCountries, viewModel.uiState.countries)
        assertNull(viewModel.uiState.error)
    }

    @Test
    fun `fetchCountries should update UI state with error when network fails`() = runTest {
        // Given
        val exception = ApolloNetworkException("Network Error")
        coEvery { repository.getCountries() } returns Result.failure(exception)

        // When
        viewModel = CountryViewModel(repository)

        // Then
        assertEquals(false, viewModel.uiState.isLoading)
        assertEquals(0, viewModel.uiState.countries.size)
        assertEquals("Network Error: Check your connection", viewModel.uiState.error)
    }

    @Test
    fun `fetchCountries should update UI state with general error for non-network failures`() = runTest {
        // Given
        val exception = RuntimeException("General error")
        coEvery { repository.getCountries() } returns Result.failure(exception)

        // When
        viewModel = CountryViewModel(repository)

        // Then
        assertEquals(false, viewModel.uiState.isLoading)
        assertEquals(0, viewModel.uiState.countries.size)
        assertEquals("Error loading countries: General error", viewModel.uiState.error)
    }
}