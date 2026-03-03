# 01-tasks-language-selector

## Relevant Files

- `src/main/resources/messages/messages.properties` - Base English message keys. Add 4 new `nav.language.*` keys here.
- `src/main/resources/messages/messages_es.properties` - Spanish translations. Add corresponding `nav.language.*` keys.
- `src/main/resources/messages/messages_de.properties` - German translations. Add corresponding `nav.language.*` keys.
- `src/main/resources/templates/fragments/layout.html` - Global navbar template. Add the language selector dropdown markup after the existing nav `<ul>`.
- `e2e-tests/tests/features/language-selector.spec.ts` - **New file.** Playwright E2E test spec for the language selector (7 tests).
- `src/test/java/org/springframework/samples/petclinic/system/I18nPropertiesSyncTest.java` - Existing i18n sync test. No modifications needed — it automatically validates new keys are present in all locale files.
- `src/main/java/org/springframework/samples/petclinic/system/WebConfiguration.java` - Existing i18n config. No modifications needed — confirms `?lang=xx` mechanism and `SessionLocaleResolver` are already wired.
- `e2e-tests/tests/fixtures/base-test.ts` - Existing test fixture. Import `test` and `expect` from `@fixtures/base-test`.
- `e2e-tests/tests/pages/home-page.ts` - Existing page object. Use `HomePage.open()` to navigate to the home page in E2E tests.
- `docs/specs/01-spec-language-selector/01-proofs/` - **New directory.** Proof artifact screenshots stored here.

### Notes

- E2E tests use `@fixtures/base-test` for `test`/`expect` imports and `@pages/*` for page objects.
- Run Java tests with `./mvnw test` or `./mvnw test -Dtest=TestClassName` from the repo root.
- Run E2E tests with `cd e2e-tests && npm test` or `npm test -- --grep "pattern"`.
- The app starts automatically via Playwright's `webServer` config when running E2E tests.
- Follow conventional commit format: `feat:`, `test:`, `docs:` prefixes.
- Endonym labels ("English", "Español", "Deutsch") are hardcoded in the template, not driven by message keys — they do not change per locale.

## Tasks

### [x] 1.0 RED Phase: Write Failing Tests and Base Message Keys

