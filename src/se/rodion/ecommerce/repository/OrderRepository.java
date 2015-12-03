package se.rodion.ecommerce.repository;

import java.util.List;
import java.util.Map;

import se.rodion.ecommerce.exceptions.RepositoryException;
import se.rodion.ecommerce.model.Order;

public interface OrderRepository
{

	Order createOrder(Order order) throws RepositoryException;

	Order updateOrder(Order order) throws RepositoryException;

	Order deleteOrder(Order order) throws RepositoryException;

	Map<Integer, Order> readOrders();

	int getNextId();

	Order getOrderById(int orderId) throws RepositoryException;

	List<Order> getOrdersByUser(String string) throws RepositoryException;
}
