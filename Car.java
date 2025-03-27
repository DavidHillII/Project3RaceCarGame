import java.lang.Math;
import java.util.ArrayList;

//FINISHED ON 3/27/2025
public class Car {
    private Position carPos;
    private Stop startingPoint;
    private Stop target;
    private double speed;
    private ArrayList<Stop> stops;
    private int stopsPassed;
    private int numOfStops;
    private Track track;

    public Car (Engine engine, Tire tire, Stop startingPoint, Track track) {
        this.startingPoint = startingPoint;
        this.target = startingPoint.getNextStop();
        this.speed = (engine.getSpeedValue() + tire.getSpeedValue())*0.02;
        this.track = track;
        carPos = startingPoint.getStopPos();
        numOfStops = track.getStops().size();
        stopsPassed = 0;
    }

    public void updateTarget (Stop newTarget) {
        target = newTarget;
    }

    public Position getCarPos() {
        return carPos;
    }

    //Determines if this Car has won the race, the criteria is as follows. (The first car to cross
    public boolean isWinner() {
        return stopsPassed == numOfStops - 1;
    }

    public double getSpeed() {
        return speed;
    }

    //going to calculate the distance using distance formula, if the target is within the Cars "speed" then return true, if not return false.
    public boolean isInRange () {
        double carX = carPos.getX();
        double carY = carPos.getY();
        double stopX = target.getStopPos().getX();
        double stopY = target.getStopPos().getY();

        double distance = Math.sqrt(Math.pow(stopX - carX, 2) + Math.pow(stopY - carY, 2));
        return distance <= speed;
    }

    //This method finds the direction it needs to move it, uses the "isInRange" method to determine if it is in its range, then move accordingly.
    public void move() {
        if (isWinner()) return;
        double carX = carPos.getX();
        double carY = carPos.getY();
        double stopX = target.getStopPos().getX();
        double stopY = target.getStopPos().getY();

        double deltaX = stopX - carX;
        double deltaY = stopY - carY;

        double carAngle = Math.atan2(deltaY, deltaX);

        double horizontalSpeedComponent = speed * Math.cos(carAngle);
        double verticalSpeedComponent = speed * Math.sin(carAngle);

        if (isInRange()) {
            carPos = target.getStopPos();
            updateTarget(target.getNextStop());
            stopsPassed++;
        }
        else {
            carPos = new Position(carPos.getX() + horizontalSpeedComponent, carPos.getY() + verticalSpeedComponent);
        }
    }
}
