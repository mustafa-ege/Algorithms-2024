import java.io.Serializable;
import java.util.*;

class UrbanTransportationApp implements Serializable {
    static final long serialVersionUID = 99L;
    
    public HyperloopTrainNetwork readHyperloopTrainNetwork(String filename) {
        HyperloopTrainNetwork hyperloopTrainNetwork = new HyperloopTrainNetwork();
        hyperloopTrainNetwork.readInput(filename);
        return hyperloopTrainNetwork;
    }

    /**
     * Function calculate the fastest route from the user's desired starting point to 
     * the desired destination point, taking into consideration the hyperloop train
     * network. 
     * @return List of RouteDirection instances
     */
    public List<RouteDirection> getFastestRouteDirections(HyperloopTrainNetwork network) {
        List<RouteDirection> routeDirections = new ArrayList<>();
        CityMap cityMap = new CityMap();

        Station start = network.startPoint;
        Station end = network.destinationPoint;
        double walkSpeed = network.averageWalkingSpeed;
        double trainSpeed = network.averageTrainSpeed;

        // start to end
        cityMap.addPath(start, end, walkSpeed);

        // start to all and all to end
        for (TrainLine line : network.lines) {
            for (Station station : line.trainLineStations) {
                cityMap.addPath(start, station, walkSpeed);
                cityMap.addPath(station, end, walkSpeed);
            }
        }

        // inside train line
        for (TrainLine line : network.lines) {
            List<Station> stations = line.trainLineStations;
            for (int i = 0; i < stations.size() - 1; i++) {
                Station current = stations.get(i);
                Station next = stations.get(i + 1);
                cityMap.addPath(current, next, trainSpeed);
            }
        }

        // between train lines
        for (int i = 0; i < network.lines.size() - 1; i++) {
            TrainLine line1 = network.lines.get(i);
            for (int j = i + 1; j < network.lines.size(); j++) {
                TrainLine line2 = network.lines.get(j);
                for (Station station1 : line1.trainLineStations) {
                    for (Station station2 : line2.trainLineStations) {
                        cityMap.addPath(station1, station2, walkSpeed);
                    }
                }
            }
        }

        Map<Station, Double> durations = new HashMap<>();
        Map<Station, Station> previousStations = new HashMap<>();
        Set<Station> visited = new HashSet<>();
        PriorityQueue<Station> queue = new PriorityQueue<>(Comparator.comparingDouble(durations::get));

        durations.put(start, 0.0);
        queue.add(start);

        while (!queue.isEmpty()) {
            Station currentStation = queue.poll();
            if (currentStation.equals(end)) {
                while (previousStations.containsKey(currentStation)) {
                    Station prevStation = previousStations.get(currentStation);
                    double duration = durations.get(currentStation) - durations.get(prevStation);
                    boolean trainRide = false;
                    if (currentStation.description.substring(0,currentStation.description.length()-2).equals(
                            prevStation.description.substring(0,prevStation.description.length()-2))){
                        trainRide = true;
                    }
                    routeDirections.add(new RouteDirection(prevStation.description, currentStation.description, duration, trainRide));
                    currentStation = prevStation;
                }
                Collections.reverse(routeDirections);
                return routeDirections;
            }
            visited.add(currentStation);
            for (Path path : cityMap.getPaths(currentStation)) {
                Station neighbor = path.to();
                double duration = durations.get(currentStation) + path.duration();
                if (!visited.contains(neighbor) && (durations.get(neighbor) == null || duration < durations.get(neighbor))) {
                    durations.put(neighbor, duration);
                    previousStations.put(neighbor, currentStation);
                    queue.add(neighbor);
                }
            }
        }
        return routeDirections;
    }
    /**
     * Function to print the route directions to STDOUT
     */
    public void printRouteDirections(List<RouteDirection> directions) {
        double duration = 0;
        for (RouteDirection route : directions){
            duration += route.duration;
        }
        duration = Math.round(duration);

        System.out.println("The fastest route takes " + (int)duration + " minute(s).");
        System.out.println("Directions\n" +
                "----------");
        int i = 0 ;
        for (RouteDirection route : directions){
            if (route.trainRide){
                System.out.println(++i + ". Get on the train from \"" + route.startStationName +"\" to \""+ route.endStationName +"\" for "+String.format("%.2f", route.duration)+" minutes.");
            } else {
                System.out.println(++i + ". Walk from \"" + route.startStationName + "\" to \"" + route.endStationName + "\" for "+String.format("%.2f", route.duration)+ " minutes.");
            }
        }

    }
}