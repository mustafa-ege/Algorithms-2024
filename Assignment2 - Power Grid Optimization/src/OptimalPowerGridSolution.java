import java.util.ArrayList;

/**
 * A class that represents the optimal solution for the Power Grid optimization scenario (Dynamic Programming)
 */

public class OptimalPowerGridSolution {
    private int maxNumberOfSatisfiedDemands;
    private ArrayList<Integer> hoursToDischargeBatteriesForMaxEfficiency;
    private int demandedEnergy = 0;

    public OptimalPowerGridSolution(int maxNumberOfSatisfiedDemands, ArrayList<Integer> hoursToDischargeBatteriesForMaxEfficiency) {
        this.maxNumberOfSatisfiedDemands = maxNumberOfSatisfiedDemands;
        this.hoursToDischargeBatteriesForMaxEfficiency = hoursToDischargeBatteriesForMaxEfficiency;
    }

    public OptimalPowerGridSolution() {
    }
    public int getmaxNumberOfSatisfiedDemands() {
        return maxNumberOfSatisfiedDemands;
    }

    public ArrayList<Integer> getHoursToDischargeBatteriesForMaxEfficiency() {
        return hoursToDischargeBatteriesForMaxEfficiency;
    }

    public void print(){
        System.out.print(
                "The total number of demanded gigawatts: "+ demandedEnergy + "\n" +
                "Maximum number of satisfied gigawatts: "+maxNumberOfSatisfiedDemands+"\n" +
                "Hours at which the battery bank should be discharged: ");
        int sizeOfHours = hoursToDischargeBatteriesForMaxEfficiency.size();
        for (int i = 0; i < sizeOfHours; i++) {
            System.out.print(hoursToDischargeBatteriesForMaxEfficiency.get(i));
            if (i < sizeOfHours - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("\nThe number of unsatisfied gigawatts: "+ (demandedEnergy - maxNumberOfSatisfiedDemands));
    }
    public void setDemandedEnergy(int energy){
        demandedEnergy = energy;
    }

}
