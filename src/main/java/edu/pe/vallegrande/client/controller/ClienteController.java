package edu.pe.vallegrande.client.controller;

import edu.pe.vallegrande.client.model.Cliente;
import edu.pe.vallegrande.client.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;  // Importa Flux

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Crear cliente
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Cliente> crearCliente(@RequestBody Cliente cliente) {
        return clienteService.crearCliente(cliente);
    }

    // Obtener cliente por ID
    @GetMapping("/{id}")
    public Mono<Cliente> obtenerCliente(@PathVariable Long id) {
        return clienteService.obtenerCliente(id);
    }

    // Obtener todos los clientes
    @GetMapping
    public Flux<Cliente> obtenerTodosClientes() {  // Este es el nuevo endpoint
        return clienteService.obtenerTodosClientes();
    }

    // Actualizar cliente
    @PutMapping("/{id}")
    public Mono<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        return clienteService.actualizarCliente(id, cliente);
    }

    // Eliminar cliente
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> eliminarCliente(@PathVariable Long id) {
        return clienteService.eliminarCliente(id);
    }
}
