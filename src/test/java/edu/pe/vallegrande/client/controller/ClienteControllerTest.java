package edu.pe.vallegrande.client.controller;

import edu.pe.vallegrande.client.model.Cliente;
import edu.pe.vallegrande.client.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@WebFluxTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ClienteService clienteService;

    private Cliente clienteTest;

    @BeforeEach
    void setUp() {
        clienteTest = new Cliente();
        clienteTest.setId(1L);
        clienteTest.setNombre("Juan Pérez");
        clienteTest.setDireccion("Av. Principal 123");
        clienteTest.setTelefono("987654321");
        clienteTest.setEmail("juan@example.com");
        clienteTest.setFechaNacimiento(LocalDate.of(1990, 5, 15));
        clienteTest.setStatus("A");
    }

    @Test
    void testCrearCliente_DeberiaRetornar201() {
        // Arrange
        when(clienteService.crearCliente(any(Cliente.class))).thenReturn(Mono.just(clienteTest));

        // Act & Assert
        webTestClient.post()
                .uri("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(clienteTest)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.nombre").isEqualTo("Juan Pérez")
                .jsonPath("$.email").isEqualTo("juan@example.com");

        verify(clienteService, times(1)).crearCliente(any(Cliente.class));
    }

    @Test
    void testObtenerCliente_CuandoExiste_DeberiaRetornar200() {
        // Arrange
        when(clienteService.obtenerCliente(1L)).thenReturn(Mono.just(clienteTest));

        // Act & Assert
        webTestClient.get()
                .uri("/clientes/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.nombre").isEqualTo("Juan Pérez");

        verify(clienteService, times(1)).obtenerCliente(1L);
    }

    @Test
    void testObtenerCliente_CuandoNoExiste_DeberiaRetornar404() {
        // Arrange
        when(clienteService.obtenerCliente(999L)).thenReturn(Mono.empty());

        // Act & Assert
        webTestClient.get()
                .uri("/clientes/999")
                .exchange()
                .expectStatus().isNotFound();

        verify(clienteService, times(1)).obtenerCliente(999L);
    }

    @Test
    void testObtenerTodosClientes_DeberiaRetornarLista() {
        // Arrange
        Cliente cliente2 = new Cliente();
        cliente2.setId(2L);
        cliente2.setNombre("María García");
        cliente2.setEmail("maria@example.com");

        when(clienteService.obtenerTodosClientes()).thenReturn(Flux.just(clienteTest, cliente2));

        // Act & Assert
        webTestClient.get()
                .uri("/clientes")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Cliente.class)
                .hasSize(2);

        verify(clienteService, times(1)).obtenerTodosClientes();
    }

    @Test
    void testActualizarCliente_DeberiaRetornar200() {
        // Arrange
        Cliente clienteActualizado = new Cliente();
        clienteActualizado.setId(1L);
        clienteActualizado.setNombre("Juan Pérez Actualizado");
        clienteActualizado.setDireccion("Nueva Dirección");
        clienteActualizado.setTelefono("999888777");
        clienteActualizado.setEmail("juannuevo@example.com");
        clienteActualizado.setFechaNacimiento(LocalDate.of(1990, 5, 15));
        clienteActualizado.setStatus("A");

        when(clienteService.actualizarCliente(anyLong(), any(Cliente.class)))
                .thenReturn(Mono.just(clienteActualizado));

        // Act & Assert
        webTestClient.put()
                .uri("/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(clienteActualizado)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.nombre").isEqualTo("Juan Pérez Actualizado")
                .jsonPath("$.direccion").isEqualTo("Nueva Dirección");

        verify(clienteService, times(1)).actualizarCliente(anyLong(), any(Cliente.class));
    }

    @Test
    void testEliminarCliente_DeberiaRetornar204() {
        // Arrange
        when(clienteService.eliminarCliente(1L)).thenReturn(Mono.empty());

        // Act & Assert
        webTestClient.delete()
                .uri("/clientes/1")
                .exchange()
                .expectStatus().isNoContent();

        verify(clienteService, times(1)).eliminarCliente(1L);
    }

    @Test
    void testCrearCliente_ConDatosInvalidos_DeberiaRetornarError() {
        // Arrange
        Cliente clienteInvalido = new Cliente();
        when(clienteService.crearCliente(any(Cliente.class)))
                .thenReturn(Mono.error(new IllegalArgumentException("Datos inválidos")));

        // Act & Assert
        webTestClient.post()
                .uri("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(clienteInvalido)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testObtenerTodosClientes_CuandoEstaVacio_DeberiaRetornarListaVacia() {
        // Arrange
        when(clienteService.obtenerTodosClientes()).thenReturn(Flux.empty());

        // Act & Assert
        webTestClient.get()
                .uri("/clientes")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Cliente.class)
                .hasSize(0);
    }
}