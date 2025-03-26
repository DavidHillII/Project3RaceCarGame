//Class for racetrack David Hill Gabriel Luciano
import java.util.ArrayList;

public class Track {
    private String trackName;
    private ArrayList<Car> cars;
    private ArrayList<Stop> stops;

    // Constructor for the track
    public Track(String trackName) {
        this.trackName = trackName;
        this.cars = new ArrayList<>();
        this.stops = new ArrayList<>();
    }

    // A function for returning the name of the track
    public String getTrackName() {
        return trackName;
    }

    // A function for returning the number of checkpoints on the track
    public int getNumCheckpoints() {
        return stops.size();
    }


    // Stops are added to the track
    public void addStop(Stop stop) {
        stops.add(stop);
    }
    
    // Function to return the Arraylist of stops
    public ArrayList<Stop> getStops() {
        return stops;
    }
}