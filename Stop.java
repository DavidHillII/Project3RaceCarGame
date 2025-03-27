//FINISHED ON 3/27/2025

//A "Stop" should know about its own position and the stop ahead of it, so it can update the Car on the next target it needs to get to
public class Stop {
    private Position stopPos;
    private Stop nextStop;
    private double x;
    private double y;

    //Constructor for Stop
    public Stop(Position stopPos) {
        this.stopPos = stopPos;
        this.x = x;
        this.y = y;
    }

    //This method is very important as it serves to inform the Car of where it should head towards next
    public void setNextStop(Stop nextStop) {
        this.nextStop = nextStop;
    }

    //returns the "nextStop" the car must drive towards.
    public Stop getNextStop() {
        return nextStop;
    }

    //gets the position of the next stop, to be used in pathfinding algorithm found on line 54-77 of Car.java
    public Position getStopPos() {
        return stopPos;
    }
}
