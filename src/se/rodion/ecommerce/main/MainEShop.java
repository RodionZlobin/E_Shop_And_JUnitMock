package se.rodion.ecommerce.main;

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
import se.rodion.ecommerce.repository.IDGenerator;
import se.rodion.ecommerce.repository.OrderIdGenerator;
import se.rodion.ecommerce.repository.OrderRepository;
import se.rodion.ecommerce.repository.ProductRepository;
import se.rodion.ecommerce.repository.UserRepository;
import se.rodion.ecommerce.service.ECommerceService;

public class MainEShop
{

	public static void main(String[] args) throws ServiceException, ModelException, RepositoryException 
	{
		UserRepository ur = new FileUserRepository();
		ProductRepository pr = new FileProductRepository();
		OrderRepository or = new FileOrderRepository();
		IDGenerator og = new OrderIdGenerator(5);
		ECommerceService ecom = new ECommerceService(ur, pr, or, og);
		
//		Product product1 = new Product(1, "Bord", "Rod", 100);
//		Product product2 = new Product(2, "Stol", "Gron", 50);
		
//		ecom.addItem(product1);
//		
//		ShoppingCart SC1 = new ShoppingCart();
//		ShoppingCart SC2 = new ShoppingCart();
//		
//		OrderRaw orderRaw1 = new OrderRaw(product1, 20);
//		OrderRaw orderRaw2 = new OrderRaw(product2, 500);
//		OrderRaw orderRaw3 = new OrderRaw(product1, 7);
		
//		
//		SC1.addOrderRaw(orderRaw1);
//		SC1.addOrderRaw(orderRaw1);
//		SC1.addOrderRaw(orderRaw2);
		
//		User user = new User (1, "Erik", "ÅndeYÅrs22å/", 19);
//		User user2 = new User (2, "Tomas", "Tomasson11/", 19);
//		User user3 = new User(3, "Peter", "Wolff12/", 20);
//		User user3 = new User (3, "Olof", "Olofsson", 18);
//		User user4 = new User (4, "Peter", "Petersso23/n", 23);
//		Product item1 = new Product (12, "Bord", "Röd", 100, 40, true);
//		Product item4 = new Product (13, "Bord", "Grön", 3, 15, false);
//		Product item2 = new Product (14, "Stol", "Röd", 200, 20, true);
//		Product item3 = new Product (15, "Stol", "Grön", 2, 12, true);

//		ecom.addClient(user);
//		ecom.removeClient(user);
//		ecom.addClient(user2);
//		ecom.addClient(user3);
//		ecom.addClient(user2);
//		ecom.addClient(user3);
//		ecom.replaceClient(user2);
//		ecom.removeClient(user2);
//		ecom.removeClient(user4);
//		ecom.addClient(user2);
//		
//		SC1.addOrderRaw(orderRaw1);
//		SC1.addOrderRaw(orderRaw2);
//		SC1.addOrderRaw(orderRaw3);
//		SC2.addOrderRaw(orderRaw2);
		
		
//		ecom.addItem(item2);
//		ecom.addItem(item3);
//		ecom.removeItem(item2);
//		ecom.replaceItem(item2);

//		vagn1.addProduct(item1);
//		vagn1.addProduct(item2);
//		vagn2.addProduct(item2);
//		vagn1.addProduct(item1);
//		vagn1.removeProduct(item1);
//		
//		Order order1 = new Order(1, user, SC1, false);
//		Order order2 = new Order(2, user, vagn1, false);
//		Order order3 = new Order(3, user, vagn2, false);
//		ecom.createOrder(user, SC1);
//		ecom.createOrder(user, SC2);
//		ecom.createOrder(user, vagn1);
//		ecom.createOrder(user2, vagn2);
		
//		ecom.createOrder(user, vagn2);
//		ecom.createOrder(user, vagn1);
//		ecom.addOrder(order2);
//		
		 
//		order1 = new Order(1, user4, vagn1);
//		ecom.replaceOrder(order3);
//		ecom.removeOrder(order1);
//		
//		System.out.println(order1.getCustomer());
//		System.out.println(order2.getOrderId());
//		System.out.println(vagn1.getShoppingCard().toString());
		
		
		
//		ecom.getUserbyId(2);
//		ecom.getAllUsers();

//		ecom.getItembyId(14);
//		ecom.getAllItems();
		
//		ecom.getOrderById(2);
//		ecom.getAllOrders();
		
//		ecom.getOrdersByUser(user.getUsername());
		
		
		

	}

}
