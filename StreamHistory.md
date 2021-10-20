# Stream History

This page is a recording of all Twitch streams related to the TOA project.

* [Stream One - Project Creation](#stream-one---project-creation)
* [Stream Two - Developer Experience](#stream-two---developer-experience)
* [Stream Three - Design System](#stream-three---design-system)
* [Stream Four - Login Implementation](#stream-four---login-implementation)
* [Stream Five - Login Implementation Continued](#stream-five---login-implementation-continued)
* [Stream Six - Dependency Injection Setup](#stream-six---dependency-injection-setup)
* [Stream Seven - Documentation](#stream-seven---documentation)

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
MainActivity starts up. 

Date: October 13, 2021

Notes:
 * Hilt docs: https://developer.android.com/training/dependency-injection/hilt-android#setup

## Stream Seven - Documentation

In this stream, we document the application architecture to help onboard newcomers to the project.

Date: October 20, 2021

PRs:
  * Arch diagram: 
