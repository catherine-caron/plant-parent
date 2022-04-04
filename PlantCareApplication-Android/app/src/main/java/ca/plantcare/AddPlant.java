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

import ca.plantcare.databinding.AddcreateplantBinding;
import cz.msebera.android.httpclient.Header;

import androidx.appcompat.app.AppCompatActivity;

public class AddPlant extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
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
        setContentView(R.layout.addplant);
       // displayAllPlants();
    }


    public void addExistingPlantButton(View v){
        startActivity(new Intent(AddPlant.this, AddExistingPlant.class));
    }

    public void createPlantButton(View v){
        startActivity(new Intent(AddPlant.this, createPlant.class));
    }

    public void goToHomePage(View v){
        startActivity(new Intent(AddPlant.this, LoggedInView.class));
    }
}
