package com.example.android.securityapp;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * Created by ramana on 10/6/2017.
 */

public class ComplaintsFragment extends Fragment {

    public ComplaintsFragment(){
        //nothing
    }
    boolean gotJson=false;
    JSONObject json = new JSONObject();
    RecyclerView rv;
    SwipeRefreshLayout mSwipeRefreshLayout;
    CountDownTimer timer;
    ArrayList<Complaint> complaints;

    private static final String ARG_SECTION_NUMBER = "section_number";
    public static ComplaintsFragment newInstance(int sectionNumber) {
        ComplaintsFragment fragment = new ComplaintsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View listv = inflater.inflate(R.layout.complaints_list,container,false);
        rv = (RecyclerView) listv.findViewById(R.id.list);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        json = getJSONFromInternet("https://sreekana123.000webhostapp.com/api/getsecuritycomplaints");

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
        mSwipeRefreshLayout = (SwipeRefreshLayout)listv.findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                gotJson=false;
                refreshview();
            }
        });

        return listv;
    }
    public void initializeFeatured(JSONObject json){
        complaints = new ArrayList<Complaint>();
        try {
            JSONArray response = json.getJSONArray("news");

            for(int i=0;i<response.length();i++){
                JSONObject currentC = response.getJSONObject(i);
                String complaint_id = currentC.getString("complaint_id");
                String title = currentC.getString("title");
                String content = currentC.getString("content");
                String date = currentC.getString("date");
                String status = currentC.getString("status");
                String complaint_by = currentC.getString("complaint_by");
                int count = currentC.getInt("upvotes");
                String image = currentC.getString("image");

                Complaint comp = new Complaint(title,content,status,complaint_by,date,complaint_id,count,image);
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
        RVAdapter adapter = new RVAdapter(complaints,this.getContext());
        rv.setAdapter(adapter);
        timer.cancel();
    }

    public void refreshview()
    {
        json=getJSONFromInternet("https://sreekana123.000webhostapp.com/api/getsecuritycomplaints");
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
        Volley.newRequestQueue(getActivity()).add(jsonRequest.setShouldCache(false));
        return json;
    }
}
