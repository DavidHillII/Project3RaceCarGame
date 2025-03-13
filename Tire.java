//Class for engine
public class Tire {
    private final double speedValue;
    private final String tireName;

    public Tire (double speedValue, String tireName) {
        this.speedValue = speedValue;
        this.tireName = tireName;
    }
    public double getSpeedValue() {
        return speedValue;
    }
    public String getTireName() {
        return tireName;
    }
}
