// Entorno de PRODUCCIÓN.
// En Render, el script scripts/generate-env.js reescribe este archivo antes
// del build usando la variable de entorno API_HOST (el host del backend,
// inyectado automáticamente vía "fromService" en render.yaml).
// Si compilas en local sin ese script, se usa este valor por defecto.
export const environment = {
  production: true,
  apiUrl: 'http://localhost:8080/api/envios/reporte',
};
