package com.example.android.securityapp;

/**
 * Created by ramana on 10/8/2017.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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


public class NewComplaint extends AppCompatActivity {
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    private EditText title;
    private EditText description;
    private Button Image;
    private Button SUBMIT;
    String _title, _description,_type;
    String addcomplainturl = "https://sreekana123.000webhostapp.com/api/addnew";
    String userId;
    SharedPreferences.Editor edit;
    Spinner spinner1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_complaint);
        title = (EditText) findViewById(R.id.editText);
        description = (EditText) findViewById(R.id.editText2);
//        Image = (Button) findViewById(R.id.buttonLoadPicture);
        SUBMIT = (Button) findViewById(R.id.button3);
        spinner1 = (Spinner) findViewById(R.id.spinner1);

        SharedPreferences authentication = getSharedPreferences(Prefer.AUTH_FILE, MODE_PRIVATE);
        edit = authentication.edit();
        userId = authentication.getString(Prefer.USER_ID,"");

        SUBMIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createComplaint();
            }
        });
    }

    public boolean validate() {
        boolean valid = true;
        _title = title.getText().toString();
        _description = description.getText().toString();
        if (_title.isEmpty() || _description.isEmpty()) {
            Toast.makeText(this, "Title and description can't be blank!", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            title.setError(null);
        }
        return valid;
    }

    private void createComplaint() {
        final String errorMsg;
        if (validate()) {
            _title = title.getText().toString();
            _description = description.getText().toString();
            _type = String.valueOf(spinner1.getSelectedItem());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, addcomplainturl,
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
                                    Toast.makeText(getApplicationContext(), "Submitted successfully!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(NewComplaint.this, MainActivity.class);
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
                    SUBMIT.setEnabled(true);
                }

            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("title", _title);
                    params.put("content", _description);
                    params.put("complaint_by", userId);
                    params.put("complaint_type",_type);
                    Log.d("code", params.get("title"));
                    return params;
                }
            };

            Volley.newRequestQueue(this).add(stringRequest.setShouldCache(false));
        }
    }
    public void loadImagefromGallery(View view) {

        // Create intent to Open Image applications like Gallery, Google Photos

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,

                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // Start the Intent

        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

    }



    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        try {

            // When an Image is picked

            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK

                    && null != data) {

                // Get the Image from data


                Uri selectedImage = data.getData();

                String[] filePathColumn = {MediaStore.Images.Media.DATA};


                // Get the cursor

                Cursor cursor = getContentResolver().query(selectedImage,

                        filePathColumn, null, null, null);

                // Move to first row

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                imgDecodableString = cursor.getString(columnIndex);

                cursor.close();

               // ImageView imgView = (ImageView) findViewById(R.id.imgView);

                // Set the Image in ImageView after decoding the String

//                imgView.setImageBitmap(BitmapFactory
//
//                        .decodeFile(imgDecodableString));

            } else {

                Toast.makeText(this, "You haven't picked Image",

                        Toast.LENGTH_LONG).show();

            }

        } catch (Exception e) {

            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)

                    .show();

        }
    }



}

