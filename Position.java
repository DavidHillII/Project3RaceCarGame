//Gabriel
//FINISHED ON 3/27/2025

//The Position Class serves to hold the coordinates of every Car and Stop, this is to ensure the accuracy of the pathfinding calculations the Cars must do.
public class Position {
    private double x;
    private double y;

    //Constructor for Position
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    //returns the x-component of the Position
    public double getX() {
        return x;
    }

    //returns the y-component of the Position
    public double getY() {
        return y;
    }
}

