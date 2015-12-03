import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import se.rodion.ecommerce.exceptions.ModelException;
import se.rodion.ecommerce.exceptions.RepositoryException;
import se.rodion.ecommerce.exceptions.ServiceException;
import se.rodion.ecommerce.model.Order;
import se.rodion.ecommerce.model.OrderRaw;
import se.rodion.ecommerce.model.Product;
import se.rodion.ecommerce.model.ShoppingCart;
import se.rodion.ecommerce.model.User;
import se.rodion.ecommerce.repository.FileOrderRepository;
import se.rodion.ecommerce.repository.FileProductRepository;
import se.rodion.ecommerce.repository.FileUserRepository;
import se.rodion.ecommerce.repository.OrderIdGenerator;
import se.rodion.ecommerce.repository.OrderRepository;
import se.rodion.ecommerce.repository.ProductRepository;
import se.rodion.ecommerce.repository.UserRepository;
import se.rodion.ecommerce.service.ECommerceService;

@RunWith(MockitoJUnitRunner.class)
public class EShopTest
{

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Mock
	private UserRepository userRepositoryMock;
	@Mock
	private OrderRepository orderRepositoryMock;
	@Mock
	private ProductRepository productRepositoryMock;
	@Mock
	private OrderIdGenerator orderIdGeneratorMock;

	private ECommerceService ecomService;

	private final User user1 = new User(1, "Erik", "Olofsson", 20);
	private final User user2 = new User(2, "Samuel", "Bergstr/22om", 25);
	private final User user3 = new User(3, "Peter", "Wolff12/", 20);
	private final User user4 = new User(4, "KarolinaKarolina", "Jansson12/", 28);
	private final User user5 = new User(4, "KarolinaKarolina", "Jansson12/", 28);

	private final Product product1 = new Product(10, "Bord", "green", 500);
	private final Product product2 = new Product(20, "Skap", "red", 4000);
	private final Product product3 = new Product(40, "Stol", "vit", 100);
	private final Product product4 = new Product(40, "Stol", "vit", 100);

	private final ShoppingCart vagn1 = new ShoppingCart();
	private final ShoppingCart vagn2 = new ShoppingCart();
	
	private final OrderRaw orderRaw1 = new OrderRaw(product1, 90);
	private final OrderRaw orderRaw2 = new OrderRaw(product2, 10);
	
	

	@Before
	public void setup() throws ServiceException
	{
		userRepositoryMock = mock(FileUserRepository.class);
		orderRepositoryMock = mock(FileOrderRepository.class);
		productRepositoryMock = mock(FileProductRepository.class);
		orderIdGeneratorMock = mock(OrderIdGenerator.class);
		ecomService = new ECommerceService(userRepositoryMock,
				productRepositoryMock,
				orderRepositoryMock,
				orderIdGeneratorMock);
	}

	@Test
	public void orderWithValueOver50KIsNotAcceptable() throws ServiceException, ModelException, RepositoryException
	{
		thrown.expect(ServiceException.class);
		thrown.expectMessage("Order value could not be more than 50 000 or empty");

		vagn1.addOrderRaw(orderRaw1);
		vagn1.addOrderRaw(orderRaw2);

		ecomService.createOrder(user3, vagn1);

		verify(orderRepositoryMock).createOrder(any());
	}

	@Test
	public void orderWithEmptyShoppingCartIsNotAcceptable() throws ServiceException, ModelException, RepositoryException
	{
		thrown.expect(ServiceException.class);
		thrown.expectMessage("Order value could not be more than 50 000 or empty");

		ecomService.createOrder(user1, vagn1);

		verify(orderRepositoryMock).createOrder(any());
	}

	@Test
	public void orderGetIdEfterRegistration() throws ModelException, ServiceException, RepositoryException
	{
		vagn1.addOrderRaw(orderRaw1);

		when(orderIdGeneratorMock.getNextOrderId()).thenReturn(5);

		Order order1 = ecomService.createOrder(user3, vagn1);

		assertThat(order1.getOrderId(), is(equalTo(5)));

		verify(orderIdGeneratorMock, times(1)).getNextOrderId();
		verify(orderRepositoryMock, times(1)).createOrder(any());
	}

