package ca.plantcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MemberProfile extends AppCompatActivity {


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
        setContentView(R.layout.memberprofile);
        viewMember();
    }


    public void viewMember()  {
        error = "";

        final TextView usernameEnterred = (TextView) findViewById(R.id.usernameEnterred);
        final TextView nameEnterred = (TextView) findViewById(R.id.nameEnterred);
        final TextView emailEnterred = (TextView) findViewById(R.id.emailEnterred);

        //final TextView chooseSchedule = (TextView) findViewById(R.id.chooseSchedule);
        //TextView nameHere = (TextView) findViewById(R.id.nameHere);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String memberId = preferences.getString("memberId",null);


        HttpUtils.get("member/getMemberByUsername/"+ memberId,new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String username = response.getString("username");
                    String name = response.getString("name");
                    String email = response.getString("email");
                    emailEnterred.setText(email);
                    nameEnterred.setText(name);
                    usernameEnterred.setText(memberId);
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

    public void backHomeview(View v){
        startActivity(new Intent(MemberProfile.this, LoggedInView.class));
    }

    public void logOff(View v){
           /*SharedPreferences preferences =getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        finish();*/
        startActivity(new Intent(MemberProfile.this, MainActivity.class));
    }
}
