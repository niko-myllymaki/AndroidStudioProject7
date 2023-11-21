package nm.vamk.assignment7;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText meetingTitleEditText, meetingLocationEditText, meetingParticipantsEditText, meetingDateTimeEditText;

    Button summaryButton;

    String title, location, participants, dateTime;

    //Here we declare ActivityResultLauncher
    ActivityResultLauncher<Intent> activityResultLauncher ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        meetingTitleEditText = findViewById(R.id.editText_meetingTitle);
        meetingLocationEditText = findViewById(R.id.editText_meetingLocation);
        meetingParticipantsEditText = findViewById(R.id.editText_meetingParticipants);
        meetingDateTimeEditText = findViewById(R.id.editText_meetingDateTime);

        summaryButton = findViewById(R.id.button_summary);
        summaryButton.setOnClickListener(ButtonClickListener);

        //Here we define ActivityResultLauncher using registerForActivityResult() method.
        //We will use activityResultLauncher to launch the intent and handle data returned
        //by the intent.
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {


                    @Override
                    public void onActivityResult(ActivityResult result) {
                        //This method processes data sent back from the activity
                        if (result.getResultCode() == RESULT_OK) {
                            //Toast.makeText(this, "message", Toast.LENGTH_LONG).show();
                            //usernameTextView.setVisibility(View.VISIBLE);
                            //usernameTextView.setText(result.getData().getStringExtra("user_name") + " " + result.getData().getStringExtra("current_time"));
                        }
                    }
                });
    }

    //This method starts the activity.
    private void startLoginActivity(Meeting meeting) {
        Bundle bundle = new Bundle();
        // passing the data into the bundle
        //Meeting meeting = new Meeting("testTitle", "testLocal", "testNames", MeetingDB.getCurrentDateTime().toString());
        bundle.putString("key1", meeting.toString());

        //Here we define intent to start LoginActivity and pass some data to it.
        //Date date = new Date();
        //Log.d("DATE", new Date().toString());
        Intent intent = new Intent(this, SummaryActivity.class);
        //intent.putExtra("data", meetingTitleEditText.getText().toString());
        intent.putExtras(bundle);

        //Here we launch the activity, which will start LoginActivity and
        //expect results from it.
        activityResultLauncher.launch(intent);
    }

    private View.OnClickListener ButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Button clickedButton = (Button) v;
            Meeting meeting = null;

            //Submit button event
            if (clickedButton.equals(summaryButton)) {

                title = "";
                location = "";
                participants = "";
                dateTime = "";

                if (meetingTitleEditText.getText().length() != 0) {
                    title = meetingTitleEditText.getText().toString();
                } else {
                    meetingTitleEditText.setBackgroundColor(Color.rgb(255, 0, 0));
                }

                if (meetingLocationEditText.getText().length() != 0) {
                    location = meetingLocationEditText.getText().toString();
                } else {
                    meetingLocationEditText.setBackgroundColor(Color.rgb(255, 0, 0));
                }

                if (meetingParticipantsEditText.getText().length() != 0) {
                    participants = meetingParticipantsEditText.getText().toString();
                } else {
                    meetingParticipantsEditText.setBackgroundColor(Color.rgb(255, 0, 0));
                }

                /*
                if (meetingDateTimeEditText.getText().length() != 0) {
                    dateTime = meetingDateTime.getText().toString();
                } else {
                    meetingDateTimeEditText.setBackgroundColor(Color.rgb(255, 0, 0));
                }
*/
                if (!title.isEmpty() && !location.isEmpty() && !participants.isEmpty()) {
                    //MeetingDB.addNewMeetingToDatabase(title, location, participants);
                    meeting = new Meeting(title,location, participants, MeetingDB.getCurrentDateTime());
                    //Log.d("MEETING", "Main " + MeetingDB.getMeetingsList().toString());

                    meetingTitleEditText.setText("");
                    meetingLocationEditText.setText("");
                    meetingParticipantsEditText.setText("");
                    meetingDateTimeEditText.setText("");
                }

                startLoginActivity(meeting);

            }


        }
    };

}