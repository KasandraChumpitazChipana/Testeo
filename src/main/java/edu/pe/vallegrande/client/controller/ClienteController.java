package edu.pe.vallegrande.client.controller;

import edu.pe.vallegrande.client.model.Cliente;
import edu.pe.vallegrande.client.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Cliente> crearCliente(@RequestBody Cliente cliente) {
        return clienteService.crearCliente(cliente);
    }

    // MODIFICADO: Ahora maneja el caso cuando no existe
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Cliente>> obtenerCliente(@PathVariable Long id) {
        return clienteService.obtenerCliente(id)
                .map(cliente -> ResponseEntity.ok(cliente))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<Cliente> obtenerTodosClientes() {
        return clienteService.obtenerTodosClientes();
    }

    @PutMapping("/{id}")
    public Mono<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        return clienteService.actualizarCliente(id, cliente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> eliminarCliente(@PathVariable Long id) {
        return clienteService.eliminarCliente(id);
    }
}