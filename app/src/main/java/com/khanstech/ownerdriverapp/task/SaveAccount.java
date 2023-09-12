package com.khanstech.ownerdriverapp.task;

import android.os.AsyncTask;

import com.khanstech.ownerdriverapp.R;
import com.khanstech.ownerdriverapp.activity.AccountActivity;
import com.khanstech.ownerdriverapp.helper.JsonConverter;
import com.khanstech.ownerdriverapp.model.Response;
import com.khanstech.ownerdriverapp.utils.OKHttp;
import com.khanstech.ownerdriverapp.utils.PhoneFunctionality;

public class SaveAccount extends AsyncTask<String, Void, String> {

    private AccountActivity activity;
    private Response response = null;

    public SaveAccount(AccountActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.setProgressBarIndeterminateVisibility(true);
    }

    @Override
    protected String doInBackground(String... params) {
        String body = "{'username':'" + params[0] + "','name':'" + params[1] + "','contact':'" + params[2] + "',";
        body += "'email':'" + params[3] + "','ride_type':'" + params[4] + "','ride_condition':'" + params[5] + "'}";
        body = body.replace("'", "\"");
        OKHttp okHttp = new OKHttp();
        String responseContent = okHttp.postCall("http://www.khanstech.com/rts/save_account.json", body);
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
            //     switch (feedback) {
            //        case "1":
            //        activity.startActivity(new Intent(activity, LoginActivity.class));
            //        break;
            //     }
            PhoneFunctionality.makeToast(activity, response.Data);
        }
    }
}
