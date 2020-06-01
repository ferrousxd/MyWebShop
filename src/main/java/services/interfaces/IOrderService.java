package services.interfaces;

import domain.Order;

import java.util.List;

public interface IOrderService {
    Order getOrderSumByUserID(long id);

    List<Order> getOrderListByUserID(long id);
}
