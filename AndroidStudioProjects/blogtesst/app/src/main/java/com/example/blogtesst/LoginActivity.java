package com.example.blogtesst;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.blogtesst.LoginRequest;
import com.example.blogtesst.MainActivity;
import com.example.blogtesst.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText login_idAccountProfile, login_passwordAccountprofile;
    private Button login_button, join_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_login );

        login_idAccountProfile = findViewById( R.id.login_idAccountProfile );
        login_passwordAccountprofile = findViewById( R.id.login_passwordAccountprofile );

        join_button = findViewById( R.id.join_button );
        join_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( LoginActivity.this, MainActivity.class );
                startActivity( intent );
            }
        });


        login_button = findViewById( R.id.login_button );
        login_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idAccountProfile = login_idAccountProfile.getText().toString();
                String passwordAccountprofile = login_passwordAccountprofile.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            boolean success = jsonObject.getBoolean( "success" );

                            if(success) {//로그인 성공시

                                String idAccountProfile = jsonObject.getString( "idAccountProfile" );
                                String passwordAccountprofile = jsonObject.getString( "passwordAccountprofile" );
                                String nicknameAccountProfile = jsonObject.getString( "nicknameAccountProfile" );

                                Toast.makeText( getApplicationContext(), String.format("%s님 환영합니다.", nicknameAccountProfile), Toast.LENGTH_SHORT ).show();
                                Intent intent = new Intent( LoginActivity.this, MainActivity.class );

                                intent.putExtra( "idAccountProfile", idAccountProfile );
                                intent.putExtra( "passwordAccountprofile", passwordAccountprofile );
                                intent.putExtra( "nicknameAccountProfile", nicknameAccountProfile );

                                startActivity( intent );

                            } else {//로그인 실패시
                                Toast.makeText( getApplicationContext(), "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT ).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest( idAccountProfile, passwordAccountprofile, responseListener );
                RequestQueue queue = Volley.newRequestQueue( LoginActivity.this );
                queue.add( loginRequest );

            }
        });
    }
}