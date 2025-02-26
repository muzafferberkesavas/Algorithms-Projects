import java.util.ArrayList;

/**
 * This class accomplishes Mission POWER GRID OPTIMIZATION
 */
public class PowerGridOptimization {
    private ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour;

    public PowerGridOptimization(ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour){
        this.amountOfEnergyDemandsArrivingPerHour = amountOfEnergyDemandsArrivingPerHour;
    }

    public ArrayList<Integer> getAmountOfEnergyDemandsArrivingPerHour() {
        return amountOfEnergyDemandsArrivingPerHour;
    }
    /**
     *     Function to implement the given dynamic programming algorithm
     *     SOL(0) <- 0
     *     HOURS(0) <- [ ]
     *     For{j <- 1...N}
     *         SOL(j) <- max_{0<=i<j} [ (SOL(i) + min[ E(j), P(j − i) ] ]
     *         HOURS(j) <- [HOURS(i), j]
     *     EndFor
     *
     * @return OptimalPowerGridSolution
     */
    public OptimalPowerGridSolution getOptimalPowerGridSolutionDP(){
        // TODO: YOUR CODE HERE
        int length = amountOfEnergyDemandsArrivingPerHour.size();

        int[] sol = new int[length + 1];

        ArrayList<ArrayList<Integer>> hours = new ArrayList<>();

        hours.add(new ArrayList<>());


        sol[0] = 0;

        for (int j = 1; j <= length; j++){
            int max = 0;
            int index = -1;
            for (int i = 0; i < j; i++){
                int result = sol[i] + Math.min(amountOfEnergyDemandsArrivingPerHour.get(j-1),E(j-i));
                if (result > max){
                    max = result;
                    index = i;
                }
            }
            sol[j] = max;
            ArrayList<Integer> arrayList = new ArrayList<>();
            if (index == -1){
                hours.add(arrayList);
                hours.get(j).add(j);
            }else {
                hours.add(arrayList);
                ArrayList<Integer> temp = hours.get(index);
                for (Integer element : temp){
                    hours.get(j).add(element);
                }
                hours.get(j).add(j);
            }
        }

        OptimalPowerGridSolution optimalPowerGridSolution = new OptimalPowerGridSolution(sol[length],hours.get(length));

        return optimalPowerGridSolution;
    }

    public static int E(int n){
        return n*n;
    }

}
