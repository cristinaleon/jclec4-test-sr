package symreg;

import net.sf.jclec.exprtree.fun.AbstractPrimitive;
import net.sf.jclec.exprtree.fun.ExprTreeFunction;

public class AQ extends AbstractPrimitive 
{
	private static final long serialVersionUID = 3388520662658073406L;

	/**
	 * This operator receives two double arrays as arguments and return
	 * a double array as result.
	 */
	
	public AQ() 
	{
		super(new Class<?> [] {Double.class, Double.class}, Double.class);
	}

	@Override
	protected void evaluate(ExprTreeFunction context) 
	{
		// Get arguments (in context stack)
		Double arg1 = pop(context);
		Double arg2 = pop(context);
		// Push result in context stack
		push(context, arg1/Math.sqrt(1+Math.pow(arg2, 2)));
	}

	// java.lang.Object methods
	
	public boolean equals(Object other)
	{
		return other instanceof AQ;
	}	
	
	public String toString()
	{
		return "/";
	}	
}
