# 01-spec-language-selector

## Introduction/Overview

The Emerald Grove Veterinary Clinic application already supports internationalization (i18n) via Spring's `SessionLocaleResolver` and `LocaleChangeInterceptor`, but provides no UI control for users to switch languages. This feature adds a language selector dropdown to the global navigation header, allowing users to switch between English, Spanish, and German. The selector uses the existing `?lang=xx` mechanism — no backend changes are required.

## Goals

- Provide a visible, accessible language selector in the global header on all pages
- Allow users to switch between English (EN), Spanish (ES), and German (DE)
- Persist the selected language across page navigation within the same session
- Preserve the current page path when switching languages (no redirect to home)
- Meet WCAG accessibility standards for the new UI control

## User Stories

- **As a Spanish-speaking user**, I want to switch the UI to Spanish so that I can navigate the clinic application in my preferred language.
- **As a German-speaking user**, I want to find "Deutsch" in the language selector so that I can switch languages even when the current UI is in an unfamiliar language.
- **As a mobile user**, I want the language selector to be accessible within the hamburger menu so that I can change languages on any device.
- **As a user with assistive technology**, I want the language selector to be keyboard-navigable and screen-reader-friendly so that I can switch languages without a mouse.

## Demoable Units of Work

### Unit 1: Message Keys and Translation Parity

**Purpose:** Establish the localized labels for the language selector across all three supported locales, verified by the existing i18n sync test.

**Functional Requirements:**
- The system shall include message keys `nav.language.selector`, `nav.language.en`, `nav.language.es`, and `nav.language.de` in `messages.properties`
- The system shall include corresponding translations in `messages_es.properties` and `messages_de.properties`
- Language names shall be displayed as endonyms (self-named): "English", "Español", "Deutsch" — these are hardcoded in the template, not pulled from message keys, because endonyms do not change per locale
- The `nav.language.selector` key shall provide a localized accessible label for the dropdown button (e.g., "Language", "Idioma", "Sprache")

**Proof Artifacts:**
- JUnit: `I18nPropertiesSyncTest` passes with the new message keys, demonstrating translation parity across all locale files

### Unit 2: Language Selector Dropdown in Navbar

**Purpose:** Add a functional language selector dropdown to the global header that switches the UI language on all pages.

**Functional Requirements:**
- The system shall display a Bootstrap 5 dropdown in the navbar, positioned after the existing navigation links, inside the collapse div
- The dropdown toggle button shall show a globe icon (`fa-globe`) and the current locale code in uppercase (e.g., "EN", "ES", "DE")
- The dropdown toggle button shall use the standard Bootstrap `btn` size (not `btn-sm`) for WCAG 2.5.5 touch target compliance
- The dropdown toggle button shall use `btn-outline-light` styling to match the dark navbar theme
- The dropdown menu shall list three options: "English", "Español", "Deutsch" — each displayed as endonyms
- Each dropdown link shall include a `lang` attribute matching its locale (e.g., `lang="de"` on the "Deutsch" link) for correct screen reader pronunciation
- The active locale shall be indicated by a checkmark icon, the Bootstrap `active` class, and `aria-current="true"`
- Inactive locale items shall use `visibility:hidden` on the checkmark to preserve text alignment
- The dropdown toggle button shall have an `aria-label` driven by the `nav.language.selector` message key
- Clicking a language option shall append/replace `?lang=xx` on the current URL, preserving the current page path
- The selected language shall persist across navigation within the same session (via the existing `SessionLocaleResolver`)
- On mobile (collapsed navbar), the language selector shall appear within the hamburger menu below the nav links

**Proof Artifacts:**
- Playwright E2E: language selector button is visible in the navbar
- Playwright E2E: dropdown lists EN, ES, and DE options
- Playwright E2E: switching to Spanish updates nav text to Spanish translations
- Playwright E2E: switching to German updates nav text to German translations
- Playwright E2E: active locale shows checkmark and `aria-current`; inactive does not
- Playwright E2E: language selector button has an accessible `aria-label`
- Playwright E2E: switching language on a non-home page preserves the current page path
- Screenshots: same page shown in English and Spanish, attached to the PR

## Non-Goals (Out of Scope)

