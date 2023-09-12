package com.khanstech.ownerdriverapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.khanstech.ownerdriverapp.R;
import com.khanstech.ownerdriverapp.adapter.RideTypeAdapter;
import com.khanstech.ownerdriverapp.model.Account;
import com.khanstech.ownerdriverapp.task.SaveAccount;
import com.khanstech.ownerdriverapp.utils.ConnectionManager;
import com.khanstech.ownerdriverapp.utils.PhoneFunctionality;
import com.khanstech.ownerdriverapp.utils.PreferenceHelper;

public class AccountActivity extends Activity {

    private static String TAG = String.valueOf(R.string.app_name);
    private TextView tv_uname, tv_name, tv_contact, tv_wemail;
    private Spinner sp_ride_type;
    private RatingBar ride_condition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_account);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        tv_uname = (TextView) findViewById(R.id.tv_acc_username);
        tv_name = (TextView) findViewById(R.id.tv_acc_name);
        tv_contact = (TextView) findViewById(R.id.tv_acc_contact);
        tv_wemail = (TextView) findViewById(R.id.tv_acc_wemail);
        sp_ride_type = (Spinner) findViewById(R.id.spinner_ride_type);
        ride_condition = (RatingBar) findViewById(R.id.rate_condition);

        tv_uname.setText(PreferenceHelper.getString(this, "logged_username"));
        RideTypeAdapter adapter = new RideTypeAdapter(this, getResources().getStringArray(R.array.ride_type_name));
        sp_ride_type.setAdapter(adapter);

        Account account = PreferenceHelper.getAccount(this);
        if (account != null) {
            tv_name.setText(account.Name);
            tv_contact.setText(account.Contact);
            tv_wemail.setText(account.Email);
            int index = 0;
            String[] arr = adapter.getAllItems();
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].equals(account.Ride_type)) {
                    index = i;
                    break;
                }
            }
            sp_ride_type.setSelection(index);
            ride_condition.setRating(Float.valueOf(account.Ride_condition));
        }
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

    public void saveAccount(View v) {

        String[] checks = new String[]{tv_name.getText().toString(), tv_contact.getText().toString()};
        if (PhoneFunctionality.isStringNotNullOrEmpty(checks)) {
            if (ConnectionManager.isConnectedToInternet(this)) {
                String wemail = tv_wemail.getText().toString().isEmpty() ? "N/A" : tv_wemail.getText().toString();
                String[] inputs = new String[]{tv_uname.getText().toString(), tv_name.getText().toString(),
                        tv_contact.getText().toString(), wemail, sp_ride_type.getSelectedItem().toString(),
                        String.valueOf(ride_condition.getRating())};
                // Save Account To Server
                SaveAccount save = new SaveAccount(this);
                save.execute(inputs);
            } else
                PhoneFunctionality.makeToast(this, "Check Internet Connection");
        } else
            PhoneFunctionality.makeToast(this, "Input required fields");
    }
}

