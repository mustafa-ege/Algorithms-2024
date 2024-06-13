import java.util.*;

public class CityMap {
    private Map<Station, List<Path>> adjacencyList;

    public CityMap() {
        this.adjacencyList = new HashMap<>();
    }

    public void addStation(Station station) {
        adjacencyList.putIfAbsent(station, new ArrayList<>());
    }

    public void addPath(Station v, Station w, double roadSpeed) {
        addStation(v);
        addStation(w);
        adjacencyList.get(v).add(new Path(v, w, roadSpeed));
        adjacencyList.get(w).add(new Path(w, v, roadSpeed));
    }

    public List<Path> getPaths(Station station) {
        return adjacencyList.get(station);
    }

}
