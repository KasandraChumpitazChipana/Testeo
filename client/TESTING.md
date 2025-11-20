# Testing Setup

This project includes both Playwright and Selenium tests for end-to-end testing.

## Playwright Tests

Playwright tests are located in the [tests](./tests) directory.

### Running Playwright Tests

```bash
# Run all Playwright tests
npm run test:playwright

# Run Playwright tests in headed mode
npx playwright test --headed

# Run Playwright tests with UI
npx playwright test --ui
```

### Playwright Test Structure

- Test files are named with `.spec.ts` extension
- Tests are organized by feature/functionality
- Configuration is in [playwright.config.ts](./playwright.config.ts)

## Selenium Tests

Selenium tests are located in the [selenium-tests](./selenium-tests) directory.

### Running Selenium Tests

```bash
# Run all Selenium tests
npm run test:selenium

# Run Selenium tests in watch mode
npm run test:selenium -- --watch
```

### Selenium Test Structure

- Test files are named with `.test.ts` extension
- Tests use Jest as the test runner
- Configuration is in [jest.config.js](./jest.config.js)

## Writing Tests

### Playwright

```typescript
import { test, expect } from '@playwright/test';

test('should display page title', async ({ page }) => {
  await page.goto('/');
  await expect(page).toHaveTitle('Expected Title');
});
```

### Selenium

```typescript
import { Builder, By, until, WebDriver } from 'selenium-webdriver';

describe('Test Suite', () => {
  let driver: WebDriver;

  beforeAll(async () => {
    driver = await new Builder().forBrowser('chrome').build();
  });

  afterAll(async () => {
    await driver.quit();
  });

  test('should display page title', async () => {
    await driver.get('http://localhost:4200');
    const title = await driver.getTitle();
    expect(title).toBe('Expected Title');
  });
});
```