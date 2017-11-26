package com.example.android.securityapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {
     //EditText curpass;
     EditText newpass;
     EditText repass;
     Button SAVE;
    String userId;
    String _newpass, _repass;
    SharedPreferences authentication;
    SharedPreferences.Editor edit;
    String url = "https://sreekana123.000webhostapp.com/api/changepass";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        authentication = getSharedPreferences(Prefer.AUTH_FILE, MODE_PRIVATE);
        edit = authentication.edit();
        userId = authentication.getString(Prefer.USER_ID,"");

        //curpass = (EditText) findViewById(R.id.cur_pass);
        newpass = (EditText) findViewById(R.id.new_pass);
        repass = (EditText) findViewById(R.id.re_pass);
        SAVE = (Button) findViewById(R.id.save_change);


        SAVE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changepassword();
            }
        });
    }

    private void changepassword(){
        _newpass=newpass.getText().toString();
        _repass=repass.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override

                    public void onResponse(String response) {
                        try {
                            Log.i("tins", response);

                            JSONObject obj = new JSONObject(response);
                            Boolean success = obj.getBoolean("success");
                            Log.d("success", success + "");
                            if (success) {
                                Log.d("check", "" + obj);
                                Toast.makeText(getApplicationContext(), "Changed successfully!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                String errorString = obj.getString("error");
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
                SAVE.setEnabled(true);
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("password", _newpass);
                params.put("confirmpass", _repass);
                params.put("user_id", userId);
                Log.d("code", params.get("user_id"));
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest.setShouldCache(false));


    }
}
