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

public class SymregEvaluatorDerivativeRmsd extends AbstractEvaluator implements IConfigure {
	private static final long serialVersionUID = 1L;

	private static final Comparator<IFitness> COMPARATOR = new ValueFitnessComparator(true);

	private double[] xvalues;

	private double[] yvalues;

	//////////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Protected methods
	//////////////////////////////////////////////////////////////////////

	@Override
	public void configure(Configuration settings) {
		String[] x = settings.getStringArray("xvalues");
		String[] y = settings.getStringArray("yvalues");

		int numberElements = x.length;

		xvalues = new double[numberElements];
		yvalues = new double[numberElements];

		System.out.println("--->" + numberElements);

		for (int i = 0; i < numberElements; i++) {
			xvalues[i] = Double.parseDouble(x[i]);
			yvalues[i] = Double.parseDouble(y[i]);
		}
	}

	@Override
	protected void evaluate(IIndividual ind) {
		// Individual genotype
		ExprTree genotype = ((ExprTreeIndividual) ind).getGenotype();

		ExprTreeFunction function = new ExprTreeFunction(genotype);

		// Estimated values
		double[] y = new double[xvalues.length];

		for (int i = 0; i < xvalues.length; i++)
			y[i] = function.<Double> execute(xvalues[i]);

		// // Pass all
		// double rms = 0.0;
		//
		// for (int i=0; i<yvalues.length; i++) {
		// double diff = y[i] - yvalues[i];
		// rms += diff * diff;
		// }
		//
		// rms = Math.sqrt(rms);
		// //ind.setFitness(new SimpleValueFitness(rms));

		// ---- Intervals ---- //
		double[] yvaluesinter = new double[yvalues.length - 1];
		double[] yinter = new double[yvalues.length - 1];
		double[] xvaluesinter = new double[yvalues.length - 1];

		for (int j = 0; j < yvalues.length - 1; j++) {
			yinter[j] = y[j + 1] - y[j]; /* intervalos y aproximada */
			xvaluesinter[j] = xvalues[j + 1]
					- xvalues[j]; /*
									 * intervalos x and xaproximada (son los
									 * mismos)
									 */
			yvaluesinter[j] = yvalues[j + 1] - yvalues[j]; /* intervalos y */
		}

		// ---- Slope-----//
		double[] slopevalues = new double[yvaluesinter.length];
		double[] slope = new double[yvaluesinter.length];

		for (int i = 0; i < yvaluesinter.length; i++) {
			slopevalues[i] = yvaluesinter[i] / xvaluesinter[i]; /* pendiente */
			slope[i] = yinter[i]
					/ xvaluesinter[i]; /* pendiente de la aproximada */

		}

		// ----- rmse ------// de la función derivada
		// Pass all
		double rmsd = 0.0;

		for (int i = 0; i < yvalues.length - 1; i++) {
			double diffd = slope[i] - slopevalues[i];
			rmsd += diffd * diffd;
		}

		rmsd = Math.sqrt(rmsd);
		ind.setFitness(new SimpleValueFitness(rmsd));

		// Combinar ambos rms y rmsd?

	}

	@Override
	public Comparator<IFitness> getComparator() {
		return COMPARATOR;
	}
}