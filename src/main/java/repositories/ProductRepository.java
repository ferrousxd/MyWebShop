package repositories;

import domain.Product;
import repositories.interfaces.IDBRepository;
import repositories.interfaces.IProductRepository;

import javax.ws.rs.BadRequestException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements IProductRepository {
    private IDBRepository dbrepo = new PostgresRepository();

    @Override
    public void add(Product entity) {
        try {
            Statement stmt = dbrepo.getConnection().createStatement();
            String sql = "INSERT INTO products(name, category, description, price) " +
                    "VALUES('" + entity.getName() + "','"+ entity.getCategory() +
                    "','"+ entity.getDescription() + "','"+ entity.getPrice() +"')";
            stmt.execute(sql);
        } catch(SQLException ex) {
            throw new BadRequestException();
        }
    }

    @Override
    public void update(Product entity) {
        try {
            Statement stmt = dbrepo.getConnection().createStatement();
            String sql = "UPDATE products SET"
                    + " name = '" + entity.getName()
                    + "', category = '" + entity.getCategory()
                    + "', description = '" + entity.getDescription()
                    + "', price = "+ entity.getPrice() +
                    " WHERE id = " + entity.getId();
            stmt.execute(sql);
        } catch(SQLException ex) {
            throw new BadRequestException();
        }
    }

    @Override
    public void remove(Product entity) {
        try {
            Statement stmt = dbrepo.getConnection().createStatement();
            String sql = "DELETE FROM products WHERE id = " + entity.getId()
                    + " AND name = '" + entity.getName() + "'";
            stmt.execute(sql);
        } catch(SQLException ex) {
            throw new BadRequestException();
        }
    }

    @Override
    public Product queryOne(String sql) {
        try {
            Statement stmt = dbrepo.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                Product product = new Product (
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getDouble("price")
                );
                return product;
            }
        } catch (SQLException throwables) {
            throw new BadRequestException();
        }
        return null;
    }

    @Override
    public List<Product> queryTwo(String sql) {
        try {
            List<Product> products = new ArrayList<>();
            Statement stmt = dbrepo.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Product product = new Product (
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getDouble("price")
                );
                products.add(product);
            }
            return products;
        } catch (SQLException throwables) {
            throw new BadRequestException();
        }
    }

    @Override
    public Product getProductByID(long id) {
        String sql = "SELECT * FROM products WHERE id = " + id + " LIMIT 1";
        return queryOne(sql);
    }

    @Override
    public List<Product> getListOfProducts() {
        String sql = "SELECT id, name, price FROM products";
        return queryTwo(sql);
    }
}
