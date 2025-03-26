//Class for car

import java.lang.Math;
import java.util.ArrayList;

public class Car {
    private Position carPos;
    private Stop target;
    private final double speed;
    private ArrayList<Stop> stops;
    private int stopsPassed;
    private int currentStop;

    public Car (Engine engine, Tire tire, Stop target) {
        this.target = target;
        this.speed = engine.getSpeedValue() + tire.getSpeedValue();
    }

    public void setStops (ArrayList<Stop> stops) {
        this.stops = stops;
    }
    
    public void updateTarget (Stop newTarget) {
        target = newTarget;
    }

    public Position getCarPos() {
        return carPos;
    }
    
    public boolean isWinner() {
        return stopsPassed < currentStop;
    }

    public boolean isInRange () {
        //going to calculate the distance using distance formula, if withing the cars speed then return true.
        double carX = carPos.getX();
        double carY = carPos.getY();
        double stopX = target.getStopPos().getX();
        double stopY = target.getStopPos().getY();

        double distance = Math.sqrt(Math.pow(stopX - carX, 2) + Math.pow(stopY - carY, 2));
        return distance <= speed;
    }

    //This method not only moves the car, it also finds the direction it needs to move in.
    public void move() {
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
