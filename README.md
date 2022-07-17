# Shortly
A single page mobile app for URL shortening using the public [shrtcode API](https://app.shrtco.de/docs) .
# Features:
* Kotlin
* MVVM
* Retrieve Network Data from API (REST API)
* Database caching
* Use cases
* Unit Tests
* MockWebServer (Okhttp)
* Kotlin Flow
* Dependency Injection
# Testing:
Local unit test is done for GetLinkList,DeleteLink and ShortenLink use cases.
## Notes:
This app requires minimum Android SDK 21 and Android Studio Chipmunk
# Libraries:
* [Kotlin coroutines](https://developer.android.com/kotlin/coroutines) Executing code asynchronously.
* [State Flow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) is a state-holder observable flow that emits the current and new state updates to its collectors.
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) is a dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project.
* [Retrofit](https://square.github.io/retrofit/) is a Type-safe HTTP client for Android, Java and Kotlin by Square.
* [Gson](https://github.com/google/gson) is a serialization/deserialization library to convert objects into JSON and back.
* [Okhttp interceptor](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor) Logs HTTP requests and responses.
* [Material Design](https://material.io/develop/android/) Build beautiful, usable products using Material Components for Android.
* [Mock web server](https://github.com/square/okhttp/tree/master/mockwebserver) A scriptable web server for testing HTTP clients.
