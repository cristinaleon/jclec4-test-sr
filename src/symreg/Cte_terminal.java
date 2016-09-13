package symreg;

import java.util.Random;

import net.sf.jclec.exprtree.fun.Argument;

public class Cte_terminal extends Argument<Double> {
	private static final long serialVersionUID = -653953979823993607L;

	public Cte_terminal() {
		super(Double.class, 0);
	}

	// java.lang.Object methods

	// Genera una cte aleatoria entre min y max (entero)
			int min=0;
			int max=10;

			// tomar semilla del .cfg muy complicado		
			int seed = 123;					
			Random rand = new Random(123);				
			double randomNum =  rand.nextDouble() * (max - min) + min;
			double cte=randomNum;
			
	public double setValue(Double x){
		cte= x;
		return cte;
	}
	
	public double addition(Double x){
		this.cte= cte + x;
		return this.cte;
	}
	
	public double getValue(){
		return cte;
	}
	
	public boolean equals(Object other) {
		return other instanceof Cte_terminal;
	}

	public String toString() {
		return String.valueOf(cte);
	}
}