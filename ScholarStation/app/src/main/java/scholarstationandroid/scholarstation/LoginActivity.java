package scholarstationandroid.scholarstation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
//import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.content.Intent;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import WebUtil.Login.LoginReq;
import WebUtil.Login.LoginRes;
import WebUtil.Profile.ProfileReq;
import WebUtil.Profile.ProfileRes;
import WebUtil.Webutil;
import layout.LoginInfo;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    String userName = "";
    String passWord = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText user = (EditText) findViewById(R.id.username);
        final EditText pass = (EditText) findViewById(R.id.password);
        final Button signIn = (Button) findViewById(R.id.signInButton);
        final Button register = (Button) findViewById(R.id.registerButton);




        //<editor-fold desc="Get Text">
        assert user != null;
        user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do some thing now
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                userName = user.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do something at this time
            }
        });
        assert pass != null;
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do some thing now
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                passWord = pass.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do something at this time
            }
        });
        //</editor-fold>
        assert signIn != null;
        signIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                class NetworkCallTask extends AsyncTask<Object, Object, Object> {



                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                    }

                    @Override
                    protected Object doInBackground(Object... params) {
                        LoginReq login = new LoginReq();

                        login.username = userName;
                        login.password = passWord;
                        LoginRes loginRes = (LoginRes) new Webutil().webRequest(login);
                        LoginInfo.username = userName;
                        LoginInfo.reminder = loginRes.reminder;
                        LoginInfo.KEY = loginRes.KEY;
                        LoginInfo.reminder = loginRes.reminder;

                        if (loginRes.success == true) {
                            Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(myIntent);
                        } else {
                            LoginActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Snackbar snackbar = Snackbar.make(findViewById(R.id.loginCoordinator),"Enter valid login and password",Snackbar.LENGTH_LONG);
                                    snackbar.show();

                                }
                            });
                        }

                        return loginRes;
                    }

                    @Override
                    protected void onProgressUpdate(Object... values) {
                        super.onProgressUpdate(values);
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        try {
                            //System.out.println("UserName :: " + userName);
                            // System.out.println("Password :: " + passWord);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                new NetworkCallTask().execute(new Object());
            }
        });

        assert register != null;
        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
            });
    }
}

