import { Builder, By, until, WebDriver } from 'selenium-webdriver';
import chrome from 'selenium-webdriver/chrome';

describe('Client Management Tests', () => {
  let driver: WebDriver;

  beforeAll(async () => {
    // Set up the Chrome driver
    const options = new chrome.Options();
    // Remove headless mode for debugging, add it back for CI/production
    // options.addArguments('--headless'); // Run in headless mode
    
    driver = await new Builder()
      .forBrowser('chrome')
      .setChromeOptions(options)
      .build();
  }, 30000); // Increase timeout for driver setup

  afterAll(async () => {
    if (driver) {
      await driver.quit();
    }
  });

  test('should load the client management page', async () => {
    await driver.get('http://localhost:4200/clients');
    
    // Wait for the page to load
    await driver.wait(until.titleIs('Sistema de Gestión de Clientes'), 5000);
    
    const title = await driver.getTitle();
    expect(title).toBe('Sistema de Gestión de Clientes');
  });

  test('should display client management form', async () => {
    await driver.get('http://localhost:4200/clients');
    
    // Check if the client management form is present
    const formHeader = await driver.findElement(By.xpath("//h2[text()='Nuevo Cliente']"));
    expect(formHeader).toBeDefined();
    
    // Check if the clients list is present
    const listHeader = await driver.findElement(By.xpath("//h2[text()='Lista de Clientes']"));
    expect(listHeader).toBeDefined();
  });

  test('should have client form fields', async () => {
    await driver.get('http://localhost:4200/clients');
    
    // Check if form fields are present
    const nombreField = await driver.findElement(By.id('nombre'));
    expect(nombreField).toBeDefined();
    
    const direccionField = await driver.findElement(By.id('direccion'));
    expect(direccionField).toBeDefined();
    
    const telefonoField = await driver.findElement(By.id('telefono'));
    expect(telefonoField).toBeDefined();
    
    const emailField = await driver.findElement(By.id('email'));
    expect(emailField).toBeDefined();
    
    const fechaNacimientoField = await driver.findElement(By.id('fecha_nacimiento'));
    expect(fechaNacimientoField).toBeDefined();
    
    const statusField = await driver.findElement(By.id('status'));
    expect(statusField).toBeDefined();
  });
});