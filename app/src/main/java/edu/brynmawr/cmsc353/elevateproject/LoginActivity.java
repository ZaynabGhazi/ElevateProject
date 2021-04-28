package edu.brynmawr.cmsc353.elevateproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";

    private EditText mEtEmail;
    private EditText mEtPassword;
    private Button mBtnLogin;
    private Button mBtnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        bindView();
        makeLogin();
        makeSingup();
    }

    private void makeLogin() {
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private void makeSingup(){
        mBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "SignUp button clicked");
                String email = mEtEmail.getText().toString();
                String password = mEtPassword.getText().toString();
                signupUser(email, password);
            }
        });

    }

    private void signupUser(String email, String password) {
        // assumes that there is a server running on the AVD's host on port 3000
        // and that it has a /test endpoint that returns a JSON object with
        // a field called "status"
        String url = "http://10.0.2.2:3000/api/user/signup";

        AuthenticatetTask task = new AuthenticatetTask(LoginActivity.this);
        task.execute(url,email,password);
    }

    private void bindView() {
        mEtEmail = findViewById(R.id.etEmail);
        mEtPassword = findViewById(R.id.etPassword);
        mBtnLogin = findViewById(R.id.btnLogin);
        mBtnSignup = findViewById(R.id.btnSignup);
    }
}