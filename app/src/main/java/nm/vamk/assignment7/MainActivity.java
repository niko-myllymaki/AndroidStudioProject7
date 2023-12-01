package nm.vamk.assignment7;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView summaryInfo;

    Button backButton;

    //Here we declare ActivityResultLauncher
    ActivityResultLauncher<Intent> activityResultLauncher ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backButton = findViewById(R.id.button_add_meeting);
        backButton.setOnClickListener(ButtonClickListener);

        //Log.d("MEETING", "Summary " + MeetingDB.getMeetingsList().toString());


        summaryInfo = findViewById(R.id.tw_summary_info);
        summaryInfo.setText(MeetingDB.getMeetingsStringBuilder());
        //convertToSummaryFormat();


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
                            Toast.makeText(MainActivity.this, "Returned from AddMeetingActivity!", Toast.LENGTH_LONG).show();
                            summaryInfo.setText(MeetingDB.getMeetingsStringBuilder());
                            //usernameTextView.setVisibility(View.VISIBLE);
                            //usernameTextView.setText(result.getData().getStringExtra("user_name") + " " + result.getData().getStringExtra("current_time"));
                            //summaryInfo.setText(result.getData().getStringExtra("meeting_data"));

                        }
                    }
                });
    }

    /*
    private void convertToSummaryFormat() {

        StringBuilder stringBuilder = new StringBuilder();
        for(Meeting meeting : MeetingDB.getMeetingsList()) {
            stringBuilder.append(meeting).append("\n");
        }

        summaryInfo.setText(stringBuilder);
    }
     */

    //This method starts the activity.
    private void startLoginActivity() {
        Bundle bundle = new Bundle();


        Intent intent = new Intent(this, AddMeetingActivity.class);
        //intent.putExtra("data", "Hello from MainActivity!");

        Bundle args = new Bundle();
        args.putSerializable("ARRAYLIST", (Serializable) MeetingDB.getMeetingsList());
        intent.putExtra("meeting_data", args);

        //Here we launch the activity, which will start LoginActivity and
        //expect results from it.
        activityResultLauncher.launch(intent);
    }

    private View.OnClickListener ButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startLoginActivity();
        }

    };



}