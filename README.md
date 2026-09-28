![Duoc UC](https://www.duoc.cl/wp-content/uploads/2022/09/logo-0.png)
#  Caso: SpeedFast -Desarrollo Orientado a Objetos II

## 👤 Autor del proyecto 
- **Nombre Completo:** [Katherine del Carmen Avila Mecia]
- **Sección:** 003A
- **Carrera:** Analista programador Computacional
- **Bimestre:** 3
- **Sede:** Campus Virtual

---
## 📘 Actividad Formativa N. 5º (Semana 7): Conectando aplicaciones Java con bases de datos mediante JDBC

## Descripción del Proyecto:
Sistema desarrollado en Java Swing para registrar pedidos, gestionar repartidores y almacenar historial de envíos mediante
una base de datos MySQL.

Este proyecto aplica el patron de arquitectura DAO para asegurar una separación limpia entre la interfaz gráfica y la persistencia de datos.

### Clases principales
* **Punto de Entrada:** 
* `Main`: Inicia la aplicación de forma segura utilizando el hilo de eventos de Swingx(`SwingUtilities.invokeLater`).

* **Modelo (Entidades):**
* `Pedido` : define los atributos del pedido (dirección, tipo, estado).
* `Repartidor`: Representa al personal de entregas.
* `Entrega`: Modelo transaccional que une un `Pedido` y un `Repartidor`, registrando además la fecha y la hora.

* **Acceso a Datos (DAO):**
* `ConexionDB`: Configura y establece la conexión con MySQL utilizando JDBC.
* `PedidoDAO` , `RepartidorDAO`, `EntregaDAO`:Centralizan todas las operaciones SQL (consultas, inserciones y actualizaciones) correspondientes a cada entidad.

* **Vista (Interfaz Gráfica):**
* `VentanaPrincipal`: Pantalla dedicada al acceso mediante botones a la gestion del sistema speedfast.
* `VentanaRegistroPedido`: Pantalla dedicada a la captura y guardado de nuevos pedidos.
* `VentanaEntrega`: Interfaz para asignar pedidos pendientes a repartidores y visualizar la tabla con el historial de entregas.
* `VentanaRegistrarRepartidores`: Pantalla dedicada a la captura, guardado y visualizacion de los repartidores.

### Características destacadas
* **Registro de Pedidos**:Permite ingresar nuevos pedidos definiendo dirección y tipo, asignándoles automáticamente el estado `PENDIENTE`.
* **Gestión de Entrega**: Interfaz protegida con listas desplegables (`JComboBox`) que evita errores de tipeo al asignar un pedido a un repartidor.
* **Transacciones Automáticas**: Al registrar una entrega, el sistema realiza un `INSERT` en el historial y un `UPDATE` automatico para cambiar el estado del pedido a `ENTREGADO`.
* **Historial Dinámico**: visualización en tiempo real a traves de tablas (`JTable`) que se refrescan al momento de abrir la ventana y realizar una nueva asignación.

## Tecnologías utilizadas

* **Lenguaje:** Java.
* **Interfaz Gráfica:** Java Swing (JFrame, JPanel, Layout managers)
* **Base de Datos:** MySQL
* **Conexión:** JDBC (Java Database Connectivity)

---
## Instrucciones de Ejecución
1. Descarga o clona el repositorio y abre la carpeta del proyecto en tu IDE.
2. Abre MySQL Workbench.
3. Ejecuta tu script SQL para crear la base de datos `speedfast_db` y las tablas necesarias (`pedido`, `repartidor`, `entrega`).
4. Abre el archivo `ConexionDB.java` o tu clase de conexión y verifica las credenciales coincidan con tu servidor local MySQL.
5. Asegúrate de que el conector de MYSQL (el archivo `.jar`) este agregado a las librerías o dependencias de tu proyecto.
6. En el panel del proyecto, dirígete al paquete principal y busca la clase `Main.java`.
7. Haz clic derecho sobre el archivo y selecciona **Run.'Main.main()'**
8. la interfaz se desplegará de forma segura en la pantalla. 


## 📁 Estructura del Proyecto

``` plaintext
SpeedFast_Semana7
├── .idea
├── .mvn
├── src
│   ├── main
│   │   ├── java
│   │   │   └── cl
│   │   │       └── duoc
│   │   │           ├── dao
│   │   │           │   ├── ConexionDB
│   │   │           │   ├── EntregaDAO
│   │   │           │   ├── PedidoDAO
│   │   │           │   └── RepartidorDAO
│   │   │           ├── main
│   │   │           │   └── Main
│   │   │           ├── model
│   │   │           │   ├── Entrega
│   │   │           │   ├── EstadoPedido
│   │   │           │   ├── Pedido
│   │   │           │   ├── Repartidor
│   │   │           │   └── TipoPedido
│   │   │           └── vista
│   │   │               ├── VentanaEntrega
│   │   │               ├── VentanaPrincipal
│   │   │               ├── VentanaRegistrarRepartidor
│   │   │               └── VentanaRegistroPedido
│   │   └── resources
│   └── test
├── target
├── .gitignore
├── pom.xml
└── README.md
---

