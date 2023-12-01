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
    ArrayList<Meeting> meetingArrayList;

    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmeeting);

        meetingTitleEditText = findViewById(R.id.editText_meetingTitle);
        meetingLocationEditText = findViewById(R.id.editText_meetingLocation);
        meetingParticipantsEditText = findViewById(R.id.editText_meetingParticipants);
        meetingDateEditText = findViewById(R.id.editText_meetingDate);
        meetingTimeEditText = findViewById(R.id.editText_meetingDate);

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


        String data = "";

        /*
        Bundle bundle = getIntent().getExtras();
        data = bundle.getString("meetingsList", "Default");
        */

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("meeting_data");
        meetingArrayList = (ArrayList<Meeting>) args.getSerializable("ARRAYLIST");

        //Log.d("MEETINGDATA",  meetingArrayList.toString());

        /*
        //Here we access the incoming Intenet object
        Bundle extras = getIntent().getExtras();

        if (extras != null)
            data = extras.getString("data");

        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
*/
        //summaryInfo = findViewById(R.id.tw_summary_info);
        //Here we set the hint value of the edit text to the data
        //sent by calling activity
        //summaryInfo.setText(MeetingDB.getMeetingsList().toString());

        /*
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        //This method processes data sent back from the activity
                        if (result.getResultCode() == RESULT_OK) {
                            //String temp = result.getData().getStringExtra()
                            Toast.makeText(AddMeetingActivity.this, "back from search", Toast.LENGTH_LONG).show();

                        }
                    }
                });
*/
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

                if (!title.isEmpty() && !location.isEmpty() && !participants.isEmpty()) {
                    MeetingDB.addNewMeetingToDatabase(title, location, participants, date, time);
                    //meeting = new Meeting(title,location, participants, MeetingDB.getCurrentDateTime());
                    Log.d("MEETINGDATA", MeetingDB.getMeetingsList().toString());

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
            if (inputField.equals(meetingTitleEditText)) {
                meetingTitleEditText.setBackgroundColor(0);
            }

            if (inputField.equals(meetingLocationEditText)) {
                meetingLocationEditText.setBackgroundColor(0);
            }

            if (inputField.equals(meetingParticipantsEditText)) {
                meetingParticipantsEditText.setBackgroundColor(0);
            }

            //Toast.makeText(MainActivity.this, inputField.getResources().getResourceEntryName(inputField.getId()), Toast.LENGTH_LONG).show();

            return false;
        }


    };

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            //set time picer dialog here and check which one is pressed.
            openDatePickerDialog();

            return false;
        }
    };

    private void openDatePickerDialog() {

        datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                meetingDateEditText.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(month+1) + "/" + String.valueOf(year));
                openTimePickerDialog();
                //Toast.makeText(AddMeetingActivity.this, String.valueOf(dayOfMonth) + " " + String.valueOf(month) + " " + String.valueOf(year),Toast.LENGTH_LONG).show();

            }
        }, 2023, 10, 23);

        datePickerDialog.show();
    }

    private void openTimePickerDialog() {

        //datePickerDialog.dismiss();
        final Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Initializing our Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                EditText meetingDateTimeEditTextTemp = meetingTimeEditText;
                meetingTimeEditText.setText(meetingDateTimeEditTextTemp.getText().toString() + " " + String.valueOf(hourOfDay)+":"+String.valueOf(minute));
                //meetingDateTimeEditTextTemp.setText("");

            }
        }, hour, minute, true);

        timePickerDialog.show();

    }

}