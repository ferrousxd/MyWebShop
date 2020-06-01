package repositories.interfaces;

import domain.Order;

import java.util.List;

public interface IOrderRepository extends IEntityRepository<Order> {
    Order getOrderSumByUserID(long id);

    List<Order> getOrderListByUserID(long id);
}
