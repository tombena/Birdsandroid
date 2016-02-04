package fr.sio.ecp.federatedbirds.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import fr.sio.ecp.federatedbirds.ApiClient;
import fr.sio.ecp.federatedbirds.R;
import fr.sio.ecp.federatedbirds.auth.TokenManager;
import fr.sio.ecp.federatedbirds.utils.ValidationUtils;

/**
 * Created by tomb on 26/01/16.
 */
public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        findViewById(R.id.validation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    private void signup(){
        //make sure data is well formated

        EditText usernameText = (EditText) findViewById(R.id.username);
        EditText passwordText = (EditText) findViewById(R.id.password);
        EditText repeatText = (EditText) findViewById(R.id.repeat);
        EditText emailText = (EditText) findViewById(R.id.email);

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        String repeat = repeatText.getText().toString();
        String email = emailText.getText().toString();

        if (!ValidationUtils.validateLogin(username)) {
            usernameText.setError(getString(R.string.invalid_format));
            usernameText.requestFocus();
            return;
        }

        if (!ValidationUtils.validatePassword(password)) {
            passwordText.setError(getString(R.string.invalid_format));
            passwordText.requestFocus();
            return;
        }

        if (!password.equals(repeat)){
            repeatText.setError(getString(R.string.non_matching));
            repeatText.requestFocus();
            return;
        }

        if (!ValidationUtils.validateEmail(email)) {
            emailText.setError(getString(R.string.invalid_format));
            emailText.requestFocus();
            return;
        }
        Log.d("email", "OK");


        //POST for account creation
        LoginTaskFragment taskFragment = new LoginTaskFragment();
        taskFragment.setArguments(username, password, email);
        taskFragment.show(getSupportFragmentManager(), "signup_task");


        //if success, redirect to LoginActivity
        Log.d("Account creation", "SUCCESS");
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);

    }






}
