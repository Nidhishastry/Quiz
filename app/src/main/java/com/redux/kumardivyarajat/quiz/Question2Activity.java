package com.redux.kumardivyarajat.quiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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


public class Question2Activity extends ActionBarActivity {
    public static final String TAG = Question2Activity.class.getSimpleName();

    public int Correct;
    public int i;

    public TextView mQuestion;
    public RadioButton mOptionA;
    public RadioButton mOptionB;
    public RadioButton mOptionC;
    public RadioButton mOptionD;
    public RadioGroup mRadioGroup_1;
    public Button msubmit;
    ColorWheel colorWheel = new ColorWheel();
    RelativeLayout mView;
    public TextView mTimer;

    public List<Questions> questionsArrayList = new ArrayList<Questions>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_question2);
        final Intent intent = getIntent();
        final String subject = intent.getStringExtra("Subject");
        final int Ques = intent.getIntExtra("Ques", 0);
        Log.d(TAG,"Ques = "  +  String.valueOf(Ques));

        Log.d(TAG, subject);

        mQuestion = (TextView) findViewById(R.id.question2);
        mOptionA = (RadioButton) findViewById(R.id.optionA_2);
        mOptionB = (RadioButton) findViewById(R.id.optionB_2);
        mOptionC = (RadioButton) findViewById(R.id.optionC_2);
        mOptionD = (RadioButton) findViewById(R.id.optionD_2);
        mRadioGroup_1 = (RadioGroup) findViewById(R.id.answers_radiogroup_2);
        msubmit = (Button) findViewById(R.id.submit_2);
        mView = (RelativeLayout)findViewById(R.id.relative_layout2);
        mView.setBackgroundColor(colorWheel.getColor());
        msubmit.setTextColor(colorWheel.getColor());
        mTimer = (TextView)findViewById(R.id.timer2);


         //int i;
        do{
            Random random = new Random();
            i = random.nextInt(20);

        }while (i==Ques || i==0);

        Log.d(TAG, "i = "  + String.valueOf(i));


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

                    final CountDownTimer timer = new CountDownTimer(11000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            mTimer.setText("seconds remaining: " + millisUntilFinished / 1000);
                        }
                        public void onFinish() {
                            mTimer.setText("Time up!");
                            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(Question2Activity.this);
                            SharedPreferences.Editor editor = app_preferences.edit();
                            editor.putInt("answer_value2", 0);
                            editor.commit();
                            Toast.makeText(getApplicationContext(),"Time Up! No marks for you.",Toast.LENGTH_SHORT).show();
                            Intent newIntent = new Intent(Question2Activity.this, Question3Activity.class);
                            newIntent.putExtra("Subject",subject);
                            newIntent.putExtra("Ques1",i);
                            newIntent.putExtra("Ques",Ques);
                            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(newIntent);
                        }
                    }.start();



                    msubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int radioButtonID = mRadioGroup_1.getCheckedRadioButtonId();
                            timer.cancel();
                            View radioButton = mRadioGroup_1.findViewById(radioButtonID);
                            int position = mRadioGroup_1.indexOfChild(radioButton);
                            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(Question2Activity.this);
                            SharedPreferences.Editor editor = app_preferences.edit();

                            if(position+1 == Correct) {
                                Toast.makeText(Question2Activity.this, "Yay. Correct Answer.", Toast.LENGTH_SHORT).show();
                                editor.putInt("answer_value2", 1);

                            } else {
                                Toast.makeText(Question2Activity.this, "Bummer. Wrong Answer.",Toast.LENGTH_SHORT).show();
                                editor.putInt("answer_value2", 0);
                            }
                            editor.commit();
                            Intent newIntent = new Intent(Question2Activity.this, Question3Activity.class);
                            newIntent.putExtra("Subject", subject);
                            newIntent.putExtra("Ques1",i);
                            newIntent.putExtra("Ques",Ques);
                            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
        getMenuInflater().inflate(R.menu.menu_question2, menu);
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
