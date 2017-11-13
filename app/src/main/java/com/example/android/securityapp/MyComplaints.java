package com.example.android.securityapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ramana on 11/11/2017.
 */

public class MyComplaints extends AppCompatActivity{

    boolean gotJson=false;
    JSONObject json = new JSONObject();
    RecyclerView rv;
    SwipeRefreshLayout mSwipeRefreshLayout;
    CountDownTimer timer;
    ArrayList<Complaint> complaints;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complaints_list);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        rv = (RecyclerView) findViewById(R.id.list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        json = getJSONFromInternet("https://ythanu999.000webhostapp.com/api/getmycomplaints");

        timer=new CountDownTimer(4000,300){
            Snackbar snack;
            @Override
            public void onTick(long millisUntilFinished) {
                if (gotJson) {
                    initializeFeatured(json);
                    initializeAdapter();
                    mSwipeRefreshLayout.setRefreshing(false);

                }
            }
            @Override
            public void onFinish()
            {
                if(gotJson)
                {
                    initializeFeatured(json);
                    initializeAdapter();
                    mSwipeRefreshLayout.setRefreshing(false);

                }
                else
                    snack=Snackbar.make(rv,"Cannot connect",Snackbar.LENGTH_INDEFINITE);
                    snack.setAction("Refresh", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refreshview();
                    }
                });
                snack.show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        };
        timer.start();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                gotJson=false;
                refreshview();
            }
        });

    }
    public void initializeFeatured(JSONObject json){
        complaints = new ArrayList<Complaint>();
        try {
            JSONArray response = json.getJSONArray("my");

            for(int i=0;i<response.length();i++){
                JSONObject currentC = response.getJSONObject(i);
                String complaint_id = currentC.getString("complaint_id");
                String title = currentC.getString("title");
                String content = currentC.getString("content");
                String date = currentC.getString("date");
                String status = currentC.getString("status");
                String complaint_by = currentC.getString("complaint_by");
//                int complaint_type = currentC.getInt("complaint_type");
                String image = currentC.getString("image");

                Complaint comp = new Complaint(title,content,status,complaint_by,date,complaint_id,image);
                complaints.add(comp);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            e.printStackTrace();
        }
    }

    public void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(complaints,MyComplaints.this);
        rv.setAdapter(adapter);
        timer.cancel();
    }

    public void refreshview()
    {
        json=getJSONFromInternet("https://ythanu999.000webhostapp.com/api/getmycomplaints");
        timer.cancel();
        timer.start();
    }

    public JSONObject getJSONFromInternet(String url)
    {
        JsonObjectRequest jsonRequest=new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        json=response;
                        gotJson=true;
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        try {
                            json.put("success","false");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        error.printStackTrace();
                    }
                });
        Volley.newRequestQueue(this).add(jsonRequest.setShouldCache(false));
        return json;
    }
}

