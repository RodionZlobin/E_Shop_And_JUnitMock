package se.rodion.ecommerce.model;

import java.io.Serializable;

public final class User implements Serializable
{
	private static final long serialVersionUID = -5121154071071761628L;
	private final int userId;
	private String password;
	private String username;
	private int age;

	public User(
			int userId,
			String username,
			String password, int age)
	{
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.age = age;

	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

	public int getUserId()
	{
		return userId;
	}

	public int getAge()
	{
		return age;
	}

	@Override
	public String toString()
	{
		return getUsername() + " " + getPassword() + " " + getUserId();
	}

	@Override
	public boolean equals(Object other)
	{
		if (other == this)
		{
			return true;
		}

		if (other instanceof User)
		{
			User otherUser = (User) other;

			if (this.username.equals(otherUser.username)
					&& this.password.equals(otherUser.password)
					&& this.userId == otherUser.userId
					&& this.age == otherUser.age)
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
		result += 37 * this.username.hashCode();
		result += 37 * this.password.hashCode();
		result += 37 * this.userId;
		result += 37 * this.age;

		return result;
	}
}
