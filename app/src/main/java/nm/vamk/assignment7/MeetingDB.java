package nm.vamk.assignment7;

import java.util.ArrayList;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MeetingDB {

    static List<Meeting> meetingsList = new ArrayList<>();

    static Date date = new Date();

    static {
        meetingsList.add(new Meeting("TITLE", "LOCAL", "BOB", getCurrentDateTime()));
    }

    public static List<Meeting> getMeetingsList() {
        return meetingsList;
    }

    public static void addNewMeetingToDatabase(String title, String location, String participants) {
        meetingsList.add(new Meeting(title, location, participants, getCurrentDateTime()));
    }




    public static String getCurrentDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }




}
