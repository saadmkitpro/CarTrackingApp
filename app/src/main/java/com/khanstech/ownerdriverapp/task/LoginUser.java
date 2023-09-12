package com.khanstech.ownerdriverapp.task;

import android.content.Intent;
import android.os.AsyncTask;

import com.khanstech.ownerdriverapp.R;
import com.khanstech.ownerdriverapp.activity.HomeActivity;
import com.khanstech.ownerdriverapp.activity.LoginActivity;
import com.khanstech.ownerdriverapp.activity.RegisterActivity;
import com.khanstech.ownerdriverapp.helper.JsonConverter;
import com.khanstech.ownerdriverapp.model.Response;
import com.khanstech.ownerdriverapp.utils.OKHttp;
import com.khanstech.ownerdriverapp.utils.PhoneFunctionality;
import com.khanstech.ownerdriverapp.utils.PreferenceHelper;

public class LoginUser extends AsyncTask<String, Void, String> {

    private LoginActivity activity;
    private Response response = null;
    private String username;

    public LoginUser(LoginActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.setProgressBarIndeterminateVisibility(true);
    }

    @Override
    protected String doInBackground(String... params) {
        String body = "{'username':'" + params[0] + "','password':'" + params[1] + "'}";
        body = body.replace("'", "\"");
        OKHttp okHttp = new OKHttp();
        String responseContent = okHttp.postCall("http://www.khanstech.com/rts/login_user.json", body);
        String feedback = "";
        if (okHttp.responseCode != 200) {
            if (okHttp.responseStatus == null || okHttp.responseStatus.isEmpty())
                feedback = activity.getString(R.string.connection_failed);
            else
                feedback = okHttp.responseStatus;
        } else {
            if (responseContent == null || responseContent.isEmpty())
                feedback = activity.getString(R.string.no_response);
            else {
                response = JsonConverter.parseJsonToResponse(responseContent);
                username = params[0];
                feedback = response.Code;
            }
        }
        return feedback;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        PhoneFunctionality.makeToast(activity, activity.getString(R.string.cancel));
        activity.setProgressBarIndeterminateVisibility(false);
    }

    @Override
    protected void onPostExecute(String feedback) {
        super.onPostExecute(feedback);
        activity.setProgressBarIndeterminateVisibility(false);
        PreferenceHelper.setInt(activity, "logged_user", 0);
        if (response == null)
            PhoneFunctionality.makeToast(activity, feedback);
        else {
            switch (feedback) {
                case "1":
                    activity.startActivity(new Intent(activity, HomeActivity.class));
                    PreferenceHelper.setInt(activity, "logged_user", 1);
                    PreferenceHelper.setString(activity, "logged_username", username);
                    break;
                case "0":
                    activity.startActivity(new Intent(activity, RegisterActivity.class));
                    break;
            }
            PhoneFunctionality.makeToast(activity, response.Data);
        }
    }
}
