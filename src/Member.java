import java.util.Random;

/**
 * Creates and manipulates member objects
 * to be used in the genetic algorithm's
 * population.
 * 
 * @author Jenna Schachner
 *
 */
public class Member {
	
	private Cities citiesObject;
	private int[] route;
	private int numCities;
	private static Random rand = new Random();
	
	public Member(Cities citiesObject, int numCities){
		this.citiesObject = citiesObject;
		this.numCities = numCities;
		route = new int[numCities];
		for (int i = 0; i < numCities - 1; i++){
			route[i] = -599;
		}
	}
	
	public void setRoute(int[] route){
		this.route = route;
	}
	
	public void printRoute(){
		for (int i = 0; i < route.length; i++){
			System.out.print(route[i] + ", ");
		}
	}
	
	public int[] getRoute(){
		return route;
	}
	
	public void newRandomPermutation(){
		boolean[] visited = new boolean[numCities];
		// reset boolean array that determines whether
		// a city has been chosen for the new 
		// permutation or not
		for (int v = 0; v < numCities - 1; v++){
			visited[v] = false;
			
		}
		// create the new random permutation
		for (int i = 0; i < numCities; i++){
			int temp = rand.nextInt(numCities);
			while (visited[temp] == true){
				temp = rand.nextInt(numCities);
			}
			route[i] = temp;
			visited[temp] = true;
			
		}
	}
	
	public void mutate(){
		route = getRoute();
		int first = rand.nextInt(numCities);
		int second = first;
		int third = first;
		while (first == second){
			second = rand.nextInt(numCities);
		}
		while (third == first || third == second){
			third = rand.nextInt(numCities);
		}
		int temp = route[first];
		int temp2 = route[second];
		route[first] = route[third];
		route[second] = temp;
		route[third] = temp2;
		setRoute(route);
	}
	
	public Member newMember(){
		Member newMember = new Member(citiesObject, numCities);
		return newMember;
	}
	
	public Cities getCities(){
		return citiesObject;
	}
	
	public int getFitness(){
		int score = 0;
		int j = 1;
		for (int i = 0; i < numCities - 1; i++){
			score += getCities().getSpecifiedDistance(
					route[i], route[j]);		
			j++;
		}
		score += getCities().getSpecifiedDistance(
				route[numCities - 1], route[0]);
		return score;
	}
}
