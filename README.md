# Simulacro de examen práctico RA5 y RA6 (UT4 y UT5) · API REST de una tienda de videojuegos

**DWES · 2.º DAW** · **3 horas** · IntelliJ IDEA, y Postman o Bruno · Se evalúan **RA5** (UT4) y **RA6** (UT5), cada uno sobre 10.

**Material permitido:** la chuleta de Spring Boot (UT4 y UT5) impresa. Nada más.

## 1. Qué tienes que hacer

Se te da un proyecto que **compila y arranca**:

- el módulo de **estudios** está hecho, menos dos detalles de la relación (tarea 1);
- del módulo de **videojuegos** está la estructura y lo que no se evalúa.

Tienes que terminar el módulo de videojuegos: la entidad, el repositorio, los DTO de entrada, una excepción, el servicio, el controlador y 8 pruebas.

### Antes de empezar (5 minutos)

| Paso | Qué haces | Lo que tiene que pasar |
|:-:|---|---|
| 1 | En IntelliJ, *File → Open* → la carpeta `tienda` (la que tiene el `pom.xml`) | Maven descarga las dependencias |
| 2 | Si IntelliJ pregunta por *annotation processing*, **Enable** | Lombok funciona |
| 3 | Abre `TiendaApplication` y pulsa ▶ | En la consola: `Tomcat started on port 8080` |
| 4 | Importa `tienda.postman_collection.json`: en Postman, *Import*; en Bruno, *Import Collection → Postman Collection* | Aparecen dos peticiones de ejemplo |
| 5 | Lanza `Ejemplo · ping` y `Ejemplo · listar estudios` | `200` y `La API de videojuegos responde` · `200` y 4 estudios |

Si los cinco pasos salen bien, el entorno está bien. **Usa el módulo de estudios como modelo.**

La base de datos es H2 **en fichero**, en la carpeta `data/` del proyecto. En cada arranque se crea de nuevo con los datos de `data.sql`. Su consola web está en `http://localhost:8080/h2-console`, con la URL `jdbc:h2:file:./data/tienda`, el usuario `sa` y sin contraseña.

## 2. El modelo

Un **estudio** desarrolla muchos **videojuegos**, y cada videojuego lo desarrolla **un solo** estudio. La relación es **1:N y bidireccional**: desde un videojuego se llega a su estudio, y desde un estudio a la lista de sus videojuegos.

```
┌──────────────────────────────┐             ┌──────────────────────────────────┐
│           Estudio            │             │            Videojuego            │
├──────────────────────────────┤             ├──────────────────────────────────┤
│ id     : Long                │ 1         * │ id          : Long               │
│ nombre : String              │◄───────────►│ titulo      : String             │
│ pais   : String              │ desarrolla  │ codigo      : String             │
│ juegos : List<Videojuego>    │             │ precio      : BigDecimal         │
└──────────────────────────────┘             │ unidades    : Integer            │
                                             │ edadMinima  : Integer            │
                                             │ estudio     : Estudio            │
                                             │ deleted     : boolean            │
                                             │ createdAt   : LocalDateTime      │
                                             │ updatedAt   : LocalDateTime      │
                                             └──────────────────────────────────┘
```

## 3. Las reglas de negocio

Las cuatro reglas se comprueban **en el servicio**. Las excepciones de R1, R2 y R3 ya están hechas; la de R4 la creas tú.

| | Regla | Si no se cumple | Mensaje exacto |
|:-:|---|---|---|
| **R1** | Dos videojuegos no pueden tener el mismo código, aunque uno esté borrado | `409` · `VideojuegoConflictException` | `Ya existe un videojuego con el código <codigo>` |
| **R2** | El estudio (`estudioId`) tiene que existir | `400` · `VideojuegoBadRequestException` | `El estudio con id <estudioId> no existe` |
| **R3** | El borrado es **lógico**: el videojuego se queda en la tabla con `deleted = true`. Un videojuego borrado no sale en el listado, y pedirlo, modificarlo, borrarlo otra vez o ajustar sus unidades da 404 | `404` · `VideojuegoNotFoundException` | El que ya trae la clase |
| **R4** | Las unidades nunca pueden quedar negativas | `409` · `VideojuegoSinUnidadesException` | `No quedan unidades suficientes de '<titulo>': hay <unidades> y se piden <cantidad>` |

