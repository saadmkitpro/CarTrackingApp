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
import com.khanstech.ownerdriverapp.task.ResetPass;
import com.khanstech.ownerdriverapp.utils.ConnectionManager;
import com.khanstech.ownerdriverapp.utils.PhoneFunctionality;

public class ResetPassActivity extends Activity {

    private static String TAG = String.valueOf(R.string.app_name);
    private TextView tv_email, tv_new_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_reset_pass);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        tv_email = (TextView) findViewById(R.id.tv_forget_email);
        tv_new_pass = (TextView) findViewById(R.id.tv_forget_newpass);
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

    public void resetPassword(View v) {
        String[] inputs = new String[]{tv_email.getText().toString(), tv_new_pass.getText().toString()};
        if (PhoneFunctionality.isStringNotNullOrEmpty(inputs)) {
            if (ConnectionManager.isConnectedToInternet(this)) {
                // Send Forget Pass Email
                ResetPass forgetPass = new ResetPass(this);
                forgetPass.execute(inputs);
            } else
                PhoneFunctionality.makeToast(this, "No Internet Connection");
        } else {
            PhoneFunctionality.makeToast(this, "Input required fields");
        }
    }

    public void showForgetPassword(View v) {
        if (tv_new_pass.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
            tv_new_pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        else
            tv_new_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        EditText et = (EditText) findViewById(R.id.tv_forget_newpass);
        et.setSelection(et.getText().length());
    }
}

