package se.rodion.ecommerce.repository;

import java.util.Map;

import se.rodion.ecommerce.exceptions.RepositoryException;
import se.rodion.ecommerce.model.Product;

public interface ProductRepository
{
	void createProduct(Product product) throws RepositoryException;

	void updateProduct(Product product) throws RepositoryException;

	void deleteProduct(Product product) throws RepositoryException;

	Map<Integer, Product> readProducts();

	Product getProductById(int productId) throws RepositoryException;
}
