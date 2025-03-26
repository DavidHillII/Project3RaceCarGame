import java.util.ArrayList;

public class RaceLogic {
    private ArrayList<Stop> listOfStops;
    private ArrayList<Car> listOfCars;
    private Track raceTrack;

    public RaceLogic() {
        listOfStops = new ArrayList<>();
        listOfCars = new ArrayList<>();
        raceTrack = new Track("Race Track 1");
    }

    public Track getRaceTrack() {
        return raceTrack;
    }

    public void beginRace() {
        for (Car car : listOfCars) {
            car.move();
        }
    }
}


