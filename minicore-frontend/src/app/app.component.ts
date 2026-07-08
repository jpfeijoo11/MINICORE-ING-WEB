import { Component }           from '@angular/core';
import { CommonModule }         from '@angular/common';
import { FormsModule }          from '@angular/forms';
import { EnvioService }         from './services/envio.service';
import { ReporteRepartidor }    from './models/reporte.model';

/**
 * COMPONENTE PRINCIPAL — vista única de la aplicación MiniCore.
 *
 * Patrón MVC en Angular:
 *  - Template (HTML)  →  Vista
 *  - Component (TS)   →  Controlador
 *  - EnvioService     →  Modelo / capa de datos
 */
@Component({
  selector:    'app-root',
  standalone:  true,
  imports:     [CommonModule, FormsModule],
  templateUrl: './app.component.html',
  styleUrls:   ['./app.component.css']
})
export class AppComponent {

  // ── Campos del formulario ────────────────────────────────────
  fechaInicio: string = '2025-05-01';
  fechaFin:    string = '2025-05-31';

  // ── Estado de la vista ───────────────────────────────────────
  reporte:    ReporteRepartidor[] = [];
  cargando:   boolean = false;
  error:      string  = '';
  consultado: boolean = false;

  constructor(private envioService: EnvioService) {}

  /** Calcula el total general de la columna Costo Total */
  get granTotal(): number {
    return this.reporte.reduce((sum, r) => sum + r.costoTotal, 0);
  }

  /** Devuelve las zonas del repartidor como texto separado por comas */
  zonasTexto(r: ReporteRepartidor): string {
    return r.zonas.length > 0 ? r.zonas.join(', ') : '—';
  }

  /**
   * Llama al servicio para obtener el reporte y actualiza la vista.
   * Este método actúa como Controlador: recibe la acción del usuario,
   * delega al Servicio (Modelo) y actualiza la Vista con los datos.
   */
  buscar(): void {
    if (!this.fechaInicio || !this.fechaFin) {
      this.error = 'Por favor ingresa ambas fechas.';
      return;
    }
    if (this.fechaFin < this.fechaInicio) {
      this.error = 'La fecha fin no puede ser anterior a la fecha inicio.';
      return;
    }

    this.cargando   = true;
    this.error      = '';
    this.reporte    = [];
    this.consultado = false;

    this.envioService.getReporte(this.fechaInicio, this.fechaFin).subscribe({
      next: (data) => {
        this.reporte    = data;
        this.cargando   = false;
        this.consultado = true;
      },
      error: (err) => {
        console.error(err);
        this.error    = 'No se pudo conectar al servidor. Verifica que el backend esté corriendo en localhost:8080.';
        this.cargando = false;
      }
    });
  }
}