En R4, `<unidades>` son las que hay **antes** del ajuste y `<cantidad>` va **sin signo**: si se piden retirar −3 unidades, el mensaje dice `3`.

## 4. Lo que se te da

```
tienda/   Spring Boot · web, data-jpa, validation, lombok, devtools, h2
├── src/main/resources/  application.properties · data.sql (los 8 videojuegos, COMENTADOS)
└── src/main/java/org/example/tienda/
    ├── common/GlobalExceptionHandler.java              HECHO
    ├── estudios/                                       HECHO, salvo:
    │   ├── models/Estudio.java                          ← TAREA 1.e
    │   └── services/EstudiosServiceImpl.java            ← TAREA 1.f
    └── videojuegos/
        ├── models/Videojuego.java                       ← TAREA 1: campos y Lombok, sin JPA
        ├── repositories/VideojuegosRepository.java      ← TAREA 2: interfaz vacía
        ├── dto/VideojuegoRequest.java                   ← TAREA 3: campos, sin validar
        ├── dto/VideojuegoResponse.java                    HECHO
        ├── mappers/VideojuegoMapper.java                  HECHO: léelo
        ├── exceptions/ NotFound · Conflict · BadRequest   HECHAS
        ├── services/VideojuegosService.java               HECHA: es el contrato
        ├── services/VideojuegosServiceImpl.java         ← TAREA 5: cada método con un throw
        └── controllers/VideojuegosRestController.java   ← TAREA 6: ping() y sanear() hechos

tienda.postman_collection.json                          ← TAREA 7: dos peticiones de ejemplo
```

Hay tres cosas que no se pueden cambiar:

- los ficheros marcados **HECHO**: antes de corregir **se sustituyen por los originales**;
- los nombres y tipos de los campos de `Videojuego` y `VideojuegoRequest`;
- las firmas de `VideojuegosService`.

## 5. Tareas

### TAREA 1 · Las entidades y la relación · 25 min

**1.a a 1.d · `Videojuego` tiene que ser una entidad JPA** que guarde cada campo así. La tabla se llama `videojuegos`.

| Campo | Columna | Tipo | Requisitos |
|---|---|---|---|
| `id` | `id` | entero largo | Clave primaria, **la genera la base de datos** al insertar |
| `titulo` | `titulo` | texto de **150** | Obligatorio |
| `codigo` | `codigo` | texto de **12** | Obligatorio y **único** en la tabla |
| `precio` | `precio` | decimal, **8 dígitos y 2 decimales** | Obligatorio |
| `unidades` | `unidades` | entero | Obligatorio |
| `edadMinima` | **`edad_minima`** | entero | Obligatorio |
| `estudio` | **`estudio_id`** | clave ajena a `estudios` | Obligatorio. El estudio **solo se carga cuando se usa** |
| `deleted` | **`is_deleted`** | booleano | Obligatorio |
| `createdAt` | **`created_at`** | fecha y hora | No cambia nunca después de insertar |
| `updatedAt` | **`updated_at`** | fecha y hora | Se pone a la hora actual **cada vez que el videojuego se modifica** |

**1.e · `Estudio` tiene que tener el lado inverso** de la relación, en este campo:

```java
private List<Videojuego> juegos = new ArrayList<>();
```

| Requisito | |
|---|---|
| Columna o tabla nueva | **Ninguna**. La relación ya la guarda `videojuegos.estudio_id` |
| Al borrar o guardar un estudio | **No** se borran ni se guardan sus videojuegos |

**1.f · `GET /api/v1/estudios/{id}`** tiene que devolver en `juegosDisponibles` **cuántos videojuegos no borrados y con al menos 1 unidad** tiene ese estudio. Se cuentan con `estudio.getJuegos()`, en `EstudiosServiceImpl.findById`.

**Cómo saber que está bien:**

