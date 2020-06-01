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
            String sql = "INSERT INTO products(product_name, product_category, product_description, product_price) " +
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
                    + " product_name = '" + entity.getName()
                    + "', product_category = '" + entity.getCategory()
                    + "', product_description = '" + entity.getDescription()
                    + "', product_price = "+ entity.getPrice() +
                    " WHERE product_id = " + entity.getId();
            stmt.execute(sql);
        } catch(SQLException ex) {
            throw new BadRequestException();
        }
    }

    @Override
    public void remove(Product entity) {
        try {
            Statement stmt = dbrepo.getConnection().createStatement();
            String sql = "DELETE FROM products WHERE product_id = " + entity.getId();
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
                        rs.getLong("product_id"),
                        rs.getString("product_name"),
                        rs.getString("product_category"),
                        rs.getString("product_description"),
                        rs.getDouble("product_price")
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
                    rs.getLong("product_id"),
                    rs.getString("product_name"),
                    rs.getDouble("product_price")
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
        String sql = "SELECT * FROM products WHERE product_id = " + id + " LIMIT 1";
        return queryOne(sql);
    }

    @Override
    public List<Product> getListOfProducts() {
        String sql = "SELECT product_id, product_name, product_price FROM products";
        return queryTwo(sql);
    }
}
