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

    public Position getCarPos() {
        return carPos;
    }

    public boolean isInRange (Position targetPos) {
        //temporary return statement.
        return true;
    }

    //This method not only moves the car, it also finds the direction it needs to move in.
    public void move(Position targetPos) {
        double carX = carPos.getX();
        double carY = carPos.getY();
        double checkpointX = targetPos.getX();
        double checkpointY = targetPos.getY();

        double deltaX = checkpointX - carX;
        double deltaY = checkpointY - carY;

        double carAngle = Math.atan2(deltaY, deltaX);

        double horizontalSpeedComponent = speed * Math.cos(carAngle);
        double verticalSpeedComponent = speed * Math.sin(carAngle);
        //gonna add a isInRange function.
    }
}
