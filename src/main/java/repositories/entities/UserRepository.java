package repositories.entities;

import domain.SigninData;
import domain.User;

import repositories.db.PostgresRepository;
import repositories.interfaces.IDBRepository;
import repositories.interfaces.IEntityRepository;

import javax.ws.rs.BadRequestException;
import javax.xml.stream.events.StartDocument;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class UserRepository implements IEntityRepository<User> {
    private IDBRepository dbrepo;

    public UserRepository() {
        dbrepo = new PostgresRepository();
    }

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

    }

    @Override
    public void remove(User entity) {

    }

    @Override
    public Iterable<User> query(String sql) {
        try {
            Statement stmt = dbrepo.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            LinkedList<User> users = new LinkedList<>();
            while (rs.next()) {
                User user = new User(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getDate("birthday")
                );
                users.add(user);
            }
            return users;
        } catch (SQLException throwables) {
            throw new BadRequestException();
        }
    }

    public User findUserByLogin(SigninData data) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try {
            PreparedStatement stmt = dbrepo.getConnection().prepareStatement(sql);
            stmt.setString(1, data.getUsername());
            stmt.setString(2, data.getPassword());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getDate("birthday"));
                return user;
            }
        } catch (SQLException throwables) {
            throw new BadRequestException();
        }
        return null;
    }
}

