package symreg;

import java.util.Comparator;

import net.sf.jclec.IConfigure;
import net.sf.jclec.IFitness;
import net.sf.jclec.IIndividual;
import net.sf.jclec.base.AbstractEvaluator;
import net.sf.jclec.exprtree.ExprTree;
import net.sf.jclec.exprtree.ExprTreeIndividual;
import net.sf.jclec.exprtree.fun.ExprTreeFunction;
import net.sf.jclec.fitness.SimpleValueFitness;
import net.sf.jclec.fitness.ValueFitnessComparator;

import org.apache.commons.configuration.Configuration;

/**
 * ExprTreeFunction execution example
 * 
 * @author Sebastian Ventura
 * @author Alberto Cano
 */

public class SymregEvaluatorDerivativeRms_nuclear_8 extends AbstractEvaluator implements IConfigure {
	private static final long serialVersionUID = 1L;

	private static final Comparator<IFitness> COMPARATOR = new ValueFitnessComparator(true);


	private double [] px1values;
	private double [] py1values;
	private double [] pz1values;


	private double [] px2values;
	private double [] py2values;
	private double [] pz2values;


	private double [] metxvalues;
	private double [] metyvalues;
	private double [] mhiggs_squaredvalues;	


	//////////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Protected methods
	//////////////////////////////////////////////////////////////////////

	
	public double[] getPx1values()
	{
		return this.px1values;
	}
	
	public double[] getPy1values()
	{
		return this.py1values;
	}
	
	public double[] getPz1values()
	{
		return this.pz1values;
	}
	
	public double[] getPx2values()
	{
		return this.px2values;
	}
	
	public double[] getPy2values()
	{
		return this.py2values;
	}
	
	public double[] getPz2values()
	{
		return this.pz2values;
	}
	
	
	public double[] getMetxvalues()
	{
		return this.metxvalues;
	}
	
	public double[] getMetyvalues()
	{
		return this.metyvalues;
	}
	
	public double[] getMhiggs_squaredvalues()
	{
		return this.mhiggs_squaredvalues;
	}
	
	@Override
	public void configure(Configuration settings) {

		String[] px1 = settings.getStringArray("px1values");
		String[] py1 = settings.getStringArray("py1values");
		String[] pz1 = settings.getStringArray("pz1values");

		String[] px2 = settings.getStringArray("px2values");
		String[] py2 = settings.getStringArray("py2values");
		String[] pz2 = settings.getStringArray("pz2values");

		String[] metx = settings.getStringArray("metxvalues");
		String[] mety = settings.getStringArray("metyvalues");
		String[] mhiggs_squared = settings.getStringArray("mhiggs_squaredvalues");

		int numberElements = px1.length;


		 px1values = new double[numberElements]; 
		 py1values = new double[numberElements]; 
		 pz1values = new double[numberElements]; 
 
		 px2values = new double[numberElements]; 
		 py2values = new double[numberElements]; 
		 pz2values = new double[numberElements]; 

		 metxvalues = new double[numberElements]; 
		 metyvalues = new double[numberElements]; 
		 mhiggs_squaredvalues = new double[numberElements]; 
		 
		System.out.println("--->" + numberElements);

		for (int i = 0; i < numberElements; i++) {

			 px1values[i] = Double.parseDouble(px1[i]);
			 py1values[i] = Double.parseDouble(py1[i]);
			 pz1values[i] = Double.parseDouble(pz1[i]);

			 px2values[i] = Double.parseDouble(px2[i]);
			 py2values[i] = Double.parseDouble(py2[i]);
			 pz2values[i] = Double.parseDouble(pz2[i]);

			 metxvalues[i] = Double.parseDouble(metx[i]);
			 metyvalues[i] = Double.parseDouble(mety[i]);
			 mhiggs_squaredvalues[i] = Double.parseDouble(mhiggs_squared[i]);
		}
	}

	@Override
	protected void evaluate(IIndividual ind) {
		// Individual genotype
		ExprTree genotype = ((ExprTreeIndividual) ind).getGenotype();

		ExprTreeFunction function = new ExprTreeFunction(genotype);

		// Estimated values
		// Queriendo predecir la masa del bosón de higgs al cuadrado
		double[] mhiggs_squared = new double[px1values.length];

		for (int i = 0; i < px1values.length; i++)
			mhiggs_squared[i] = function.<Double> execute( px1values[i],py1values[i],pz1values[i],px2values[i],py2values[i],pz2values[i],metxvalues[i],metyvalues[i]);

		// Pass all
		double rms = 0.0;

		for (int i = 0; i <  mhiggs_squaredvalues.length; i++) {
			double diff =  mhiggs_squared[i] -  mhiggs_squaredvalues[i];
			rms += diff * diff;
		}

		rms = Math.sqrt(rms);
		ind.setFitness(new SimpleValueFitness(rms));

	}

	@Override
	public Comparator<IFitness> getComparator() {
		return COMPARATOR;
	}
}