import java.util.ArrayList;

public class RaceLogic {
    private ArrayList<Stop> listOfStops;
    private ArrayList<Car> listOfCars;
    private Track raceTrack;

    public RaceLogic() {
        listOfStops = new ArrayList<>();
        listOfCars = new ArrayList<>();
    }

    public void setTrack(Track track) {
        raceTrack = track;
    }

    public void setListOfStops(ArrayList<Stop> listOfStops) {
        this.listOfStops = listOfStops;
    }

    public ArrayList<Stop> getListOfStops() {
        return listOfStops;
    }

    public void setListOfCars(ArrayList<Car> listOfCars) {
        this.listOfCars = listOfCars;
    }

    public ArrayList<Car> getListOfCars() {
        return listOfCars;
    }

    public Track getRaceTrack() {
        return raceTrack;
    }

    public void beginRace() {
        //gives all the cars the list of stops so they can calculate the pathfinding
        for (Car car : listOfCars) {
            car.setStops(listOfStops);
        }

        //actual game running
        boolean gameOver = false;
        while (!gameOver) {
            for (Car car : listOfCars) {
                car.move();
                gameOver = car.isWinner();
            }
        }
    }
}


