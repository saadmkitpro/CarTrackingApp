package com.khanstech.ownerdriverapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.khanstech.ownerdriverapp.R;
import com.khanstech.ownerdriverapp.task.GetAccount;
import com.khanstech.ownerdriverapp.utils.ConnectionManager;
import com.khanstech.ownerdriverapp.utils.PreferenceHelper;

public class HomeActivity extends Activity {

    private static String TAG = String.valueOf(R.string.app_name);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_home);
        if (ConnectionManager.isConnectedToInternet(this)) {
            // Get Account From Server
            GetAccount login = new GetAccount(this);
            login.execute(PreferenceHelper.getString(this, "logged_username"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_empty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void gotoSetupAccount(View v) {
        startActivity(new Intent(this, AccountActivity.class));
    }
}

