package se.rodion.ecommerce.repository;

import java.util.Map;

import se.rodion.ecommerce.exceptions.RepositoryException;
import se.rodion.ecommerce.model.Product;

public class FileProductRepository implements ProductRepository
{
	Storage<Integer, Product> productsStorage = new Storage<Integer, Product>("src/storage/", "products.data");

	@Override
	public void createProduct(Product product) throws RepositoryException
	{
		try
		{
			productsStorage.add(product.getProductId(), product);
		}
		catch (RuntimeException e)
		{
			throw new RepositoryException("Couldn´t create a product", e);
		}
	}

	@Override
	public void updateProduct(Product product) throws RepositoryException
	{
		try
		{
			if (productsStorage.getAll().containsKey(product.getProductId()))
			{
				Map<Integer, Product> updatedProducts = productsStorage.getAll();
				updatedProducts.replace(product.getProductId(), product);
				productsStorage.clearAll();
				productsStorage.addAll(updatedProducts);
			}
			else
			{
				createProduct(product);
			}
		}
		catch (RuntimeException e)
		{
			throw new RepositoryException("Couldn´t update a product", e);
		}
	}

	@Override
	public void deleteProduct(Product product) throws RepositoryException
	{
		try
		{
			Map<Integer, Product> productsAfterDelete = productsStorage.getAll();
			productsAfterDelete.remove(product.getProductId());
			productsStorage.clearAll();
			productsStorage.addAll(productsAfterDelete);
		}
		catch (RuntimeException e)
		{
			throw new RepositoryException("Couldn´t delete a product", e);
		}
	}

	@Override
	public Map<Integer, Product> readProducts()
	{
		Map<Integer, Product> productsFromFile = productsStorage.getAll();

		return productsFromFile;
	}

	@Override
	public Product getProductById(int productId) throws RepositoryException
	{
		Product getProductById;
		if (productsStorage.getAll().containsKey(productId))
		{
			getProductById = productsStorage.getAll().get(productId);
			return getProductById;
		}
		else
		{
			throw new RepositoryException("Cann´t find product with specified ID");
		}
	}

}
