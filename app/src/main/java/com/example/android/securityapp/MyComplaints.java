package com.example.android.securityapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class MyComplaints extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private TextView Info;
    private TextView Login;
    SharedPreferences authentication;
    SharedPreferences.Editor edit;
    //private int counter = 5;
    String _userid;
    String loginurl = "https://sreekana123.000webhostapp.com/api/getmycomplaints";
    ArrayList<Complaint> complaints;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complaints_list);
        rv = (RecyclerView) findViewById(R.id.list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        authentication = getApplicationContext().getSharedPreferences(Prefer.AUTH_FILE, MODE_PRIVATE);
        _userid=authentication.getString(Prefer.USER_ID,null);
        edit = authentication.edit();

        getcomplaints();
    }

    private void getcomplaints() {
        final String errorMsg;

            StringRequest stringRequest = new StringRequest(Request.Method.POST, loginurl,
                    new Response.Listener<String>() {

                        @Override

                        public void onResponse(String response) {
                            try {
                                Log.i("tins", response);
                                complaints = new ArrayList<Complaint>();
                                JSONObject obj = new JSONObject(response);
                                Boolean success = obj.getBoolean("success");
                                Log.d("success", success + "");
                                if (success) {
                                    Log.d("check", "" + obj);
                                    JSONArray responsee = obj.getJSONArray("my");
                                    for(int i=0;i<responsee.length();i++){
                                        JSONObject currentC = responsee.getJSONObject(i);
                                        String complaint_id = currentC.getString("complaint_id");
                                        String title = currentC.getString("title");
                                        String content = currentC.getString("content");
                                        String date = currentC.getString("date");
                                        String status = currentC.getString("status");
                                        String complaint_by = currentC.getString("complaint_by");
                                        int count = currentC.getInt("upvotes");
                        //                int complaint_type = currentC.getInt("complaint_type");
                                        String image = currentC.getString("image");

                                        Complaint comp = new Complaint(title,content,status,complaint_by,date,complaint_id,count,image);
                                        complaints.add(comp);
                                        RVAdapter adapter = new RVAdapter(complaints,MyComplaints.this);
                                        rv.setAdapter(adapter);
                                    }
                                } else {
                                    String errorString = "loding error";
                                    Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.i("eeee", error.toString());
                    Login.setEnabled(true);
                }

            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_id", _userid);
                    Log.d("code", params.get("user_id"));
                    return params;
                }
            };

            Volley.newRequestQueue(this).add(stringRequest.setShouldCache(false));

    }
}
