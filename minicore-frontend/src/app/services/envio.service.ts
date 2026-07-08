import { Injectable }       from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable }       from 'rxjs';
import { ReporteRepartidor } from '../models/reporte.model';
import { environment }      from '../../environments/environment';

/**
 * SERVICE (Angular) — encapsula la comunicación HTTP con el backend.
 *
 * Llama a:  GET {environment.apiUrl}
 * Parámetros: fechaInicio=YYYY-MM-DD  fechaFin=YYYY-MM-DD
 *
 * La URL base viene de environment.ts (dev) / environment.prod.ts (build de
 * producción, generado automáticamente en Render — ver scripts/generate-env.js).
 */
@Injectable({ providedIn: 'root' })
export class EnvioService {

  private readonly apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  /**
   * Solicita el reporte de envíos al backend para el rango indicado.
   * @param fechaInicio string en formato YYYY-MM-DD
   * @param fechaFin    string en formato YYYY-MM-DD
   */
  getReporte(fechaInicio: string, fechaFin: string): Observable<ReporteRepartidor[]> {
    const params = new HttpParams()
      .set('fechaInicio', fechaInicio)
      .set('fechaFin',    fechaFin);

    return this.http.get<ReporteRepartidor[]>(this.apiUrl, { params });
  }
}
