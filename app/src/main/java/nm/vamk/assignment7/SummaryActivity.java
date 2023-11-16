package nm.vamk.assignment7;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SummaryActivity extends AppCompatActivity {

    TextView summaryInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        Log.d("MEETING", "Summary " + MeetingDB.getMeetingsList().toString());

        String data = "";

        /*
        Bundle bundle = getIntent().getExtras();

        data = bundle.getString("key2", "Default");

        summaryInfo = findViewById(R.id.tw_summary_info);
        summaryInfo.setText(data);
         */

        //Here we access the incoming Intenet object
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            data = extras.getString("data");


        summaryInfo = findViewById(R.id.tw_summary_info);
        //Here we set the hint value of the edit text to the data
        //sent by calling activity
        summaryInfo.setText(MeetingDB.getMeetingsList().toString());

    }
}