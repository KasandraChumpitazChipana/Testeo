package edu.pe.vallegrande.client.service;

import edu.pe.vallegrande.client.model.Cliente;
import edu.pe.vallegrande.client.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
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
    void testCrearCliente_DeberiaGuardarCorrectamente() {
        // Arrange
        when(clienteRepository.save(any(Cliente.class))).thenReturn(Mono.just(clienteTest));

        // Act
        Mono<Cliente> resultado = clienteService.crearCliente(clienteTest);

        // Assert
        StepVerifier.create(resultado)
                .expectNext(clienteTest)
                .verifyComplete();

        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void testObtenerCliente_CuandoExiste_DeberiaRetornarCliente() {
        // Arrange
        when(clienteRepository.findById(1L)).thenReturn(Mono.just(clienteTest));

        // Act
        Mono<Cliente> resultado = clienteService.obtenerCliente(1L);

        // Assert
        StepVerifier.create(resultado)
                .expectNext(clienteTest)
                .verifyComplete();

        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    void testObtenerCliente_CuandoNoExiste_DeberiaRetornarVacio() {
        // Arrange
        when(clienteRepository.findById(999L)).thenReturn(Mono.empty());

        // Act
        Mono<Cliente> resultado = clienteService.obtenerCliente(999L);

        // Assert
        StepVerifier.create(resultado)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void testObtenerTodosClientes_DeberiaRetornarLista() {
        // Arrange
        Cliente cliente2 = new Cliente();
        cliente2.setId(2L);
        cliente2.setNombre("María García");
        cliente2.setEmail("maria@example.com");

        when(clienteRepository.findAll()).thenReturn(Flux.just(clienteTest, cliente2));

        // Act
        Flux<Cliente> resultado = clienteService.obtenerTodosClientes();

        // Assert
        StepVerifier.create(resultado)
                .expectNext(clienteTest)
                .expectNext(cliente2)
                .verifyComplete();

        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void testActualizarCliente_CuandoExiste_DeberiaActualizar() {
        // Arrange
        Cliente clienteActualizado = new Cliente();
        clienteActualizado.setNombre("Juan Pérez Actualizado");
        clienteActualizado.setDireccion("Nueva Dirección 456");
        clienteActualizado.setTelefono("999888777");
        clienteActualizado.setEmail("juannuevo@example.com");
        clienteActualizado.setFechaNacimiento(LocalDate.of(1990, 5, 15));
        clienteActualizado.setStatus("A");

        when(clienteRepository.findById(1L)).thenReturn(Mono.just(clienteTest));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(Mono.just(clienteActualizado));

        // Act
        Mono<Cliente> resultado = clienteService.actualizarCliente(1L, clienteActualizado);

        // Assert
        StepVerifier.create(resultado)
                .expectNextMatches(cliente -> 
                    cliente.getNombre().equals("Juan Pérez Actualizado") &&
                    cliente.getDireccion().equals("Nueva Dirección 456")
                )
                .verifyComplete();

        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void testActualizarCliente_CuandoNoExiste_DeberiaRetornarVacio() {
        // Arrange
        Cliente clienteActualizado = new Cliente();
        when(clienteRepository.findById(999L)).thenReturn(Mono.empty());

        // Act
        Mono<Cliente> resultado = clienteService.actualizarCliente(999L, clienteActualizado);

        // Assert
        StepVerifier.create(resultado)
                .expectNextCount(0)
                .verifyComplete();

        verify(clienteRepository, times(1)).findById(999L);
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void testEliminarCliente_DeberiaEliminarCorrectamente() {
        // Arrange
        when(clienteRepository.deleteById(1L)).thenReturn(Mono.empty());

        // Act
        Mono<Void> resultado = clienteService.eliminarCliente(1L);

        // Assert
        StepVerifier.create(resultado)
                .verifyComplete();

        verify(clienteRepository, times(1)).deleteById(1L);
    }

    @Test
    void testCrearCliente_ConDatosNulos_DeberiaFallar() {
        // Arrange
        Cliente clienteInvalido = new Cliente();
        when(clienteRepository.save(any(Cliente.class)))
                .thenReturn(Mono.error(new IllegalArgumentException("Datos inválidos")));

        // Act
        Mono<Cliente> resultado = clienteService.crearCliente(clienteInvalido);

        // Assert
        StepVerifier.create(resultado)
                .expectError(IllegalArgumentException.class)
                .verify();
    }
}