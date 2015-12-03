package se.rodion.ecommerce.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public final class Storage<K, V>
{

	private final File destination;
	private final Map<K, V> list;

	public Storage(String directory, String filename)
	{
		File dir = new File(directory);
		this.destination = new File(dir, filename);
		this.list = new HashMap<>();

		if (!dir.exists())
		{
			dir.mkdir();
		}
		restore();
	}

	private void persist()
	{
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(destination)))
		{
			out.writeObject(list);
		}
		catch (IOException e)
		{
			throw new RuntimeException("Could not persist users", e);
		}
	}

	@SuppressWarnings("unchecked")
	private void restore()
	{
		if (destination.exists())
		{

			try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(destination)))
			{
				this.list.putAll((Map<K, V>) in.readObject());
			}
			catch (IOException | ClassNotFoundException e)
			{
				throw new RuntimeException("Could not restore storage", e);
			}
		}
	}

	public void add(K key, V value)
	{
		list.put(key, value);
		persist();
	}

	public void addAll(Map<K, V> list)
	{
		this.list.putAll(list);
		persist();
	}

	public Map<K, V> getAll()
	{
		return new HashMap<>(list);
	}

	public Map<K, V> clearAll()
	{
		list.clear();
		return new HashMap<>(list);
	}
}
