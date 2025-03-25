//Class for car

import java.lang.Math;

public class Car {
    private final Engine engine;
    private final Tire tire;
    private Position carPos;
    private Position targetPos;
    private final double speed;

    public Car (Engine engine, Tire tire) {
        this.engine = engine;
        this.tire = tire;
        this.speed = this.engine.getSpeedValue() + this.tire.getSpeedValue();
    }

    //add function to update target position

    public Position getCarPos() {
        return carPos;
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
    public void move(Position targetPos) {
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
        }
        else {
            carPos = new Position(carPos.getX() + horizontalSpeedComponent, carPos.getY() + verticalSpeedComponent);
        }
    }
}
