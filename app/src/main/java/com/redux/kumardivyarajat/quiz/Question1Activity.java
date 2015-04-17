package com.redux.kumardivyarajat.quiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Question1Activity extends ActionBarActivity {

    ColorWheel colorWheel = new ColorWheel();
    RelativeLayout mView;

    public static final String TAG = Question1Activity.class.getSimpleName();

    public int Correct;


    public TextView mTimer1;
    public TextView mQuestion;
    public RadioButton mOptionA;
    public RadioButton mOptionB;
    public RadioButton mOptionC;
    public RadioButton mOptionD;
    public RadioGroup mRadioGroup_1;
    public Button msubmit;

    public List<Questions> questionsArrayList = new ArrayList<Questions>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_question1);



        final Intent intent = getIntent();
        final String subject = intent.getStringExtra("Subject");
        Log.d(TAG, subject);
        mTimer1 = (TextView)findViewById(R.id.timer1);
        mQuestion = (TextView) findViewById(R.id.question3);
        mOptionA = (RadioButton) findViewById(R.id.optionA_1);
        mOptionB = (RadioButton) findViewById(R.id.optionB_1);
        mOptionC = (RadioButton) findViewById(R.id.optionC_1);
        mOptionD = (RadioButton) findViewById(R.id.optionD_1);
        mRadioGroup_1 = (RadioGroup) findViewById(R.id.answers_radiogroup_1);
        msubmit = (Button) findViewById(R.id.submit_3);
        mView = (RelativeLayout)findViewById(R.id.relative_layout);
        mView.setBackgroundColor(colorWheel.getColor());
        msubmit.setTextColor(colorWheel.getColor());


        Random random = new Random();
        final int i = random.nextInt(20);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Questions");
        query.whereEqualTo("Subject",subject);
        query.whereEqualTo("QuestionNumber",i);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e== null) {
                    mQuestion.setText(parseObject.getString("Question"));
                    mOptionA.setText(parseObject.getString("OptionA"));
                    mOptionB.setText(parseObject.getString("OptionB"));
                    mOptionC.setText(parseObject.getString("OptionC"));
                    mOptionD.setText(parseObject.getString("OptionD"));
                    Correct = parseObject.getInt("CorrectAnswer");

                   final CountDownTimer timer  = new CountDownTimer(11000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            mTimer1.setText("seconds remaining: " + millisUntilFinished / 1000);
                        }
                        public void onFinish() {
                            mTimer1.setText("Time Up!");
                            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(Question1Activity.this);
                            SharedPreferences.Editor editor = app_preferences.edit();
                            editor.putInt("answer_value", 0);
                            editor.commit();
                            Toast.makeText(getApplicationContext(),"Time Up! No marks for you.",Toast.LENGTH_SHORT).show();
                            Intent newIntent = new Intent(Question1Activity.this, Question2Activity.class);
                            newIntent.putExtra("Subject",subject);
                            newIntent.putExtra("Ques",i);
                            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(newIntent);
                        }
                    }.start();

                    msubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            timer.cancel();
                            int radioButtonID = mRadioGroup_1.getCheckedRadioButtonId();
                            View radioButton = mRadioGroup_1.findViewById(radioButtonID);
                            int position = mRadioGroup_1.indexOfChild(radioButton);
                            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(Question1Activity.this);
                            SharedPreferences.Editor editor = app_preferences.edit();
                            int a;
                            if(position+1 == Correct) {
                               Toast.makeText(Question1Activity.this, "Yay. Correct Answer.",Toast.LENGTH_SHORT).show();
                                editor.putInt("answer_value", 1);
                                a = 1;

                            } else {
                                Toast.makeText(Question1Activity.this, "Bummer. Wrong Answer.",Toast.LENGTH_SHORT).show();
                                editor.putInt("answer_value", 0);
                                a  = 0;
                            }
                            editor.commit();
                            Intent newIntent = new Intent(Question1Activity.this, Question2Activity.class);

                            newIntent.putExtra("Subject", subject);
                            newIntent.putExtra("Ques",i);
                            startActivity(newIntent);
                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(),"No Network",Toast.LENGTH_SHORT).show();
                }
            }


        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question1, menu);
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
