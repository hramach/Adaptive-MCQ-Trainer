/**
 * Encapsulates two objects associated with each other.
 * 
 * @author Harish Ramachandran
 *
 * @param <L> the "left" object in the pair
 * @param <R> the "right" object in the pair
 */
public class Pair<L, R>
{
	private L l;
	private R r;
	
	/**
	 * Constructs an object encapsulating two objects associated with each other.
	 * 
	 * @param l
	 * @param r
	 */
	public Pair(L l, R r)
	{
		this.l = l;
		this.r = r;
	}
	
	/**
	 * Returns the left object of the pair.
	 * 
	 * @return the left object of the pair
	 */
	public L getL()
	{
		return l;
	}
	
	/**
	 * Returns the right object of the pair.
	 * 
	 * @return the right object of the pair
	 */
	public R getR()
	{
		return r;
	}
	
	/**
	 * Sets the left object of the pair.
	 * 
	 * @param l the left object of the pair
	 */
	public void setL(L l)
	{
		this.l = l;
	}
	
	/**
	 * Sets the right object of the pair.
	 * 
	 * @param r the right object of the pair
	 */
	public void setR(R r)
	{
		this.r = r;
	}
}
