package com.mvp.nytimes.network;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

/*
    This is a Singleton Class which makes sure at any given instance there is only one instance of
    request queue. If there are multiple requests, they will be added directly to the request queue
 */
public class NYTimesVolleyRequest {

    private static NYTimesVolleyRequest mInstance;
    private Context mContext;
    private RequestQueue mRequestQueue;

    private NYTimesVolleyRequest(Context context)
    {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized NYTimesVolleyRequest getInstance(Context context)
    {
        if(mInstance==null) {
            mInstance = new NYTimesVolleyRequest(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue()
    {
        // Instantiate the cache and set a cap of 4MB of cache size
        Cache cache = new DiskBasedCache(mContext.getCacheDir(), 4*1024 * 1024);

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();

        //return the request queue to perform the api call
        return mRequestQueue;
    }

}
