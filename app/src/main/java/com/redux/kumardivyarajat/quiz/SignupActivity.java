package com.redux.kumardivyarajat.quiz;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignupActivity extends ActionBarActivity {

    protected EditText mFname;
    protected EditText mLname;
    protected EditText muname;
    protected EditText mpword;
    protected EditText mEmail;

    protected Button mSignUpButton;
    protected Button mBackToLoginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        mFname = (EditText) findViewById(R.id.fname);
        mLname = (EditText) findViewById(R.id.lname);
        muname = (EditText) findViewById(R.id.uname);
        mEmail = (EditText) findViewById(R.id.email);
        mpword = (EditText) findViewById(R.id.pword);
        mBackToLoginButton=(Button)findViewById(R.id.bktologin);
        mSignUpButton = (Button) findViewById(R.id.register);
        //mProgressView = findViewById(R.id.login_progress);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = mFname.getEditableText().toString();
                String lastName = mLname.getEditableText().toString();
                String username = muname.getEditableText().toString();
                String password = mpword.getEditableText().toString();
                String email = mEmail.getEditableText().toString();

                firstName = firstName.trim();
                lastName = lastName.trim();
                username = username.trim();
                password = password.trim();
                email = email.trim();

                if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);

                    builder.setMessage("Let's have sex on the beach.");
                    builder.setTitle("SEXY");
                    builder.setPositiveButton(android.R.string.ok, null);

                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {
                    ParseUser newUser = new ParseUser();
                    newUser.setUsername(username);
                    newUser.setEmail(email);
                    newUser.setPassword(password);
                    newUser.put("FirstName", firstName);
                    newUser.put("LastName", lastName);
                    newUser.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {

                            if (e == null) {


                                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
            }
        });
        mBackToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivtiy.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
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
