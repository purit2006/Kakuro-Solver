import java.util.Random;





public class Algorithm {
	private static final int tournamentSize = 5;
	private static final double uniformRate = 0.5;
	private static final double mutationRate = 0.015;
	private static final boolean elitism = true;
	 // Evolve a population
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
            newIndiv.adjust();

            newPopulation.saveIndividual(i, newIndiv);
        }

        // Mutate population
//        for (int i = elitismOffset; i < newPopulation.size(); i++) {
//            mutate(newPopulation.getIndividual(i));
//        	
//        }

        return newPopulation;
    }
    // Mutate an individual
    private static void mutate(KIndividual indiv) {
    	Random rand = new Random();
        // Loop through every play cell in the Individual
        for (int r = 0; r < indiv.size(); r++) {
        	for(int c = 0; c < indiv.size(); ++c){
        		if (indiv.getCell(r, c) < 0 || Double.isNaN(indiv.getCell(r, c))) 
        			continue;
        		else if (Math.random() <= mutationRate) {
	                // Create random num
	                //double num = rand.nextInt(10-1)+1;
	                indiv.setCell(r,c, indiv.getCell(r, c) + 1);
	            }
        	}
        }
    }
    
    
 // Crossover individuals
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
    
    // randomly select the play cell from 2 of the individuals
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
   
    
    // Select individuals for crossover
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
