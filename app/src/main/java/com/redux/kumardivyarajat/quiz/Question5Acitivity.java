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


public class Question5Acitivity extends ActionBarActivity {

    public static final String TAG = Question5Acitivity.class.getSimpleName();

    public int Correct;
    public int i;

    public TextView mTimer5;
    public TextView mQuestion;
    public RadioButton mOptionA;
    public RadioButton mOptionB;
    public RadioButton mOptionC;
    public RadioButton mOptionD;
    public RadioGroup mRadioGroup_1;
    public Button msubmit;
    ColorWheel colorWheel = new ColorWheel();
    RelativeLayout mView;

    public List<Questions> questionsArrayList = new ArrayList<Questions>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_question5_acitivity);
        final Intent intent = getIntent();
        final String subject = intent.getStringExtra("Subject");
        final int Ques = intent.getIntExtra("Ques", 0);
        final int Ques1 = intent.getIntExtra("Ques1", 0);
        final int Ques2 = intent.getIntExtra("Ques2", 0);
        final int Ques3 = intent.getIntExtra("Ques3", 0);
        Log.d(TAG, subject);
        Log.d(TAG,"Ques = "  +  String.valueOf(Ques));
        Log.d(TAG,"Ques1 = "  +  String.valueOf(Ques1));
        Log.d(TAG,"Ques2 = "  +  String.valueOf(Ques2));
        Log.d(TAG,"Ques3 = "  +  String.valueOf(Ques3));

        mTimer5 = (TextView)findViewById(R.id.timer5);
        mQuestion = (TextView) findViewById(R.id.question5);
        mOptionA = (RadioButton) findViewById(R.id.optionA_5);
        mOptionB = (RadioButton) findViewById(R.id.optionB_5);
        mOptionC = (RadioButton) findViewById(R.id.optionC_5);
        mOptionD = (RadioButton) findViewById(R.id.optionD_5);
        mRadioGroup_1 = (RadioGroup) findViewById(R.id.answers_radiogroup_5);
        msubmit = (Button) findViewById(R.id.submit_5);
        mView = (RelativeLayout)findViewById(R.id.relative_layout5);
        mView.setBackgroundColor(colorWheel.getColor());
        msubmit.setTextColor(colorWheel.getColor());

        do {
            Random random = new Random();
            i = random.nextInt(20);
        }while(i==Ques || i==Ques1 || i==Ques2 || i==Ques3 ||i==0);
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
                            mTimer5.setText("seconds remaining: " + millisUntilFinished / 1000);
                        }

                        public void onFinish() {
                            mTimer5.setText("Time up!");
                            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(Question5Acitivity.this);
                            SharedPreferences.Editor editor = app_preferences.edit();
                            editor.putInt("answer_value5", 0);
                            editor.commit();
                            Toast.makeText(getApplicationContext(),"Time Up! No marks for you.",Toast.LENGTH_SHORT).show();
                            Intent newIntent = new Intent(Question5Acitivity.this, ScoreActivity.class);
                            newIntent.putExtra("Subject",subject);
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
                            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(Question5Acitivity.this);
                            SharedPreferences.Editor editor = app_preferences.edit();

                            if(position+1 == Correct) {
                                Toast.makeText(Question5Acitivity.this, "Yay. Correct Answer.", Toast.LENGTH_SHORT).show();
                                editor.putInt("answer_value5", 1);

                            } else {
                                Toast.makeText(Question5Acitivity.this, "Bummer. Wrong Answer.",Toast.LENGTH_SHORT).show();
                                editor.putInt("answer_value5", 0);
                            }
                            editor.commit();
                            Intent newIntent = new Intent(Question5Acitivity.this, ScoreActivity.class);

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
        getMenuInflater().inflate(R.menu.menu_question5_acitivity, menu);
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
