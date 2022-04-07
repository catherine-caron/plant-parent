package ca.plantcare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
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

public class LoggedInView extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    private String error = null;
    private List<String> personUsernames = new ArrayList<>();
    private ArrayAdapter<String> personAdapter;
    private ArrayAdapter<String> eventAdapter;
    private List<String> personPasswords = new ArrayList<>();
    private List<String> plants = new ArrayList<>();
    private  String givenName = "";
    private  String botanicalName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
            viewPlant();

    }

    public void viewPlant()  {
        error = "";
        // final TextView username = (TextView) findViewById(R.id.usernameReg);

        //TextView nameHere = (TextView) findViewById(R.id.nameHere);

       SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
       String memberId = preferences.getString("memberId", null);
       //nameHere.setText(memberId);
         HttpUtils.get("member/getMemberByUsername/"+ memberId,new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {

                        JSONArray arr = response.getJSONArray("plants");
                        LinearLayout llMain = (LinearLayout) findViewById(R.id.linearLayoutScroll);
                       // nameHere.setText("test1");
                        for (int i = 0; i < response.length(); i++) {

                            JSONObject plants = arr.getJSONObject(i);

                          givenName = plants.getString("givenName");
                         botanicalName = plants.getString("botanicalName");
                            Integer plantId = plants.getInt("plantId");

                        //String endDateTime = plants.getString("endDateTime");
                           // nameHere.setText(givenName);
                            TextView textNumber = new TextView(LoggedInView.this);
                            textNumber.setText("Plant Name:");

                            TextView textGiven = new TextView(LoggedInView.this);
                            textGiven.setText(givenName);

                            Button myButton = new Button(LoggedInView.this);
                            myButton.setText("Plant Info");
                            myButton.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();

                                    editor.putInt("plantIdView", plantId);
                                    editor.commit();
                                    Intent intent = new Intent(LoggedInView.this, ViewPlant.class);
                                    startActivity(intent);
                                }
                            });

                            Button waterPlant = new Button(LoggedInView.this);
                            waterPlant.setText("Water");
                            waterPlant.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    RequestParams body = new RequestParams();
                                    body.add("plantId", plantId.toString());

                                    try {
                                        HttpUtils.put("plant/waterPlant/",body, new JsonHttpResponseHandler() {
                                            @Override
                                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                                try {
                                                    JSONObject serverResp = new JSONObject(response.toString());
                                                } catch (JSONException e) {
                                                    error += e.getMessage();
                                                }
                                                error += "Yay! Plant watered!";
                                            }
                                            @Override
                                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                                try {
                                                    error += errorResponse.get("message").toString();
                                                } catch (JSONException e) {
                                                    error += e.getMessage();
                                                }
                                                //refreshErrorMessage();
                                            }
                                        });
                                    } catch (UnsupportedEncodingException e) {
                                        error += e.getMessage();
                                    }
                                }
                            });
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            
                            textNumber.setTextSize(24);
                            textNumber.setGravity(View.TEXT_ALIGNMENT_CENTER);
                            textNumber.setPadding(0,0,0,10);
                            textGiven.setTextSize(24);
                            textGiven.setGravity(View.TEXT_ALIGNMENT_CENTER);
                            textGiven.setPadding(0,0,0,60);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                           LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT
                            );
                            textNumber.setLayoutParams(params);
                            llMain.addView(textNumber);

                            textGiven.setLayoutParams(params);
                            llMain.addView(textGiven);

                            llMain.addView(myButton, lp);
                            llMain.addView(waterPlant, lp);


                        }

                        //displayAppointments.add(currServiceName + "\n" + HelperMethods.displayDateTimeFromTo(currStart, currEnd));
                    } catch (JSONException e) {
                       // nameHere.setText("fail");
                        error += e.getMessage();
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
                 TextView tvError = (TextView) findViewById(R.id.emailEnterred); //error is an id in xml files
                 tvError.setText(error);

                 if (error == null || error.length() == 0) {
                     tvError.setVisibility(View.GONE);
                 } else {
                     tvError.setVisibility(View.VISIBLE);
                 }

             }
        });
    }
    public void addPlantButton(View v){
        startActivity(new Intent(LoggedInView.this, AddPlant.class));
    }

    public void goToMember(View v){
        startActivity(new Intent(LoggedInView.this, MemberProfile.class));
    }

    public void goToReminder(View v){
        startActivity(new Intent(LoggedInView.this, Reminder.class));
    }

}