	@Test
	public void userWithLongNameIsNotAcceptable() throws ServiceException, RepositoryException
	{
		thrown.expect(ServiceException.class);
		thrown.expectMessage("Username could not be longer than 10 symbols");

		ecomService.addClient(user4);

		verify(userRepositoryMock).createUser(user3);
	}

	@Test
	public void userWithEasyPasswordIsNotAcceptable() throws ServiceException, RepositoryException
	{
		thrown.expect(ServiceException.class);
		thrown.expectMessage("Please check password creating rules");

		when(userRepositoryMock.createUser(any())).thenReturn(user1);

		ecomService.addClient(user1);

		verify(userRepositoryMock).createUser(user1);
	}
	
	
	@Test
	public void getUserById() throws RepositoryException, ServiceException
	{
		when(userRepositoryMock.getUserById(3)).thenReturn(user3);

		User user = ecomService.getUserbyId(3);

		verify(userRepositoryMock).getUserById(3);
		assertThat(user, is(equalTo(user3)));
	}

	@Test
	public void getAllUsers() throws ServiceException
	{
		Map<Integer, User> users = new HashMap<>();
		users.put(user2.getUserId(), user2);
		users.put(user3.getUserId(), user3);

		when(userRepositoryMock.readUsers()).thenReturn(users);

		Map<Integer, User> allUsers = ecomService.getAllUsers();

		verify(userRepositoryMock).readUsers();
		assertThat(users, is(equalTo(allUsers)));
	}

	@Test
	public void createNewUser() throws ServiceException, RepositoryException
	{
		
		ecomService.addClient(user3);

		verify(userRepositoryMock).createUser(user3);
	}

	@Test
	public void updateUser() throws ServiceException, RepositoryException
	{
		ecomService.replaceClient(user3);

		verify(userRepositoryMock, times(1)).updateUser(user3);
	}

	@Test
	public void deleteUser() throws ServiceException, RepositoryException
	{
		ecomService.removeClient(user3);

		verify(userRepositoryMock).deleteUser(user3);
	}

	@Test
	public void getProductById() throws RepositoryException, ServiceException
	{
		when(productRepositoryMock.getProductById(20)).thenReturn(product2);

		Product product = ecomService.getItembyId(20);

		verify(productRepositoryMock).getProductById(20);
		assertThat(product, is(equalTo(product2)));
	}

	@Test
	public void getAllProducts() throws ServiceException
	{
		Map<Integer, Product> products = new HashMap<>();
		products.put(product1.getProductId(), product1);
		products.put(product2.getProductId(), product2);

		when(productRepositoryMock.readProducts()).thenReturn(products);

		Map<Integer, Product> allProducts = ecomService.getAllItems();

		verify(productRepositoryMock).readProducts();
		assertThat(products, is(equalTo(allProducts)));
	}

	@Test
	public void createNewProduct() throws ServiceException, RepositoryException
	{
		ecomService.addItem(product1);

		verify(productRepositoryMock).createProduct(product1);
	}

	@Test
	public void updateProduct() throws ServiceException, RepositoryException
	{
		ecomService.replaceItem(product1);

		verify(productRepositoryMock, times(1)).updateProduct(product1);
	}

	@Test
	public void deleteProduct() throws ServiceException, RepositoryException
	{
		ecomService.removeItem(product1);

		verify(productRepositoryMock).deleteProduct(product1);
	}

	@Test
	public void getOrderById() throws RepositoryException, ServiceException, ModelException
	{
		vagn1.addOrderRaw(orderRaw1);

		Order order1 = new Order(10, user2, vagn1, false);
		Order order2 = ecomService.createOrder(user2, vagn1);

		when(orderRepositoryMock.getOrderById(10)).thenReturn(order1);
		when(orderRepositoryMock.getOrderById(order2.getOrderId())).thenReturn(order2);

		Order order3 = ecomService.getOrderById(10);
		Order order4 = ecomService.getOrderById(order2.getOrderId());

		verify(orderRepositoryMock).getOrderById(10);
		assertThat(order1, is(equalTo(order3)));

		verify(orderRepositoryMock).getOrderById(order2.getOrderId());
		assertThat(order2, is(equalTo(order4)));
	}

