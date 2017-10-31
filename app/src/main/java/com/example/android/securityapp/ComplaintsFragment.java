package com.example.android.securityapp;


import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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
//    private String mJSONURLString = "http://localhost/ci_intro/api/getcomplaints";
    boolean gotJson=false;
    JSONObject json = new JSONObject();

    private static final String ARG_SECTION_NUMBER = "section_number";
    public ComplaintsFragment(){
        //
    }
    public static ComplaintsFragment newInstance(int sectionNumber) {
        ComplaintsFragment fragment = new ComplaintsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View listv = inflater.inflate(R.layout.complaints_list,container,false);
        RecyclerView rv = (RecyclerView) listv.findViewById(R.id.list);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        final ArrayList<Complaint> complaints = new ArrayList<Complaint>();

        json = getJSONFromInternet("http://localhost/ci_intro/api/getcomplaints");

        try {
            JSONArray response = json.getJSONArray("news");

            for(int i=0;i<response.length();i++){
                JSONObject currentC = response.getJSONObject(i);
//                JSONObject properties = currentEQ.getJSONObject("properties");
//                String complaint_id = currentC.getString("complaint_id");
                String title = currentC.getString("title");
                String content = currentC.getString("content");
                String date = currentC.getString("date");
                String status = currentC.getString("status");
                String complaint_by = currentC.getString("complaint_by");
//                int complaint_type = currentC.getInt("complaint_type");
//                String image = currentC.getString("image");

                Complaint comp = new Complaint(title,content,status,complaint_by,date);
                complaints.add(comp);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            e.printStackTrace();
        }

        RVAdapter adapter;
        adapter = new RVAdapter(complaints,this.getContext());
        rv.setAdapter(adapter);

        return listv;
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
