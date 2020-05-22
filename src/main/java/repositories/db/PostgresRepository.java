package repositories.db;

import repositories.interfaces.IDBRepository;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresRepository implements IDBRepository {
    @Override
    public Connection getConnection() {
        try {
            String connStr = "jdbc:postgresql://localhost:5432/mydatabase";
            Connection conn = DriverManager.getConnection(connStr, "postgres", "12345");
            return conn;
        } catch (SQLException ex) {
            throw new ServerErrorException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
