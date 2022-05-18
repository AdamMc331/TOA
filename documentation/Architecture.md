# App Architecture

The TOA application follows a MVVM architecture pattern throughout the project. Below is the architecture diagram for the Login Screen, though every screen in the app should follow the same organization.  

For the most up to date diagram, check [Miro](https://miro.com/app/board/o9J_ltxT_4k=/?invite_link_id=617869994956).

![Architecture Diagram](assets/TOAFlows.jpg)

This document is intended to clarify each of the components in the above image and their responsibilities. 

## Repository

A repository is any component that is responsible for making data requests. This could be to a remote server, local preferences, database, etc. The repository should only request and receive data. It should not have any side effects such as calling a different repository. The repository should be responsible for mapping information from DTO (data transfer object) models to domain specific data classes.

Repositories should not be responsible for any data manipulation of a response. For example, if a repository is requesting a list of users, but a screen only cares about users with a certain property, the use case should be responsible for filtering the list accordingly. 

## Use Case

We were inspired to leverage the idea of Use Cases thanks to [this blog post](https://proandroiddev.com/why-you-need-use-cases-interactors-142e8a6fe576).

The purpose of a use case is to perform any specific action that occurs on a screen. This could be fetching or submitting data, filtering items, etc. Domain specific business logic like this deserves its own component in the application's architecture.

A repository is only responsible for requesting and receiving data. A ViewModel is only responsible for taking the results of an action, and mapping it into a ViewState. The use case is responsible for whatever happens in between - consuming responses from data requests, and mapping that into a relevant result for the ViewModel to handle.

### When To Use Use Cases

In general, adding a use case adds an extra layer of complexity to a screen that may not always be warranted. We prefer to have our ViewModels depend directly on a repository. If a piece of business logic does more than just request from our repository (such as data manipulation or input validation), or needs to combine data from multiple repositories, moving this logic into a use case can help with organization and testing.

### Use Case Interfaces

In some implementations, we often see use cases backed by an interface, like so:

```kotlin
interface MarkTaskAsCompleteUseCase { }

class MarkTaskAsCompleteUseCaseImpl : MarkTaskAsCompleteUseCase { } 
```

While this approach can be beneficial for testing, in general this just adds even more complexity. Use cases should be made as their own individual class so that in our tests we can test the full integration between view models and their use cases. Interfaces should only be added for truly complex use cases that we may have difficulty writing full integration tests for. 

## ViewModel

This component is responsible for connecting a screen with any relevant use cases. The ViewModel consumes UI events, triggers a necessary use case, and processes a use case response into a ViewState that is exposed for the screen to render.

A ViewModel may also have certain actions that are triggered the moment ViewModel is created.

## View

The View component is solely responsible for being able to display data on the UI. It can consume a view state which specifies what information should be rendered. Any UI events such as inputs, button clicks, gestures, etc will be passed to the ViewModel which determines what action needs to happen. 

If a screen has multiple complex views or multiple responsibilities, it's possible to reference multiple
ViewModel dependencies.

