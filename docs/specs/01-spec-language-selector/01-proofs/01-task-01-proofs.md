# Task 1.0 Proof Artifacts — RED Phase

## JUnit RED Gate: I18nPropertiesSyncTest

**Command:** `./mvnw test -Dtest=I18nPropertiesSyncTest`

**Result:** FAILURE (expected — RED gate)

```
Translation files are not in sync:
Missing keys in messages_tr.properties:
  nav.language.de
  nav.language.en
  nav.language.es
  nav.language.selector
Missing keys in messages_pt.properties:
  nav.language.de
  nav.language.en
  nav.language.es
  nav.language.selector
Missing keys in messages_ru.properties:
  nav.language.de
  nav.language.en
  nav.language.es
  nav.language.selector
Missing keys in messages_es.properties:
  nav.language.de
  nav.language.en
  nav.language.es
  nav.language.selector
Missing keys in messages_de.properties:
  nav.language.de
  nav.language.en
  nav.language.es
  nav.language.selector
Missing keys in messages_fa.properties:
  nav.language.de
  nav.language.en
  nav.language.es
  nav.language.selector
Missing keys in messages_ko.properties:
  nav.language.de
  nav.language.en
  nav.language.es
  nav.language.selector

Tests run: 2, Failures: 1, Errors: 0, Skipped: 0
BUILD FAILURE
```

**Demonstrates:** Adding `nav.language.*` keys to `messages.properties` without corresponding translations in locale files correctly triggers the I18n sync test failure.

## Playwright RED Gate: Language Selector E2E Tests

**Command:** `cd e2e-tests && npx playwright test --grep "Language Selector"`

**Result:** 7 failed (expected — RED gate)

```
  7 failed
    [chromium] › language-selector.spec.ts:7  › Language Selector › language selector button is visible in the navbar
    [chromium] › language-selector.spec.ts:15 › Language Selector › dropdown lists EN, ES, and DE options
    [chromium] › language-selector.spec.ts:26 › Language Selector › switching to Spanish updates nav text
    [chromium] › language-selector.spec.ts:38 › Language Selector › switching to German updates nav text
    [chromium] › language-selector.spec.ts:50 › Language Selector › active locale shows checkmark and aria-current
    [chromium] › language-selector.spec.ts:69 › Language Selector › language selector button has accessible aria-label
    [chromium] › language-selector.spec.ts:77 › Language Selector › switching language preserves the current page path
```

**Demonstrates:** All 7 E2E tests fail because the language selector markup does not yet exist in `layout.html`. Tests fail for the correct reason (element not found).
