package com.redux.kumardivyarajat.quiz;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.parse.ParseUser;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Drawable mainActivityBackground = findViewById(R.id.main).getBackground();
        mainActivityBackground.setAlpha(50);

        Drawable engBackground = findViewById(R.id.eng).getBackground();
        engBackground.setAlpha(50);

        Drawable bioBackground = findViewById(R.id.bio).getBackground();
        bioBackground.setAlpha(50);
        Drawable phyBackground = findViewById(R.id.phy).getBackground();
        phyBackground.setAlpha(50);
        Drawable chemBackground = findViewById(R.id.chem).getBackground();
        chemBackground.setAlpha(50);
        Drawable biochemBackground = findViewById(R.id.biochem).getBackground();
        biochemBackground.setAlpha(50);


        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            Log.i(TAG, currentUser.getUsername());
        } else {
            navigateToLogin();
        }
        final Animation out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(300);
        final TextView mEng = (TextView)findViewById(R.id.eng);
        final TextView mPhy = (TextView)findViewById(R.id.phy);
        final TextView mChem = (TextView)findViewById(R.id.chem);
        final TextView mBio = (TextView)findViewById(R.id.bio);
        final TextView mBioChem = (TextView)findViewById(R.id.biochem);

        mEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 mEng.startAnimation(out);

                Intent intent = new Intent(MainActivity.this , QuizGameActivity.class);
                intent.putExtra("Subject","English");
                startActivity(intent);
            }
        });

        mPhy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhy.startAnimation(out);
                Intent intent = new Intent(MainActivity.this , QuizGameActivity.class);
                intent.putExtra("Subject","Physics");
                startActivity(intent);
            }
        });

        mChem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChem.startAnimation(out);
                Intent intent = new Intent(MainActivity.this , QuizGameActivity.class);
                intent.putExtra("Subject","Chemistry");
                startActivity(intent);
            }
        });

        mBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBio.startAnimation(out);
                Intent intent = new Intent(MainActivity.this , QuizGameActivity.class);
                intent.putExtra("Subject","Biology");
                startActivity(intent);
            }
        });

        mBioChem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBioChem.startAnimation(out);
                Intent intent = new Intent(MainActivity.this , QuizGameActivity.class);
                intent.putExtra("Subject","BioChemistry");
                startActivity(intent);
            }
        });

    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivtiy.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        } else if (id == R.id.action_logout) {
            ParseUser.logOut();
            ParseUser currentUser = ParseUser.getCurrentUser();
            navigateToLogin();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}
