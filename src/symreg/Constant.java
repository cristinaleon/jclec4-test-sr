package symreg;

import org.apache.commons.configuration.Configuration;

import net.sf.jclec.IConfigure;
import net.sf.jclec.exprtree.IPrimitive;
import net.sf.jclec.exprtree.fun.AbstractPrimitive;
import net.sf.jclec.exprtree.fun.ExprTreeFunction;

public class Constant extends AbstractPrimitive implements IConfigure
{
	/**	 */
	
	private static final long serialVersionUID = -1663054943360509202L;
	
	protected double value;
	
	public Constant()
	{
		super(new Class<?> [0], Double.class);
	}
	
	protected Constant(double value)
	{
		this();
		setValue(value);
	}
	
	public void setValue(double value) {
		this.value = value;		
	}
	
	public double getValue() {
		return this.value;
	}
	
	@Override
	protected void evaluate(ExprTreeFunction context) 
	{
		push(context, value);
	}

	@Override
	public IPrimitive copy() 
	{
		return new Constant(value);
	}

	@Override
	public IPrimitive instance() 
	{
		return new Constant(value);
	}

	@Override
	public void configure(Configuration settings) 
	{
		double v = settings.getDouble("value");
		setValue(v);
	}

	//modificado
	public String toString() {
		return "1";
	}
}
