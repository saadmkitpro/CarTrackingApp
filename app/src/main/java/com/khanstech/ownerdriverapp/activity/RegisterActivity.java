package com.khanstech.ownerdriverapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.khanstech.ownerdriverapp.R;
import com.khanstech.ownerdriverapp.task.RegisterUser;
import com.khanstech.ownerdriverapp.utils.ConnectionManager;
import com.khanstech.ownerdriverapp.utils.PhoneFunctionality;

public class RegisterActivity extends Activity {

    private TextView tv_uname, tv_pass, tv_email;
    private static String TAG = "OwnerDriverApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_register);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        tv_uname = (TextView) findViewById(R.id.tv_reg_username);
        tv_pass = (TextView) findViewById(R.id.tv_reg_pass);
        tv_email = (TextView) findViewById(R.id.tv_reg_email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_empty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void registerUser(View v) {
        String[] inputs = new String[]{tv_uname.getText().toString(), tv_pass.getText().toString(), tv_email.getText().toString()};
        if (PhoneFunctionality.isStringNotNullOrEmpty(inputs)) {
            if (ConnectionManager.isConnectedToInternet(this)) {
                // Register User To Server
                RegisterUser register = new RegisterUser(this);
                register.execute(inputs);
            } else
                PhoneFunctionality.makeToast(this, "Check Internet Connection");
        } else
            PhoneFunctionality.makeToast(this, "Input required fields");
    }

    public void showRegisterPassword(View v) {
        if (tv_pass.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
            tv_pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        else
            tv_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        EditText et = (EditText) findViewById(R.id.tv_reg_pass);
        et.setSelection(et.getText().length());
    }
}
