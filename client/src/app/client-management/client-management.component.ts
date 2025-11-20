import { Component, OnInit } from '@angular/core';
import { ClienteService } from '../../service/ClienteService';
import { Cliente, ClienteCreate } from '../../interface/Cliente';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-client-management',
  templateUrl: './client-management.component.html',
  styleUrl: './client-management.component.css'
})
export class ClientManagementComponent implements OnInit {
  clientes: Cliente[] = [];
  cliente: ClienteCreate = {
    nombre: '',
    direccion: '',
    telefono: '',
    email: '',
    fecha_registro: new Date().toISOString(),
    fecha_nacimiento: '',
    status: 'A'
  };
  clienteEdit: Cliente = {
    id: 0,
    nombre: '',
    direccion: '',
    telefono: '',
    email: '',
    fecha_registro: new Date().toISOString(),
    fecha_nacimiento: '',
    status: 'A'
  };
  isEditing = false;

  constructor(private clienteService: ClienteService) {}

  ngOnInit(): void {
    this.loadClientes();
  }

  loadClientes(): void {
    this.clienteService.obtenerTodosClientes().subscribe(
      (data: Cliente[]) => {
        this.clientes = data;
      },
      (error) => {
        console.error('Error loading clients', error);
      }
    );
  }

  saveCliente(): void {
    if (this.isEditing) {
      this.clienteService.actualizarCliente(this.clienteEdit.id, this.clienteEdit).subscribe(
        (data: Cliente) => {
          this.loadClientes();
          this.resetForm();
        },
        (error) => {
          console.error('Error updating client', error);
        }
      );
    } else {
      // When creating, we use cliente which doesn't have an ID
      this.clienteService.crearCliente(this.cliente).subscribe(
        (data: Cliente) => {
          this.loadClientes();
          this.resetForm();
        },
        (error) => {
          console.error('Error creating client', error);
        }
      );
    }
  }

  editCliente(cliente: Cliente): void {
    this.clienteEdit = { ...cliente };
    this.isEditing = true;
  }

  deleteCliente(id: number): void {
    if (confirm('¿Está seguro de eliminar este cliente?')) {
      this.clienteService.eliminarCliente(id).subscribe(
        () => {
          this.loadClientes();
        },
        (error) => {
          console.error('Error deleting client', error);
        }
      );
    }
  }

  resetForm(): void {
    this.cliente = {
      nombre: '',
      direccion: '',
      telefono: '',
      email: '',
      fecha_registro: new Date().toISOString(),
      fecha_nacimiento: '',
      status: 'A'
    };
    this.clienteEdit = {
      id: 0,
      nombre: '',
      direccion: '',
      telefono: '',
      email: '',
      fecha_registro: new Date().toISOString(),
      fecha_nacimiento: '',
      status: 'A'
    };
    this.isEditing = false;
  }
}