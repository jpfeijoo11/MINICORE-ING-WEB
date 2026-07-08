# MiniCore — Reporte de Envíos por Repartidor

Aplicación MVC full-stack para calcular el costo total de envíos por repartidor
dentro de un rango de fechas, aplicando la tarifa por kg de cada zona.

| Capa | Tecnología |
|------|-----------|
| Backend (Modelo + Controlador) | Spring Boot 3 · Spring Data JPA · H2 (en memoria) |
| Frontend (Vista + Controlador) | Angular 17 (Standalone Components) |

---

## Estructura del proyecto

```
MiniCore/
├── minicore-backend/          # Spring Boot
│   ├── pom.xml
│   └── src/main/java/com/minicore/
│       ├── MiniCoreApplication.java
│       ├── model/             # Entidades JPA (Zona, Repartidor, Envio)
│       ├── repository/        # Interfaces Spring Data JPA
│       ├── service/           # Lógica de negocio (EnvioService)
│       ├── controller/        # REST endpoint (EnvioController)
│       └── dto/               # Objeto de transferencia (ReporteRepartidorDTO)
│
└── minicore-frontend/         # Angular
    └── src/app/
        ├── app.component.*    # Vista principal + controlador Angular
        ├── services/          # EnvioService (HTTP)
        └── models/            # Interfaz ReporteRepartidor
```

---

## Cómo ejecutar

### 1. Backend — Spring Boot

**Requisitos:** Java 17+ y Maven 3.8+ instalados.

```bash
cd minicore-backend
./mvnw spring-boot:run          # Linux / Mac
mvnw.cmd spring-boot:run        # Windows
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

1. Abre http://localhost:4200 en el navegador.
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
