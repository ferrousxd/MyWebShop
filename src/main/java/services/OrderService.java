package services;

import domain.Order;
import repositories.OrderRepository;
import repositories.interfaces.IOrderRepository;
import services.interfaces.IOrderService;

import java.util.List;

public class OrderService implements IOrderService {
    private IOrderRepository orderrepo = new OrderRepository();

    @Override
    public Order getOrderSumByUserID(long id) {
        return orderrepo.getOrderSumByUserID(id);
    }

    @Override
    public List<Order> getOrderListByUserID(long id) {
        return orderrepo.getOrderListByUserID(id);
    }
}
