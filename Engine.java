//Class for engine
public class Engine {
    private final double speedValue;
    private final String engineName;

    //Constructor for Engine
    public Engine (double speedValue, String engineName) {
        this.speedValue = speedValue;
        this.engineName = engineName;
    }

    //get methods to be able to use these values across different classes.
    public double getSpeedValue() {
        return speedValue;
    }
    public String getEngineName() {
        return engineName;
    }
}
