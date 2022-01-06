# Stream History

This page is a recording of all Twitch streams related to the TOA project.

If you're interested in following along in chronoligical order, check out these streams:

* [Stream One - Project Creation](#stream-one---project-creation)
* [Stream Two - Developer Experience](#stream-two---developer-experience)
* [Stream Three - Design System](#stream-three---design-system)
* [Stream Four - Login Implementation](#stream-four---login-implementation)
* [Stream Five - Login Implementation Continued](#stream-five---login-implementation-continued)
* [Stream Six - Dependency Injection Setup](#stream-six---dependency-injection-setup)
* [Stream Seven - Documentation](#stream-seven---documentation)
* [Stream Eight - Pixel 6 & Material 3](#stream-eight---pixel-6--material-3)
* [Stream Nine - Dev Experience & Kover Plugin](#stream-nine---dev-experience)
* [Stream Ten - Task List UI](#stream-ten---task-list-ui)
* [Stream Eleven - Navigation](#stream-eleven---navigation)
* [Stream Twelve - Task Management Logic & UI](#stream-twelve---task-management)

## Stream One - Project Creation

In the very first stream, we discussed the purpose of this project, created a repo,
and added a number of GitHub issues. 

Date: September 1, 2021

YouTube: https://youtu.be/8Umvlpx-Wvg

Blog: https://androidessence.com/toa-twitch-series

PRs: N/A

## Stream Two - Developer Experience

In this stream, we discuss a number of ways to help dev experience in Android like CI tooling and
static analysis. 

Date: September 8, 2021

YouTube: https://youtu.be/ePpbpLyYI1w

Blog: https://androidessence.com/essential-dev-experience-concepts-android

PRs:
  * GitHub Actions: https://github.com/AdamMc331/TOA/pull/27
  * Danger: https://github.com/AdamMc331/TOA/pull/29
  * Ktlint: https://github.com/AdamMc331/TOA/pull/30
  * Detekt: https://github.com/AdamMc331/TOA/pull/31
  * Git Hooks: https://github.com/AdamMc331/TOA/pull/32

## Stream Three - Design System

In this stream, we setup a design system for the application. We intended to add the app logo, but 
due to classic Android Studio issues we pivoted to also including the login UI. 

PRs:
  * Colors: https://github.com/AdamMc331/TOA/pull/35
  * Typography: https://github.com/AdamMc331/TOA/pull/36
  * Buttons: https://github.com/AdamMc331/TOA/pull/37
  * TextField: https://github.com/AdamMc331/TOA/pull/38
  * Login UI: https://github.com/AdamMc331/TOA/pull/39

Date: September 22, 2021

YouTube:
  * Design System: https://youtu.be/ilNi6Pl0FI0
  * Login UI: https://youtu.be/VvILEVD5zFE

## Stream Four - Login Implementation

In this stream, we design the app architecture and build out the necessary components to power
our login screen.

Date: September 29, 2021

PRs:
  * Use Case: https://github.com/AdamMc331/TOA/pull/40
  * Repository: https://github.com/AdamMc331/TOA/pull/41
  * View State Update: https://github.com/AdamMc331/TOA/pull/42
  * View Model: https://github.com/AdamMc331/TOA/pull/43

Notes:
  * https://proandroiddev.com/why-you-need-use-cases-interactors-142e8a6fe576 
  * https://proandroiddev.com/kotlin-extension-functions-more-than-sugar-1f04ca7189ff
  * https://handstandsam.com/2020/06/08/wrapping-mockito-mocks-for-reusability/

YouTube:
  * Use Cases: https://youtu.be/F4WajzYPh9s
  * Repository Layer: https://youtu.be/TWeu9fRNPWY
  * View State: https://youtu.be/3UNa6l0KlcU
  * View Model: https://youtu.be/pJOHI6Le3BE

## Stream Five - Login Implementation Continued

In this stream, we continue working on our login screen implementation.

Date: October 6, 2021

Notes:
  * https://github.com/Kotlin/kotlinx.coroutines/issues/1205

PRs:
  * InvalidCredentials Test: https://github.com/AdamMc331/TOA/pull/45
  * Login Input Errors: https://github.com/AdamMc331/TOA/pull/46

YouTube: https://youtu.be/mCQfK3J5K5w

## Stream Six - Dependency Injection Setup

In this stream, we implement the Hilt library and setup dependency injection for the login screen.
We also took that implementation and used it to display the LoginScreen when our
MainActivity starts up. At the end of all this, we tinker with the login UI and fix some weirdness
around inputs.

Date: October 13, 2021

Notes:
 * Hilt docs: https://developer.android.com/training/dependency-injection/hilt-android#setup

PRs:
  * Hilt: https://github.com/AdamMc331/TOA/pull/52
  * Login via MainActivity: https://github.com/AdamMc331/TOA/pull/53
  * Handle successful login flow: https://github.com/AdamMc331/TOA/pull/54
  * Handle Disabled Inputs: https://github.com/AdamMc331/TOA/pull/55/
  * Password input modifications: https://github.com/AdamMc331/TOA/pull/56

## Stream Seven - Documentation

In this stream, we document the application architecture to help onboard newcomers to the project.

After documentation we moved into more tinkering and features. We also began the process of building out the home screen. 

Date: October 20, 2021

PRs:
  * Arch diagram: https://github.com/AdamMc331/TOA/pull/57
  * Edge to edge UI:
  * Task list item: 
  * Task list:

## Stream Eight - Pixel 6 / Material 3

In this stream, we unboxed the new Pixel 6 Pro and added Material 3 to the application.

Date: October 28, 2021

YouTube: https://youtu.be/JGbI32wPR6k

PRs:
* Material 3: https://github.com/AdamMc331/TOA/pull/61

## Stream Nine - Dev Experience

In this stream, we refactored how dependency versions are managed, and added a plugin to help us identify out of date dependencies.

We also added the Kover plugin for generating test coverage.

YouTube:
* Dependency Updates: https://youtu.be/nCA32eZPS9k
* Kover Plugin: https://youtu.be/Oa31C5MgWL4

PRs:
* Dependency Updates: https://github.com/AdamMc331/TOA/pull/62
* Kover Plugin: https://github.com/AdamMc331/TOA/pull/63

Notes:
* Kover Video: https://www.youtube.com/watch?v=jNu5LY9HIbw
* Kover Repo: https://github.com/Kotlin/kotlinx-kover

## Stream Ten - Task List UI

In this stream, we finished porting over the task list to Material 3. We also finalized some more view 
states of the task list screen.

PRs:
* Material 3: https://github.com/AdamMc331/TOA/pull/64
* TaskListViewModel & Loading State: https://github.com/AdamMc331/TOA/pull/65
* GetAllTasksUseCase: https://github.com/AdamMc331/TOA/pull/67

## Stream Eleven - Navigation

In this stream, we added the Compose Destinations library and setup navigation between the login
screen and the task list screen. We also looked at adding some unit tests. 

PRs:
* Navigation: https://github.com/AdamMc331/TOA/pull/68
* Animation: https://github.com/AdamMc331/TOA/pull/69
* VM Unit tests: https://github.com/AdamMc331/TOA/pull/70

Notes:
* https://github.com/raamcosta/compose-destinations

## Stream Twelve - Task Management

In this stream, we started working on the task management logic and UI.

PRs:
* Use Cases: https://github.com/AdamMc331/TOA/pull/71
* Add Task UI: https://github.com/AdamMc331/TOA/pull/72
* Add Task VM & Navigation: https://github.com/AdamMc331/TOA/pull/73
* Date Picker: https://github.com/AdamMc331/TOA/pull/74
* Description Text Handling: https://github.com/AdamMc331/TOA/pull/75

Notes:
* https://vanpra.github.io/compose-material-dialogs/DateTimePicker/

## Stream Thirteen - Add Task Support

In this stream, we added support for adding a task and seeing it return on the home screen. 