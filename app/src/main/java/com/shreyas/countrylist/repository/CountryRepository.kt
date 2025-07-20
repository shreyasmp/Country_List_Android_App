package com.shreyas.countrylist.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.shreyas.countrylist.CountriesQuery
import com.shreyas.countrylist.CountriesQuery.Country
import javax.inject.Inject

interface CountryRepository {
    suspend fun getCountries(): Result<List<Country>>
}

class CountryRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
) : CountryRepository {

    override suspend fun getCountries(): Result<List<Country>> {
        return try {
            val response = apolloClient.query(CountriesQuery()).execute()
            val countries = response.data?.countries?.map {
                Country(code = it?.code ?: "", name = it?.name ?: "")
            } ?: emptyList()
            Result.success(countries)
        } catch (e: ApolloException) {
            Result.failure(e)
        }
    }
}