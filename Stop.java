public class Stop {
    //Stop is allowed to know its position and the next Stop
    private Position stopPos;
    private Stop nextStop;

    public Stop(Position stopPos) {
        this.stopPos = stopPos;
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
}
