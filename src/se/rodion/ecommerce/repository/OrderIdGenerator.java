package se.rodion.ecommerce.repository;

import java.util.concurrent.atomic.AtomicInteger;

public class OrderIdGenerator implements IDGenerator
{

	private AtomicInteger orderIDGenerator;
	
	public OrderIdGenerator(int startNumber)
	{
		orderIDGenerator = new AtomicInteger(startNumber);
	}
	
	@Override
	public int getNextOrderId()
	{
		return orderIDGenerator.incrementAndGet();
	}

}
