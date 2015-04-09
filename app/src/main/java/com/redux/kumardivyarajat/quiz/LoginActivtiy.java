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
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LoginActivtiy extends ActionBarActivity {

    protected EditText mpword;
    protected EditText muname;
    protected Button mLoginButton;


    public static final String TAG = LoginActivtiy.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activtiy);

        Button mRegisterButton = (Button)findViewById(R.id.registerbtn);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivtiy.this , SignupActivity.class);
                startActivity(intent);
            }
        });
        Button mResetButton = (Button)findViewById(R.id.passres);
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivtiy.this , ResetPasswordActivity.class);
                startActivity(intent);
            }
        });

        muname = (EditText) findViewById(R.id.email);
        mpword = (EditText) findViewById(R.id.pword);



        mLoginButton = (Button) findViewById(R.id.login);
        //mProgressView = findViewById(R.id.login_progress);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = mpword.getEditableText().toString();
                String email = muname.getEditableText().toString();

                password = password.trim();
                email = email.trim();

                if (email.isEmpty() || password.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivtiy.this);

                    builder.setMessage("Please fill all the fields");
                    builder.setTitle("SEXY");
                    builder.setPositiveButton(android.R.string.ok, null);

                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {
                    ParseUser.logInInBackground(email, password, new LogInCallback() {
                        public void done(ParseUser user, ParseException e) {

                            if (user != null) {
                                    Log.i(TAG, user.getEmail());
                                    Intent inte = new Intent(LoginActivtiy.this , MainActivity.class);
                                inte.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                inte.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(inte);

                            } else {
                            // Signup failed. Look at the ParseException to see what happened.
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_activtiy, menu);
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
