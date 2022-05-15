package plantSimulator;

public class Plant {
	double height;
	double flower;
	double poison;
	double cost;
	
	public Plant(double height, double flower, double poison) {
		this.height = height;
		this.flower = flower;
		this.poison = poison;
		this.cost = Math.pow(this.height, 3) * Math.pow(this.flower, 2)
				* Math.pow(this.poison, 2) + 2.0;
	}
	
	Plant reproduce() {
		double modifier;
		
		modifier = (Math.random() / 10) - 0.05;
		double h = Math.abs(this.height + modifier);
		
		modifier = (Math.random() / 10) - 0.05;
		double f = Math.abs(this.flower + modifier);
		
		modifier = (Math.random() / 10) - 0.05;
		double p = Math.abs(this.poison + modifier);
		
		return new Plant(h, f, p);
		
	}
	
	double[] coords() {
		double[] coordinates = new double[] {this.height, this.flower, this.poison};
		return coordinates;
	}
	
	void printSelf() {
		System.out.println();
		System.out.println("Height: " + Math.round(this.height * 1000.0) / 1000.0);
		System.out.println("Flower: " + Math.round(this.flower * 1000.0) / 1000.0);
		System.out.println("Poison: " + Math.round(this.poison * 1000.0) / 1000.0);
	}
}