Establish the TDD RED gate by writing the Playwright E2E test spec for the language selector (all tests fail because the markup doesn't exist yet) and adding the four new message keys to `messages.properties` only (causing `I18nPropertiesSyncTest` to fail because `messages_es.properties` and `messages_de.properties` are missing the keys).

#### 1.0 Proof Artifact(s)

- Test: `./mvnw test -Dtest=I18nPropertiesSyncTest` fails with "Missing keys in messages_es.properties" and "Missing keys in messages_de.properties" — demonstrates the RED gate for translation parity
- Test: `cd e2e-tests && npm test -- --grep "Language Selector"` fails with element-not-found errors for all 7 tests — demonstrates the RED gate for UI functionality
- CLI: `./mvnw test -Dtest=I18nPropertiesSyncTest` output captured showing specific missing key names demonstrates correct test targeting

#### 1.0 Tasks

- [x] 1.1 Confirm baseline: run `./mvnw test -Dtest=I18nPropertiesSyncTest` and verify it passes (establishes that the current state is green before we make it red)
- [x] 1.2 Add four new message keys to `src/main/resources/messages/messages.properties` at the end of the file: `nav.language.selector=Language`, `nav.language.en=English`, `nav.language.es=Spanish`, `nav.language.de=German`
- [x] 1.3 Run `./mvnw test -Dtest=I18nPropertiesSyncTest` and verify it **fails** with missing keys in `messages_es.properties` and `messages_de.properties` — this is the RED gate for translation parity
- [x] 1.4 Create the Playwright E2E test file `e2e-tests/tests/features/language-selector.spec.ts` with 7 tests: (1) language selector button is visible in navbar, (2) dropdown lists EN/ES/DE options, (3) switching to Spanish updates nav text, (4) switching to German updates nav text, (5) active locale shows checkmark and `aria-current`, inactive does not, (6) language selector button has accessible `aria-label`, (7) switching language on `/vets.html` preserves the page path. Use `data-testid` locators: `language-selector`, `lang-en`, `lang-es`, `lang-de`. Import from `@fixtures/base-test` and `@pages/home-page`.
- [x] 1.5 Run `cd e2e-tests && npm test -- --grep "Language Selector"` and verify all 7 tests **fail** with element-not-found errors — this is the RED gate for UI functionality

### [x] 2.0 GREEN Phase: Translations and Dropdown Markup

Make all tests pass by adding the translated message keys to `messages_es.properties` and `messages_de.properties` (turning `I18nPropertiesSyncTest` green), then adding the language selector dropdown markup to `layout.html` (turning the Playwright E2E tests green).

#### 2.0 Proof Artifact(s)

- Test: `./mvnw test -Dtest=I18nPropertiesSyncTest` passes — demonstrates translation parity across all three locale files
- Test: `cd e2e-tests && npm test -- --grep "Language Selector"` passes all 7 tests — demonstrates the language selector is visible, functional, accessible, and preserves page paths
- Test: `./mvnw test` full suite passes — demonstrates no regressions in existing JUnit tests
- Test: `cd e2e-tests && npm test` full suite passes — demonstrates no regressions in existing E2E tests

#### 2.0 Tasks

- [x] 2.1 Add translated message keys to `src/main/resources/messages/messages_es.properties` at the end of the file: `nav.language.selector=Idioma`, `nav.language.en=English`, `nav.language.es=Español`, `nav.language.de=Alemán`
- [x] 2.2 Add translated message keys to `src/main/resources/messages/messages_de.properties` at the end of the file: `nav.language.selector=Sprache`, `nav.language.en=English`, `nav.language.es=Spanisch`, `nav.language.de=Deutsch`
- [x] 2.3 Run `./mvnw test -Dtest=I18nPropertiesSyncTest` and verify it **passes** — translation parity restored
- [x] 2.4 Add the language selector dropdown markup to `src/main/resources/templates/fragments/layout.html` between line 68 (`</ul>`) and line 69 (`</div>`). The markup is a `<div class="nav-item dropdown ms-2">` containing: a `<button>` toggle with `btn btn-outline-light dropdown-toggle`, `data-bs-toggle="dropdown"`, `data-testid="language-selector"`, `th:aria-label="#{nav.language.selector}"`, a globe icon (`fa-globe`), and `th:text="${#locale.language.toUpperCase()}"`. Inside: a `<ul class="dropdown-menu dropdown-menu-end">` with three `<li>` items for EN/ES/DE, each with `data-testid="lang-xx"`, `lang="xx"`, endonym text ("English"/"Español"/"Deutsch"), `th:classappend` for `active`, `th:attr` for `aria-current`, and a checkmark icon (`fa-check`) with `visibility:hidden` when inactive.
- [x] 2.5 Run `cd e2e-tests && npm test -- --grep "Language Selector"` and verify all 7 tests **pass**. If the path-preservation test fails (redirects to `/` instead of staying on `/vets.html`), swap `th:href="@{''(lang='xx')}"` to `th:href="@{${#httpServletRequest.requestURI}(lang='xx')}"` in the template and re-run.
- [x] 2.6 Run `./mvnw test` (full Java suite) and verify no regressions. Pay attention to `checkNonInternationalizedStrings` — if it flags bare text in the new markup, ensure all visible text uses `th:text` or has `th:remove="all"`.
- [x] 2.7 Run `cd e2e-tests && npm test` (full E2E suite) and verify no regressions in existing specs.

### [ ] 3.0 Verification, Screenshots, and PR Readiness

Run the complete verification suite, capture proof screenshots, perform a manual accessibility check, and prepare the commit with conventional format.

#### 3.0 Proof Artifact(s)

- Screenshot: Home page in English showing the language selector with "EN" displayed — demonstrates default state
- Screenshot: Home page in Spanish showing the language selector with "ES" displayed and Spanish nav text — demonstrates language switching works
- Test: `./mvnw test` full suite green — demonstrates no Java test regressions
- Test: `cd e2e-tests && npm test` full suite green — demonstrates no E2E regressions
- CLI: Accessibility audit (Lighthouse or axe) showing zero new violations on navbar — demonstrates WCAG compliance

#### 3.0 Tasks

- [ ] 3.1 Create the proof artifacts directory: `docs/specs/01-spec-language-selector/01-proofs/`
- [ ] 3.2 Start the application (`./mvnw spring-boot:run`) and capture a screenshot of the home page in English (default state, showing the language selector with "EN" and globe icon). Save to `docs/specs/01-spec-language-selector/01-proofs/home-en.png`.
- [ ] 3.3 Switch to Spanish (`?lang=es`) and capture a screenshot of the home page showing the language selector with "ES" and Spanish nav text. Save to `docs/specs/01-spec-language-selector/01-proofs/home-es.png`.
- [ ] 3.4 Verify the dropdown at 320px viewport width (browser devtools) — confirm no horizontal overflow and the selector appears in the hamburger menu.
- [ ] 3.5 Run a Lighthouse accessibility audit (or inject axe-core) on the home page and verify zero new violations related to the navbar/language selector.
- [ ] 3.6 Tab through the navbar with keyboard only — verify the dropdown opens with Enter, arrow keys navigate options, Escape closes it.
- [ ] 3.7 Run the full test suites one final time: `./mvnw test` and `cd e2e-tests && npm test` — confirm all green.
- [ ] 3.8 Stage all changed/new files and commit with conventional format: `feat: add language selector to header (#1)`. Include the 5 changed/new source files. Do not commit proof screenshots unless the team convention requires it.
