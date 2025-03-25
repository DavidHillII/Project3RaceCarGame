//Class for racetrack David Hill Gabriel Luciano
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Track {
    private String trackName;
    private ArrayList<Car> cars;
    private ArrayList<Stop> stops;
    private JPanel panel;

    // Constructor for the track
    public Track(String trackName) {
        this.trackName = trackName;
        this.panel = panel;
        this.cars = new ArrayList<>();
        this.stops = new ArrayList<>();
    }

    // A function for returning the name of the track
    public String getTrackName() {
        return trackName;
    }

    // A function for returning the number of stops/checkpoints on the track
    public int getNumCheckpoints() {
        return stops.size();
    }

    // Cars are added to the track
    public void addCar(Car car) {
        cars.add(car);
    }

    // Stops are added to the track
    public void addStop(Stop stop) {
        stops.add(stop);
    }

    // Function to return the Arraylist of cars
    public ArrayList<Car> getCars() {
        return cars;
    }

    // Function to return the Arraylist of stops
    public ArrayList<Stop> getStops() {
        return stops;
    }
    // Build track for gui
    public void buildTrack() {
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Color labelColor = Color.BLACK;

        for (Stop stop : stops) {
            JLabel label = new JLabel(stop.getName());
            label.setFont(labelFont);
            label.setForeground(labelColor);
            label.setBounds((int) stop.getX(), (int) stop.getY(), 30, 30);
            panel.add(label);
        }

        panel.revalidate();
        panel.repaint();
    }
}
