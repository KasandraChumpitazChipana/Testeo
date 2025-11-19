package edu.pe.vallegrande.client.repository;

import edu.pe.vallegrande.client.model.Cliente;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends R2dbcRepository<Cliente, Long> {
}
