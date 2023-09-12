package com.khanstech.ownerdriverapp.task;

import android.os.AsyncTask;

import com.khanstech.ownerdriverapp.R;
import com.khanstech.ownerdriverapp.activity.HomeActivity;
import com.khanstech.ownerdriverapp.helper.JsonConverter;
import com.khanstech.ownerdriverapp.model.Account;
import com.khanstech.ownerdriverapp.model.Response;
import com.khanstech.ownerdriverapp.utils.OKHttp;
import com.khanstech.ownerdriverapp.utils.PhoneFunctionality;
import com.khanstech.ownerdriverapp.utils.PreferenceHelper;

public class GetAccount extends AsyncTask<String, Void, String> {

    private HomeActivity activity;
    private Response response = null;

    public GetAccount(HomeActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.setProgressBarIndeterminateVisibility(true);
    }

    @Override
    protected String doInBackground(String... params) {
        String body = "{'username':'" + params[0] + "'}";
        body = body.replace("'", "\"");
        OKHttp okHttp = new OKHttp();
        String responseContent = okHttp.postCall("http://www.khanstech.com/rts/get_account.json", body);
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
        if (response == null)
            PhoneFunctionality.makeToast(activity, feedback);
        else {
            switch (feedback) {
                case "1":
                    Account account = JsonConverter.parseJsonToAccount(response.Data);
                    PreferenceHelper.setAccount(activity, account);
                    break;
                case "0":
                    PhoneFunctionality.makeToast(activity, response.Data);
                    PreferenceHelper.setAccount(activity, null);
                    break;
            }
        }
    }
}
