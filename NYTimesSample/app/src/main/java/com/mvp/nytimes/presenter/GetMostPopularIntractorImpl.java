package com.mvp.nytimes.presenter;

import android.content.Context;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mvp.nytimes.R;
import com.mvp.nytimes.contract.AppContractMvp;
import com.mvp.nytimes.model.MostViewedModel;
import com.mvp.nytimes.network.NYTimesVolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
    This Intractor class implementation takes care of fetching data from the API and returning it
    to the Presentor. The presentor assigns this data to the adapter and the same can be seen in
    the recyclerview.
 */
public class GetMostPopularIntractorImpl implements AppContractMvp.GetMostPopularIntractor {

    //Url to fetch allsections articles from passt 7 days
    public static final String API_URL="http://api.nytimes.com/svc/mostpopular/v2/mostviewed/all-sections/7.json?apikey=7f3b49db5ad743f59e0bda7fd2809a1c";
    private Context mContext;

    public GetMostPopularIntractorImpl(Context context){
        this.mContext = context;
    }
    @Override
    public void getMostRecentArticles(final OnFinishListener onFinishListener) {

        RequestQueue queue = NYTimesVolleyRequest.getInstance(mContext).getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("MY_APP","Inside onResponse");
                ArrayList<MostViewedModel> mostViewedModelsCollection = new ArrayList<>();
                try {
                    JSONObject obj = new JSONObject(response);
                    //Getting the 'results' part of the response received
                    String resp = obj.getString("results");
                    //convert the string to JSONArray to parse and fetch the required data
                    JSONArray allMostPopularArticles = new JSONArray(resp);

                    //Parse the JSONArray to fetchnecessary data
                    for(int i=0;i<allMostPopularArticles.length();i++)
                    {
                        JSONObject mostViewed = allMostPopularArticles.getJSONObject(i);
                        //Fetch necessary fields needed by the Model and add it to the list
                        mostViewedModelsCollection.add(new MostViewedModel( mostViewed.getString("section"),
                                mostViewed.getString("byline"),mostViewed.getString("published_date"),
                                mostViewed.getString("title")));
                    }
                    //On Success add the fetched list to the adapter
                    onFinishListener.onFinish(mostViewedModelsCollection);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = "";
                int statusCode = 0;
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.statusCode ==400) {
                    // HTTP Status Code: 401 Unauthorized
                    message = mContext.getResources().getString(R.string.network_error);
                    statusCode = 400;
                }
                else if (networkResponse != null && networkResponse.statusCode ==404) {
                    message = mContext.getResources().getString(R.string.not_found_error);
                    statusCode = 404;
                }
                else if (networkResponse != null && networkResponse.statusCode ==500) {
                    // HTTP Status Code: 401 Unauthorized
                    message = mContext.getResources().getString(R.string.server_error);
                    statusCode = 500;
                }
                else if (networkResponse != null && networkResponse.statusCode ==401){
                    String jsonError = new String(networkResponse.data);
                    try {
                        JSONObject object = new JSONObject(jsonError);
                        message = object.has("message")?object.getString("message"):mContext.getResources().getString(R.string.session_exp_error);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    statusCode = 401;
                }
                else if (error instanceof NoConnectionError) {
                    message = mContext.getResources().getString(R.string.no_connection);
                    statusCode = 1;
                }
                else if (error instanceof TimeoutError) {
                    message = mContext.getResources().getString(R.string.timeout_error);
                    statusCode = 2;
                }
                onFinishListener.onFailed(message,statusCode);
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("accept","text/plain;v=1.0");
                return header;
            }
        };
        queue.add(stringRequest);
    }

}
