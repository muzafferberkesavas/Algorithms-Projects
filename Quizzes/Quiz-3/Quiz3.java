import java.text.DecimalFormat;
import java.util.*;
import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Quiz3 {
    public static void main(String[] args) throws IOException {

        String file = args[0];
        String[] lines = readFile(file,true,true);
        int testNumber = Integer.parseInt(lines[0]);
        int count = 1;
        for (int i = 0; i< testNumber; i++){
            int station_with_drone = Integer.parseInt(lines[count].split("\\s+")[0]);
            int total_station = Integer.parseInt(lines[count].split("\\s+")[1]);
            List<Station> stations = new ArrayList<>();
            for (int j = count + 1; j <= count + total_station; j++){
                int x = Integer.parseInt(lines[j].split("\\s+")[0]);
                int y = Integer.parseInt(lines[j].split("\\s+")[1]);
                Station station = new Station(x, y);
                stations.add(station);
            }
            MST mst = new MST(stations);
            mst.primMST();

            PriorityQueue<Edge> pq = new PriorityQueue<Edge>(Comparator.comparingDouble(Edge -> -Edge.weight));
            pq.addAll(mst.edges);
            while (station_with_drone > 1){
                pq.poll();
                station_with_drone--;
            }
            Edge edge = pq.peek();
            String formatted = String.format("%.2f",edge.weight);
            System.out.println(formatted);
            count += total_station + 1;
        }

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
    static class Station{
        public int x;
        public int y;
        public Station(int x, int y){
            this.x = x;
            this.y = y;
        }
        double distanceTo(Station other){
            double dx = x - other.x;
            double dy = y - other.y;
            double distance = Math.sqrt((dx * dx) + (dy * dy));
            return distance;
        }
    }

    static class Edge{
        Station source,destination;
        double weight;
        public Edge(Station source, Station destination, double weight){
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    static class MST{

        public List<Station> stations;
        public List<Edge> edges;

        public MST(List<Station> stations){
            this.stations = stations;
            this.edges = new ArrayList<>();
        }
        void primMST(){
            PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingDouble(edge -> edge.weight));
            List<Station> visited = new ArrayList<>();

            Station startingStation = stations.get(0);
            visited.add(startingStation);
            pq.addAll(getEdges(startingStation));

            while (!pq.isEmpty()){
                Edge currentEdge = pq.poll();
                Station currentStation = currentEdge.destination;

                if (visited.contains(currentStation)){
                    continue;
                }

                visited.add(currentStation);
                edges.add(currentEdge);

                for (Edge adjacentEdge : getEdges(currentStation)){
                    Station adjacentStation = adjacentEdge.destination;
                    if (!visited.contains(adjacentStation)){
                        pq.add(adjacentEdge);
                    }
                }
            }
        }

        public List<Edge> getEdges(Station station){
            List<Edge> edgeStations = new ArrayList<>();
            for (Station other : stations){
                if (!station.equals(other)){
                    double distance = station.distanceTo(other);
                    double weight = distance;
                    edgeStations.add(new Edge(station, other, weight));
                }
            }
            return edgeStations;
        }

    }
}