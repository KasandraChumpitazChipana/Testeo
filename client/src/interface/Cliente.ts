export interface Cliente {
  id: number;
  nombre: string;
  direccion: string;
  telefono: string;
  email: string;
  fecha_registro: string;     // También puede ser Date si lo parseas
  fecha_nacimiento: string;   // También puede ser Date si lo necesitas
  status: 'A' | 'I' | string; // Activo/Inactivo u otros valores si los manejas
}

