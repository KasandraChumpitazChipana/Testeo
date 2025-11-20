import { test, expect } from '@playwright/test';

test('should display client management page', async ({ page }) => {
  await page.goto('/clients');
  await expect(page).toHaveTitle('Sistema de Gestión de Clientes');
});

test('should display client management form and list', async ({ page }) => {
  await page.goto('/clients');
  
  // Check if the client management form is present
  await expect(page.getByText('Nuevo Cliente')).toBeVisible();
  
  // Check if the clients list is present
  await expect(page.getByText('Lista de Clientes')).toBeVisible();
});

test('should have client form fields', async ({ page }) => {
  await page.goto('/clients');
  
  // Check if form fields are present
  await expect(page.locator('#nombre')).toBeVisible();
  await expect(page.locator('#direccion')).toBeVisible();
  await expect(page.locator('#telefono')).toBeVisible();
  await expect(page.locator('#email')).toBeVisible();
  await expect(page.locator('#fecha_nacimiento')).toBeVisible();
  await expect(page.locator('#status')).toBeVisible();
});

test('should allow filling client form', async ({ page }) => {
  await page.goto('/clients');
  
  // Fill the form
  await page.locator('#nombre').fill('Test Client');
  await page.locator('#direccion').fill('123 Test Street');
  await page.locator('#telefono').fill('123-456-7890');
  await page.locator('#email').fill('test@example.com');
  await page.locator('#fecha_nacimiento').fill('1990-01-01');
  
  // Verify values were set
  await expect(page.locator('#nombre')).toHaveValue('Test Client');
  await expect(page.locator('#direccion')).toHaveValue('123 Test Street');
  await expect(page.locator('#telefono')).toHaveValue('123-456-7890');
  await expect(page.locator('#email')).toHaveValue('test@example.com');
  await expect(page.locator('#fecha_nacimiento')).toHaveValue('1990-01-01');
});