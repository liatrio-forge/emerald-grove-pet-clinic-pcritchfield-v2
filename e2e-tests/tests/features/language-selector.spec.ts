import { test, expect } from '@fixtures/base-test';

import { HomePage } from '@pages/home-page';

test.describe('Language Selector', () => {

  test('language selector button is visible in the navbar', async ({ page }) => {
    const home = new HomePage(page);
    await home.open();

    const selector = page.locator('[data-testid="language-selector"]');
    await expect(selector).toBeVisible();
  });

  test('dropdown lists EN, ES, and DE options', async ({ page }) => {
    const home = new HomePage(page);
    await home.open();

    await page.locator('[data-testid="language-selector"]').click();

    await expect(page.locator('[data-testid="lang-en"]')).toBeVisible();
    await expect(page.locator('[data-testid="lang-es"]')).toBeVisible();
    await expect(page.locator('[data-testid="lang-de"]')).toBeVisible();
  });

  test('switching to Spanish updates nav text', async ({ page }) => {
    const home = new HomePage(page);
    await home.open();

    await page.locator('[data-testid="language-selector"]').click();
    await page.locator('[data-testid="lang-es"]').click();

    await expect(
      page.locator('nav.navbar').getByText('Buscar propietarios')
    ).toBeVisible();
  });

  test('switching to German updates nav text', async ({ page }) => {
    const home = new HomePage(page);
    await home.open();

    await page.locator('[data-testid="language-selector"]').click();
    await page.locator('[data-testid="lang-de"]').click();

    await expect(
      page.locator('nav.navbar').getByText('Besitzer suchen')
    ).toBeVisible();
  });

  test('active locale shows checkmark and aria-current', async ({ page }) => {
    const home = new HomePage(page);
    await home.open();

    // Switch to ES so we have a known active state
    await page.locator('[data-testid="language-selector"]').click();
    await page.locator('[data-testid="lang-es"]').click();

    // Re-open the dropdown to inspect active state
    await page.locator('[data-testid="language-selector"]').click();

    const esItem = page.locator('[data-testid="lang-es"]');
    const enItem = page.locator('[data-testid="lang-en"]');

    await expect(esItem).toHaveClass(/active/);
    await expect(esItem).toHaveAttribute('aria-current', 'true');
    await expect(enItem).not.toHaveClass(/active/);
  });

  test('language selector button has accessible aria-label', async ({ page }) => {
    const home = new HomePage(page);
    await home.open();

    const button = page.locator('[data-testid="language-selector"]');
    await expect(button).toHaveAttribute('aria-label', /.+/);
  });

  test('switching language preserves the current page path', async ({ page }) => {
    await page.goto('/vets.html');

    await page.locator('[data-testid="language-selector"]').click();
    await page.locator('[data-testid="lang-es"]').click();

    await page.waitForURL(/vets/);
    expect(page.url()).toContain('/vets.html');
  });

});
