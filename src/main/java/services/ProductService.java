package services;

import domain.Product;
import repositories.ProductRepository;
import repositories.interfaces.IProductRepository;
import services.interfaces.IProductService;

import java.util.List;

public class ProductService implements IProductService {
    private IProductRepository prodrepo = new ProductRepository();

    @Override
    public Product getProductByID(long id) {
        return prodrepo.getProductByID(id);
    }

    @Override
    public List<Product> getListOfProducts() {
        return prodrepo.getListOfProducts();
    }
}
