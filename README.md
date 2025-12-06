# 💼 Finance App
-----------
## 📖 Description
Finance App es una aplicación de escritorio desarrollada en Java + JavaFX que permite gestionar tus finanzas personales de manera simple, ordenada y visual.

Con esta app podrás:

- Registrar ingresos y gastos

- Visualizar tus movimientos financieros

- Consultar tu balance total

- Administrar categorías

- Crear cuentas de usuario

- Autenticarse mediante un login seguro

- Guardar y cargar datos con una base de datos local

La interfaz está diseñada con un estilo moderno, intuitivo y responsivo, utilizando JavaFX + CSS para lograr una experiencia agradable.
## 🚀 Getting Started
Sigue estos pasos para ejecutar la aplicación en tu entorno local.

### 🧩 Prerequisites

Asegúrate de tener instalado:

- Java 17 o superior

- JavaFX 21+

- Maven o Gradle

- Git (opcional)

(No requiere MySQL ni servidores externos — la BD H2 es embebida)

### 🪄 Steps
1️⃣ Clonar el repositorio
git clone https://github.com/your-username/finance-app.git
cd finance-app

2️⃣ Configurar la base de datos H2

No es necesario instalar nada.
Solo asegúrate de tener en src/main/resources/application.properties:
 ``` bash
spring.datasource.url=jdbc:h2:file:./data/finance-db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# H2 Console (opcional)
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
 ``` 

Esto permite:

- BD persistente en la carpeta /data

- Acceso a consola H2 vía navegador

- Auto-creación de tablas por JPA

3️⃣ Compilar el proyecto

Con Maven:
 ``` bash
mvn clean install
 ``` 

Con Gradle:
 ``` bash
gradle build
 ``` 
4️⃣ Ejecutar la aplicación
 ``` bash
mvn javafx:run
 ``` 
## 🧰 Tech Stack
### 🖥️ Desktop App

- Java 17

- JavaFX 21

- FXML

- CSS (interfaz moderna y customizada)

### ⚙️ Backend / Persistencia

- Hibernate / JPA

- H2 Database (embebida)

- Arquitectura MVC
