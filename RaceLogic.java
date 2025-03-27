import java.util.ArrayList;

//FINISHED ON 3/27/2025
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

    public void beginRace() {
        //gives all the cars the list of stops so they can calculate the pathfinding
        for (Car car : listOfCars) {
            car.setStops(listOfStops);
        }
        System.out.println("SET STOPS: DONE");

        //actual game running
        boolean gameOver = false;
        while (!gameOver) {
            for (Car car : listOfCars) {
                System.out.println("MOVING CARS: DONE");
                car.move();
                gameOver = car.isWinner();
                if (gameOver) {
                    System.out.println("IF STATEMENT PROCKED");
                    break;
                }
            }
            System.out.println("FOR LOOP DONE");
        }
        System.out.println("WHILE LOOP DONE");
    }
}


