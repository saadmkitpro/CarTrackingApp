package com.khanstech.ownerdriverapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.khanstech.ownerdriverapp.R;
import com.khanstech.ownerdriverapp.task.LoginUser;
import com.khanstech.ownerdriverapp.utils.ConnectionManager;
import com.khanstech.ownerdriverapp.utils.PhoneFunctionality;

public class LoginActivity extends Activity {

    private TextView tv_uname, tv_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_login);

        tv_uname = (TextView) findViewById(R.id.tv_login_username);
        tv_pass = (TextView) findViewById(R.id.tv_login_pass);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_progress, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void loginUser(View v) {
        String[] inputs = new String[]{tv_uname.getText().toString(), tv_pass.getText().toString()};
        if (PhoneFunctionality.isStringNotNullOrEmpty(inputs)) {
            if (ConnectionManager.isConnectedToInternet(this)) {
                // Login User To Server
                LoginUser login = new LoginUser(this);
                login.execute(inputs);
            } else
                PhoneFunctionality.makeToast(this, "Check Internet Connection");
        } else
            PhoneFunctionality.makeToast(this, "Input required fields");
    }

    public void gotoForgetPassword(View v) {
        startActivity(new Intent(this, ResetPassActivity.class));
    }

    public void gotoRegisterUser(View v) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void showLoginPassword(View v) {
        if (tv_pass.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
            tv_pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        else
            tv_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        EditText et = (EditText) findViewById(R.id.tv_login_pass);
        et.setSelection(et.getText().length());
    }
}

