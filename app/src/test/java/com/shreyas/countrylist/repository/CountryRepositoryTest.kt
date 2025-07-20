package com.shreyas.countrylist.repository

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloNetworkException
import com.shreyas.countrylist.CountriesQuery
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CountryRepositoryTest {

    private lateinit var apolloClient: ApolloClient
    private lateinit var repository: CountryRepository
    private lateinit var mockQuery: ApolloCall<CountriesQuery.Data>

    @Before
    fun setup() {
        // Create mocks
        apolloClient = mockk(relaxed = true)
        mockQuery = mockk(relaxed = true)

        // Setup repository with mocked client
        repository = CountryRepositoryImpl(apolloClient)

        // Setup default query mock
        every { apolloClient.query(any<CountriesQuery>()) } returns mockQuery
    }

    @Test
    fun `getCountries should return failure when Apollo throws exception`() = runTest {
        // Given
        val networkException = ApolloNetworkException("Network error")

        // Setup query execution to throw exception
        coEvery { mockQuery.execute() } throws networkException

        // When
        val result = repository.getCountries()

        // Then
        assertTrue(result.isFailure)
        assertEquals(networkException, result.exceptionOrNull())
    }
}