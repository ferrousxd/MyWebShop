package controllers;

import domain.Order;
import filters.customAnnotation.JWTTokenNeeded;
import org.glassfish.jersey.media.multipart.FormDataParam;
import repositories.OrderRepository;
import services.OrderService;
import services.interfaces.IOrderService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/orders/users")
public class OrderController {
    private IOrderService orderService = new OrderService();
    private OrderRepository orderrepo = new OrderRepository();

    @JWTTokenNeeded
    @GET
    @Path("/{id}")
    public Response getUserOrderListByID(@PathParam("id") long id) {
        List<Order> orders;
        try {
            orders = orderService.getOrderListByUserID(id);
        } catch (ServerErrorException ex) {
            return Response
                    .serverError()
                    .build();
        }

        if (orders == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Orders list is empty!")
                    .build();
        } else {
            return Response
                    .status(Response.Status.OK)
                    .entity(orders)
                    .build();
        }
    }

    @JWTTokenNeeded
    @GET
    @Path("/{id}/total")
    public Response getOrderTotal(@PathParam("id") long id) {
        Order order;
        try {
            order = orderService.getOrderSumByUserID(id);
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Unable to get total sum of your order!")
                    .build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(order)
                .build();
    }

    @JWTTokenNeeded
    @POST
    @Path("/add")
    public Response addProductToOrder(@FormDataParam("user_id") int user_id,
                                      @FormDataParam("product_id") int product_id)
    {

        Order order = new Order(user_id, product_id);
        try {
            orderrepo.add(order);
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Cannot add product to your order!")
                    .build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(order)
                .build();
    }

    @JWTTokenNeeded
    @POST
    @Path("/delete")
    public Response deleteProductFromOrder(@FormDataParam("order_id") int order_id)
    {
        Order order = new Order(order_id);
        try {
            orderrepo.remove(order);
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Cannot delete product from your order!")
                    .build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(order)
                .build();
    }
}
