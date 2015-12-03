package se.rodion.ecommerce.repository;

import java.util.Map;

import se.rodion.ecommerce.exceptions.RepositoryException;
import se.rodion.ecommerce.model.User;

public interface UserRepository
{
	User createUser(User user) throws RepositoryException;

	User deleteUser(User user) throws RepositoryException;

	Map<Integer, User> readUsers();

	User updateUser(User user) throws RepositoryException;

	User getUserById(int userId) throws RepositoryException;
}
