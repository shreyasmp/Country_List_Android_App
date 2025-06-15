# Country List Android App

A modern Android application that displays a list of countries fetched from a GraphQL API. Built
with Jetpack Compose and following clean architecture principles.

## Technologies Used

- **Kotlin** - Primary programming language
- **Jetpack Compose** - Modern UI toolkit for building native Android UI
- **Apollo GraphQL** - For efficient data fetching from the GraphQL API
- **Hilt** - Dependency injection library
- **MVVM Architecture** - Utilizing ViewModel components
- **Coroutines** - For asynchronous programming

## Features

- Fetch countries data from a GraphQL API
- Display countries in a scrollable list
- Show country name and code for each entry
- Material Design 3 components

## Setup

1. Clone the repository:
   ```
   git clone https://github.com/shreyasmp/Country_List_Android_App.git
   ```

2. Open the project in Android Studio

3. Configure the GraphQL server URL in `CountryViewModel.kt` if needed:
   ```kotlin
   private val apolloClient = ApolloClient.Builder()
       .serverUrl("http://10.0.2.2:8080/graphql") // Update as needed
       .build()
   ```

4. Run the application on an emulator or physical device

## Project Structure

- **ViewModel Layer**: Handles data operations and state management
- **UI Layer**: Compose UI components that display the country data
- **GraphQL**: Apollo client integration for API communication

## Requirements

- Android Studio Meerkat (2024.3.2) or newer
- Min SDK: 24 (Android 7.0)
- Target SDK: 34 (Android 14)

## Architecture

This project follows the MVVM (Model-View-ViewModel) architecture pattern:

- **Model**: GraphQL data models (Country data)
- **View**: Jetpack Compose UI components
- **ViewModel**: CountryViewModel managing state and data operations

## Screenshots

![alt text](images/Video.gif)
