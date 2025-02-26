import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.Serializable;
import java.util.*;

public class UrbanInfrastructureDevelopment implements Serializable {
    static final long serialVersionUID = 88L;

    /**
     * Given a list of Project objects, prints the schedule of each of them.
     * Uses getEarliestSchedule() and printSchedule() methods of the current project to print its schedule.
     * @param projectList a list of Project objects
     */
    public void printSchedule(List<Project> projectList) {

        for (Project project : projectList){
            int[] earliestSchedule = project.getEarliestSchedule();
            project.printSchedule(earliestSchedule);
        }

    }

    public List<Project> readXML(String filename) {
        List<Project> projectList = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(filename);

            Element rootElement = doc.getDocumentElement();


            NodeList projectsList = rootElement.getElementsByTagName("Project");
            NodeList projectNameList = rootElement.getElementsByTagName("Name");


            for (int i = 0; i < projectsList.getLength(); i++) {

                Element projectElement = (Element) projectsList.item(i);
                NodeList taskList = projectElement.getElementsByTagName("Task");
                NodeList taskIDList = projectElement.getElementsByTagName("TaskID");
                NodeList descriptionList = projectElement.getElementsByTagName("Description");
                NodeList durationList = projectElement.getElementsByTagName("Duration");
                NodeList dependenciesList = projectElement.getElementsByTagName("Dependencies");


                List<Task> tasks = new ArrayList<>();

                for (int j = 0; j < taskList.getLength(); j++) {
                    int taskID = Integer.parseInt(taskIDList.item(j).getTextContent());
                    String description = descriptionList.item(j).getTextContent();
                    int duration = Integer.parseInt(durationList.item(j).getTextContent());
                    Element dependeciesElement = (Element) dependenciesList.item(j);
                    NodeList dependenciesIDList = dependeciesElement.getElementsByTagName("DependsOnTaskID");
                    List<Integer> dependencyList = new ArrayList<>();
                    for (int k = 0; k < dependenciesIDList.getLength(); k++){
                        int dependenciesID = Integer.parseInt(dependenciesIDList.item(k).getTextContent());
                        dependencyList.add(dependenciesID);
                    }
                    Task task = new Task(taskID,description,duration,dependencyList);
                    tasks.add(task);
                }

                // Get project name
                String projectName = projectNameList.item(i).getTextContent();

                Project project = new Project(projectName,tasks);
                projectList.add(project);

            }
        }catch (Exception e){

        }

        return projectList;
    }
}
