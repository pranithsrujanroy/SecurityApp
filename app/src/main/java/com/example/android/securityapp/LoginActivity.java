package com.example.android.securityapp;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
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
public class LoginActivity extends AppCompatActivity  {

    private EditText username;
    private EditText password;
    private TextView Info;
    private Button Login;
    //private int counter = 5;
    String _username, _password;
    String loginurl = "https://ythanu999.000webhostapp.com/api/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        username = (EditText)findViewById(R.id.etName);
        password = (EditText)findViewById(R.id.etPassword);

        Info = (TextView)findViewById(R.id.tvInfo);
        Login = (Button)findViewById(R.id.btnLogin);

        //  Info.setText("No of attempts remaining: 5");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                senddata();
            }
        });
    }

    public boolean validate() {
        boolean valid = true;

        _username = username.getText().toString();
        _password = password.getText().toString();

        if (_username.isEmpty() || _password.isEmpty() ) {
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
                                Boolean success=obj.getBoolean("success");
                                Log.d("success",success+"");
                                if (success) {
                                    Log.d("check", "" + obj);

                                    JSONObject userDetail=obj.getJSONObject("userDetails");
//                                    edit.putString(Preferences.USER_ID, userDetail.getString("user_id"));
//                                    edit.putString(Preferences.USER_PROFILE_PIC, userDetail.getString("display_picture_small"));
//                                    edit.putString(Preferences.DISPLAY_NAME, userDetail.getString("display_name"));
//                                    edit.putString(Preferences.USER_NAME, userDetail.getString("username"));
//                                    edit.putString(Preferences.USER_EMAIL, userDetail.getString("email"));
//                                    edit.putBoolean(Preferences.USER_LOGGED_IN, true);
//                                    edit.apply();
                                    Toast.makeText(getApplicationContext(),"Signed In successfully!",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                    finish();
                                } else {
                                    String errorString=obj.getString("error");
                                    Toast.makeText(getApplicationContext(),errorString,Toast.LENGTH_LONG).show();
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
                    Log.d("code",params.get("roll_no"));
                    return params;
                }
            };

            Volley.newRequestQueue(this).add(stringRequest.setShouldCache(false));
        }
    }

}

