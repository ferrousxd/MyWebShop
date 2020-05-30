package controllers;

import domain.Product;
import filters.customAnnotation.JWTTokenNeeded;
import services.ProductService;
import services.interfaces.IProductService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/products")
public class ProductController {
    private IProductService productService = new ProductService();

    @GET
    public Response getListOfProducts() {
        List<Product> products;
        try {
            products = productService.getListOfProducts();
        } catch (ServerErrorException ex) {
            return Response
                    .serverError().build();
        }

        if (products == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Products list is empty!")
                    .build();
        } else {
            return Response
                    .status(Response.Status.OK)
                    .entity(products)
                    .build();
        }
    }

    @JWTTokenNeeded
    @GET
    @Path("/{id}")
    public Response getProductByID(@PathParam("id") long id) {
        Product product;
        try {
            product = productService.getProductByID(id);
        } catch (ServerErrorException ex) {
            return Response
                    .serverError().build();
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("This product cannot be created").build();
        }

        if (product == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Product does not exist!")
                    .build();
        } else {
            return Response
                    .status(Response.Status.OK)
                    .entity(product)
                    .build();
        }
    }
}
