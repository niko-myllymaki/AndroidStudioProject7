package nm.vamk.assignment7;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddMeetingActivity extends AppCompatActivity {

    EditText meetingTitleEditText, meetingLocationEditText, meetingParticipantsEditText, meetingDateEditText, meetingTimeEditText;

    Button submitButton, summaryButton, searchButton;

    String title, location, participants, date, time;

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    ArrayList<Meeting> meetingArrayList;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmeeting);

        meetingTitleEditText = findViewById(R.id.editText_meetingTitle);
        meetingLocationEditText = findViewById(R.id.editText_meetingLocation);
        meetingParticipantsEditText = findViewById(R.id.editText_meetingParticipants);
        meetingDateEditText = findViewById(R.id.editText_meetingDate);
        meetingTimeEditText = findViewById(R.id.editText_meetingTime);

        meetingDateEditText.setOnTouchListener(onTouchListener);
        meetingTimeEditText.setOnTouchListener(onTouchListener);

        submitButton = findViewById(R.id.button_submit);
        submitButton.setOnClickListener(ButtonClickListener);

        summaryButton = findViewById(R.id.button_summary);
        summaryButton.setOnClickListener(ButtonClickListener);

        searchButton = findViewById(R.id.button_search);
        searchButton.setOnClickListener(ButtonClickListener);

        meetingTitleEditText.setOnKeyListener(KeyListener);
        meetingLocationEditText.setOnKeyListener(KeyListener);
        meetingParticipantsEditText.setOnKeyListener(KeyListener);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("meeting_data");
        meetingArrayList = (ArrayList<Meeting>) bundle.getSerializable("ARRAYLIST");

        //Log.d("MEETINGDATA",  meetingArrayList.toString());

        }

    private View.OnClickListener ButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Button clickedButton = (Button) v;

            //Summary button event
            if (clickedButton.equals(summaryButton)) {
                Intent intent = new Intent();
                intent.putExtra("meeting_data",MeetingDB.getMeetingsStringBuilder().toString());

                setResult(RESULT_OK, intent);
                finish();
            }

            //Search button event
            if (clickedButton.equals(searchButton)) {
                Intent intent = new Intent(AddMeetingActivity.this, SearchMeetingActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST", (Serializable) MeetingDB.getMeetingsList());
                intent.putExtra("meeting_data", args);

                startActivity(intent);
            }

            //Submit button event
            if (clickedButton.equals(submitButton)) {

                title = "";
                location = "";
                participants = "";
                date = "";
                time = "";

                //Check the input fields
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

                if (meetingDateEditText.getText().length() != 0) {
                    date = meetingDateEditText.getText().toString();
                } else {
                    meetingDateEditText.setBackgroundColor(Color.rgb(255, 0, 0));
                }

                if (meetingTimeEditText.getText().length() != 0) {
                    time = meetingTimeEditText.getText().toString();
                } else {
                    meetingTimeEditText.setBackgroundColor(Color.rgb(255, 0, 0));
                }

                //Add a new meeting to database
                if (!title.isEmpty() && !location.isEmpty() && !participants.isEmpty()) {
                    MeetingDB.addNewMeetingToDatabase(title, location, participants, date, time);
                    //meeting = new Meeting(title,location, participants, MeetingDB.getCurrentDateTime());
                    //Log.d("MEETINGDATA", MeetingDB.getMeetingsList().toString());

                    meetingTitleEditText.setText("");
                    meetingLocationEditText.setText("");
                    meetingParticipantsEditText.setText("");
                    meetingDateEditText.setText("");
                    meetingTimeEditText.setText("");

                }
            }
        }
    };

    private View.OnKeyListener KeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            EditText inputField = (EditText) v;
            //Typing to edit text events
            if (inputField.equals(meetingTitleEditText)) {
                meetingTitleEditText.setBackgroundColor(0);
            }

            if (inputField.equals(meetingLocationEditText)) {
                meetingLocationEditText.setBackgroundColor(0);
            }

            if (inputField.equals(meetingParticipantsEditText)) {
                meetingParticipantsEditText.setBackgroundColor(0);
            }

            return false;
        }
    };

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            //set time picker dialog here and check which one is pressed.

            EditText clickedEditText = (EditText) v;
            if(clickedEditText.equals(meetingDateEditText)) {
                openDatePickerDialog();
            }
            if(clickedEditText.equals(meetingTimeEditText)) {
                openTimePickerDialog();
            }

            return false;
        }
    };

    private void openDatePickerDialog() {

        datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                datePickerDialog.hide();
                meetingDateEditText.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(month+1) + "/" + String.valueOf(year));
            }
        }, 2023, 10, 23);

        datePickerDialog.show();
    }

    private void openTimePickerDialog() {

        final Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Initializing Time Picker Dialog
        timePickerDialog = new TimePickerDialog(this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                meetingTimeEditText.setText("");
                meetingTimeEditText.setText(String.valueOf(hourOfDay)+":"+String.valueOf(minute));
            }
        }, hour, minute, true);

        timePickerDialog.show();

    }

}