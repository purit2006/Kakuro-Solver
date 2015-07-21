

public class KPopulation {
	private KIndividual[] individuals;
	
	
	/**
	 * Constructor
	 * @param popSize
	 * @param initialize
	 */
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
	
	/**
	 * remove the current individual which is 'fittest' and generate a new one
	 */
	public void resetElite(){
		int fittestIdx = 0;
        // Loop through individuals to find fittest
        for (int i = 0; i < size(); i++) {
            if (getIndividual(fittestIdx).getFitness() > getIndividual(i).getFitness()) {
                fittestIdx = i;
            }
        }
        individuals[fittestIdx] = new KIndividual(KUtility.getTable());
        individuals[fittestIdx].generateSolution();
	}
	
	/* Getters */
	
	/**
	 * get individual according to the index value
	 * @param index
	 * @return
	 */
    public KIndividual getIndividual(int index) {
        return individuals[index];
    }
    
    
    /**
     * get the fittest individual
     * @return
     */
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
	
	/**
     * get the population size
     * @return
     */
    public int size() {
        return individuals.length;
    }
    
 // Save individual
    /**
     * save the individual indiv at index position
     * @param index
     * @param indiv
     */
    public void saveIndividual(int index, KIndividual indiv) {
        individuals[index] = indiv;
    }

}
