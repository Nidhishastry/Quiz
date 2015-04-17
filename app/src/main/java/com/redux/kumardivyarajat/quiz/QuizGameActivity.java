package com.redux.kumardivyarajat.quiz;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class QuizGameActivity extends ActionBarActivity {

    public static final String TAG = QuizGameActivity.class.getSimpleName();
    public String Question;
    public String OptionA;
    public String OptionB;
    public String OptionC;
    public String OptionD;
    public int Correct;
    public static int count;

    public String[] sOptionA;
    public String[] sOptionB;
    public String[] sOptionC;
    public String[] sOptionD;
    public String[] sQuestion;
    public String[] sCorrectAns;
    public Questions q;

    public TextView mQuestion;
    public RadioButton mOptionA;
    public RadioButton mOptionB;
    public RadioButton mOptionC;
    public RadioButton mOptionD;
    public RadioGroup mRadioGroup;
    public Button msubmit;

    public List<Questions> questionsArrayList = new ArrayList<Questions>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_game);
        Intent intent = getIntent();
        String subject = intent.getStringExtra("Subject");
        Log.d(TAG, subject);

        mQuestion = (TextView) findViewById(R.id.question3);
        mOptionA = (RadioButton) findViewById(R.id.optionA);
        mOptionB = (RadioButton) findViewById(R.id.optionB);
        mOptionC = (RadioButton) findViewById(R.id.optionC);
        mOptionD = (RadioButton) findViewById(R.id.optionD);
        mRadioGroup = (RadioGroup) findViewById(R.id.answers_radiogroup);
        msubmit = (Button) findViewById(R.id.submit_3);


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Questions");
        query.whereEqualTo("Subject", subject);

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {

                    for (ParseObject object : objects) {

                        q = new Questions();
                        q.setQuestion(object.getString("Question").toString());
                        q.setOptionA(object.getString("OptionA").toString());
                        q.setOptionB(object.getString("OptionB").toString());
                        q.setOptionC(object.getString("OptionC").toString());
                        q.setOptionD(object.getString("OptionD").toString());
                        q.setCorrectAnswer(object.getInt("CorrectAnswer"));
                        questionsArrayList.add(q);
                    }

                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(QuizGameActivity.this);
                    dialog.setTitle("OOPS");
                    dialog.setPositiveButton("Either you are not connected to the internet or the app is not responding", null);

                    AlertDialog dialog1 = dialog.create();
                    dialog1.show();
                }


                Integer[] arr = new Integer[15];
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = i;
                }
                Log.d(TAG, String.valueOf(questionsArrayList.size()));
                Collections.shuffle(Arrays.asList(arr));
                for (int i = 0; i < 5; i++) {
                    Questions question = new Questions();
                    question = questionsArrayList.get(i);
                    mQuestion.setText(question.getQuestion());
                    Log.d(TAG, mQuestion.getText().toString());
                    mOptionA.setText(question.getOptionA());
                    mOptionB.setText(question.getOptionB());
                    mOptionC.setText(question.getOptionC());
                    mOptionD.setText(question.getOptionD());

                    int radioButtonID = mRadioGroup.getCheckedRadioButtonId();
                    View radioButton = mRadioGroup.findViewById(radioButtonID);
                    int idx = mRadioGroup.indexOfChild(radioButton);

                    if(idx+1 == question.getCorrectAnswer()) {
                        Toast.makeText(getApplicationContext(), "Yay", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Gay", Toast.LENGTH_SHORT).show();

                    }

                   /* RadioGroup rg = (RadioGroup) findViewById(R.id.answers_radiogroup);
                    rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            switch (checkedId) {
                                case R.id.optionA:
                                    // TODO Something
                                    if (R.id.optionA)
                                        Toast.makeText(getApplicationContext(), "Yay", Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.optionB:
                                    // TODO Something
                                    Toast.makeText(getApplicationContext(), "Yay", Toast.LENGTH_SHORT).show();

                                    break;
                                case R.id.optionC:
                                    // TODO Something
                                    Toast.makeText(getApplicationContext(), "Yay", Toast.LENGTH_SHORT).show();

                                    break;

                                case R.id.optionD:
                                    Toast.makeText(getApplicationContext(), "Yay", Toast.LENGTH_SHORT).show();

                                    break;
                            }
                        }
                    });*/
                }

            }

        });




    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz_game, menu);
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