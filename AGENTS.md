# AGENTS.md

## Scope

This file defines contributor and coding-agent rules for this repository.
Rules apply repo-wide, with primary implementation focus in `composeApp`.

## Project Context

This is a Kotlin Multiplatform project with Compose Multiplatform UI.
Key modules and roots:

- `composeApp/` shared KMP app module (main implementation surface)
- `androidApp/` Android-specific wrapper/entry setup
- `iosApp/` iOS app entrypoint and Xcode project

## Canonical Common Structure (Enforced)

For shared code, place new files under:
`composeApp/src/commonMain/kotlin/com/shah/cashwise`

Use this package layout as canonical:

- `app`: app root composition and top-level app wiring (shell, tabs, top bars, NavHost call site)
- `core`: cross-cutting helpers
- `core/extensions`: extension functions
- `core/utils`: utility helpers (including app-wide constants, feature flags if needed)
- `data`: data layer
- `data/local`: local storage/data sources
- `data/remote`: network/remote data sources
- `data/mapper`: DTO/entity/domain mappers
- `data/repo`: repository implementations
- `data/sync`: background sync/coordinator logic
- `domain`: business layer
- `domain/model`: domain models
- `domain/repo`: repository contracts/interfaces
- `domain/rules`: business rules/use-cases
- `di`: dependency injection modules
- `navigation`: routes and navigation graph wiring
- `ui`: presentation layer
- `ui/components`: reusable UI components ONLY (no navigation, no business logic)
- `ui/screens`: screen composables organized by feature
- `ui/theme`: design system, colors, typography, theme setup

Do not create parallel alternative top-level layers unless explicitly requested.

## Feature Co-location (Enforced)

Each feature must be self-contained under `ui/screens/<feature>/` and must co-locate its ViewModel and state types with the screen:

- `ui/screens/<feature>/`
  - `<Feature>Screen.kt`
  - `<Feature>ViewModel.kt`
  - `<Feature>State.kt`
  - `<Feature>Action.kt` (or `Event.kt`)
  - `<Feature>Effect.kt` (one-shot events, optional)
  - feature-specific UI helpers (optional, keep minimal)

Do NOT create a separate `ui/viewmodels` folder for feature ViewModels.

## Source Set Placement

- Put cross-platform logic/UI in `commonMain`.
- Put platform-specific integrations only in target source sets:
  - `composeApp/src/androidMain`
  - `composeApp/src/iosMain`
  - `composeApp/src/jvmMain`
- Keep iOS app bootstrap and native wiring in `iosApp/`.

## Layer Boundaries

- Define repository interfaces in `domain/repo`.
- Implement repository interfaces in `data/repo`.
- Keep mapping logic in `data/mapper`.
- Keep business rules in `domain/rules`.
- Keep route definitions and navigation orchestration in `navigation`.
- Keep reusable UI pieces in `ui/components`, not in screen files.
- Avoid leaking `data/*` types into `ui/*` directly; map to domain models.

## Navigation Placement (Enforced)

- Route constants live in `navigation/Destinations.kt` (or `Routes.kt`).
- Navigation graph builder functions live in `navigation/NavGraph.kt` (or similar).
- The app shell (tabs, top bars) and NavHost call site live in `app/App.kt`.
- Screens should not hardcode route strings; they should call typed navigation helpers or use `Destinations`.

## Global App Shell (Enforced)

Mobile:
- Bottom tabs: Home, Transactions, Budgets, Insights (4 tabs).
- Top-right: Avatar/Initial button opens Account/Settings sheet.
- Wallet selection is accessible from the top bar (or within filters where appropriate).

Tablet/Desktop:
- Left navigation rail/drawer mirrors the same top-level destinations.
- Keep screen content max widths sane (avoid stretched text).

## Responsive Layout (Enforced)

- Use window-size breakpoints for layout variants:
  - Compact: stacked layout
  - Medium: stacked with maxWidth constraints (avoid stretched UI)
  - Expanded: split layout (visual/content) where appropriate
- Do not create one mega-layout with many nested ifs; prefer separate layout composables per breakpoint.

## Offline-first Rules (Enforced)

- The app must be usable without sign-in.
- "Continue Offline" is a first-class path; do not gate core flows behind auth.
- Sync-related UI must be behind feature flags and/or only shown when sync is enabled.
- Never assume network availability; handle offline states gracefully.

## Strings & Localization (Enforced)

- Do not hardcode user-facing strings in composables or ViewModels.
- Use Compose resources for strings:
  - `composeApp/src/commonMain/composeResources/values/strings.xml`
  - localized variants: `values-hi/strings.xml`, etc.
- Use placeholders for formatted strings (avoid string concatenation for UI text).

## Constants & Extensions

- App-wide constants (rare) go in `core/utils/Constants.kt`.
- Feature flags go in `core/utils/FeatureFlags.kt` and are read from settings in `data/local`.
- Feature-specific constants should live inside the feature package (e.g., `ui/screens/transactions/TransactionConstants.kt`).
- Extension functions go in `core/extensions/` (e.g., `StringExtensions.kt`).
- UI-only extensions (e.g., `Modifier` helpers) may live under `ui/components/` if truly UI-specific.

## Feature Flags (Enforced)

- Any of these must be behind a feature flag:
  - Sync engine / background sync
  - Shared wallets / invites
  - Attachment syncing (if implemented)
- Flags live in `core/utils/FeatureFlags.kt` and are read from settings in `data/local`.

## Shared Wallet Discipline (Enforced)

- For shared wallet actions (create/edit/delete/restore transaction, member changes), record an audit event.
- Role-based gating must be enforced in UI and in domain rules (not just UI).

## Feature Completion Definition

A feature is considered "done" only when it includes:
- UI screen(s) + ViewModel + State
- Repository integration (or stubbed interface if explicitly requested)
- Empty/loading/error states
- Basic validation and edge cases
- At least minimal tests for pure logic / repository methods where applicable

## UI Components Rules (Enforced)

- `ui/components` must contain only stateless/reusable composables.
- No repository calls, no navigation, no persistence access in components.
- Components accept state via parameters and emit events via callbacks.

## Build and Validation

Before finishing work, run the smallest relevant command for touched areas:

- `./gradlew :composeApp:compileDebugKotlinAndroid`
- `./gradlew :composeApp:assembleDebug`
- `./gradlew :composeApp:check`

If a command cannot be run locally, state that explicitly in the final report.

## Change Safety Rules

- Keep changes task-scoped and architecture-consistent.
- Do not refactor unrelated code in the same change unless requested.
- Never revert unrelated user changes.
- Never use destructive git commands unless explicitly requested.

## Reporting Expectations

When reporting completed work, include:

- files changed
- commands run
- pass/fail status
- unresolved limitations or follow-ups