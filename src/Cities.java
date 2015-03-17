import java.io.FileNotFoundException;
import java.util.List;
import java.io.File;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Initializes the city table used for 
 * generations in the genetic algorithm. 
 * Each cities object contains a number of cities
 * and the distance between each in order
 * of which they come in the array.
 * @author Jenna Schachner
 *
 */
public class Cities {
	
	private int numCities;
	private int[][] distanceMatrix;
	private static Random rand = new Random();
	private int fixNumCities;
	
	/**
	 * Constructor.
	 * @param items
	 */
	public Cities(int items){
		numCities = items;
		fixNumCities = items;
		distanceMatrix = new int[numCities][numCities];
		for (int i = 0; i < numCities - 1; i++){
			for (int j = 0; j < numCities - 1; j++){
				distanceMatrix[i][j] = 599;
			}
		}
		try {
			createDistances();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Getter for number of cities.
	 * @return
	 */
	public int getNumCities(){
		return numCities;
	}
	
	/**
	 * Puts random distances into the matrix
	 * and saves them to a file to be referenced
	 * later in the program. Overwrites old file.
	 * @throws UnsupportedEncodingException 
	 * @throws FileNotFoundException 
	 */
	public void generateDistances() throws FileNotFoundException, 
									UnsupportedEncodingException{
		rand = new Random();
		PrintWriter writer = new PrintWriter("cities.txt", "UTF-8");
		writer.print(numCities);
		writer.println();
		for (int i = 0; i < numCities; i++){
			for (int j = 0; j < i; j++){
                int temp = rand.nextInt(50) + 1;
				String input = temp + " ";
				if (i == j){
					writer.write(0 + " ");
				}
				else{
					writer.print(input);
				}
			}
		writer.println();
		}
		writer.close();
		createDistances();
	}
	
	/**
	 * Reads from the cities.txt files and puts
	 * the values into a matrix for the program
	 * to use.
	 * @throws FileNotFoundException
	 */
	public void createDistances() throws FileNotFoundException{
		File cities = new File("cities.txt");
		Scanner sc = new Scanner(cities);
		int tempNumCities = 0;
		if (sc.hasNextInt()){
			tempNumCities = sc.nextInt();
		}
		else {
			tempNumCities = 0;
		}
		if (tempNumCities == 0){
			numCities = fixNumCities;
			try {
				generateDistances();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// put random distances into the matrix for 
		// cities to generate no differing distances between 
		// any two cities
		for (int i = 0; i < numCities; i++){			
			for (int j = 0; j < i; j++){
				if (sc.hasNextInt()){
                    int temp = sc.nextInt();
					distanceMatrix[i][j] = temp;
                    distanceMatrix[j][i] = temp;
                    System.out.print(distanceMatrix[i][j]);
				}
				else {
					break;
				}
			}
            System.out.println();
		}
		sc.close();
	}
	
	/**
	 * Returns the distance matrix.
	 * @return
	 */
	public int[][] getMatrix(){
		return distanceMatrix;
	}
	
	/**
	 * Gets a specified distance in the 
	 * distance matrix.
	 * @param city1
	 * @param city2
	 * @return
	 */
	public int getSpecifiedDistance(int city1, int city2){
		if (city1 <= -1 || city1 > numCities){
			throw new IndexOutOfBoundsException(
					"city1 is out of range");
		}
		if (city2 > numCities){
			throw new IndexOutOfBoundsException(
					 "city2 is out of range");
		}
		return distanceMatrix[city1][city2];
	}
	
	/**
	 * Gets the total distance from each city to
	 * the next.
	 * @return
	 */
	public int getTotalDistance(){
		int distance = 0;
		for (int i = 0; i < numCities; i++){
			for (int j = 0; j < i; j++){
				distance += distanceMatrix[i][j];
			}
		}
		return distance;
	}

	public void setNumCities(int numCities) {
		// TODO Auto-generated method stub
		this.numCities = numCities;	
	}
	
//	public static void main(String[] args){
//		Cities c = new Cities(5);
//		try {
//			c.generateDistances();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(c.getTotalDistance());
//	}
	
}
