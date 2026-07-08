/**
 * Interfaz que mapea exactamente el JSON que devuelve
 * el endpoint GET /api/envios/reporte del backend Spring Boot.
 */
export interface ReporteRepartidor {
  nombre:       string;
  totalEnvios:  number;
  totalKg:      number;
  zonas:        string[];
  costoTotal:   number;
}
