package generatorbase;

import configbase.Config;

public class NormalDistribution extends Distribution {

	public NormalDistribution() {
		super();
	}
	
	/**
	 * @param config
	 */
	public NormalDistribution(Config config) {
		super(config);
		// TODO Auto-generated constructor stub
	}

	/* Return probability density value at the point of x */
	public double pdf(double x) {
		return (double) (Math.exp(-(x-getMean())*(x-getMean())/(2*getVar()))/Math.sqrt(2*Math.PI*getVar()));
	}

	/*
	 * Estimate total area under the distribution's pdf curve from x to y using
	 * trapezoids. Condition: x<y Condition: f_range(min, max) = 1
	 */
	public double cdf_range(double x, double y) {
		double step = 0.0001;
		int numstep = (int) ((y-x)/step);
		double result = 0;
		for (int i = 0; i<numstep; i++) {
			result += 0.5*(pdf(x+step*(i+1))+pdf(x+step*i))*step;
		}
		return result;
	}

	/* Estimate cdf cdf of the function, making use of cdf_range() above */
	public double cdf(double x) {
		return cdf_range(x-getVar()*100, x);
	}

	/* Substitution function for exponential part in the */
	double _exp_part(double x) {
		return (double) Math.exp(-(x-getMean())*(x-getMean())/(2*getVar()));
	}

}
