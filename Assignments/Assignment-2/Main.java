import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Main class
 */
// FREE CODE HERE
public class Main {
    public static void main(String[] args) throws IOException {

       /** MISSION POWER GRID OPTIMIZATION BELOW **/

        System.out.println("##MISSION POWER GRID OPTIMIZATION##");
        // TODO: Your code goes here
        // You are expected to read the file given as the first command-line argument to read 
        // the energy demands arriving per hour. Then, use this data to instantiate a 
        // PowerGridOptimization object. You need to call getOptimalPowerGridSolutionDP() method
        // of your PowerGridOptimization object to get the solution, and finally print it to STDOUT.

        String file1 = args[0];//"demandSchedule.dat";

        ArrayList<Integer> arrayList = new ArrayList<>();

        String[] line = readFile(file1,true,true);

        int totalDemand = 0;

        for (String element : line[0].split(" ")){
            Integer number = Integer.parseInt(element);
            arrayList.add(number);
            totalDemand += number;
        }

        PowerGridOptimization powerGridOptimization = new PowerGridOptimization(arrayList);

        OptimalPowerGridSolution optimalPowerGridSolution = powerGridOptimization.getOptimalPowerGridSolutionDP();

        System.out.println("The total number of demanded gigawatts: "+ totalDemand);

        System.out.println("Maximum number of satisfied gigawatts: "+ optimalPowerGridSolution.getmaxNumberOfSatisfiedDemands());

        System.out.println("Hours at which the battery bank should be discharged: "+ optimalPowerGridSolution.getHoursToDischargeBatteriesForMaxEfficiency().toString().replace("[", "").replace("]", ""));

        int unsatisfiedNum = totalDemand - optimalPowerGridSolution.getmaxNumberOfSatisfiedDemands();

        System.out.println("The number of unsatisfied gigawatts: "+ unsatisfiedNum);

        System.out.println("##MISSION POWER GRID OPTIMIZATION COMPLETED##");

        /** MISSION ECO-MAINTENANCE BELOW **/

        System.out.println("##MISSION ECO-MAINTENANCE##");
        // TODO: Your code goes here
        // You are expected to read the file given as the second command-line argument to read
        // the number of available ESVs, the capacity of each available ESV, and the energy requirements 
        // of the maintenance tasks. Then, use this data to instantiate an OptimalESVDeploymentGP object.
        // You need to call getMinNumESVsToDeploy(int maxNumberOfAvailableESVs, int maxESVCapacity) method
        // of your OptimalESVDeploymentGP object to get the solution, and finally print it to STDOUT.
        String file2 = args[1];//"ESVMaintenance.dat";

        String[] lines = readFile(file2,true,true);

        int maxNumberOfAvailableESVs = Integer.parseInt(lines[0].split(" ")[0]);

        int maxESVCapacity = Integer.parseInt(lines[0].split(" ")[1]);

        ArrayList<Integer> maintenanceTaskEnergyDemands = new ArrayList<>();

        for (String element : lines[1].split(" ")){
            int num = Integer.parseInt(element);
            maintenanceTaskEnergyDemands.add(num);
        }

        OptimalESVDeploymentGP optimalESVDeploymentGP = new OptimalESVDeploymentGP(maintenanceTaskEnergyDemands);

        int ESVNumber = optimalESVDeploymentGP.getMinNumESVsToDeploy(maxNumberOfAvailableESVs,maxESVCapacity);

        if (ESVNumber == -1){
            System.out.println("Warning: Mission Eco-Maintenance Failed.");
        }else {
            System.out.println("The minimum number of ESVs to deploy: "+ ESVNumber);
            for (int i = 1; i <= ESVNumber; i++){
                System.out.println("ESV "+ i + " tasks: " + optimalESVDeploymentGP.getMaintenanceTasksAssignedToESVs().get(i-1));
            }
        }

        System.out.println("##MISSION ECO-MAINTENANCE COMPLETED##");
    }
    public static String[] readFile(String path, boolean discardEmptyLines, boolean trim) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path)); //Gets the content of file to the list.
            if (discardEmptyLines) { //Removes the lines that are empty with respect to trim.
                lines.removeIf(line -> line.trim().equals(""));
            }
            if (trim) { //Trims each line.
                lines.replaceAll(String::trim);
            }
            return lines.toArray(new String[0]);
        } catch (IOException e) { //Returns null if there is no such a file.
            e.printStackTrace();
            return null;
        }
    }
}
