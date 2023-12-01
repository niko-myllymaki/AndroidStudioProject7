package nm.vamk.assignment7;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class Meeting implements Serializable {

    private String title;
    private String location;
    private String participants;
    private String dateTime;

    public Meeting(String title, String location, String participants , String dateTime) {
        this.title = title;
        this.location = location;
        this.participants = participants;
        this.dateTime = dateTime;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getParticipants() {
        return participants;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String toString() {
        return title + ", " + location + ", " + participants + ", " + dateTime;
    }

}