package repositories;

import domain.Order;
import repositories.interfaces.IDBRepository;
import repositories.interfaces.IOrderRepository;

import javax.ws.rs.BadRequestException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements IOrderRepository {
    private IDBRepository dbrepo = new PostgresRepository();

    @Override
    public void add(Order entity) {
        try {
            Statement stmt = dbrepo.getConnection().createStatement();
            String sql = "INSERT INTO orders (user_id, product_id) VALUES ("
                    + entity.getUser_id() + ","
                    + entity.getProduct_id() + ")";
            stmt.execute(sql);
        } catch(SQLException ex) {
            throw new BadRequestException();
        }
    }

    @Override
    public void update(Order entity) {

    }

    @Override
    public void remove(Order entity) {
        try {
            Statement stmt = dbrepo.getConnection().createStatement();
            String sql = "DELETE FROM orders WHERE order_id = " + entity.getOrder_id();
            stmt.execute(sql);
        } catch(SQLException ex) {
            throw new BadRequestException();
        }
    }

    @Override
    public Order queryOne(String sql) {
        try {
            Statement stmt = dbrepo.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                Order order = new Order (
                        rs.getString("username"),
                        rs.getDouble("sum")
                );
                return order;
            }
        } catch (SQLException throwables) {
            throw new BadRequestException();
        }
        return null;
    }

    @Override
    public List<Order> queryTwo(String sql) {
        try {
            List<Order> orders = new ArrayList<>();
            Statement stmt = dbrepo.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Order order = new Order (
                        rs.getString("username"),
                        rs.getString("product_name"),
                        rs.getDouble("product_price")
                );
                orders.add(order);
            }
            return orders;
        } catch (SQLException throwables) {
            throw new BadRequestException();
        }
    }

    @Override
    public Order getOrderSumByUserID(long id) {
        String sql = "SELECT username, SUM(product_price) " +
                "FROM orders o JOIN users u ON o.user_id = u.user_id " +
                "JOIN products p ON o.product_id = p.product_id WHERE u.user_id = " + id +
                " GROUP BY u.user_id";
        return queryOne(sql);
    }

    @Override
    public List<Order> getOrderListByUserID(long id) {
        String sql = "SELECT username, product_name, product_price " +
                "FROM orders o JOIN users u ON o.user_id = u.user_id " +
                "JOIN products p ON o.product_id = p.product_id WHERE u.user_id = " + id;
        return queryTwo(sql);
    }
}
