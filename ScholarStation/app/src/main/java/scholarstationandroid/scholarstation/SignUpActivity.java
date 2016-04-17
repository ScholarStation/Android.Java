package scholarstationandroid.scholarstation;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import WebUtil.Login.CreateLoginReq;
import WebUtil.Profile.CreateProfileReq;
import WebUtil.WebResponse;
import WebUtil.Webutil;

public class SignUpActivity extends AppCompatActivity {
    String userName = "";
    String passWord = "";
    String firstName = "";
    String lastName = "";
    String signUpPage = "";
    String signUpGender = "";
    String signUpEmail = "";
    String signUpYear = "";
    String signUpMajor = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        final EditText user = (EditText) findViewById(R.id.signup_user);
        final EditText pass = (EditText) findViewById(R.id.signup_password);
        final EditText fName = (EditText) findViewById(R.id.signup_fname);
        final EditText lName = (EditText) findViewById(R.id.signup_lName);
        final EditText age = (EditText) findViewById(R.id.signup_age);
        final EditText gender = (EditText) findViewById(R.id.signup_gender);
        final EditText email = (EditText) findViewById(R.id.signup_email);
        final EditText year = (EditText) findViewById(R.id.signup_year);
        final EditText major = (EditText) findViewById(R.id.signup_major);
        final Button signUp = (Button) findViewById(R.id.signupButton);

        setTitle("Register");
        age.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);

        //<editor-fold desc="On text change">
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
        assert fName != null;
        fName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do some thing now
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                firstName = fName.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do something at this time
            }
        });
        assert lName != null;
        lName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do some thing now
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                lastName = lName.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do something at this time
            }
        });
        assert age != null;
        age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do some thing now
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                signUpPage = age.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do something at this time
            }
        });
        assert gender != null;
        gender.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do some thing now
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                signUpGender = gender.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do something at this time
            }
        });
        assert email != null;
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do some thing now
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                signUpEmail = email.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do something at this time
            }
        });
        assert year != null;
        year.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do some thing now
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                signUpYear = year.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do something at this time
            }
        });
        assert major != null;
        major.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do some thing now
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                signUpMajor = major.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do something at this time
            }
        });
        //</editor-fold>

        assert signUp != null;
        signUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                class NetworkCallTask extends AsyncTask<Object, Object, Object> {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                    }

                    @Override
                    protected Object doInBackground(Object... params) {
                        CreateLoginReq createLogin = new CreateLoginReq();
                        createLogin.username = userName;
                        createLogin.password = passWord;
                        new Webutil().webRequest(createLogin);

                        CreateProfileReq createProfileReq = new CreateProfileReq();

                        createProfileReq.fname = firstName;
                        createProfileReq.lname = lastName;
                        createProfileReq.age = signUpPage;
                        createProfileReq.gender = signUpGender;
                        createProfileReq.email = signUpEmail;
                        createProfileReq.year = signUpYear;
                        createProfileReq.major = signUpMajor;
                        createProfileReq.username = createLogin.username;
                        WebResponse createProfileRes = (WebResponse) new Webutil().webRequest(createProfileReq);
                        return createProfileRes;
                    }

                    @Override
                    protected void onProgressUpdate(Object... values) {
                        super.onProgressUpdate(values);
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        try {
                            Intent myIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(myIntent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                new NetworkCallTask().execute(new Object());
            }
        });
    }
}
