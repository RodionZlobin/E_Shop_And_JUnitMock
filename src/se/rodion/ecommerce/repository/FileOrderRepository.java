package se.rodion.ecommerce.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import se.rodion.ecommerce.exceptions.RepositoryException;
import se.rodion.ecommerce.model.Order;

public class FileOrderRepository implements OrderRepository
{
	Storage<Integer, Order> ordersStorage = new Storage<Integer, Order>("src/storage/", "orders.data");

	@Override
	public Order createOrder(Order order) throws RepositoryException
	{
		try
		{
			System.out.println("test1");
			ordersStorage.add(order.getOrderId(), order);
		}
		catch (RuntimeException e)
		{
			throw new RepositoryException("Couldn´t create an order", e);
		}
		
		return order;
	}

	@Override
	public Order updateOrder(Order order) throws RepositoryException
	{
		try
		{
			if (ordersStorage.getAll().containsKey(order.getOrderId()))
			{
				Map<Integer, Order> updatedOrders = ordersStorage.getAll();
				updatedOrders.replace(order.getOrderId(), order);
				ordersStorage.clearAll();
				ordersStorage.addAll(updatedOrders);
			}
			else
			{
				createOrder(order);
			}
		}
		catch (RuntimeException e)
		{
			throw new RepositoryException("Couldn´t update a product", e);
		}
		
		return order;

	}

	@Override
	public Order deleteOrder(Order order) throws RepositoryException
	{
		try
		{
			Map<Integer, Order> ordersAfterDelete = ordersStorage.getAll();
			ordersAfterDelete.remove(order.getOrderId());
			ordersStorage.clearAll();
			ordersStorage.addAll(ordersAfterDelete);
		}
		catch (RuntimeException e)
		{
			throw new RepositoryException("Couldn´t delete a product", e);
		}
		
		return order;
	}

	@Override
	public int getNextId()
	{
		return 0;
	}

	@Override
	public Map<Integer, Order> readOrders()
	{
		Map<Integer, Order> ordersFromFile = ordersStorage.getAll();

		return ordersFromFile;
	}

	@Override
	public Order getOrderById(int orderId) throws RepositoryException
	{
		Order getOrderById;
		if (ordersStorage.getAll().containsKey(orderId))
		{
			getOrderById = ordersStorage.getAll().get(orderId);
			return getOrderById;
		}
		else
		{
			throw new RepositoryException("Cann´t find product with specified ID");
		}
	}

	@Override
	public List<Order> getOrdersByUser(String username) throws RepositoryException
	{
		ArrayList<Order> ordersByUser = new ArrayList<Order>();
		for (Order order : ordersStorage.getAll().values())
		{
			if (order.getCustomer().equals(username))
			{
				ordersByUser.add(order);
			}
		}
		if (ordersByUser.isEmpty())
		{
			throw new RepositoryException("No orders for this user");
		}

		return ordersByUser;
	}
}
