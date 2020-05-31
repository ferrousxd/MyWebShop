package repositories;

import domain.LoginData;
import domain.User;
import repositories.interfaces.IDBRepository;
import repositories.interfaces.IUserRepository;

import javax.ws.rs.BadRequestException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserRepository implements IUserRepository {
    private IDBRepository dbrepo = new PostgresRepository();

    @Override
    public void add(User entity) {
        try {
            Statement stmt = dbrepo.getConnection().createStatement();
            String sql = "INSERT INTO users(name, surname, username, password, birthday) " +
                    "VALUES('" + entity.getName() + "','"+ entity.getSurname() +
                    "','"+ entity.getUsername() +"','"+ entity.getPassword() +
                    "','"+ entity.getBirthday() +"')";
            stmt.execute(sql);
        } catch(SQLException ex) {
            throw new BadRequestException();
        }
    }

    @Override
    public void update(User entity) {
        try {
            Statement stmt = dbrepo.getConnection().createStatement();
            String sql = "UPDATE users SET" +
                    " name = '" + entity.getName()
                    + "', surname = '" + entity.getSurname()
                    + "', password = '" + entity.getPassword()
                    + "' WHERE id = " + entity.getId();
            stmt.execute(sql);
        } catch(SQLException ex) {
            throw new BadRequestException();
        }
    }

    @Override
    public void remove(User entity) {
        try {
            Statement stmt = dbrepo.getConnection().createStatement();
            String sql = "DELETE FROM products WHERE username = " + entity.getUsername();
            stmt.execute(sql);
        } catch(SQLException ex) {
            throw new BadRequestException();
        }
    }

    @Override
    public User queryOne(String sql) {
        try {
            Statement stmt = dbrepo.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                User user = new User (
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("username"),
                        rs.getDate("birthday")
                );
                return user;
            }
        } catch (SQLException throwables) {
            throw new BadRequestException();
        }
        return null;
    }

    @Override
    public List<User> queryTwo(String sql) {
        return null;
    }

    @Override
    public User getUserByID(long id) {
        String sql = "SELECT * FROM users WHERE id = " + id + " LIMIT 1";
        return queryOne(sql);
    }

    @Override
    public User getUserByLogin(LoginData data) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ? LIMIT 1";
        try {
            PreparedStatement stmt = dbrepo.getConnection().prepareStatement(sql);
            stmt.setString(1, data.getUsername());
            stmt.setString(2, data.getPassword());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User (
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getDate("birthday")
                );
                return user;
            }
        } catch (SQLException throwables) {
            throw new BadRequestException();
        }
        return null;
    }
}
