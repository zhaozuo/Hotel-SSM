package cn.sdut.dao;

import cn.sdut.domain.Order;

import java.util.List;

public interface OrderDao {
    List<Order> findAllOrder();
    Order findOrderById(Integer id);
    int addOrder(Order order);
    int deleteOrder(Integer id);
}
