package symreg;

import net.sf.jclec.exprtree.fun.AbstractPrimitive;
import net.sf.jclec.exprtree.fun.ExprTreeFunction;

public class Raiz extends AbstractPrimitive 
{
	private static final long serialVersionUID = 3388520662658073406L;

	/**
	 * This operator receives a double array as arguments and return
	 * a double array as result.
	 */
	
	public Raiz() 
	{
		super(new Class<?> [] { Double.class }, Double.class);
	}

	@Override
	protected void evaluate(ExprTreeFunction context) 
	{
		// Get arguments (in context stack)
		Double arg1 = pop(context);
		
		// Push result in context stack		
		if (arg1 <= 0.0){
			arg1 = 0.0;
			push(context, arg1);	
		} else{
			push(context, Math.sqrt(arg1));	
		}	
		
	}

	// java.lang.Object methods
	
	public boolean equals(Object other)
	{
		return other instanceof Raiz;
	}	
	
	public String toString()
	{
		return "R";
	}	
}
