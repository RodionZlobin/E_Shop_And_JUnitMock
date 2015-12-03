package se.rodion.ecommerce.model;

import java.io.Serializable;

public final class OrderRaw implements Serializable
{
	
	private static final long serialVersionUID = 5249633984639767076L;
	private double orderedQuantity;
	Product product;
	
	public OrderRaw(Product product, double orderedQuantity)
	{		
		this.product = product;
		this.orderedQuantity = orderedQuantity;
	}
	
	public double getOrderedQuantity()
	{
		return orderedQuantity;
	}
	
	public Product getProduct()
	{
		return product;
	}
	
	public double getOrderRawCost()
	{
		return orderedQuantity * product.getProductPrice();
	}
	
	public void setOrderedQuantity(double orderedQuantity)
	{
		this.orderedQuantity = orderedQuantity;
	}
	
	@Override
	public String toString()
	{
		return product.getProductName() +" "+ orderedQuantity;
	}
}
