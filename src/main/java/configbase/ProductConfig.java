package configbase;

import modelbase.Entity;
import generatorbase.Distribution;

public class ProductConfig extends Config {
	int				numTypes;
	int				numProducts;
	int				priceMin;
	int				priceMax;
	int				numCategories;
	/* Distribution for products range <-> price range */
	Distribution	distribution;

	public int getNumCategories() {
		return numCategories;
	}

	public void setNumCategories(int numCategories) {
		this.numCategories = numCategories;
	}

	public void setNumProducts(int numProducts) {
		this.numProducts = numProducts;
	}

	public void setPriceMin(int priceMin) {
		this.priceMin = priceMin;
	}

	public void setPriceMax(int priceMax) {
		this.priceMax = priceMax;
	}

	public void setDistribution(Distribution distribution) {
		this.distribution = distribution;
	}

	public int getNumTypes() {
		return numTypes;
	}

	public void setNumTypes(int numTypes) {
		this.numTypes = numTypes;
	}

	public int getNumProducts() {
		return numProducts;
	}

	public int getPriceMin() {
		return priceMin;
	}

	public int getPriceMax() {
		return priceMax;
	}

	public Distribution getDistribution() {
		return distribution;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see configbase.Config#configure(modelbase.Entity)
	 */
	@Override
	public void configure(Entity e) {
		// TODO Auto-generated method stub

	}
}
