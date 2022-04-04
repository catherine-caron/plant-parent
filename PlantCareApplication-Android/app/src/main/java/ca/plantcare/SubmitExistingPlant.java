package ca.plantcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class SubmitExistingPlant extends AppCompatActivity {

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
        setContentView(R.layout.submit_existingplant);
    }


    public void addExistingPlantToUser(View view){

        final TextView enterGivenName = (TextView) findViewById(R.id.enterGivenNameUser);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String memberId = preferences.getString("memberId", null);
        int plantId = preferences.getInt("plantIdAdd",0);
        String plantIdString = String.valueOf(plantId);
        RequestParams body = new RequestParams();
        body.add("plantId", plantIdString);
        body.add("memberId", memberId);
        body.add("givenName", enterGivenName.getText().toString());


        //nameHere.setText(memberId);
        //TextView testEdit = (TextView)findViewById(R.id.chooseReminderTest);
      //  testEdit.setText(enterGivenName.getText().toString());
        HttpUtils.post("plant/addPlant/",body, new JsonHttpResponseHandler() {

            private static final String TAG = "";

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                //JSONArray arr = response.getJSONArray("plants");
                //String testEdit = findViewById(R.id.testEdit);
                // testEdit.setText("test2");
                Intent intent = new Intent(SubmitExistingPlant.this, LoggedInView.class);
                startActivity(intent);
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                } catch (JSONException e) {
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

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable){
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.v(TAG, "onFailure" + responseString);
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
