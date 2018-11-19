package application.android.com.expencestracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.HashMap;

import application.android.com.expencestracker.Model.UserSessionManager;;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_USERNAME = "USER_NAME";
    private BottomNavigationView mainnav;
    private HomeFragment homeFragment;
    private ExpensesFragment expensesFragment;
    private StatisticsFragment statisticsFragment;
    private AboutUsFragment aboutUsFragment;

    private FrameLayout mainFrame;


    private EditText emailInput;
    private EditText pwInput;

    private Context _context;
    private UserSessionManager session;
    String user_id;
    String user_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this._context = this.getApplicationContext();
        this.session = new UserSessionManager(this._context);

        if (!this.session.CheckLogIn()) {
            this.session.logoutUser();
        }

        HashMap<String, String> usr  = this.session.getUserDetails();
        //user_id = usr.get(KEY_ID);
        user_name = usr.get(getResources().getString(R.string.KEY_USERNAME));

        TextView tv = (TextView) findViewById(R.id.tv_UserId);
        //tv.setText("Welcome   " + user_name +"!!");

        homeFragment= new HomeFragment();
        statisticsFragment = new StatisticsFragment();
        expensesFragment= new ExpensesFragment();
        aboutUsFragment = new AboutUsFragment();

        setFragment(homeFragment);

        mainnav=(BottomNavigationView)findViewById(R.id.main_nav);
        mainFrame=(FrameLayout)findViewById(R.id.main_frame);
        mainnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.home:
                        //mainnav.setItemBackgroundResource(R.color.colorPrimaryDark);

                        setFragment(homeFragment);

                        return true;

                    case R.id.Expenses:
                        setFragment(expensesFragment);
                        //mainnav.setItemBackgroundResource(R.color.nav_colors);
                        return true;
                    case R.id.Statistics:
                        setFragment(statisticsFragment);
                        return true;
                    case R.id.AboutUs:
                        setFragment(aboutUsFragment);
                        return true;
                     default:
                         return false;
                }

            }
        });



    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
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
            Intent intent= new Intent(getApplicationContext(),SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id== R.id.action_LogOut){
            session.logoutUser();
            return true;
        }

            return super.onOptionsItemSelected(item);



    }



}
