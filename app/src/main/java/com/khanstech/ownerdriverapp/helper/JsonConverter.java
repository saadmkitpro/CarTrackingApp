package com.khanstech.ownerdriverapp.helper;

import android.support.annotation.Nullable;
import android.util.Log;

import com.khanstech.ownerdriverapp.model.Account;
import com.khanstech.ownerdriverapp.model.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonConverter {

    private static String TAG = "JsonConverter";

    @Nullable
    public static Response parseJsonToResponse(String jsonData) {
        try {
            JSONObject obj = new JSONObject(jsonData);
            Response m = new Response();
            m.Code = (obj.getString("code"));
            m.Status = (obj.getString("status"));
            m.Data = (obj.getString("data"));
            return m;
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return null;
    }

    @Nullable
    public static Account parseJsonToAccount(String jsonData) {
        try {
            JSONObject obj = new JSONObject(jsonData);
            Account m = new Account();
            m.Name = (obj.getString("name"));
            m.Contact = (obj.getString("contact"));
            m.Email = (obj.getString("email"));
            m.Ride_type = (obj.getString("ride_type"));
            m.Ride_condition = (obj.getString("ride_condition"));
            m.Nor = (obj.getString("nor"));
            m.Score = (obj.getString("score"));
            m.Rate = (obj.getString("rate"));
            return m;
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return null;
    }

    public static List<String> parseJsonToList(String jsonData) {
        try {
            JSONArray jsonArr = new JSONArray(jsonData);
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < jsonArr.length(); i++) {
                list.add(jsonArr.getString(i));
            }
            return list;
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return null;
    }

    public static List<List<String>> parseJsonToListofStringList(String jsonData) {
        try {
            JSONArray jsonArr = new JSONArray(jsonData);
            List<List<String>> lists = new ArrayList<List<String>>();
            List<String> list;
            JSONArray array = new JSONArray();
            for (int i = 0; i < jsonArr.length(); i++) {
                list = new ArrayList<String>();
                array = jsonArr.getJSONArray(i);
                for (int j = 0; j < array.length(); j++)
                    list.add(array.getString(j));
                lists.add(list);
                ;
            }
            return lists;
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return null;
    }

  /*  public static List<Machine> parseJsonToMachine(String jsonData) {
        try {
            JSONArray jsonArr = new JSONArray(jsonData);
            List<Machine> list = new ArrayList<Machine>();
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject object = jsonArr.getJSONObject(i);
                Machine m = new Machine();
                m.setIp(object.getString("IP"));
                m.setName(object.getString("NAME"));
                m.setMac(object.getString("MAC"));
                m.setDb(object.getString("DB"));
                m.setWeb(object.getString("WEB"));
                m.setMstatus(object.getString("MSTATUS"));
                m.setDstatus(object.getString("DSTATUS"));
                m.setWstatus(object.getString("WSTATUS"));
                list.add(m);
            }
            return list;
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return null;
    }*/

    public static String parseListOfStringToJson(List<String> list) {
        JSONArray jsonArr = new JSONArray();
        try {
            for (int i = 0; i < list.size(); i++) {
                jsonArr.put(list.get(i));
            }
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return jsonArr.toString();
    }

    private static Object CheckForNull(Object value) {
        if (value == null)
            return "";
        else
            return value;
    }
}