1. **Languages beyond EN/ES/DE**: The codebase has message files for FA, KO, PT, RU, TR, but these are not included in this feature. They can be added in a follow-up after translation quality is verified.
2. **RTL (right-to-left) support**: Persian (FA) is an RTL language and would require `dir="rtl"` on the HTML element. This is out of scope for the initial three-language set.
3. **Backend Java changes**: The `SessionLocaleResolver` and `LocaleChangeInterceptor` are already configured in `WebConfiguration.java`. No modifications needed.
4. **Fixing `messages_de.properties` encoding corruption**: Lines 7-8 have corrupted characters (`ung�ltiges Datum`). This is a pre-existing bug and should be fixed in a separate issue.
5. **Extracting the dropdown items into a Thymeleaf fragment**: The three `<li>` blocks are explicit and readable as-is. Fragment extraction is an optional future refactor.

## Design Considerations

- **Placement**: After the existing `<ul class="nav navbar-nav ms-auto">`, as a sibling element inside the collapse div — not inside the nav `<ul>`, because it is a utility control, not page navigation
- **Component type**: Bootstrap 5 dropdown (not inline links or button group) — scales for future language additions without crowding the navbar at intermediate breakpoints
- **Styling**: `btn-outline-light` harmonizes with the dark navbar; `dropdown-menu-end` prevents right-edge clipping
- **Active state**: Three simultaneous indicators — checkmark icon, `active` class (background change), and `aria-current="true"` — to avoid relying on color alone
- **Endonyms**: Language names are always displayed in their own language ("Español", not "Spanish") so users can find their language even when the current locale is unfamiliar
- **Responsive**: Selector appears inside the hamburger menu on mobile; verify no overflow at 320px width

## Repository Standards

- **Strict TDD**: All implementation must follow Red-Green-Refactor — failing tests before production code
- **I18n parity**: `I18nPropertiesSyncTest` enforces that all locale files have matching keys; adding keys to `messages.properties` without translations will fail this test (this is the natural RED gate)
- **Non-internationalized string check**: `checkNonInternationalizedStrings` scans HTML for bare text without `th:text` or `#{}` — all new markup must use Thymeleaf i18n attributes
- **E2E conventions**: Playwright tests in `e2e-tests/tests/features/`, page objects in `e2e-tests/tests/pages/`, imports use `@fixtures/base-test` and `@pages/*` aliases
- **Conventional commits**: Use conventional commit format for all commits

## Technical Considerations

- **URL mechanism**: `th:href="@{''(lang='xx')}"` appends `?lang=xx` to the current URL path. If this doesn't preserve the path correctly (redirects to `/`), the fallback is `th:href="@{${#httpServletRequest.requestURI}(lang='xx')}"` — a one-line swap
- **Locale detection**: `#locale.language` is available in Thymeleaf templates to detect the active locale and drive active state styling
- **Bootstrap JS**: `bootstrap.bundle.min.js` is already loaded in `layout.html` — dropdown functionality works out of the box with `data-bs-toggle="dropdown"`
- **`data-testid` attributes**: Add `data-testid="language-selector"`, `data-testid="lang-en"`, `data-testid="lang-es"`, `data-testid="lang-de"` for stable Playwright selectors
- **Font Awesome**: `fa-globe` and `fa-check` icons are available via the already-included Font Awesome CSS

## Security Considerations

No specific security considerations identified. The `?lang=xx` parameter is already handled by Spring's `LocaleChangeInterceptor` which validates locale values. No user input is persisted to the database.

## Success Metrics

1. **All existing tests pass**: No regressions in the full JUnit and Playwright suites
2. **I18nPropertiesSyncTest green**: New message keys present in all three locale files
3. **7 Playwright E2E tests pass**: Covering visibility, dropdown options, language switching (ES and DE), active state, accessibility, and path preservation
4. **WCAG compliance**: Zero new accessibility violations on the navbar (verified via Lighthouse or axe audit)

## Open Questions

No open questions at this time. All clarifying decisions have been resolved by the Watch Council review:
- Q1 (display names): Endonyms — resolved by UX best practice
- Q2 (button size): Standard `btn` — resolved by WCAG compliance
- Q3 (language set): EN/ES/DE only — resolved by Phil per issue scope
- Q4 (proof artifacts): Playwright + I18nPropertiesSyncTest + PR screenshots — resolved by layered verification strategy
