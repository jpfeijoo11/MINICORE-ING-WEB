# MiniCore — Reporte de Envíos por Repartidor

Aplicación full-stack que calcula el costo total de envíos por repartidor
dentro de un rango de fechas, aplicando la tarifa por kg de cada zona.

**MVC utilizado:** Spring MVC (backend) + Angular (frontend). Cada capa
implementa su propia separación Modelo / Vista / Controlador — ver detalle
más abajo.

**Video explicativo:** [PENDIENTE — agregar link a Loom/YouTube]

**Proyecto deployado:** [PENDIENTE — agregar URL del frontend en Render]

| Capa | Tecnología |
|------|-----------|
| Backend (Modelo + Controlador) | Spring Boot 3 · Spring Data JPA · H2 (en memoria) |
| Frontend (Vista + Controlador) | Angular 17 (Standalone Components) |

---

## Patrón MVC aplicado

### Backend — Spring MVC

| Rol MVC | Carpeta / clase | Responsabilidad |
|---------|------------------|------------------|
| **Modelo** | `model/` (`Zona`, `Repartidor`, `Envio`) + `repository/` | Entidades JPA y acceso a datos (Spring Data JPA sobre H2) |
| **Modelo (lógica de negocio)** | `service/EnvioService.java` | Filtra envíos por rango de fechas y calcula el costo por repartidor |
| **Controlador** | `controller/EnvioController.java` | Expone el endpoint REST, recibe parámetros, delega al service y responde en JSON |
| **Vista** | `dto/ReporteRepartidorDTO.java` (serializado a JSON) | Representación de salida que consume el frontend |
| Config transversal | `config/CorsConfig.java` | Permite que el frontend (otro origen/dominio) llame a la API |

### Frontend — Angular

| Rol MVC | Archivo | Responsabilidad |
|---------|---------|------------------|
| **Vista** | `app.component.html` / `.css` | Formulario de fechas, botón Consultar y tabla de resultados |
| **Controlador** | `app.component.ts` | Recibe la acción del usuario, valida, llama al servicio y actualiza el estado de la vista |
| **Modelo (capa de datos)** | `services/envio.service.ts` + `models/reporte.model.ts` | Encapsula la llamada HTTP a la API y tipa la respuesta |

---

## Estructura del proyecto

```
MiniCore/
├── render.yaml                 # Blueprint de deploy (Render)
├── minicore-backend/           # Spring Boot
│   ├── pom.xml
│   ├── Dockerfile
│   └── src/main/java/com/minicore/
│       ├── MiniCoreApplication.java
│       ├── model/              # Entidades JPA (Zona, Repartidor, Envio)
│       ├── repository/         # Interfaces Spring Data JPA
│       ├── service/            # Lógica de negocio (EnvioService)
│       ├── controller/         # REST endpoint (EnvioController)
│       ├── dto/                # Objeto de transferencia (ReporteRepartidorDTO)
│       └── config/             # CorsConfig
│
└── minicore-frontend/          # Angular
    └── src/
        ├── app/
        │   ├── app.component.*  # Vista + controlador Angular
        │   ├── services/        # EnvioService (HTTP)
        │   └── models/          # Interfaz ReporteRepartidor
        └── environments/        # apiUrl por entorno (dev / prod)
```

---

## Cómo ejecutar en local

### 1. Backend — Spring Boot

**Requisitos:** Java 17+ y Maven 3.8+ instalados.

```bash
cd minicore-backend
mvn spring-boot:run
```

> El servidor arranca en **http://localhost:8080**
> Consola H2 disponible en **http://localhost:8080/h2-console**
> (JDBC URL: `jdbc:h2:mem:minicore`, usuario: `sa`, contraseña: vacía)

### 2. Frontend — Angular

**Requisitos:** Node.js 18+ y npm instalados.

```bash
cd minicore-frontend
npm install
npm start                       # o: ng serve
```

> La aplicación abre en **http://localhost:4200**

---

## Cómo usar

1. Abre la app (local: http://localhost:4200, o el link deployado).
2. Ingresa una **Fecha Inicio** y una **Fecha Fin**.
3. Haz clic en **Consultar**.
4. La tabla muestra por cada repartidor: envíos, kg totales, zona(s) y costo total.

**Rango de prueba del seed data:** `2025-05-01` → `2025-05-31`
Resultado esperado: Andrés $48.00 · Camila $36.00 · Luis sin envíos.

---

## Lógica del cálculo (Service)

```
Para cada repartidor:
  Filtrar envíos donde fechaEnvio BETWEEN fechaInicio AND fechaFin
  Para cada envío:
    costo_envio = peso_kg × tarifa_por_kg (de la zona del envío)
  costoTotal = Σ costo_envio
```

---

## Datos de prueba (seed)

| Tabla | Registros |
|-------|-----------|
| Zona  | Norte ($1.50/kg), Sur ($2.00/kg), Centro ($1.75/kg) |
| Repartidor | Andrés, Camila, Luis |
| Envio | 11 envíos en distintas fechas (mayo, abril y junio 2025) |

---

## Endpoint REST

```
GET /api/envios/reporte?fechaInicio=YYYY-MM-DD&fechaFin=YYYY-MM-DD

Respuesta (JSON):
[
  {
    "nombre":      "Andrés",
    "totalEnvios": 5,
    "totalKg":     32.0,
    "zonas":       ["Norte"],
    "costoTotal":  48.0
  },
  ...
]
```

---

## Deploy

El proyecto incluye `render.yaml` para desplegar ambos servicios en
[Render](https://render.com) con un solo clic (**New + → Blueprint**):

- `minicore-backend`: web service Docker (ver `minicore-backend/Dockerfile`).
- `minicore-frontend`: sitio estático, compilado con Angular. El script
  `minicore-frontend/scripts/generate-env.js` arma automáticamente la URL
  real del backend (`API_HOST`) antes del build, así los dos servicios
  quedan conectados sin editar nada a mano.
