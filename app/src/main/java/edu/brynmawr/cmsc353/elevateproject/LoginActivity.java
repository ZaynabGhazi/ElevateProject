package edu.brynmawr.cmsc353.elevateproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                Log.i(TAG, "Login button clicked");
                String email = mEtEmail.getText().toString();
                String password = mEtPassword.getText().toString();
                loginUser(email, password);
            }
        });
    }

    private void loginUser(String email, String password) {
        String url = "http://10.0.2.2:3000/api/user/login";
        LoginTask task = new LoginTask(LoginActivity.this);
        task.execute(url,email,password);
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
        String url = "http://10.0.2.2:3000/api/user/signup";
        SignupTask task = new SignupTask(LoginActivity.this);
        task.execute(url,email,password);
    }

    private void bindView() {
        mEtEmail = findViewById(R.id.etEmail);
        mEtPassword = findViewById(R.id.etPassword);
        mBtnLogin = findViewById(R.id.btnLogin);
        mBtnSignup = findViewById(R.id.btnSignup);
    }
}