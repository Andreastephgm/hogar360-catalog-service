# Catalog Service

Este es un microservicio hecho con Spring Boot para gestionar las categorías de productos en un sistema. El servicio está conectado a una base de datos MySQL, y te dejo dos formas para configurar la base de datos: usando Docker o con un archivo `.sql`.

## Requisitos

- JDK 17 o superior
- Docker (opcional, si prefieres usar contenedor)
- MySQL (si no usas Docker)

## Configuración de la Base de Datos

Este proyecto tiene dos opciones para configurar la base de datos:

### Opción 1: Usar Docker

Si prefieres usar Docker, aquí puedes levantar la base de datos de forma fácil:

1. Asegúrate de tener Docker instalado en tu máquina.
2. Entra en la carpeta `infrastructure/docker` del proyecto.
3. Corre el siguiente comando para levantar el contenedor con MySQL:

    ```bash
    docker-compose up -d
    ```

4. La base de datos estará corriendo en el contenedor y podrás acceder a ella desde `localhost:3306`.

### Opción 2: Usar el archivo `.sql`

Si prefieres no usar Docker, puedes configurar MySQL manualmente o usar un servidor de base de datos que ya tengas. El archivo `infrastructure/database/schema.sql` tiene todo lo que necesitas para crear las tablas.

1. Crea una base de datos MySQL en tu servidor.
2. Ejecuta el archivo `infrastructure/database/schema.sql` para crear las tablas.
3. Cambia la configuración de la base de datos en el archivo `src/main/resources/application.properties`:

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/tu_base_de_datos
    spring.datasource.username=tu_usuario
    spring.datasource.password=tu_contraseña
    ```

## Configuración del Proyecto

### Configuración de Spring Boot

1. Revisa la configuración de la base de datos en `src/main/resources/application.properties` según lo que hayas elegido en la opción anterior.
2. Si estás usando Docker, asegúrate de que el contenedor de MySQL esté corriendo.

### Iniciar el Proyecto

Para levantar el microservicio, sigue estos pasos:

1. Clona el repositorio:

    ```bash
    git clone https://github.com/tu-usuario/catalog-service.git
    cd catalog-service
    ```

2. Si estás usando Docker, levanta la base de datos con el siguiente comando:

    ```bash
    docker-compose up -d
    ```

3. Levanta el proyecto con Gradle:

    ```bash
    ./gradlew bootRun
    ```

El microservicio estará disponible en `http://localhost:8080`.

## Pruebas

Si quieres correr las pruebas del proyecto, usa:

```bash
./gradlew test
