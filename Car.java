//Class for car

import java.lang.Math;
import java.util.ArrayList;

public class Car {
    private final Engine engine;
    private final Tire tire;
    private Position carPos;
    private Position targetPos;
    private final double speed;
    private ArrayList<Stop> stops;
    private int stopsPassed;

    public Car (Engine engine, Tire tire, Position targetPos) {
        this.engine = engine;
        this.tire = tire;
        this.targetPos = targetPos;
        this.speed = this.engine.getSpeedValue() + this.tire.getSpeedValue();
    }

    public void getStops (ArrayList<Stop> stops) {
        this.stops = stops;
    }
    
    public void updateTargetPos () {
        
        targetPos = newTargetPos;
    }

    public Position getCarPos() {
        return carPos;
    }
    
    public boolean isWinner(int numOfStops) {
        return stopsPassed < numOfStops;
    }

    public boolean isInRange (Position targetPos) {
        //going to calculate the distance using distance formula, if withing the cars speed then return true.
        double carX = carPos.getX();
        double carY = carPos.getY();
        double stopX = targetPos.getX();
        double stopY = targetPos.getY();

        double distance = Math.sqrt(Math.pow(stopX - carX, 2) + Math.pow(stopY - carY, 2));
        return distance <= speed;
    }

    //This method not only moves the car, it also finds the direction it needs to move in.
    public void move() {
        double carX = carPos.getX();
        double carY = carPos.getY();
        double stopX = targetPos.getX();
        double stopY = targetPos.getY();

        double deltaX = stopX - carX;
        double deltaY = stopY - carY;

        double carAngle = Math.atan2(deltaY, deltaX);

        double horizontalSpeedComponent = speed * Math.cos(carAngle);
        double verticalSpeedComponent = speed * Math.sin(carAngle);
        if (isInRange(targetPos)) {
            carPos = targetPos;
            stopsPassed++;
        }
        else {
            carPos = new Position(carPos.getX() + horizontalSpeedComponent, carPos.getY() + verticalSpeedComponent);
        }
    }
}
