# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

CashWise is an offline-first personal finance app built with Kotlin Multiplatform + Compose Multiplatform, targeting Android, iOS, and Desktop (JVM). All UI and most logic is shared from `composeApp/src/commonMain`.

## Conventions

`AGENTS.md` is the authoritative contributor/architecture spec — **read it before structural work.** It defines the enforced package layout, feature co-location rules, layer boundaries, navigation placement, offline-first rules, feature-flag requirements, and the "feature done" definition. CLAUDE.md does not repeat those rules; it covers build/test mechanics and the wiring not obvious from a single file.

## Build & Test

The single Gradle module is `:composeApp`. iOS has no Gradle target for running; build it from `iosApp/` in Xcode.

```shell
./gradlew :composeApp:assembleDebug              # build Android APK
./gradlew :composeApp:run                        # run Desktop (JVM) app
./gradlew :composeApp:compileDebugKotlinAndroid  # fastest compile check for Android
./gradlew :composeApp:check                      # all verification incl. tests
./gradlew :composeApp:jvmTest                    # run commonTest suite (via JVM target)
```

Run a single test class/method:

```shell
./gradlew :composeApp:jvmTest --tests "com.shah.cashwise.ComposeAppCommonTest"
./gradlew :composeApp:jvmTest --tests "com.shah.cashwise.ComposeAppCommonTest.formatsIndianNumberGroups"
```

After touching code, run the smallest relevant command above; if a command can't be run locally, say so explicitly in the final report (per `AGENTS.md`).

## Architecture

**Platform entry points** all delegate to the single shared `App()` composable in `app/App.kt`:
- Android: `MainActivity` (androidMain)
- Desktop: `main.kt` (jvmMain), `mainClass = com.shah.cashwise.MainKt`
- iOS: `MainViewController()` (iosMain), consumed by the Xcode project in `iosApp/`

**App composition root** — `app/App.kt` wraps everything in `KoinApplication(modules = appModules)` then `CashWiseTheme`. This is where DI and theme are installed; it is also the NavHost call site per `AGENTS.md`.

**Dependency injection (Koin)** — `di/AppModules.kt` exposes `appModules`, a list of per-feature `module {}` blocks. Register a feature's ViewModel with `viewModelOf(::FooViewModel)` and add the module to the `appModules` list. Screens obtain ViewModels via `koinViewModel<FooViewModel>()`.

**MVI-style screens** — each feature under `ui/screens/<feature>/` co-locates `Screen`, `ViewModel`, `State`, and `Action`. Pattern (see `onboarding/`): the ViewModel holds a `MutableStateFlow<FooState>` exposed as `StateFlow`; the screen collects it with `collectAsState()` and routes UI events through a single `onAction(action): ...` entry point. State classes carry derived values as computed `val get()` properties (e.g. `OnboardingState.isLastPage`).

**Responsive layouts** — screens branch on window width via `BoxWithConstraints` into Compact (`< 600.dp`), Medium (`< 840.dp`), and Expanded breakpoints, with a separate layout composable per breakpoint (`OnboardingCompactLayout`, `OnboardingMediumLayout`, `OnboardingExpandedLayout`) — never one layout with nested `if`s.

**Theme / design system** — `ui/theme/`. `CashWiseTheme` supplies Material3 `ColorScheme` (`CashwiseLight`/`CashwiseDark`), `cashWiseTypography()`, and `CashWiseShapes`. Colors beyond the Material palette (e.g. `warning`) are provided through `LocalCashWiseSupportColors` and read via `CashWiseThemeTokens.supportColors`.

## Resources & Strings

User-facing strings are Compose resources, not Android resources: `composeApp/src/commonMain/composeResources/values/strings.xml` (localized variants in `values-<lang>/`). Drawables live in `composeResources/drawable/`. Access them through the generated `cashwise.composeapp.generated.resources.Res` object (`Res.string.*`, `Res.drawable.*`) with `stringResource(...)` / `painterResource(...)`. Never hardcode UI text in composables or ViewModels.

## Persistence

SQLDelight is configured (`CashWiseDatabase`, package `com.shah.cashwise.db`); `.sq` schema files and platform drivers (android/native/sqlite) are wired but the data layer is not yet implemented. Networking deps (Ktor, kotlinx.serialization) and image loading (Coil 3) are likewise set up but unused so far.

## Versions

All dependency versions are centralized in `gradle/libs.versions.toml` (Kotlin 2.3.0, Compose Multiplatform 1.10.0, Koin 4.1.1, Ktor 3.4.0, SQLDelight 2.0.2). Android minSdk 28 / target & compile SDK 36. JVM toolchain 11.