	@Test
	public void getAllOrders() throws ServiceException, ModelException, RepositoryException
	{
		vagn1.addOrderRaw(orderRaw1);
		vagn2.addOrderRaw(orderRaw2);
		Order order1 = ecomService.createOrder(user2, vagn1);
		Order order2 = ecomService.createOrder(user3, vagn2);

		Map<Integer, Order> orders = new HashMap<>();
		orders.put(order1.getOrderId(), order1);
		orders.put(order2.getOrderId(), order2);

		when(orderRepositoryMock.readOrders()).thenReturn(orders);

		Map<Integer, Order> allOrders = ecomService.getAllOrders();

		verify(orderRepositoryMock, times(2)).createOrder(any());
		verify(orderRepositoryMock).readOrders();
		assertThat(orders, is(equalTo(allOrders)));
	}
	
	@Test
	public void getOrdersFromSpecifiedUser() throws ModelException, ServiceException, RepositoryException
	{
		vagn1.addOrderRaw(orderRaw1);
		vagn2.addOrderRaw(orderRaw2);
		
		Order order1 = ecomService.createOrder(user2, vagn1);
		Order order2 = ecomService.createOrder(user3, vagn2);
		Order order3 = ecomService.createOrder(user3, vagn1);
		
		List<Order> orders = new ArrayList<Order>();
		orders.add(order2);
		orders.add(order3);
		
		when(orderRepositoryMock.getOrdersByUser(user3.getUsername())).thenReturn(orders);
		
		List<Order> ordersFromSpecifiedUser = ecomService.getOrdersByUser(user3.getUsername());
		
		assertThat(orders, is(equalTo(ordersFromSpecifiedUser)));
		assertThat(ordersFromSpecifiedUser.size(), is(equalTo(2)));
		
		verify(orderRepositoryMock, times(1)).getOrdersByUser(user3.getUsername());
		verify(orderRepositoryMock, times(3)).createOrder(any());
	}
	
	@Test
	public void createNewOrder() throws ServiceException, ModelException, RepositoryException
	{
		vagn1.addOrderRaw(orderRaw1);

		ecomService.createOrder(user2, vagn1);

		verify(orderRepositoryMock).createOrder(any());
	}

	@Test
	public void updateOrder() throws ServiceException, ModelException, RepositoryException
	{
		vagn1.addOrderRaw(orderRaw1);
		Order order1 = ecomService.createOrder(user2, vagn1);

		ecomService.replaceOrder(order1);

		verify(orderRepositoryMock, times(1)).updateOrder(order1);
	}

	@Test
	public void deleteOrder() throws ServiceException, ModelException, RepositoryException
	{
		vagn1.addOrderRaw(orderRaw1);
		Order order1 = ecomService.createOrder(user2, vagn1);

		ecomService.removeOrder(order1);

		verify(orderRepositoryMock).deleteOrder(order1);
	}

	@Test
	public void similarOrdersAreEquals() throws ServiceException, ModelException, RepositoryException
	{
		vagn1.addOrderRaw(orderRaw2);
		Order newOrder1 = ecomService.createOrder(user3, vagn1);
		Order newOrder2 = ecomService.createOrder(user3, vagn1);

		assertThat(newOrder1, is(equalTo(newOrder2)));
	}

	@Test
	public void similarOrdersHaveSameHashcode() throws ServiceException, ModelException, RepositoryException
	{
		vagn1.addOrderRaw(orderRaw2);
		Order newOrder1 = ecomService.createOrder(user3, vagn1);
		Order newOrder2 = ecomService.createOrder(user3, vagn1);

		assertThat(newOrder1.hashCode(), is((equalTo(newOrder2.hashCode()))));
	}

	@Test
	public void similarProductsAreEquals()
	{
		assertThat(product3, is(equalTo(product4)));
	}

	@Test
	public void similarProductsHaveSameHashcode()
	{
		assertThat(product3.hashCode(), is(equalTo(product4.hashCode())));
	}

	@Test
	public void similarUsersAreEquals()
	{
		assertThat(user4, is(equalTo(user5)));
	}

	@Test
	public void simialrUsersHaveSameHashcode()
	{
		assertThat(user4.hashCode(), is(equalTo(user5.hashCode())));
	}
}
