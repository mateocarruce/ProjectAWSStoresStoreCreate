package src.Main;

// DAO/AlmacenDAO.java

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AlmacenDAO {
    private Connection connection;

    public AlmacenDAO(Connection connection) {
        this.connection = connection;
    }

    public void agregarAlmacen(String nombre, String direccion, int capacidad) throws SQLException {
        String sql = "INSERT INTO Almacenes (Nombre, Direccion, Capacidad) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, direccion);
            stmt.setInt(3, capacidad);
            stmt.executeUpdate();
        }
    }
}
