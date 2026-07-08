// Genera src/environments/environment.prod.ts en build time usando la
// variable de entorno API_HOST (host del backend en Render, sin esquema).
// Se ejecuta como parte del buildCommand definido en render.yaml, ANTES de
// `ng build`. Si API_HOST no está definida (build local), no toca el archivo
// y se usa el valor por defecto de environment.prod.ts.
const fs = require('fs');
const path = require('path');

const apiHost = process.env.API_HOST;

if (!apiHost) {
  console.log('[generate-env] API_HOST no definido, se usa environment.prod.ts existente.');
  process.exit(0);
}

const apiUrl = `https://${apiHost}/api/envios/reporte`;
const outPath = path.join(__dirname, '..', 'src', 'environments', 'environment.prod.ts');

const content = `// Generado automáticamente por scripts/generate-env.js durante el build de Render.
export const environment = {
  production: true,
  apiUrl: '${apiUrl}',
};
`;

fs.writeFileSync(outPath, content);
console.log(`[generate-env] environment.prod.ts generado con apiUrl=${apiUrl}`);
