package polic72.dimbag.util;


/**
 * Represents a pair of values, like the Tuple seen in C#. Java doesn't have this natively, so here we are.
 * <p/>
 * Super lazy implementation. Does <b>not</b> override the {@link Object#equals(Object)} and 
 * {@link Object#hashCode()} methods.
 * 
 * @author polic72
 *
 * @param <A> The first type of the pair.
 * @param <B> The second type of the pair.
 */
public class Pair<A, B>
{
	/**
	 * The first value.
	 */
	private A first;
	
	
	/**
	 * The second value.
	 */
	private B second;
	
	
	/**
	 * Constructs a pair with the given <i>first<i/> and <i>second<i/> values.
	 * 
	 * @param first The first value of the pair.
	 * @param second The second value of the pair.
	 */
	public Pair(A first, B second)
	{
		this.first = first;
		this.second = second;
	}
	
	
	/**
	 * Gets the first value.
	 * 
	 * @return The first value.
	 */
	public A getFirst()
	{
		return first;
	}
	
	
	/**
	 * Gets the second value.
	 * 
	 * @return The second value.
	 */
	public B getSecond()
	{
		return second;
	}
}
