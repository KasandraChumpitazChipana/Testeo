package edu.pe.vallegrande.client.service;

import edu.pe.vallegrande.client.model.Cliente;
import edu.pe.vallegrande.client.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;  // Asegúrate de importar Flux

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Mono<Cliente> crearCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Mono<Cliente> obtenerCliente(Long id) {
        return clienteRepository.findById(id);
    }

    public Flux<Cliente> obtenerTodosClientes() {  // Este es el nuevo método
        return clienteRepository.findAll();
    }

    public Mono<Cliente> actualizarCliente(Long id, Cliente cliente) {
        return clienteRepository.findById(id)
                .flatMap(existingCliente -> {
                    existingCliente.setNombre(cliente.getNombre());
                    existingCliente.setDireccion(cliente.getDireccion());
                    existingCliente.setTelefono(cliente.getTelefono());
                    existingCliente.setEmail(cliente.getEmail());
                    existingCliente.setFechaNacimiento(cliente.getFechaNacimiento());
                    existingCliente.setStatus(cliente.getStatus());
                    return clienteRepository.save(existingCliente);
                });
    }

    public Mono<Void> eliminarCliente(Long id) {
        return clienteRepository.deleteById(id);
    }
}
