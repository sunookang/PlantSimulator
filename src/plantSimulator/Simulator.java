package plantSimulator;

import java.util.*;

public class Simulator {
	
	static double distance(double[] p1, double[] p2) {
		double dx = Math.pow(p1[0] - p2[0], 2);
		double dy = Math.pow(p1[1] - p2[1], 2);
		double dz = Math.pow(p1[2] - p2[2], 2);
		double distance = Math.sqrt(dx + dy + dz);
		return distance;
	}

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		
		ArrayList<Plant> plants = new ArrayList<Plant>();
		
		System.out.print("Start Count: ");
		int startCount = input.nextInt();
		System.out.print("Max Turns: ");
		int totalTurns = input.nextInt();
		System.out.print("Sunlight: ");
		double sunlight = input.nextDouble();
		System.out.print("Poison Factor: ");
		double poisons = input.nextDouble();
		System.out.println("");
		System.out.println("Loading...");
		System.out.println("");
		
		
		for (int i = 0; i<startCount; i++) {
			plants.add(new Plant(1.0, 1.0, 1.0));
		}
		
		for (int i = 0; i<totalTurns; i++) {
			
			ArrayList<Plant> plantsCloned = (ArrayList<Plant>) plants.clone();
			int rivalCount = Math.round(plants.size() / 30);
			
			for (int j = plantsCloned.size() - 1; j >= 0; j--) {
				Plant currentPlant = plantsCloned.get(j);
				
				double eatenChance = 1 / (currentPlant.poison * poisons);
				double randomChance = Math.random();
				
				if (randomChance < eatenChance) {
					plants.remove(j);
					continue;
				}
				
				ArrayList<Plant> plantsShuffled = (ArrayList<Plant>) plantsCloned.clone();
				Collections.shuffle(plantsShuffled);
				double light = 1.0;
				
				for (int h = 0; h<rivalCount; h++) {
					if (plantsShuffled.get(h).height > currentPlant.height * 0.9) {
						light *= 0.75;
					}
				}
				
				if (sunlight * light < currentPlant.cost && Math.random() > 0.8) {
					plants.remove(j);
					continue;
				}
				
				long children;
				
				if (sunlight * light >= currentPlant.cost * 2.0) {
					children = Math.round(3.0 * Math.random() * Math.pow(currentPlant.flower, 2));
				} else if (sunlight * light >= currentPlant.cost * 1.5) {
					children = Math.round(2.0 * Math.random() * Math.pow(currentPlant.flower, 2));
				} else if (sunlight * light >= currentPlant.cost) {
					children = Math.round(1.0 * Math.random() * Math.pow(currentPlant.flower, 2));
				} else {
					children = 0;
				}
				
				for (int h = 0; h< (int) children; h++) {
					plants.add(currentPlant.reproduce());
				}
				
			}
			if (plants.size() > 2000) {
				System.out.println("Total Turns: " + i);
				break;
			}
			
			
		}
		
		System.out.println("Total Plants: " + plants.size());
		/*Collections.shuffle(plants);
		if (plants.size() > 8) {
			for (int i=0; i<8; i++) {
				plants.get(i).printSelf();
			}
		}*/
		
		input.close();
		
		if (plants.size() > 20) {
			double[][] plantChart = new double[plants.size()][3];
			for (int i=0; i<plants.size(); i++) {
				plantChart[i] = plants.get(i).coords();
			}
			
			ArrayList<double[]> centroids = new ArrayList<double[]>();
			
			for (int i=0; i<plantChart.length; i++) {
				double[] currentPoint = plantChart[i];
				boolean skip = false;
				int closePoints = 0;
				
				for (int j=0; j<centroids.size(); j++) {
					if (distance(currentPoint, centroids.get(j)) < 0.4) {
						skip = true;
					}
				}
				
				if (skip) {
					continue;
				}
				
				for (int j=0; j<plantChart.length; j++) {
					double[] objectPoint = plantChart[j];
					
					if (distance(currentPoint, objectPoint) < 0.3) {
						closePoints += 1;
					}
					
				}
					
				if (closePoints > (int) plantChart.length / 20) {
					centroids.add(currentPoint);
				}
				
			}
			
			ArrayList<Integer> centroidPopularity = new ArrayList<Integer>();
			for (int i=0; i<centroids.size(); i++) {
				centroidPopularity.add(null);
			}
			
			for (int k=0; k<30; k++) {
			
				for (int i=0; i<centroids.size(); i++) {
					double[] centroid = centroids.get(i);
					double xsum = 0;
					double ysum = 0;
					double zsum = 0;
					int pointCount = 0;
					for (int j=0; j<plantChart.length; j++) {
						double[] objectPoint = plantChart[j];
						if (distance(centroid, objectPoint) < 0.3) {
							pointCount += 1;
							xsum += objectPoint[0];
							ysum += objectPoint[1];
							zsum += objectPoint[2];
						}
					}
					
					centroid[0] = xsum / pointCount;
					centroid[1] = ysum / pointCount;
					centroid[2] = zsum / pointCount;
					centroidPopularity.set(i, pointCount);
				}
			}
			
			for (int k=centroids.size() - 1; k>=0; k--) {
				for (int i=0; i<centroids.size(); i++) {
					if (distance(centroids.get(k), centroids.get(i)) < 0.1
							&& centroids.get(k) != centroids.get(i)) {
						centroids.remove(k);
						centroidPopularity.remove(k);
						break;
					}
				}
			}
				
				
			for (int i=0; i<centroids.size(); i++) {
				System.out.println();
				System.out.println("Species " + (i+1) + ": ");
				System.out.println("Height: " + Math.round(centroids.get(i)[0]*1000.0)/1000.0);
				System.out.println("Flower: " + Math.round(centroids.get(i)[1]*1000.0)/1000.0);
				System.out.println("Poison: " + Math.round(centroids.get(i)[2]*1000.0)/1000.0);
				System.out.println("No. of Plants: " + centroidPopularity.get(i));
			}
					
					
					
			
		}
		
		
	}

}
