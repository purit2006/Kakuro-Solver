import java.util.ArrayList;
import java.util.Random;





public class Algorithm {
	private static final int tournamentSize = 10;
	private static final double uniformRate = 0.5;
	private static final double mutationRate = 0.35;
	private static final boolean elitism = true;
	
	
	/**
	 * Perform evolution of the population
	 * @param pop
	 * @return
	 */
    public static KPopulation evolvePopulation(KPopulation pop) {
        KPopulation newPopulation = new KPopulation(pop.size(), false);

        // Keep our best individual
        if (elitism) {
            newPopulation.saveIndividual(0, pop.getFittest());
        }

        // Crossover population
        int elitismOffset;
        if (elitism) {
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }
        // Loop over the population size and create new individuals with
        // crossover
        for (int i = elitismOffset; i < pop.size(); i++) {
            KIndividual indiv1 = tournamentSelection(pop);
            KIndividual indiv2 = tournamentSelection(pop);
            KIndividual newIndiv = crossoverV2(indiv1, indiv2);
            //newIndiv.adjust();

            newPopulation.saveIndividual(i, newIndiv);
        }

        // Mutate population
        for (int i = elitismOffset; i < newPopulation.size(); i++) {
            mutate(newPopulation.getIndividual(i));
        	
        }

        return newPopulation;
    }
    
    /**
     * Mutate individual
     * @param indiv
     */
    private static void mutate(KIndividual indiv) {
    	double currentFitness = indiv.getFitness();
    	Random rand = new Random();
        // Loop through every play cell in the Individual
        for (int r = 0; r < indiv.size(); r++) {
        	for(int c = 0; c < indiv.size(); ++c){
        		// skip the puzzle cells or non-value cell
        		if (indiv.getCell(r, c) < 0 || Double.isNaN(indiv.getCell(r, c))) 
        			continue;
        		
        		// according to mutationRate, perform the mutation.
        		else if (Math.random() <= mutationRate) {
        			if(Math.random() <= 0.5){
        				while(indiv.setCell(r,c, indiv.getCell(r, c) + 1)){
        					if(indiv.getFitness() < currentFitness) currentFitness = indiv.getFitness();
        					else break;
        				}
        			}
        			else{
        				
        				while(indiv.setCell(r,c, (indiv.getCell(r, c) - 1))){
        					if(indiv.getFitness() < currentFitness) currentFitness = indiv.getFitness();
        					else break;
        				}
        			}
	                
	            }
        	}
        }
    }
    
    
    /**
     * #######################################################
     * CROSSOVER METHODS
     * #######################################################
     */
    
    /**
     * crossover by randomly select the set of puzzle and it's corresponding play cell values.
     * @param indiv1
     * @param indiv2
     * @return
     */
    private static KIndividual crossover(KIndividual indiv1, KIndividual indiv2) {
        KIndividual newSol = new KIndividual(KUtility.getTable());
        int[] x = KUtility.getPX();
        int[] y = KUtility.getPY();
        // Loop through the puzzle cells
        for (int i = 0; i < x.length; i++) {
            // Crossover
            if (Math.random() <= uniformRate) {
                newSol.setCrossVal(x[i], y[i], indiv1);
                
            } else {
            	newSol.setCrossVal(x[i], y[i], indiv2);
            }
        }
        return newSol;
    }
    
    /**
     * this version randomly select the play cell from 2 of the individuals.
     * @param indiv1
     * @param indiv2
     * @return
     */
    private static KIndividual crossoverV2(KIndividual indiv1, KIndividual indiv2){
    	KIndividual newSol = new KIndividual(KUtility.getTable());
        for(int r = 0; r < indiv1.size(); ++r){
        	for(int c = 0; c < indiv1.size(); ++c){
        		if(!Double.isNaN(indiv1.getCell(r, c)) && indiv1.getCell(r, c) >= 0) continue;
        		if (Math.random() <= uniformRate) {
                    newSol.setCell(r, c, indiv1.getCell(r, c));
                    
                } else {
                	newSol.setCell(r, c, indiv2.getCell(r, c));
                }
        	}
        }
    	newSol.generateSolution();
        return newSol;
    }
    
    /**
     * this version using method getBestTrait() 
     * which is a puzzle cell which has the most minimum difference between 
     * it's puzzle value and the sum of it's corresponding play cells value
     * @param indiv1
     * @param indiv2
     * @return
     */
    private static KIndividual crossoverV3(KIndividual indiv1, KIndividual indiv2){
    	KIndividual newSol = new KIndividual(KUtility.getTable());
        double idiv1BestTraits = indiv1.getBestTrait();
        
        double idiv2BestTraits = indiv2.getBestTrait();
   
        String[] arr=String.valueOf(idiv1BestTraits).split("\\.");
        newSol.setCrossVal(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), indiv1);
        
        arr=String.valueOf(idiv2BestTraits).split("\\.");
        newSol.setCrossVal(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), indiv2);
        
    	newSol.generateSolution();
    	return newSol;
    }
   
    
    
    
    /**
     * Select individuals for crossover
     * @param pop
     * @return
     */
    private static KIndividual tournamentSelection(KPopulation pop) {
        // Create a tournament population
        KPopulation tournament = new KPopulation(tournamentSize, false);
        // For each place in the tournament get a random individual
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.size());
            tournament.saveIndividual(i, pop.getIndividual(randomId));
        }
        // Get the fittest
        KIndividual fittest = tournament.getFittest();
        return fittest;
    }
    
    
}
