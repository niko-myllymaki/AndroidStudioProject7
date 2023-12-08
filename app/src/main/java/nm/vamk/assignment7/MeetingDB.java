package nm.vamk.assignment7;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MeetingDB implements Serializable {

    static List<Meeting> meetingsList = new ArrayList<>();

    static Date date = new Date();

    static {
        meetingsList.add(new Meeting("Lecture", "VAMK A3009",createParticipantList("Teacher", "TT2020-4A"), getCurrentDateTime()));
        meetingsList.add(new Meeting("Sprint review", "HQ", createParticipantList("Product owner", "Scrum team"), "19/10/2023 15:00"));
        meetingsList.add(new Meeting("Coffee Break", "Café", createParticipantList("Bob", "Jack"), "26/12/2023 19:30"));
    }

    public static List<Meeting> getMeetingsList() {
        return meetingsList;
    }

    // Utility method to create a list of participants
    private static List<String> createParticipantList(String... participants) {
        List<String> participantList = new ArrayList<>();
        for (String participant : participants) {
            participantList.add(participant);
        }
        return participantList;
    }

    public static StringBuilder getMeetingsStringBuilder() {
        StringBuilder stringBuilder = new StringBuilder();
        for(Meeting meeting : getMeetingsList()) {
            stringBuilder.append(meeting).append("\n");
        }

        return stringBuilder;
    }

    public static void addNewMeetingToDatabase(String title, String location, String participants, String date, String time) {
        meetingsList.add(new Meeting(title, location, createParticipantList(participants), date + " " + time));
    }

    public static String getCurrentDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }




}
