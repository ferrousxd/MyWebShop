package repositories.interfaces;

import domain.Product;

import java.util.ArrayList;
import java.util.List;

public interface IProductRepository extends IEntityRepository<Product> {
    Product getProductByID(long id);

    List<Product> getListOfProducts();
}
