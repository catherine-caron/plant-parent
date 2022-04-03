package ca.plantcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
            viewPlant();

    }

    public void viewPlant()  {
        error = "";
        // final TextView username = (TextView) findViewById(R.id.usernameReg);

        TextView nameHere = (TextView) findViewById(R.id.nameHere);

       SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
       String memberId = preferences.getString("memberId", null);
       //nameHere.setText(memberId);
         HttpUtils.get("member/getMemberByUsername/"+ memberId,new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {

                        JSONArray arr = response.getJSONArray("plants");
                        nameHere.setText("test1");
                        for (int i = 0; i < 2; i++) {

                            JSONObject plants = arr.getJSONObject(i);

                            String givenName = plants.getString("givenName");
                        String botanicalName = plants.getString("botanicalName");

                        //String endDateTime = plants.getString("endDateTime");
                            nameHere.setText(givenName);
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
        });
    }
}
