package ca.plantcare;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SignUp extends AppCompatActivity {
    private String error = null;
    private List<String> personUsernames = new ArrayList<>();
    private ArrayAdapter<String> personAdapter;
    private ArrayAdapter<String> eventAdapter;
    private List<String> personPasswords = new ArrayList<>();
    private List<String> plants = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
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

    public void addPerson(View v) throws JSONException {
        error = "";
        // final TextView username = (TextView) findViewById(R.id.usernameReg);


        final TextView enterUsername = (TextView) findViewById(R.id.enterUsernameReg);
        final TextView name = (TextView) findViewById(R.id.enternameReg);
        final TextView enterPassword = (TextView) findViewById(R.id.enterPassword);
        final TextView enterEmail = (TextView) findViewById(R.id.enterEmailReg);

        RequestParams body = new RequestParams();
        body.add("username", enterUsername.getText().toString());
        body.add("name", name.getText().toString());
        body.add("password", enterPassword.getText().toString());
        body.add("email", enterEmail.getText().toString());

        HttpUtils.post("member/create/",body, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
                enterUsername.setText("");
                name.setText("");
                enterPassword.setText("");
                enterEmail.setText("");


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
    }

}
