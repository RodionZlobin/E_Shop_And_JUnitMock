package se.rodion.ecommerce.model;

import java.io.Serializable;

public final class Product implements Serializable
{
	private static final long serialVersionUID = -509127866486459443L;
	private final int productId;
	private String productName;
	private String productDescription;
	private double productPrice;

	public Product(int productId,
			String productName,
			String productDescription,
			double productPrice)
	{
		this.productId = productId;
		this.productName = productName;
		this.productDescription = productDescription;
		this.productPrice = productPrice;
	}

	public int getProductId()
	{
		return productId;
	}

	public String getProductName()
	{
		return productName;
	}

	public String getProductDescription()
	{
		return productDescription;
	}

	public double getProductPrice()
	{
		return productPrice;
	}

//	public double getProductTotalCost()
//	{
//		return productQuantity * productPrice;
//	}

	@Override
	public String toString()
	{
		return "ID: " + productId +
				" Prdct name: " + productName +
				" Prdct descr: " + productDescription;
	}

	@Override
	public boolean equals(Object other)
	{
		if (other == this)
		{
			return true;
		}

		if (other instanceof Product)
		{
			Product otherProduct = (Product) other;

			if (this.productId == otherProduct.productId
					&& this.productName.equals(otherProduct.productName)
					&& this.productDescription.equals(otherProduct.productDescription))
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
		result += 37 * this.productId;
		result += 37 * this.productName.hashCode();
		result += 37 * this.productDescription.hashCode();

		return result;
	}
}
