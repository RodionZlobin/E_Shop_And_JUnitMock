package se.rodion.ecommerce.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import se.rodion.ecommerce.exceptions.ModelException;

public final class ShoppingCart implements Serializable
{
	private static final long serialVersionUID = -7922050282371173699L;
	private List<OrderRaw> shoppingCart = new ArrayList<>();

	public OrderRaw addOrderRaw(OrderRaw orderRaw) throws ModelException
	{
		for (OrderRaw orderRawInSC : shoppingCart)
		{
			if (orderRaw.getProduct().equals(orderRawInSC.getProduct()))
			{
				OrderRaw updatedOrderRaw = new OrderRaw(orderRawInSC.getProduct(),
						orderRawInSC.getOrderedQuantity() + orderRaw.getOrderedQuantity());
				shoppingCart.set(shoppingCart.indexOf(orderRawInSC), updatedOrderRaw);
				return updatedOrderRaw;
			}
		}

		try
		{
			shoppingCart.add(orderRaw);
		}
		catch (RuntimeException e)
		{
			throw new ModelException("Sorry, please check orderraw");
		}

		return orderRaw;
	}

	public void removeOrderRaw(OrderRaw orderRaw) throws ModelException
	{

		try
		{
			shoppingCart.remove(orderRaw);
		}
		catch (RuntimeException e)
		{
			e.printStackTrace();
		}
	}

	public List<OrderRaw> getShoppingCart()
	{
		return shoppingCart;
	}

	public double totalAmountOfShoppingCart()
	{
		double totalAmount = 0;

		for (OrderRaw orderRaw : getShoppingCart())
		{
			totalAmount += orderRaw.getOrderRawCost();
		}
		return totalAmount;
	}

	public void clear()
	{
		shoppingCart.clear();
	}

	@Override
	public String toString()
	{
		return "Shopping card contains: " + shoppingCart;
	}

	@Override
	public boolean equals(Object other)
	{
		if (this == other)
		{
			return true;
		}
		if (other instanceof ShoppingCart)
		{
			ShoppingCart shopcard = (ShoppingCart) other;
			if (this.shoppingCart.equals(shopcard.getShoppingCart()))
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
		result += 37 * this.shoppingCart.hashCode();

		return result;
	}
}
