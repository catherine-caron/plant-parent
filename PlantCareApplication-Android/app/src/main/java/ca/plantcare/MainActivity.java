package ca.plantcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//log in or sign up view
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homebeforelogin);
    }

    public void signUp(View v){
        startActivity(new Intent(MainActivity.this, SignUp.class));
    }

    public void logIn(View v){
        startActivity(new Intent(MainActivity.this, LogIn.class));
    }
}