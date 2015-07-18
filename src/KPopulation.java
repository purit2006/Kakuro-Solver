

public class KPopulation {
	private KIndividual[] individuals;
	
	public KPopulation(int popSize,boolean initialize){
		individuals = new KIndividual[popSize];
		
		if (initialize) {
            // Loop and create individuals
            for (int i = 0; i < size(); i++) {
                KIndividual newIndividual = new KIndividual(KUtility.getTable());
                newIndividual.generateSolution();;
                saveIndividual(i, newIndividual);
            }
        }
		
	}
	
	/* Getters */
    public KIndividual getIndividual(int index) {
        return individuals[index];
    }

    public KIndividual getFittest() {
        KIndividual fittest = individuals[0];
        // Loop through individuals to find fittest
        for (int i = 0; i < size(); i++) {
            if (fittest.getFitness() > getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }
	
	/* Public methods */
    // Get population size
    public int size() {
        return individuals.length;
    }
    
 // Save individual
    public void saveIndividual(int index, KIndividual indiv) {
        individuals[index] = indiv;
    }

}
