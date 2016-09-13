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

public class SymregEvaluatorDerivative_nuclear extends AbstractEvaluator implements IConfigure {
	private static final long serialVersionUID = 1L;

	private static final Comparator<IFitness> COMPARATOR = new ValueFitnessComparator(true);

	private double[] xvalues;

	private double[] yvalues;

	// private static final double [] xvalues = {-2., -1., 0., 1., 2.};
	//
	// private static final double [] yvalues = {10., 0., 0., 4., 30.};

	// private static final double [] xvalues =
	// {-1.00,-0.98,-0.96,-0.94,-0.92,-0.90,-0.88,-0.86,-0.84,-0.82,-0.80,-0.78,-0.76,-0.74,-0.72,-0.70,-0.68,-0.66,-0.64,-0.62,-0.60,-0.58,-0.56,-0.54,-0.52,-0.50,-0.48,-0.46,-0.44,-0.42,-0.40,-0.38,-0.36,-0.34,-0.32,-0.30,-0.28,-0.26,-0.24,-0.22,-0.20,-0.18,-0.16,-0.14,-0.12,-0.10,-0.08,-0.06,-0.04,-0.02,0.00,0.02,0.04,0.06,0.08,0.10,0.12,0.14,0.16,0.18,0.20,0.22,0.24,0.26,0.28,0.30,0.32,0.34,0.36,0.38,0.40,0.42,0.44,0.46,0.48,0.50,0.52,0.54,0.56,0.58,0.60,0.62,0.64,0.66,0.68,0.70,0.72,0.74,0.76,0.78,0.80,0.82,0.84,0.86,0.88,0.90,0.92,0.94,0.96,0.98,1.00};
	// private static final double [] yvalues =
	// {0.00000000,-0.03842384,-0.07378944,-0.10623504,-0.13589504,-0.16290000,-0.18737664,-0.20944784,-0.22923264,-0.24684624,-0.26240000,-0.27600144,-0.28775424,-0.29775824,-0.30610944,-0.31290000,-0.31821824,-0.32214864,-0.32477184,-0.32616464,-0.32640000,-0.32554704,-0.32367104,-0.32083344,-0.31709184,-0.31250000,-0.30710784,-0.30096144,-0.29410304,-0.28657104,-0.27840000,-0.26962064,-0.26025984,-0.25034064,-0.23988224,-0.22890000,-0.21740544,-0.20540624,-0.19290624,-0.17990544,-0.16640000,-0.15238224,-0.13784064,-0.12275984,-0.10712064,-0.09090000,-0.07407104,-0.05660304,-0.03846144,-0.01960784,0.00000000,0.02040816,0.04166656,0.06382896,0.08695296,0.11110000,0.13633536,0.16272816,0.19035136,0.21928176,0.24960000,0.28139056,0.31474176,0.34974576,0.38649856,0.42510000,0.46565376,0.50826736,0.55305216,0.60012336,0.64960000,0.70160496,0.75626496,0.81371056,0.87407616,0.93750000,1.00412416,1.07409456,1.14756096,1.22467696,1.30560000,1.39049136,1.47951616,1.57284336,1.67064576,1.77310000,1.88038656,1.99268976,2.11019776,2.23310256,2.36160000,2.49588976,2.63617536,2.78266416,2.93556736,3.09510000,3.26148096,3.43493296,3.61568256,3.80396016,4.00000000};

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

		// Pass all
		double rms = 0.0;

		for (int i = 0; i < yvalues.length; i++) {
			double diff = y[i] - yvalues[i];
			rms += diff * diff;
		}

		rms = Math.sqrt(rms);
		// ind.setFitness(new SimpleValueFitness(rms));

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

		// System.out.println("y0 "+yvalues[0] + " y1 "+yvalues[1] + " y2
		// "+yvalues[2] + " y3 "+yvalues[3] + " y4 "+yvalues[4] );
		// System.out.println("x0 "+xvalues[0] + " x1 "+xvalues[1] + " x2
		// "+xvalues[2] + " x3 "+xvalues[3] + " x4 "+xvalues[4] );
		// System.out.println(" yvaluesinter0 " + yvaluesinter[0] + "
		// yvaluesinter1 " + yvaluesinter[1] + " yvaluesinter2 " +
		// yvaluesinter[2]+ " yvaluesinter3 " + yvaluesinter[3] );
		// System.out.println(" yinter0 " + yinter[0] + " " + yinter[1] + " " +
		// yinter[2] + " " + yinter[3] );

