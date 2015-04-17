package com.redux.kumardivyarajat.quiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ScoreActivity extends ActionBarActivity {

    TextView q1_score, q2_score, q3_score, q4_score, q5_score, final_score;
    int finalscore;
    int q2answer, q3answer, q4answer, q5answer;
    String q1;

    Button mnewquizButton;
    Button mrateButton;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_score);
        initControls();
    }


    public void onStart(){
        super.onStart();
    }

    public void initControls(){

        q1_score = (TextView)findViewById(R.id.q1_score);
        q2_score = (TextView)findViewById(R.id.q2_score);
        q3_score = (TextView)findViewById(R.id.q3_score);
        q4_score = (TextView)findViewById(R.id.q4_score);
        q5_score = (TextView)findViewById(R.id.q5_score);
        final_score = (TextView)findViewById(R.id.final_score);

        final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);

        int q1answer = app_preferences.getInt("answer_value", 0);
        int q2answer = app_preferences.getInt("answer_value2", 0);
        int q3answer = app_preferences.getInt("answer_value3", 0);
        int q4answer = app_preferences.getInt("answer_value4", 0);
        int q5answer = app_preferences.getInt("answer_value5", 0);

        if ( q1answer == 1 ){
            q1_score.setText("Correct");
        } else {
            q1_score.setText("Incorrect");
        }

        if ( q2answer == 1){
            q2_score.setText("Correct");
        } else {
            q2_score.setText("Incorrect ");
        }

        if ( q3answer == 1){
            q3_score.setText("Correct");
        } else {
            q3_score.setText("Incorrect ");
        }

        if ( q4answer == 1){
            q4_score.setText("Correct");
        } else {
            q4_score.setText("Incorrect ");
        }

        if ( q5answer == 1){
            q5_score.setText("Correct");
        } else {
            q5_score.setText("Incorrect ");
        }
        finalscore =  q1answer + q2answer + q3answer + q4answer + q5answer;
        final_score.setText(finalscore + "/5");


        mnewquizButton = (Button)findViewById(R.id.final_button_new_game);
        mnewquizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);            }
        });

        mrateButton = (Button)findViewById(R.id.final_button_views);
        mrateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this, RateActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_score, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
