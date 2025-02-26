import java.util.ArrayList;
import java.util.Collections;

/**
 * This class accomplishes Mission Eco-Maintenance
 */
public class OptimalESVDeploymentGP
{
    private ArrayList<Integer> maintenanceTaskEnergyDemands;

    /*
     * Should include tasks assigned to ESVs.
     * For the sample input:
     * 8 100
     * 20 50 40 70 10 30 80 100 10
     * 
     * The list should look like this:
     * [[100], [80, 20], [70, 30], [50, 40, 10], [10]]
     * 
     * It is expected to be filled after getMinNumESVsToDeploy() is called.
     */
    private ArrayList<ArrayList<Integer>> maintenanceTasksAssignedToESVs = new ArrayList<>();

    ArrayList<ArrayList<Integer>> getMaintenanceTasksAssignedToESVs() {
        return maintenanceTasksAssignedToESVs;
    }

    public OptimalESVDeploymentGP(ArrayList<Integer> maintenanceTaskEnergyDemands) {
        this.maintenanceTaskEnergyDemands = maintenanceTaskEnergyDemands;
    }

    public ArrayList<Integer> getMaintenanceTaskEnergyDemands() {
        return maintenanceTaskEnergyDemands;
    }

    /**
     *
     * @param maxNumberOfAvailableESVs the maximum number of available ESVs to be deployed
     * @param maxESVCapacity the maximum capacity of ESVs
     * @return the minimum number of ESVs required using first fit approach over reversely sorted items.
     * Must return -1 if all tasks can't be satisfied by the available ESVs
     */
    public int getMinNumESVsToDeploy(int maxNumberOfAvailableESVs, int maxESVCapacity)
    {
        // TODO: Your code goes here


        int[] array = new int[maxNumberOfAvailableESVs];

        for (int i = 0; i < maxNumberOfAvailableESVs; i++){
            array[i]  = 0;
        }


        int index = 0;

        Collections.sort(maintenanceTaskEnergyDemands, Collections.reverseOrder());


        for ( Integer energy : maintenanceTaskEnergyDemands){
            if (energy > maxESVCapacity){
                return -1;
            }
            boolean isAdded = false;
            for (ArrayList<Integer> ESV : maintenanceTasksAssignedToESVs){
                int totalEnergy = array[index];
                if (totalEnergy + energy <= maxESVCapacity){
                    ESV.add(energy);
                    array[index] = array[index] + energy;
                    isAdded = true;
                    break;
                }
                index++;
            }
            if (!isAdded && maintenanceTasksAssignedToESVs.size() != maxNumberOfAvailableESVs){
                ArrayList<Integer> newESV = new ArrayList<>();
                newESV.add(energy);
                maintenanceTasksAssignedToESVs.add(newESV);
                array[index] = energy;
            }
            if (!isAdded && maintenanceTasksAssignedToESVs.size() == maxNumberOfAvailableESVs){
                return -1;
            }
            index = 0;
        }
        return maintenanceTasksAssignedToESVs.size();
    }

}
