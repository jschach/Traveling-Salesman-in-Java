import java.util.Comparator;
import java.util.Random;
import java.util.Arrays;

import static java.util.Arrays.sort;


/**
 * Runs the genetic algorithm.
 * 
 * @author Jenna
 *
 */
public class Population {
	
	private Member[] population;
	private Cities citiesObject;
	private int populationSize;
	private int numCities;
	private double mutationRate;
	private Member[] populationChildren;
	private static Random rand = new Random();
	
	public Population(int numCities, int populationSize, 
					double mutationRate, Cities citiesObject){
		population = new Member[populationSize];
		this.citiesObject = citiesObject;
		this.populationSize = populationSize;
		this.numCities = numCities;
		citiesObject.setNumCities(numCities);
		this.mutationRate = mutationRate;
		startPopulation();
		populationChildren = new Member[(int) (populationSize * .4)];
	}
	
	public int insert(int index){
		int previous = index - 1;
		Member current = population[index];
		int currentFitness = current.getFitness();
		
		while (previous >= 0 && population[previous].getFitness()
				> currentFitness){
			population[previous + 1] = population[previous];
			previous -= 1;
		}
		
		population[previous + 1] = current;
		return previous + 1;
	}
	
	public Member[] getPopulation(){
        return population;
	}
	
	public void startPopulation(){
		for (int i = 0; i < populationSize; i++){
			Member member = new Member(citiesObject, numCities);
			member.newRandomPermutation();
//			member.printRoute();
			population[i] = member;
			insert(i);
		}
	}
	
	public int selectParent(){
		int num1 = rand.nextInt(populationSize - 2);
		int num2 = rand.nextInt(populationSize - 1);
		if (num1 <= num2){
			return num1;
		}
		else {
			return num2;
		}
	}
	
	public Member createChild(){
		Member child = new Member(citiesObject, numCities);
		int[] newRoute = new int[numCities];
		int bestDistance = citiesObject.getTotalDistance();
		// indexes for the best two cities
		int[] bestCities = new int[2];
		
		// boolean used array
		boolean[] used = new boolean[numCities];
		// initialize used
		for (int i = 0; i < numCities; i++){
			used[i] = false;
		}
		
		// select the parents
		Member parent1 = population[selectParent()];
		Member parent2 = parent1;
		while (parent1 == parent2){
			parent2 = population[selectParent()];
		}
		
		// check for the best distance between 
		// cities of parents and keep it
		for (int i = 0; i < numCities - 1; i++){
			int temp1 = citiesObject.getSpecifiedDistance(
					parent1.getRoute()[i], parent1.getRoute()[i + 1]);
			
			if (temp1 < bestDistance){
				bestDistance = temp1;
//				System.out.println(parent1.getRoute()[i]);
				bestCities[0] = parent1.getRoute()[i];
				bestCities[1] = parent1.getRoute()[i + 1];
			}
			
			int temp2 = citiesObject.getSpecifiedDistance(
					parent2.getRoute()[i], parent2.getRoute()[i + 1]);
			
			if (temp2 < bestDistance){
				bestDistance = temp2;
//				System.out.println(parent2.getRoute()[i]);
				bestCities[0] = parent2.getRoute()[i];
				bestCities[1] = parent2.getRoute()[i + 1];
			}
		}
		newRoute[0] = bestCities[0];
		used[newRoute[0]] = true;
		newRoute[1] = bestCities[1];
		used[newRoute[1]] = true;
//		System.out.println(bestCities[0]);
//		System.out.println(bestCities[1]);
//		System.out.println(used[newRoute[0]]);
//		System.out.println(used[newRoute[1]]);
		
		// put rest of cities in array by taking from
		// the parent with the best fitness
		if (parent2.getFitness() > parent1.getFitness()){
            int place = 2;
			for (int i = 0; i < numCities; i++){
				if (parent1.getRoute()[i] != bestCities[0]
						&& parent1.getRoute()[i] != bestCities[1]
						&& used[parent1.getRoute()[i]] == false){	
					newRoute[place] = parent1.getRoute()[i];
//					System.out.println(parent1.getRoute()[i]);
//					System.out.println(used[parent1.getRoute()[i]]);
					used[parent1.getRoute()[i]] = true;
                    place++;
				}
			}
		}
		else if (parent2.getFitness() <= parent1.getFitness()){
            int place = 2;
			for (int i = 0; i < numCities; i++){
				if (parent2.getRoute()[i] != bestCities[0]
						&& parent2.getRoute()[i] != bestCities[1]
						&& used[parent2.getRoute()[i]] == false) {
                    newRoute[place] = parent2.getRoute()[i];
//					System.out.println(parent2.getRoute()[i]);
//					System.out.println(used[parent2.getRoute()[i]]);
                    used[parent2.getRoute()[i]] = true;
                    place++;
                }
			}
		}
		child.setRoute(newRoute);
		return child;
	}
	
	public void runAlgorithm(){
		int numNewChild = (int)(populationSize * .75);
		int numRemaining = populationSize - numNewChild;
		Member[] newPopulation = new Member[populationSize];
		for (int i = 0; i < numRemaining; i++){
			newPopulation[i] = population[i];
		}
		for (int j = 0; j < numNewChild; j++){
			Member child = createChild();
			int temp = rand.nextInt(100) + 1;
			if (temp <= (mutationRate * 100)){
				child.mutate();
			}
			newPopulation[numRemaining + j] = child;
		}
		population = newPopulation;
//		insert(populationSize - 1);
        sort(population, new MemberComparator());
	}

	/**
	 * Getter method for citiesObject.
	 * @return
	 */
	public Cities getCities(){
        return citiesObject;
	}

    /**
     * Inner class to handle comparing
     * member objects.
     */
    private class MemberComparator implements Comparator<Member> {

        @Override
        public int compare(Member m1, Member m2) {
            if (m1.getFitness() > m2.getFitness())
                return 1;
            else if (m1.getFitness() < m2.getFitness())
                return -1;
            else
                return 0;
        }

        public boolean compareRoute(Member m1, Member m2){
            int[] route1 = m1.getRoute();
            int[] route2 = m2.getRoute();

            return false;

        }
    }
}

