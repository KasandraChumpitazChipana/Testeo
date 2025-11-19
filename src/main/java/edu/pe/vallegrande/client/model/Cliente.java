package edu.pe.vallegrande.client.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Table("clientes")
public class Cliente {

    @Id
    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private LocalDateTime fechaRegistro;
    private LocalDate fechaNacimiento;
    private String status;
}

