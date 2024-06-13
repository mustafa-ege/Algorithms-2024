import java.io.File;
import java.io.Serializable;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class UrbanInfrastructureDevelopment implements Serializable {
    static final long serialVersionUID = 88L;

    /**
     * Given a list of Project objects, prints the schedule of each of them.
     * Uses getEarliestSchedule() and printSchedule() methods of the current project to print its schedule.
     * @param projectList a list of Project objects
     */
    public void printSchedule(List<Project> projectList) {
        for (Project proj : projectList){
            proj.printSchedule(proj.getEarliestSchedule());
        }
    }

    /**
     * @param filename the input XML file
     * @return a list of Project objects
     */
    public List<Project> readXML(String filename) {
        List<Project> projectList = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filename));

            NodeList projectNodes = document.getElementsByTagName("Project");
            for (int i = 0; i < projectNodes.getLength(); i++) {
                Element projectElement = (Element) projectNodes.item(i);
                String projectName = projectElement.getElementsByTagName("Name").item(0).getTextContent();
                NodeList taskNodes = projectElement.getElementsByTagName("Task");
                List<Task> tasks = new ArrayList<>();
                for (int j = 0; j < taskNodes.getLength(); j++) {
                    Element taskElement = (Element) taskNodes.item(j);
                    int taskId = Integer.parseInt(taskElement.getElementsByTagName("TaskID").item(0).getTextContent());
                    String description = taskElement.getElementsByTagName("Description").item(0).getTextContent();
                    int duration = Integer.parseInt(taskElement.getElementsByTagName("Duration").item(0).getTextContent());
                    List<Integer> dependencies = new ArrayList<>();
                    NodeList dependsOnNodes = taskElement.getElementsByTagName("DependsOnTaskID");
                    for (int k = 0; k < dependsOnNodes.getLength(); k++) {
                        dependencies.add(Integer.parseInt(dependsOnNodes.item(k).getTextContent()));
                    }
                    tasks.add(new Task(taskId, description, duration, dependencies));
                }
                projectList.add(new Project(projectName, tasks));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projectList;
    }
}
