package se.rodion.ecommerce.model;

import java.io.Serializable;

public final class Order implements Serializable
{

	private static final long serialVersionUID = -4149774839397172262L;
	private final int orderId;
	private String customer;
	private boolean isShipped;

	private ShoppingCart shoppingCart; 

	public Order(int orderId, User user, ShoppingCart shoppingCart, boolean isShipped) 
	{
		this.orderId = orderId;
		customer = user.getUsername();
		this.shoppingCart = shoppingCart;
		this.isShipped = isShipped;
	}

	public int getOrderId()
	{
		return orderId;
	}

	public String getCustomer()
	{
		return customer;
	}

	public ShoppingCart getShoppingCart()
	{
		return shoppingCart;
	}

	public boolean setShipped(boolean isShipped)
	{
		return this.isShipped;
	}

	@Override
	public String toString()
	{
		return "Order: " + getOrderId() + " user: " + customer + " ordered items: " + shoppingCart;
	}

	@Override
	public boolean equals(Object other)
	{
		if (this == other)
		{
			return true;
		}
		if (other instanceof Order)
		{
			Order order = (Order) other;
			if (this.orderId == order.getOrderId()
					&& this.customer.equals(order.getCustomer())
					&& this.shoppingCart.equals(order.getShoppingCart()))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public int hashCode()
	{
		int result = 1;
		result += 37 * this.orderId;
		result += 37 * this.customer.hashCode();
		result += 37 * this.shoppingCart.hashCode();

		return result;
	}
}
