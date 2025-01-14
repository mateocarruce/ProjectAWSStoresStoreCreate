package src.Main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static spark.Spark.*;

public class WebAppAlmacenes {

    public static void main(String[] args) {
        // Configurar la base de datos
        String jdbcURL = "jdbc:mysql://18.208.216.174:3306/Almacenes_Crear";
        String dbUser = "admin"; // Usuario remoto de MySQL
        String dbPassword = "Admin123.";

        // Rutas de la aplicación web
        staticFiles.location("/templates"); // Ruta de los archivos estáticos (HTML)

        // Ruta principal
        get("/", (req, res) -> {
            return """
                <h1>Crear un Almacén</h1>
                <form action="/crear" method="POST">
                    <label>Nombre:</label><br>
                    <input type="text" name="nombre" required><br>
                    <label>Dirección:</label><br>
                    <input type="text" name="direccion" required><br>
                    <label>Capacidad:</label><br>
                    <input type="number" name="capacidad" required><br>
                    <button type="submit">Crear Almacén</button>
                </form>
            """;
        });

        // Ruta para manejar el formulario de creación
        post("/crear", (req, res) -> {
            String nombre = req.queryParams("nombre");
            String direccion = req.queryParams("direccion");
            int capacidad = Integer.parseInt(req.queryParams("capacidad"));

            try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
                String sql = "INSERT INTO Almacenes (Nombre, Direccion, Capacidad) VALUES (?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, nombre);
                statement.setString(2, direccion);
                statement.setInt(3, capacidad);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return "Error al guardar el almacén";
            }

            res.redirect("/");
            return null;
        });
    }
}
