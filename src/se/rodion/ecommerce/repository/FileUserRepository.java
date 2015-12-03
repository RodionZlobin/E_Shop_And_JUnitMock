package se.rodion.ecommerce.repository;

import java.util.Map;

import se.rodion.ecommerce.exceptions.RepositoryException;
import se.rodion.ecommerce.model.User;

public class FileUserRepository implements UserRepository
{
	Storage<Integer, User> userStorage = new Storage<Integer, User>("src/storage/", "users.data");

	@Override
	public User createUser(User user) throws RepositoryException
	{
		try
		{
			userStorage.add(user.getUserId(), user);
		}
		catch (RuntimeException e)
		{
			throw new RepositoryException("Couldn´t create an user", e);
		}
		return user;
	}

	@Override
	public User updateUser(User user) throws RepositoryException
	{
		try
		{
			if (userStorage.getAll().containsKey(user.getUserId()))
			{
				Map<Integer, User> updatedUsers = userStorage.getAll();
				updatedUsers.replace(user.getUserId(), user);
				userStorage.clearAll();
				userStorage.addAll(updatedUsers);
			}
			else
			{
				createUser(user);
			}
		}
		catch (RuntimeException e)
		{
			throw new RepositoryException("Couldn´t update an user", e);
		}
		return user;
	}

	@Override
	public User deleteUser(User user) throws RepositoryException
	{
		try
		{
			Map<Integer, User> usersAfterDelete = userStorage.getAll();
			usersAfterDelete.remove(user.getUserId());
			userStorage.clearAll();
			userStorage.addAll(usersAfterDelete);
		}
		catch (RuntimeException e)
		{
			throw new RepositoryException("Couldn´t delete an user", e);
		}
		
		return user;
	}

	@Override
	public Map<Integer, User> readUsers()
	{
		Map<Integer, User> usersFromFile = userStorage.getAll();

		return usersFromFile;
	}

	@Override
	public User getUserById(int userId) throws RepositoryException
	{
		User getUserById;
		if (userStorage.getAll().containsKey(userId))
		{
			getUserById = userStorage.getAll().get(userId);
			return getUserById;
		}
		else
		{
			throw new RepositoryException("Cann´t find user with specified ID");
		}
	}
}
