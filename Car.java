//Class for car
import java.lang.Math;

public class Car {
    private  Engine engine;
    private Tire tire;
    private Position carPos;
    private Position targetPos;
    private double speed;

    public Car (Engine engine, Tire tire) {
        this.engine = engine;
        this.tire = tire;
        this.speed = this.engine.getSpeedValue() + this.tire.getSpeedValue();
    }

    public Position getCarPos() {
        return carPos;
    }

    public void move() {
        int deltaX = this.targetPos.getX() - this.carPos.getX();
        int deltaY = this.targetPos.getY() - this.carPos.getY();

    }
}
