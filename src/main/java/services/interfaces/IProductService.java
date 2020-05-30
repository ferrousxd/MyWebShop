package services.interfaces;

import domain.Product;

import java.util.List;

public interface IProductService {
    Product getProductByID(long id);

    List<Product> getListOfProducts();
}