1. Descomenta el bloque de videojuegos de `data.sql` y arranca: tienen que entrar los 8 videojuegos sin error.
2. En la consola de H2, `SHOW TABLES` tiene que mostrar solo `ESTUDIOS` y `VIDEOJUEGOS`.
3. `SHOW COLUMNS FROM VIDEOJUEGOS` tiene que mostrar las columnas de la tabla de arriba.

Como apoyo, esta es la tabla que tiene que generar Hibernate:

```sql
CREATE TABLE videojuegos (
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    titulo        VARCHAR(150) NOT NULL,
    codigo        VARCHAR(12)  NOT NULL UNIQUE,
    precio        NUMERIC(8,2) NOT NULL,
    unidades      INTEGER      NOT NULL,
    edad_minima   INTEGER      NOT NULL,
    estudio_id    BIGINT       NOT NULL REFERENCES estudios (id),
    is_deleted    BOOLEAN      NOT NULL,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP
);
```

### TAREA 2 · El repositorio · 15 min

`VideojuegosRepository` tiene que ser un repositorio de Spring Data que haga esto:

| | Qué tiene que poder hacer | Recibe | Devuelve |
|:-:|---|---|---|
| **2.a** | Las operaciones básicas de `Videojuego` (guardar, buscar por id…), con clave `Long` | — | — |
| **2.a** | Buscar con una `Specification` y paginar a la vez: `findAll(spec, pageable)` | — | — |
| **2.b** | Buscar un videojuego por su id, **solo si no está borrado** | `Long id` | `Optional<Videojuego>` |
| **2.c** | Saber si ya hay **algún** videojuego con un código | `String codigo` | `boolean` |

Las consultas de 2.b y 2.c no se escriben a mano: **Spring las deduce del nombre del método**.

### TAREA 3 · Los DTO de entrada · 15 min

**3.a · Las validaciones de `VideojuegoRequest`.** Es el cuerpo de entrada del POST y del PUT.

| Campo | ¿Obligatorio? | Regla |
|---|:-:|---|
| `titulo` | Sí, y no vacío | De 1 a **150** caracteres |
| `codigo` | Sí, y no vacío | Cumple la expresión regular `[A-Z]{3}-\d{4}-[A-Z]`. Ejemplo: `NIN-2017-Z` |
| `precio` | Sí | **0 o más** (`0` vale: hay juegos gratuitos; `-0.01` no) |
| `unidades` | Sí | De **0 a 999**, los dos incluidos |
| `edadMinima` | Sí | De **3 a 18**, los dos incluidos |
| `estudioId` | Sí | — |

**3.b · Crea `UnidadesRequest`** en `videojuegos/dto`. Es el cuerpo de `PATCH /{id}/unidades`.

```java
public record UnidadesRequest(Integer cantidad) {}
```

| Campo | ¿Obligatorio? | Regla |
|---|:-:|---|
| `cantidad` | Sí | De **−20 a 20**, los dos incluidos. Positiva: entran unidades. Negativa: se venden |

En los dos DTO, cada regla lleva su propio mensaje en español. Si una validación falla, el `GlobalExceptionHandler` responde `400` con un objeto `campos`, que tiene una entrada por cada campo que ha fallado. Eso ya está hecho.

### TAREA 4 · La excepción propia · 10 min

| | |
|---|---|
| Clase | `VideojuegoSinUnidadesException`, en `videojuegos/exceptions` |
| Tipo | Excepción **no comprobada** |
| Respuesta HTTP | **409**, sin tocar el `GlobalExceptionHandler` |
| Constructor | `public VideojuegoSinUnidadesException(String titulo, int unidades, int cantidad)` |
| Mensaje | El de R4, exacto |

### TAREA 5 · El servicio · 55 min

En `VideojuegosServiceImpl`:

- **5.0** Recibe también el `VideojuegosRepository` por el constructor.
- **5.1** Implementa cada método del contrato, haciendo las comprobaciones **en este orden**.

