//David Hill Class made for Venue of the race


public class Venue {
    //The private variables for information about the venue.
    private String cityLocation;
    private String venueName;


    //Here is the Constructor for the class.
    public Venue(String cityLocation, String venueName) {
        this.cityLocation = cityLocation;
        this.venueName = venueName;
    }

    //Here is the getter to return the string of the venue name.
    public String getVenueName() {
        return venueName;
    }
    //Here is the getter to return the string of the location of the venue.
    public String getLocation() {
        return cityLocation;
    }
}