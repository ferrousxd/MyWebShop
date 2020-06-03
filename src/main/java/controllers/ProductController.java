package controllers;

import domain.Product;
import filters.customAnnotation.JWTTokenNeeded;
import filters.customAnnotation.OnlyAdmin;
import org.glassfish.jersey.media.multipart.FormDataParam;
import repositories.ProductRepository;
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
                    .serverError()
                    .build();
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

    @OnlyAdmin
    @GET
    @Path("/{id}")
    public Response getProductByID(@PathParam("id") long id) {
        Product product;
        try {
            product = productService.getProductByID(id);
        } catch (ServerErrorException ex) {
            return Response
                    .serverError()
                    .build();
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("This product cannot be created")
                    .build();
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

    @OnlyAdmin
    @POST
    @Path("/add")
    public Response addProduct(@FormDataParam("name") String name,
                               @FormDataParam("category") String category,
                               @FormDataParam("description") String description,
                               @FormDataParam("price") double price)
    {
        ProductRepository prodRepo = new ProductRepository();
        Product product = new Product(name, category, description, price);
        try {
            prodRepo.add(product);
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Cannot add product!")
                    .build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(product)
                .build();
    }

    @JWTTokenNeeded
    @POST
    @Path("/update")
    public Response updateProduct(@FormDataParam("id") int id,
                                  @FormDataParam("name") String name,
                                  @FormDataParam("category") String category,
                                  @FormDataParam("description") String description,
                                  @FormDataParam("price") double price)
    {
        ProductRepository productRepo = new ProductRepository();
        Product product = new Product(id, name, category, description, price);
        try {
            productRepo.update(product);
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Cannot add product!")
                    .build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(product)
                .build();
    }

    @OnlyAdmin
    @POST
    @Path("/delete")
    public Response deleteProduct(@FormDataParam("id") int id,
                                  @FormDataParam("name") String name)
    {
        ProductRepository productRepo = new ProductRepository();
        Product product = new Product(id, name);
        try {
            productRepo.update(product);
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Cannot delete product!")
                    .build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(product)
                .build();
    }
}
