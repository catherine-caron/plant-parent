package ca.plantcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class AddExistingPlant extends AppCompatActivity {

    private String error = null;
    private List<String> personUsernames = new ArrayList<>();
    private ArrayAdapter<String> personAdapter;
    private ArrayAdapter<String> eventAdapter;
    private List<String> personPasswords = new ArrayList<>();
    private List<String> plants = new ArrayList<>();
    private  String commonName = "";
    private  String botanicalName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addexistingplant);
        displayAllPlants();
    }


    public void displayAllPlants(){
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String memberId = preferences.getString("memberId", null);
        //nameHere.setText(memberId);
       // TextView testEdit = (TextView)findViewById(R.id.testEdit);
        //testEdit.setText("test1");
        HttpUtils.get("plant/ori/",new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {

                    //JSONArray arr = response.getJSONArray("plants");
                    LinearLayout llMain = (LinearLayout) findViewById(R.id.linearLayoutScroll);
                   //String testEdit = findViewById(R.id.testEdit);
                  // testEdit.setText("test2");
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject plants = response.getJSONObject(i);

                        commonName = plants.getString("commonName");
                        botanicalName = plants.getString("botanicalName");
                        Integer plantId = plants.getInt("plantId");

                        //String endDateTime = plants.getString("endDateTime");
                        // nameHere.setText(givenName);
                        TextView textNumber = new TextView(AddExistingPlant.this);
                        textNumber.setText("Common Name:");
                        TextView textGiven = new TextView(AddExistingPlant.this);
                        textGiven.setText(commonName);

                        TextView botan = new TextView(AddExistingPlant.this);
                        botan.setText("Botanical Name:");
                        TextView textBotan = new TextView(AddExistingPlant.this);
                        textBotan.setText(botanicalName);

                        Button myButton = new Button(AddExistingPlant.this);
                        myButton.setText("Select Plant");
                        myButton.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();

                                editor.putInt("plantIdAdd", plantId);
                                editor.commit();
                                Intent intent = new Intent(AddExistingPlant.this, SubmitExistingPlant.class);
                                startActivity(intent);
                                  }
                        });


                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);



                        botan.setTextSize(14);
                        botan.setGravity(View.TEXT_ALIGNMENT_CENTER);
                        //textNumber.setPadding(0,0,0,10);
                        textBotan.setTextSize(14);
                        textBotan.setGravity(View.TEXT_ALIGNMENT_CENTER);
                         textGiven.setPadding(0,0,0,30);

                        textNumber.setTextSize(14);
                        textNumber.setGravity(View.TEXT_ALIGNMENT_CENTER);
                        //textNumber.setPadding(0,0,0,10);
                        textGiven.setTextSize(14);
                        textGiven.setGravity(View.TEXT_ALIGNMENT_CENTER);
                        // textGiven.setPadding(0,0,0,60);


                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT
                        );
                        textNumber.setLayoutParams(params);
                        llMain.addView(textNumber);

                        textGiven.setLayoutParams(params);
                        llMain.addView(textGiven);

                        botan.setLayoutParams(params);
                        llMain.addView(botan);

                        textBotan.setLayoutParams(params);
                        llMain.addView(textBotan);

                        llMain.addView(myButton, lp);


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
                TextView tvError = (TextView) findViewById(R.id.error); //error is an id in xml files
                tvError.setText(error);

                if (error == null || error.length() == 0) {
                    tvError.setVisibility(View.GONE);
                } else {
                    tvError.setVisibility(View.VISIBLE);
                }

            }
        }); }
}