		// // ---- Slope-----//
		double[] slopevalues = new double[yvaluesinter.length];
		double[] slope = new double[yvaluesinter.length];

		for (int i = 0; i < yvaluesinter.length; i++) {
			slopevalues[i] = yvaluesinter[i] / xvaluesinter[i]; /* pendiente */
			slope[i] = yinter[i]
					/ xvaluesinter[i]; /* pendiente de la aproximada */

			// Slope Positive 1, Negative 0
			// if (slopevalues[i] >=0){slopevalues[i] = 1;} else
			// {slopevalues[i]= 0; } // lo estoy conviertiendo en entero
			// if (slope[i] >= 0){slope[i] = 1;} else {slope[i]= 0;}
		}

		// Slope comparation
		double slopecomp = 0.0;
		double[] difslope = new double[slopevalues.length];
		for (int i = 0; i < slopevalues.length; i++) {
			difslope[i] = slopevalues[i] - slope[i];
		}

		double tol = 0.05;
		for (int i = 0; i < slopevalues.length; i++) {
			if (Math.abs(difslope[i]) <= tol) {
				slopecomp += 1;
			}
		}

		slopecomp = (1 - slopecomp / slopevalues.length); // ratio de pendientes
															// que difieren más
															// de tol

		// Introduce slopecomp also as fitness
		double lambda = 1.0;
		if (slopecomp >= 0.8) {
			lambda = 1.1; // aumenta rms
		} else if (slopecomp >= 0.5) {
			lambda = 1.05; // aumenta un poco rms
		} else {
			lambda = 0.95; // disminuye un poco rms
		}

		// double frmsslope =0.5*(rms+slopecomp); // rms y slopecomp han de ser
		// pequenyos
		double frmsslope = lambda * rms; // rms y slopecomp han de ser pequenyos

		// // Set rms frmssign or frmsslope as fitness for ind
		//// System.out.println("-----");
		//// System.out.println(rms);
		//// System.out.println(slopecomp);
		//// System.out.println(frmsslope);
		//
		ind.setFitness(new SimpleValueFitness(frmsslope));

		// ---- Y interval -----//
		// double [] yvaluesintersigns = new double[yvalues.length-1];
		// double [] yintersigns = new double[yvalues.length-1];
		//
		// for (int i=0; i<yvalues.length-1; i++) {
		// yvaluesintersigns[i] = Math.signum(yvalues[i+1] - yvalues[i]); /*
		// signo de los intervalos y */
		// yintersigns[i] = Math.signum(y[i+1] - y[i]); /* signo de los
		// intervalos de la y aproximada */
		// }
		//
		// // Sign comparation
		// double signsum = 0.0;
		// for (int i=0; i<yvaluesintersigns.length; i++) {
		// // vector de signos: iguales 1, distintos 0
		// if ( yvaluesintersigns[i] == yintersigns[i] ){signsum += 1.0;}
		// }
		//
		//
		// double singcomp = (1.0 - signsum/yvaluesintersigns.length); /* ratio
		// de los intervalos que no coinciden en signo*/
		//
		// // Introduce signcomp also as fitness
		// double lambda = 1.0;
		// if (singcomp >= 0.8){
		// lambda = 1.1; // aumenta rms
		// } else if (singcomp >= 0.5){
		// lambda = 1.05; // aumenta un poco rms
		// }
		// else{
		// lambda = 0.95; //disminuye un poco rms
		// }
		//
		//
		//// System.out.println(rms);
		//// System.out.println(rms+singcomp);
		// double frmssign = lambda*rms;

		// Set rms frmssign or frmsslope as fitness for ind
		// ind.setFitness(new SimpleValueFitness(frmssign));

	}

	@Override
	public Comparator<IFitness> getComparator() {
		return COMPARATOR;
	}
}