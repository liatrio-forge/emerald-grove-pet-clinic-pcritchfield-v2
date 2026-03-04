# 01 Questions Round 1 - Language Selector

Please answer each question below (select one or more options, or add your own notes). Feel free to add additional context under any question.

## 1. Language Display Names

How should each language option be labeled in the dropdown?

- [ ] (A) **Endonym (self-named)**: Always show each language in its own language regardless of current locale — "English", "Español", "Deutsch". This is common in browser/OS settings and helps users find their language even if the current locale is unfamiliar.
- [ ] (B) **Translated to current locale**: Show language names translated into the active language — e.g., when in Spanish, German shows as "Alemán". This is consistent with fully-localized UIs.
- [ ] (C) Other (describe)

## 2. Button Size vs. Accessibility

The dropdown toggle button in the navbar can be sized two ways. Adorabelle flagged a WCAG touch target concern.

- [ ] (A) **`btn-sm` (small)**: Compact, visually subtle, fits the navbar without standing out. Touch target is ~30px, below the 44px WCAG 2.5.5 recommendation — fine for desktop, borderline on mobile.
- [ ] (B) **`btn` (regular)**: Larger touch target (~36-40px), closer to WCAG recommendation. Slightly more prominent in the navbar.
- [ ] (C) Other (describe)

## 3. Initial Language Set

The issue specifies EN/ES/DE as the initial set. The codebase already has message files for additional locales (FA, KO, PT, RU, TR). Should the selector include only the three, or expose all available locales?

- [ ] (A) **EN/ES/DE only**: Keep it small as the issue specifies. Additional languages can be added later.
- [ ] (B) **All available locales**: Include every language that has a `messages_xx.properties` file. More complete but larger dropdown.
- [ ] (C) Other (describe)

## 4. Proof Artifacts

What proof artifacts should demonstrate this feature works? (Select all that apply)

- [ ] (A) **Playwright E2E tests**: Automated tests that switch languages and verify UI text changes, active state, path preservation, and accessibility attributes.
- [ ] (B) **Screenshots**: Side-by-side screenshots showing the same page in two different languages.
- [ ] (C) **JUnit test**: The existing `I18nPropertiesSyncTest` passes with the new message keys (verifies translation parity).
- [ ] (D) Other (describe)
