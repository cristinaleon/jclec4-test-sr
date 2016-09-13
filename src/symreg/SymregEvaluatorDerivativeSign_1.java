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

public class SymregEvaluatorDerivativeSign_1 extends AbstractEvaluator implements IConfigure
{
	private static final long serialVersionUID = 1L;

	private static final Comparator<IFitness> COMPARATOR = new ValueFitnessComparator(true);
	
	private double [] xvalues;
	
	private double [] yvalues;
	
	
	//////////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Protected methods
	//////////////////////////////////////////////////////////////////////
	
	@Override
	public void configure(Configuration settings)
	{
		String[] x = settings.getStringArray("xvalues");
		String[] y = settings.getStringArray("yvalues");
		
		int numberElements = x.length;
		
		xvalues = new double[numberElements];
		yvalues = new double[numberElements];
		
		System.out.println("--->"+numberElements);
		
		for(int i = 0; i < numberElements; i++)
		{
			xvalues[i] = Double.parseDouble(x[i]);
			yvalues[i] = Double.parseDouble(y[i]);
		}
	}

	@Override
	protected void evaluate(IIndividual ind) 
	{
		// Individual genotype
		ExprTree genotype = ((ExprTreeIndividual) ind).getGenotype();

		ExprTreeFunction function = new ExprTreeFunction(genotype);

		// Estimated values
		double [] y = new double[xvalues.length];
		
		for(int i = 0; i<xvalues.length; i++)
			y[i] = function.<Double>execute(xvalues[i]);
		
		// Pass all
		double rms = 0.0;
		
		for (int i=0; i<yvalues.length; i++) {
			double diff = y[i] - yvalues[i];
			rms += diff * diff;
		}
		
		rms = Math.sqrt(rms);
		//ind.setFitness(new SimpleValueFitness(rms));			

				

		// ---- Intervals ---- //		
		double [] yvaluesinter = new double[yvalues.length-1];
		double [] yinter = new double[yvalues.length-1];
		double [] xvaluesinter = new double[yvalues.length-1];


		for (int j = 0; j<yvalues.length-1; j++) {
			yinter[j] = y[j+1] - y[j];                    /* intervalos  y aproximada */
			xvaluesinter[j] =  xvalues[j+1] - xvalues[j]; /* intervalos  x and xaproximada (son los mismos)*/
			yvaluesinter[j] =  yvalues[j+1] - yvalues[j]; /* intervalos  y*/
		}
	
				
		// ---- Y interval -----//
		double [] yvaluesintersigns = new double[yvalues.length-1];
		double [] yintersigns = new double[yvalues.length-1];
		
		for (int i=0; i<yvalues.length-1; i++) {
			yvaluesintersigns[i] =  Math.signum(yvalues[i+1] - yvalues[i]); /* signo de los intervalos  y */
			yintersigns[i] = Math.signum(y[i+1] - y[i]);                    /* signo de los intervalos  de la y aproximada */
		}
		
		// Sign comparation
		double signsum = 0.0;
		for (int i=0; i<yvaluesintersigns.length; i++) {
			 // vector de signos: iguales 1, distintos 0 
			if ( yvaluesintersigns[i] == yintersigns[i] ){signsum += 1.0;} 	
		}
				
		
		double singcomp = (1.0 - signsum/yvaluesintersigns.length); /* ratio de los intervalos que no coinciden en signo*/	
		
		// Introduce signcomp also as fitness
		double lambda = 1.0;
		if (singcomp >= 0.8){
			lambda = 1.1;		 // aumenta rms
		} else if (singcomp >= 0.5){
			lambda = 1.05;		 // aumenta un poco rms
		}
		else{
			lambda = 0.95; //disminuye un poco rms
		}		
		

		double frmssign  = lambda*rms; 
	

        // Set rms frmssign or frmsslope as fitness for ind
		ind.setFitness(new SimpleValueFitness(frmssign));
			
		
   }


	@Override
	public Comparator<IFitness> getComparator() {
		return COMPARATOR;
	}
}