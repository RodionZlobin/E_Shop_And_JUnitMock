package se.rodion.ecommerce.service;

import se.rodion.ecommerce.exceptions.RepositoryException;
import se.rodion.ecommerce.exceptions.ServiceException;
import se.rodion.ecommerce.model.Order;
import se.rodion.ecommerce.model.Product;
import se.rodion.ecommerce.model.ShoppingCart;
import se.rodion.ecommerce.model.User;
import se.rodion.ecommerce.repository.IDGenerator;
import se.rodion.ecommerce.repository.OrderRepository;
import se.rodion.ecommerce.repository.ProductRepository;
import se.rodion.ecommerce.repository.UserRepository;

import java.util.List;
import java.util.Map;

public class ECommerceService
{
	private UserRepository userRepository;
	private ProductRepository productRepository;
	private OrderRepository orderRepository;
	private IDGenerator orderIdGenerator;

	public ECommerceService(UserRepository userRepository,
			ProductRepository productRepository,
			OrderRepository orderRepository,
			IDGenerator orderIdGenerator) throws ServiceException
	{
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.orderRepository = orderRepository;
		this.orderIdGenerator = orderIdGenerator;
	}

	public void addClient(User user) throws ServiceException, RepositoryException
	{
		if (checkUsername(user))
		{
			if (checkPassword(user))
			{
				if (ageControl(user) && checkUsername(user))
				{
					userRepository.createUser(user);
				}
				else
				{
					throw new ServiceException("Customer is too young");
				}
			}
			else
			{
				throw new ServiceException("Please check password creating rules");
			}
		}
		else
		{
			throw new ServiceException("Username could not be longer than 10 symbols");
		}
	}

	public void removeClient(User user) throws RepositoryException
	{
		try
		{
			userRepository.deleteUser(user);
		}
		catch (RuntimeException e)
		{
			e.printStackTrace();
		}
	}

	public void replaceClient(User user) throws ServiceException, RepositoryException
	{
		if (ageControl(user))
		{
			userRepository.updateUser(user);
		}
		else
		{
			throw new ServiceException("Customer is too young");
		}

	}

	public User getUserbyId(int userId) throws RepositoryException
	{
		return userRepository.getUserById(userId);
	}

	public Map<Integer, User> getAllUsers() throws ServiceException
	{
		try
		{
			return userRepository.readUsers();
		}
		catch (RuntimeException e)
		{
			throw new ServiceException("Could not get Users");
		}
	}

	public void addItem(Product product) throws RepositoryException
	{
		try
		{
			productRepository.createProduct(product);
		}
		catch (RuntimeException e)
		{
			e.printStackTrace();
		}
	}

	public void removeItem(Product product) throws RepositoryException
	{
		try
		{
			productRepository.deleteProduct(product);
		}
		catch (RuntimeException e)
		{
			e.printStackTrace();
		}
	}

	public void replaceItem(Product product) throws RepositoryException
	{
		try
		{
			productRepository.updateProduct(product);
		}
		catch (RuntimeException e)
		{
			e.printStackTrace();
		}
	}

	public Product getItembyId(int productId) throws RepositoryException
	{
		return productRepository.getProductById(productId);
	}

	public Map<Integer, Product> getAllItems() throws ServiceException
	{
		try
		{
			return productRepository.readProducts();
		}
		catch (RuntimeException e)
		{
			throw new ServiceException("Could not get Products");
		}
	}

	public Order createOrder(User user, ShoppingCart shoppingCart) throws ServiceException, RepositoryException
	{
		Order creatingOrder = null;

		if (shoppingCart.totalAmountOfShoppingCart() < 50000 &&
				!(shoppingCart.getShoppingCart().isEmpty()))
		{
			try
			{
				int newOrderId = orderIdGenerator.getNextOrderId();
				creatingOrder = new Order(newOrderId, user, shoppingCart, false);
				orderRepository.createOrder(creatingOrder);
			}
			catch (RuntimeException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			throw new ServiceException("Order value could not be more than 50 000 or empty");
		}
		return creatingOrder;
	}

	public Order getOrderById(int orderId) throws ServiceException, RepositoryException
	{
		try
		{
			return orderRepository.getOrderById(orderId);
		}
		catch (RuntimeException e)
		{
			throw new ServiceException("Could not get order: " + e.getMessage(), e);
		}
	}

	public List<Order> getOrdersByUser(String customersUsername) throws ServiceException
	{
		try
		{
			List<Order> ordersByCustomer = orderRepository.getOrdersByUser(customersUsername);
			return ordersByCustomer;
		}
		catch (RepositoryException e)
		{
			throw new ServiceException("Could not get orders from this customer");
		}
	}

	public void replaceOrder(Order order) throws ServiceException, RepositoryException
	{
		if (isShippedControl(order))
		{
			throw new ServiceException("This order is already shipped");
		}
		else
		{
			orderRepository.updateOrder(order);
		}

	}

	public void removeOrder(Order order) throws ServiceException, RepositoryException
	{
		if (isShippedControl(order))
		{
			throw new ServiceException("This order is already shipped");
		}
		else
		{
			orderRepository.deleteOrder(order);
		}

	}

	public Map<Integer, Order> getAllOrders() throws ServiceException
	{
		try
		{
			return orderRepository.readOrders();
		}
		catch (RuntimeException e)
		{
			throw new ServiceException("Could not get orders");
		}
	}

	private boolean ageControl(User user)
	{
		if (user.getAge() >= 18)
		{
			return true;
		}
		return false;
	}

	// private boolean inStockControl(Product product)
	// {
	// if (product.setStatusInStock(true))
	// {
	// return true;
	// }
	// return false;
	// }

	private boolean isShippedControl(Order order)
	{
		if (order.setShipped(true))
		{
			return true;
		}
		return false;
	}

	private boolean checkUsername(User user)
	{
		if (user.getUsername().length() < 10)
		{
			return true;
		}
		return false;
	}

	private boolean checkPassword(User user)
	{
		if (user.getPassword() == null || user.getPassword().trim().isEmpty())
		{
			return false;
		}

		boolean digits = false;
		boolean versal = false;
		boolean specialCharacter = false;
		int counterNumbers = 0;

		for (int i = 0; i < user.getPassword().length(); i++)
		{
			if (user.getPassword().substring(i, i + 1).matches("[A-ZÅÖÄa-zåöä\\d\\p{Punct}]+"))
			{
				// check for all decimal digits (0-9)
				if (user.getPassword().substring(i, i + 1).matches("\\d+"))
				{
					counterNumbers++;

					if (counterNumbers >= 2)
					{
						digits = true;
					}
				}

				// An uppercase letter / Pattern
				// if (user.getPassword().substring(i, i+1).matches("\\p{Lu}+"))
				if (user.getPassword().substring(i, i + 1).matches("[A-ZÅÄÖ]+"))
				{
					versal = true;
				}

				// Punctuation (\\p{Punct}) or all characters except all
				// letters, digits and whitespace [^\\s\\d\\p{L}]
				if (user.getPassword().substring(i, i + 1).matches("\\p{Punct}+"))
				{
					specialCharacter = true;
				}
			}
			else
			{
				return false;
			}
		}

		return (digits && versal && specialCharacter);
	}
}
