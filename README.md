# Custom-Weather-App

This App is created for experimental purposes for showcasing Android principles and architecture. 
The app makes use of solely Jetpack compose to create UI interface.
This app adheres to Clean Architecture, writing code that is clean and easily readible without having to add comments.
The app makes use of this free public API, i.e, https://api.weather.gov/ to display weather alerts and weather forecast within the Unites States of America.
Though there are many APIs in the link above, six have been used in the app.
The function of the app includes:
- Checking weather alerts by an area code in the US, however, the user is not expected to known thier code by heart, there is a compliled dropdown list of area codes with contain the area code acronym as well as their backronym.
- Checking weather alerts by a US zone, the app has made it convenient for the user to choose thier zone in a compiled dropdwonlist.
- Checking weather forecasst and hourly forecast, the user does not need to input their gripoint(x,y), instead we have compile a list of all the Weather Forecast Office ID (WFO) in each city, when a user chooses a WFO and city in the dropdown list, they get the weather forecast of associayted with the office ID gridpoints.
- Users can check all weather terms in the app, that are used in the app and in general.
- Finally, the user can check weather alert types terminology, and for each term when the user clicks on it, they are redirected to their phone's dictional if it exist or else to a google serach of that term.

The app makes use of the following technologies:
- Reastful APIs
- Dagger-Hilt dependency injection
- Jetpack composables UI
- MVVM and a repository
- LiveData
- Stateful Flows and Flows in general
- Kotlin
- The principle of separation of concerns
- Reusability of functions
- Room Database
too name a few.
