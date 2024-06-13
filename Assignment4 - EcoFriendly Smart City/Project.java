import java.io.Serializable;
import java.util.*;

public class Project implements Serializable {
    static final long serialVersionUID = 33L;
    private final String name;
    private final List<Task> tasks;

    public Project(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    /**
     * @return the total duration of the project in days
     */
    public int getProjectDuration() {
        int[] schedule = getEarliestSchedule();
        int projectDuration = schedule[schedule.length-1] + tasks.get(tasks.size()-1).getDuration();
        return projectDuration;
    }

    /**
     * Schedule all tasks within this project such that they will be completed as early as possible.
     *
     * @return An integer array consisting of the earliest start days for each task.
     */
    public int[] getEarliestSchedule() {
        int[] earliestStart = new int[tasks.size()];
        Arrays.fill(earliestStart, -1);
        earliestStart[0] = 0;

        boolean[] scheduled = new boolean[tasks.size()];
        Arrays.fill(scheduled, false);
        scheduled[0] = true;

        boolean allCheck = false;
        while (!allCheck) {
            allCheck = true;
            for (int i = 1; i < tasks.size(); i++) {
                if (!scheduled[i]) {
                    Task task = tasks.get(i);
                    boolean dependenciesScheduled = true;
                    for (int dependencyId : task.getDependencies()) {
                        if (!scheduled[dependencyId]) {
                            dependenciesScheduled = false;
                            break;
                        }
                    }
                    if (dependenciesScheduled) {
                        int maxDependencyEndTime = 0;
                        for (int dependencyId : task.getDependencies()) {
                            maxDependencyEndTime = Math.max(maxDependencyEndTime,
                                    earliestStart[dependencyId] + tasks.get(dependencyId).getDuration());
                        }
                        earliestStart[i] = maxDependencyEndTime;
                        scheduled[i] = true;
                    } else
                        allCheck = false;
                }
            }
        }
        return earliestStart;
    }

    public static void printlnDash(int limit, char symbol) {
        for (int i = 0; i < limit; i++) System.out.print(symbol);
        System.out.println();
    }

    /**
     * Some free code here. YAAAY! 
     */
    public void printSchedule(int[] schedule) {
        int limit = 65;
        char symbol = '-';
        printlnDash(limit, symbol);
        System.out.println(String.format("Project name: %s", name));
        printlnDash(limit, symbol);

        // Print header
        System.out.println(String.format("%-10s%-45s%-7s%-5s","Task ID","Description","Start","End"));
        printlnDash(limit, symbol);
        for (int i = 0; i < schedule.length; i++) {
            Task t = tasks.get(i);
            System.out.println(String.format("%-10d%-45s%-7d%-5d", i, t.getDescription(), schedule[i], schedule[i]+t.getDuration()));
        }
        printlnDash(limit, symbol);
        System.out.println(String.format("Project will be completed in %d days.", tasks.get(schedule.length-1).getDuration() + schedule[schedule.length-1]));
        printlnDash(limit, symbol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;

        int equal = 0;

        for (Task otherTask : ((Project) o).tasks) {
            if (tasks.stream().anyMatch(t -> t.equals(otherTask))) {
                equal++;
            }
        }

        return name.equals(project.name) && equal == tasks.size();
    }

}
