package ca.plantcare;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Reminder extends AppCompatActivity {


    private String error = null;
    private List<String> personUsernames = new ArrayList<>();
    private ArrayAdapter<String> personAdapter;
    private ArrayAdapter<String> eventAdapter;
    private List<String> personPasswords = new ArrayList<>();
    private List<String> plants = new ArrayList<>();
    private  String givenName = "";
    private  String botanicalName = "";
    private  String soilType = "";
    private  String sunType = "";
    private  String wateringRec = "";
    private  String bloomTime = "";
    private  String icon = "";
    private String date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder);
        viewReminders();    }


    public void viewReminders()  {
        error = "";

        //final TextView chooseSchedule = (TextView) findViewById(R.id.chooseSchedule);
       // TextView myTest = (TextView) findViewById(R.id.myTest);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String memberId = preferences.getString("memberId",null);
        //myTest.setText("bruh");
        HttpUtils.get("member/getMemberByUsername/"+ memberId,new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                  //  response.getJSONArray("plants");
                    JSONArray arr = response.getJSONArray("plants");
                    LinearLayout llMain = (LinearLayout) findViewById(R.id.linearLayoutScrollReminder);
                    String myresp = String.valueOf(response.length());
                    //myTest.setText("waht");

                    for (int i = 0; i < response.length(); i++) {
//                        myTest.setText("test2");

                        JSONObject plants = arr.getJSONObject(i);
                        JSONObject schedule = plants.getJSONObject("wateringRecommendation");

                        //JSONObject reminder = schedule.getJSONObject("reminder");
                        JSONArray arrReminder = schedule.getJSONArray("reminder");

                      //  myTest.setText("testrem");
                        for (int k = 0; k < 1; k++) {

                            JSONObject reminder = arrReminder.getJSONObject(0);
                            date = reminder.getString("date");}

                        //myTest.setText(String.valueOf(i));

                        givenName = plants.getString("givenName");
                        Integer plantId = plants.getInt("plantId");
                        //myTest.setText("test3");
                        //String endDateTime = plants.getString("endDateTime");
                        // nameHere.setText(givenName);
                        TextView textNumber = new TextView(Reminder.this);
                        textNumber.setText("Plant Name:");

                        TextView textGiven = new TextView(Reminder.this);
                        textGiven.setText(givenName);

                        TextView textReminder = new TextView(Reminder.this);
                        textReminder.setText(date);
                        //myTest.setText("test4");

                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                        textNumber.setTextSize(24);
                        textNumber.setGravity(View.TEXT_ALIGNMENT_CENTER);
                        textNumber.setPadding(0,0,0,10);
                        textGiven.setTextSize(24);
                        textGiven.setGravity(View.TEXT_ALIGNMENT_CENTER);
                        textGiven.setPadding(0,0,0,10);
                        textReminder.setTextSize(24);
                        textReminder.setGravity(View.TEXT_ALIGNMENT_CENTER);
                        textReminder.setPadding(0,0,0,60);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT
                        );
                        textNumber.setLayoutParams(params);
                        llMain.addView(textNumber);

                        textGiven.setLayoutParams(params);
                        llMain.addView(textGiven);

                        textReminder.setLayoutParams(params);
                        llMain.addView(textReminder);

                    } }
                catch (JSONException e) {
                    error += e.getMessage();
                   // myTest.setText("error");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }

            private void refreshErrorMessage() {
                //set the error message
                TextView tvError = (TextView) findViewById(R.id.error); //error is an id in xml files
                tvError.setText(error);

                if (error == null || error.length() == 0) {
                    tvError.setVisibility(View.GONE);
                } else {
                    tvError.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    public void backHomeviewReminder(View v){
        startActivity(new Intent(Reminder.this, LoggedInView.class));
    }

    public void logReminder(View v){
           /*SharedPreferences preferences =getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        finish();*/
        startActivity(new Intent(Reminder.this, MemberProfile.class));
    }
}
