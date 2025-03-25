public class Stop {
    //Stop is allowed to know its position and the next Stop
    private Position stopPos;
    private Stop nextStop;
    private String name;
    private double x;
    private double y;

    public Stop(Position stopPos) {
        this.stopPos = stopPos;
        this.name = name;
        this.x = x;
        this.y = y;
    }
    public void setNextStop(Stop nextStop) {
        this.nextStop = nextStop;
    }
    public Stop getNextStop() {
        return nextStop;
    }
    public Position getStopPos() {
        return stopPos;
    }
    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}
