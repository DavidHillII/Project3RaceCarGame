import java.util.ArrayList;

//FINISHED ON 3/27/2025

//This class mainly behaves like an intersection between the other classes, allowing for an easy and safe exchange of information.
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

    public void setListOfCars(ArrayList<Car> listOfCars) {
        this.listOfCars = listOfCars;
    }

    public ArrayList<Car> getListOfCars() {
        return listOfCars;
    }
}


