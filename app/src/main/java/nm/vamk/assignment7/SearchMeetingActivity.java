package nm.vamk.assignment7;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchMeetingActivity extends AppCompatActivity {

    TextView searchResult;
    AutoCompleteTextView autoCompleteTextViewDate, autoCompleteTextViewTime, autoCompleteTextViewParticipants;
    ArrayAdapter<String> dateArrayAdapter, timeArrayAdapter, participantsArrayAdapter;
    Button backButton;

    String meetingData;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchmeeting);

        searchResult = findViewById(R.id.tw_search_result);

        backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(ButtonClickListener);

        autoCompleteTextViewDate = findViewById(R.id.auto_complete_tw_date);
        autoCompleteTextViewTime = findViewById(R.id.auto_complete_tw_time);
        autoCompleteTextViewParticipants = findViewById(R.id.auto_complete_tw_participants);

        autoCompleteTextViewDate.setOnItemClickListener(ItemClickListener);
        autoCompleteTextViewTime.setOnItemClickListener(ItemClickListener);
        autoCompleteTextViewParticipants.setOnItemClickListener(ItemClickListener);

        autoCompleteTextViewDate.setThreshold(1);
        autoCompleteTextViewTime.setThreshold(1);
        autoCompleteTextViewParticipants.setThreshold(1);

        meetingData = "";

        //Here we access the incoming Intenet object
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            meetingData = extras.getString("meeting_data");

        //Toast.makeText(this, meetingData, Toast.LENGTH_LONG).show();
        Log.d("MEETINGDATA", "Before conversion: " + meetingData);

        Log.d("MEETINGDATA", convertToArrayList().toString());

        updateArrayAdapter();
    }


    public List<String> convertToArrayList() {
        List<String> convertedMeetingList = new ArrayList<>();

        String[] meetingDataString =  meetingData.split("\n");
        for(String meetings : meetingDataString) {
            convertedMeetingList.add(meetings);
        }


        return convertedMeetingList;
    }


    public void updateArrayAdapter() {
        //Here we define an array adapters with a style and a content list
        dateArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, convertToArrayList());
        timeArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, convertToArrayList());
        participantsArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, convertToArrayList());

        //Here we set the array adapter for the AutoCompleteTextView
        autoCompleteTextViewDate.setAdapter(dateArrayAdapter);
        autoCompleteTextViewTime.setAdapter(timeArrayAdapter);
        autoCompleteTextViewParticipants.setAdapter(participantsArrayAdapter);
    }

    private AdapterView.OnItemClickListener ItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if(adapterView.getAdapter().toString().equals(autoCompleteTextViewDate.getAdapter().toString())) {
                searchResult.setText(autoCompleteTextViewDate.getText().toString());
                autoCompleteTextViewDate.setText("");
            }

            if(adapterView.getAdapter().toString().equals(autoCompleteTextViewTime.getAdapter().toString())) {
                searchResult.setText(autoCompleteTextViewTime.getText().toString());
                autoCompleteTextViewTime.setText("");
            }

            if(adapterView.getAdapter().toString().equals(autoCompleteTextViewParticipants.getAdapter().toString())) {
                searchResult.setText(autoCompleteTextViewParticipants.getText().toString());
                autoCompleteTextViewParticipants.setText("");
            }

        }
    };



    private View.OnClickListener ButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }

    };
}
