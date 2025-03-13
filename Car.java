//Class for car
public class Car {
    private  Engine engine;
    private Tire tire;
    private Position carPos;
    private double speed;

    public Car (Engine engine, Tire tire) {
        this.engine = engine;
        this.tire = tire;
        this.speed = this.engine.getSpeedValue() + this.tire.getSpeedValue();
    }
}
