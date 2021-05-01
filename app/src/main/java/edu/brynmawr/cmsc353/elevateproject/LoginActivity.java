package edu.brynmawr.cmsc353.elevateproject;

import androidx.appcompat.app.AppCompatActivity;
import edu.brynmawr.cmsc353.elevateproject.models.User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.parceler.Parcels;

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
        try {
            String token = task.get();
            Log.i(TAG,"token is "+token);
           if (!token.contains("ERROR")){
               AuthenticateTask task_ = new AuthenticateTask(LoginActivity.this);
               task_.execute("http://10.0.2.2:3000/api/user/me",token);
               User currentUsr = task_.get();
               //go to main
               Intent intent = new Intent(this, MainActivity.class);
               intent.putExtra("currentuser", Parcels.wrap(currentUsr));
               startActivity(intent);
               finish();

           }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
        mBtnLogin = findViewById(R.id.btnConnect);
        mBtnSignup = findViewById(R.id.btnReject);
    }
}