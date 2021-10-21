# Package Structure

Within our project's root package, packages are grouped by feature. The only exception is a core module with classes that are shared across the application. Within a feature package, information is split by responsibility. The ui code will go into one package, while the domain information in another - which is then split by use cases, repositories, and model classes.

```
|-- toa
|   |-- core
|   |-- login
|   |   |-- domain
|   |   |   |-- model
|   |   |   |   |-- Credentials.kt
|   |   |   |   |-- InvalidCredentialsException.kt
|   |   |   |   |-- LoginResponse.kt
|   |   |   |   |-- LoginResult.kt
|   |   |   |   |-- Token.kt
|   |   |   |-- repository
|   |   |   |   |-- DemoLoginService.kt
|   |   |   |   |-- DemoTokenService.kt
|   |   |   |   |-- LoginRepository.kt
|   |   |   |   |-- TokenRepository.kt
|   |   |   |-- usecase
|   |   |       |-- CredentialsLoginUseCase.kt
|   |   |       |-- ProdCredentialsLoginUseCase.kt
|   |   |-- ui
|   |   |   |-- LoginContent.kt
|   |   |   |-- LoginScreen.kt
|   |   |   |-- LoginViewModel.kt
|   |   |   |-- LoginViewState.kt
```


