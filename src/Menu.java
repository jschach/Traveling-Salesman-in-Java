import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * Interface for genetic algorithm.
 * 
 * @author Jenna
 *
 */
public class Menu {
	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException{
		Scanner input = new Scanner(System.in);
		System.out.println("***********************************\n"
                       	 + "* The traveling salesman travels  *\n"
                         + "* to several cities on his route. *\n"
                         + "* What is the most efficient path *\n"
                       	 + "* for him to take to travel?      *\n"
                       	 + "***********************************\n");
		System.out.println("Please select the number of cities:");
//		int numCities = input.nextInt();
		int numCities = 20;
		System.out.println("Please enter the mutation rate in decimal"
						 + "format (must be less than 1.0):");
//		double mutationRate = input.nextDouble();
		double mutationRate = .50;
		System.out.println("Please specify the population size:");
//		int popSize = input.nextInt();
		int popSize = 1000;
		System.out.println("Would you like to generate new distances?");
//		String generate = input.nextLine();
		String generate = "n";
		input.close();
		
		Cities citiesObject = new Cities(numCities);
		Population p = new Population(numCities, popSize, 
									mutationRate, citiesObject);
		if (generate == "y"){
			p.getCities().generateDistances();
		}
		else {
			p.getCities().createDistances();
		}
		
		for (int i = 0; i < 100000; i++){
			p.runAlgorithm();

		}
        Member top = p.getPopulation()[0];
        Member top2 = p.getPopulation()[1];
        System.out.print("First place is: ");
        top.printRoute();
        System.out.println(" with " + top.getFitness());
        System.out.print("Middle is: ");
        p.getPopulation()[popSize/2].printRoute();
        System.out.println(" with " + p.getPopulation()[popSize/2].getFitness());
        System.out.print("Last is: ");
        p.getPopulation()[popSize - 1].printRoute();
        System.out.println(" with " + p.getPopulation()[popSize - 1].getFitness());


    }
}
