package com.example.recipesapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
//comment 1111.....
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;

                case R.id.navigation_account:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;


            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void add(View v){
        Intent n= new Intent(this,addRecipe.class);
        startActivity(n);
        finish();
    }


    public void goTosignIn(View v){
        Intent sighnInIntnt=new Intent(this,Login.class);
        startActivity(sighnInIntnt);
    }

    public void goToSearch(View v){
        Intent i =new Intent(this,SearchForRecipes.class);
        startActivity(i);

    }

    public void userPage(View v){
        Intent i =new Intent(this,userRecipePersonalpage.class);
        startActivity(i);
    }

}