| Método | Qué hace, en orden | Devuelve |
|---|---|---|
| `findAll(titulo, estudio, edadMax, pageable)` | Solo videojuegos no borrados. Cada filtro que llegue se **suma** a los demás (ver la tabla de 6.a). La búsqueda y la paginación las hace la base de datos | `Page<VideojuegoResponse>` |
| `findById(id)` | 1. Videojuego no borrado con ese id, o R3 | `VideojuegoResponse` |
| `save(request)` | 1. R1. 2. R2. 3. Lo guarda | El videojuego guardado |
| `update(id, request)` | 1. R3. 2. R1, **solo si el código cambia**. 3. R2. 4. Reemplaza **todos** los campos | El videojuego modificado |
| `deleteById(id)` | 1. R3. 2. Lo marca como borrado | — |
| `ajustarUnidades(id, cantidad)` | 1. R3. 2. Nuevas unidades = unidades + cantidad. 3. R4 si las nuevas unidades son menos de 0. 4. Lo guarda | El videojuego con las unidades nuevas |

- **5.2** Cada método es **transaccional**. Las dos lecturas, de solo lectura.

### TAREA 6 · El controlador · 30 min

Todas las rutas empiezan por `/api/v1/videojuegos`.

| Endpoint | Entrada | Llama a | Éxito | Errores |
|---|---|---|---|---|
| `GET /` | Filtros opcionales (abajo) y paginación | `findAll` | `200` · página de `VideojuegoResponse` | — |
| `GET /{id}` | `id` en la ruta | `findById` | `200` · `VideojuegoResponse` | `404` |
| `POST /` | Cuerpo `VideojuegoRequest`, **validado** | `save` | **`201`** · `VideojuegoResponse` · cabecera **`Location: /api/v1/videojuegos/{id}`** | `400` · `409` |
| `PUT /{id}` | `id` y cuerpo `VideojuegoRequest`, **validado** | `update` | `200` · `VideojuegoResponse` | `400` · `404` · `409` |
| `DELETE /{id}` | `id` | `deleteById` | **`204`**, sin cuerpo | `404` |
| `PATCH /{id}/unidades` | `id` y cuerpo `UnidadesRequest`, **validado** | `ajustarUnidades` | `200` · `VideojuegoResponse` | `400` · `404` · `409` |

**6.a · El listado.** Todos los parámetros son opcionales y se pueden combinar.

| Parámetro | Tipo | Qué hace |
|---|---|---|
| `titulo` | texto | El título **contiene** el texto, sin distinguir mayúsculas |
| `estudio` | texto | El **nombre del estudio** contiene el texto, sin distinguir mayúsculas |
| `edadMax` | entero | Juegos aptos para esa edad: `edadMinima` **menor o igual** que `edadMax` |
| `page` · `size` · `sort` | — | Si no llegan: página 0, **10 por página**, ordenados por **título** |

Antes de pasar el `Pageable` al servicio, llama a `sanear(...)`, que ya está hecho. `sanear` recorta `size` a 50 e ignora los `sort` por campos no permitidos.

**6.b** El controlador no tiene lógica de negocio: recibe la petición, llama al servicio y devuelve la respuesta con su código.

### TAREA 7 · Las pruebas · 20 min

Añade a `tienda.postman_collection.json` estas 8 peticiones, debajo de las dos de ejemplo:

- **sin carpetas**;
- con **estos nombres** y en este orden;
- cada una con sus tests.

Para que los resultados cuadren, **reinicia la aplicación** antes de lanzarlas.

| Nombre | Petición | Tests |
|---|---|---|
| `01 Listar paginado` | `GET /api/v1/videojuegos?page=1&size=3&sort=precio,asc` | código 200 · `content.length` es 3 |
| `02 Filtros combinados` | `GET /api/v1/videojuegos?estudio=nintendo&edadMax=3` | código 200 · `content.length` es 2 |
| `03 Videojuego que no existe` | `GET /api/v1/videojuegos/999` | código 404 |
| `04 Crear videojuego` | `POST /api/v1/videojuegos` con un videojuego correcto, `estudioId` 3 y un código nuevo | código 201 · `estudio` es `Mercury Steam` |
| `05 Crear con datos inválidos` | `POST /api/v1/videojuegos` con al menos tres campos inválidos | código 400 |
| `06 Código repetido` | `POST /api/v1/videojuegos` con el código `NIN-2017-Z` | código 409 |
| `07 Vender sin unidades` | `PATCH /api/v1/videojuegos/3/unidades` con `{"cantidad": -2}` (el videojuego 3 tiene 0 unidades) | código 409 |
| `08 Borrar (lógico)` | `DELETE /api/v1/videojuegos/5` | código 204 |

