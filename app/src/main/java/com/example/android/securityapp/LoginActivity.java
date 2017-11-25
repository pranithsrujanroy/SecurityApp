package com.example.android.securityapp;

        import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private TextView Info;
    private Button Login;
    SharedPreferences authentication;
    SharedPreferences.Editor edit;
    //private int counter = 5;
    String _username, _password;
    String loginurl = "https://ythanu999.000webhostapp.com/api/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        authentication = getApplicationContext().getSharedPreferences(Prefer.AUTH_FILE, MODE_PRIVATE);
        edit = authentication.edit();

        if(authentication.getString(Prefer.ROLL_NO, null)!=null ){
            Log.v("Logged", "In");
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }else {

            username = (EditText) findViewById(R.id.etName);
            password = (EditText) findViewById(R.id.etPassword);
            Info = (TextView) findViewById(R.id.tvInfo);
            Login = (Button) findViewById(R.id.btnLogin);

            Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    senddata();
                }
            });
        }
    }

    public boolean validate() {
        boolean valid = true;

        _username = username.getText().toString();
        _password = password.getText().toString();
//        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putString("roll_no", _username);
        if (_username.isEmpty() || _password.isEmpty()) {
            Toast.makeText(this, "Username and Password can't be blank!", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            username.setError(null);
        }
        return valid;
    }

    private void senddata() {
        final String errorMsg;
        if (validate()) {
            _username = username.getText().toString();
            _password = password.getText().toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, loginurl,
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

                                    JSONObject userDetail = obj.getJSONObject("userDetails");
                                    String name = userDetail.getString("name");
                                    edit.putString(Prefer.USER_ID, userDetail.getString("user_id"));
                                    edit.putString(Prefer.ROLL_NO, userDetail.getString("roll_no"));
                                    edit.putString(Prefer.DISPLAY_NAME, userDetail.getString("name"));
                                    edit.putString(Prefer.USER_EMAIL, userDetail.getString("email"));
                                    edit.putString(Prefer.USER_ROLE, userDetail.getString("user_role"));
                                    edit.putString(Prefer.USER_HALL, userDetail.getString("hall"));
                                    edit.putBoolean(Prefer.USER_LOGGED_IN, true);
                                    edit.apply();

                                    Toast.makeText(getApplicationContext(), "Signed In successfully!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                    intent.putExtra("username", name);
//                                    intent.putExtra("roll_no",_username);
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
                    Login.setEnabled(true);
                }

            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("roll_no", _username);
                    params.put("password", _password);
                    Log.d("code", params.get("roll_no"));
                    return params;
                }
            };

            Volley.newRequestQueue(this).add(stringRequest.setShouldCache(false));
        }
    }
}
