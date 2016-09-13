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

public class SymregEvaluatorDerivativeSign_3_xy extends AbstractEvaluator implements IConfigure
{
	private static final long serialVersionUID = 1L;

	private static final Comparator<IFitness> COMPARATOR = new ValueFitnessComparator(true);
	
	private double [] xvalues;
	
	private double [] yvalues;
	
	private double [] zvalues;
	
	//////////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Protected methods
	//////////////////////////////////////////////////////////////////////
	
	@Override
	public void configure(Configuration settings)
	{
		String[] x = settings.getStringArray("xvalues");
		String[] y = settings.getStringArray("yvalues");
		String[] z = settings.getStringArray("zvalues");
		
		int numberElements = x.length;
		
		xvalues = new double[numberElements];
		yvalues = new double[numberElements];
		zvalues = new double[numberElements];
		
		System.out.println("--->"+numberElements);
		
		for(int i = 0; i < numberElements; i++)
		{
			xvalues[i] = Double.parseDouble(x[i]);
			yvalues[i] = Double.parseDouble(y[i]);
			zvalues[i] = Double.parseDouble(z[i]);
		}
	}

	@Override
	protected void evaluate(IIndividual ind) 
	{
		// Individual genotype
		ExprTree genotype = ((ExprTreeIndividual) ind).getGenotype();

		ExprTreeFunction function = new ExprTreeFunction(genotype);

		// Estimated values
		double [] z = new double[xvalues.length];
		
		for(int i = 0; i<xvalues.length; i++)
			z[i] = function.<Double>execute(xvalues[i],yvalues[i]);
		
		// Pass all
		double rms = 0.0;
		
		for (int i=0; i<zvalues.length; i++) {
			double diff = z[i] - zvalues[i];
			rms += diff * diff;
		}
		
		rms = Math.sqrt(rms);
		//ind.setFitness(new SimpleValueFitness(rms));			

				

		// ---- Intervals ---- //				
		double [] zvaluesinter = new double[yvalues.length-1];
		double [] zinter = new double[yvalues.length-1];
		double [] xvaluesinter = new double[yvalues.length-1];
		double [] yvaluesinter = new double[yvalues.length-1];


		for (int j = 0; j<yvalues.length-1; j++) {
			zinter[j] = z[j+1] - z[j];                    /* intervalos  y aproximada */
			xvaluesinter[j] =  xvalues[j+1] - xvalues[j]; /* intervalos  x and xaproximada (son los mismos)*/
			yvaluesinter[j] =  yvalues[j+1] - yvalues[j]; /* intervalos  y*/
			zvaluesinter[j] =  zvalues[j+1] - zvalues[j]; /* intervalos  y*/
		}
	
	
				
		// ---- z interval -----//
		double [] zvaluesintersigns = new double[zvalues.length-1];
		double [] zintersigns = new double[zvalues.length-1];
		
		for (int i=0; i<zvalues.length-1; i++) {
			zvaluesintersigns[i] =  Math.signum(zvalues[i+1] - zvalues[i]); /* signo de los intervalos  z */
			zintersigns[i] = Math.signum(z[i+1] - z[i]);                    /* signo de los intervalos  de la y aproximada */
		}
		
		// Sign comparation
		double signsum = 0.0;
		for (int i=0; i<zvaluesintersigns.length; i++) {
			 // vector de signos: iguales 1, distintos 0 
			if ( zvaluesintersigns[i] == zintersigns[i] ){signsum += 1.0;} 	
		}
				
		
		double singcomp = (1.0 - signsum/zvaluesintersigns.length); /* ratio de los intervalos que no coinciden en signo*/	
		
		// Introduce signcomp also as fitness
		double lambda = 1.0;
		if (singcomp >= 0.8){
			lambda = 1.2;		 // aumenta rms
		} else if (singcomp >= 0.5){
			lambda = 1.05;		 // aumenta un poco rms
		}
		else{
			lambda = 0.90; //disminuye un poco rms
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