Los tests se escriben en la pestaña *Tests* de cada petición, que se llama así en Postman y en Bruno:

```javascript
pm.test("Código 201", function () { pm.response.to.have.status(201); });
pm.test("estudio", function () { pm.expect(pm.response.json().estudio).to.eql("Mercury Steam"); });
pm.test("tamaño", function () { pm.expect(pm.response.json().content.length).to.eql(3); });
```

Al terminar, **exporta la colección en JSON** con el mismo nombre de fichero: en Postman, *Export → Collection v2.1*; en Bruno, *Export → Postman Collection*.

## 6. Entrega

Un ZIP llamado **`nombre_alumno_ra5_ra6.zip`** con:

- la carpeta `tienda/`, **sin `target/`, `.idea/` ni `data/`**;
- el fichero `tienda.postman_collection.json`.

> Antes de entregar, *Build → Rebuild Project*. **Si un método no te sale, deja su `throw` y sigue: si el proyecto no compila, la nota es 0.**

## 7. Calificación

**RA5 y RA6 se califican por separado, cada uno sobre 10, y cada uno se aprueba con 5.** Uno no compensa al otro.

**Solo puntúa el código que se puede comprobar que funciona.** Para corregir:

1. Se reponen los ficheros HECHOS originales.
2. Se arranca el proyecto.
3. Se lanza la **colección de corrección**, con 41 peticiones numeradas y sus tests.

Cada bloque se califica así:

> **Nota del bloque = puntos del bloque × peticiones en verde ÷ peticiones del bloque**
>
> Una petición está en verde cuando pasan **todos** sus tests.

### RA5 · Separar la presentación de la lógica de negocio (UT4)

| Bloque | Qué se comprueba | Peticiones | Puntos |
|---|---|:-:|:-:|
| **1 · Validación de `VideojuegoRequest`** | Cada regla da 400, y los valores límite se aceptan | 10 | **3** |
| **2 · `UnidadesRequest` y la excepción propia** | Los 400 de `cantidad`, y el 409 de R4 con su mensaje | 3 | **1,5** |
| **3 · Las reglas del servicio** | R2, el PUT, el ajuste de unidades, y que los cambios se guardan | 7 | **2,5** |
| **4 · El controlador** | 201 + `Location` al crear, 204 al borrar, y los 404 | 6 | **2** |
| **5 · Tus pruebas** | Tu colección, lanzada contra la solución: de tus 8 peticiones, las que tienen el nombre y los tests pedidos y salen en verde | 8 | **1** |
| | | | **10** |

### RA6 · Acceso a datos con JPA (UT5)

| Bloque | Qué se comprueba | Peticiones | Puntos |
|---|---|:-:|:-:|
| **1 · La entidad y la tabla** | `data.sql` carga los 8 videojuegos. Y en la consola de H2: que solo hay 2 tablas, que las columnas son las de la tarea 1 y que `updated_at` se refresca | 1 + 3 consultas | **2,5** |
| **2 · La relación bidireccional** | El nombre del estudio en cada videojuego, y `juegosDisponibles` | 3 | **2** |
| **3 · Repositorio y borrado lógico** | El borrado da 404 y no sale en el listado; el código repetido da 409 | 3 | **1,5** |
| **4 · Los filtros** | `titulo`, `estudio` y `edadMax`, solos y combinados | 4 | **2,5** |
| **5 · Paginación y orden** | `page`, `size` y `sort`, los valores por defecto y el saneado | 4 | **1,5** |
| | | | **10** |

### Ajustes

| Situación | Nota |
|---|---|
| **El ZIP no se abre o está corrupto** | **0 en cada RA** |
| **El proyecto no compila** | **0 en cada RA** |
