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

public class ViewPlant extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plantinfo);
        viewPlant();
    }


    public void viewPlant()  {
        error = "";
        final TextView watering = (TextView) findViewById(R.id.chooseSchedule);
        final TextView iconPic = (TextView) findViewById(R.id.iconPic);
        final TextView plantGivenNameText = (TextView) findViewById(R.id.plantGivenName);
        final TextView plantBotanical = (TextView) findViewById(R.id.plantBotanical);
        final TextView chooseSunlight = (TextView) findViewById(R.id.chooseSunlight);
        final TextView chooseSoil = (TextView) findViewById(R.id.chooseSoil);
        final TextView chooseBloom = (TextView) findViewById(R.id.chooseBloom);
        final TextView chooseToxicity = (TextView) findViewById(R.id.chooseToxicity);
        final TextView chooseCommon = (TextView) findViewById(R.id.enterCommon);

        //final TextView chooseSchedule = (TextView) findViewById(R.id.chooseSchedule);
        //TextView nameHere = (TextView) findViewById(R.id.nameHere);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int plantId = preferences.getInt("plantIdView",0);
        String plantIdString = String.valueOf(plantId);
        //iconPic.setText(plantId);
        //plantGivenNameText.setText(plantIdString);

        HttpUtils.get("plant/"+ plantIdString,new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {


                    //JSONObject serverResp = new JSONObject(response.toString());


                    //JSONArray arr = response.getJSONArray("plants");
                    LinearLayout llMain = (LinearLayout) findViewById(R.id.linearLayoutScroll);
                    // nameHere.setText("test1");

                    givenName = response.getString("givenName");
                    botanicalName = response.getString("botanicalName");
                    Integer icon = response.getInt("icon");
                    String bloomTime = response.getString("bloomTime");
                    String commonName = response.getString("commonName");
                    String soilType = response.getString("soilType");
                    String sunExposure = response.getString("sunExposure");
                    String toxicity = response.getString("toxicity");
                    JSONObject schedule = response.getJSONObject("wateringRecommendation");
                    String wateringSchedule = schedule.getString("hoursBetweenWatering");

                    // iconPic.setText(icon);
                    watering.setText(wateringSchedule);
                    plantGivenNameText.setText(givenName);
                    plantBotanical.setText(botanicalName);
                    chooseSunlight.setText(sunExposure);
                    chooseSoil.setText(soilType);
                    chooseBloom.setText(bloomTime);
                    chooseToxicity.setText(toxicity);
                    chooseCommon.setText(commonName);
                    //displayAppointments.add(currServiceName + "\n" + HelperMethods.displayDateTimeFromTo(currStart, currEnd));
                } catch (JSONException e) {
                    // nameHere.setText("fail");
                    error += e.getMessage();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    iconPic.setText("fail");
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
    public void goToHome(View view)  {
        startActivity(new Intent(ViewPlant.this, LoggedInView.class));

    }
}
