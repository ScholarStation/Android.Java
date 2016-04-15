package scholarstationandroid.scholarstation;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import WebUtil.Profile.EditProfileReq;
import WebUtil.Profile.ProfileReq;
import WebUtil.Profile.ProfileRes;
import WebUtil.WebResponse;
import WebUtil.Webutil;
import layout.LoginInfo;

public class EditProfile extends AppCompatActivity {
    String firstName = "";
    String lastName = "";
    String editAge = "";
    String editGender = "";
    String editEmail = "";
    String editYear = "";
    String editMajor = "";
    EditProfileReq editProfileReq = new EditProfileReq();
    ProfileRes profileRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        final EditText fname = (EditText) findViewById(R.id.fname_edit);
        final EditText lname = (EditText) findViewById(R.id.profile_lname_text_edit);
        final EditText age = (EditText) findViewById(R.id.profile_age_text_edit);
        final EditText gender = (EditText) findViewById(R.id.profile_gender_text_edit);
        final EditText email = (EditText) findViewById(R.id.profile_email_text_edit);
        final EditText year = (EditText) findViewById(R.id.profile_year_text_edit);
        final EditText major = (EditText) findViewById(R.id.profile_major_text_edit);
        final Button saveProfileButton = (Button) findViewById(R.id.profile_edit_save_button);

        setTitle("Edit Profile");



        class NetworkCallTask extends AsyncTask<Object, Object, Object> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object... params) {
                ProfileReq pr = new ProfileReq();
                pr.username = LoginInfo.username;
                pr.KEY = LoginInfo.KEY;
                profileRes = (ProfileRes) new Webutil().webRequest(pr);
                return profileRes;
            }

            @Override
            protected void onProgressUpdate(Object... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPostExecute(Object o) {
                try {
                    ProfileRes profileRes = (ProfileRes) o;
                    fname.setText(profileRes.fname);
                    lname.setText(profileRes.lname);
                    age.setText(String.valueOf(profileRes.age));
                    gender.setText(profileRes.gender);
                    email.setText(profileRes.email);
                    year.setText(profileRes.year);
                    major.setText(profileRes.major);

                    firstName = profileRes.fname;
                    lastName = profileRes.lname;
                    editAge = String.valueOf(profileRes.age);
                    editEmail = profileRes.email;
                    editGender = profileRes.gender;
                    editMajor = profileRes.major;
                    editYear = profileRes.year;
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        new NetworkCallTask().execute(new Object());




        //age.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        //<editor-fold desc="On text change">
        assert fname != null;
        fname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                firstName = fname.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        assert lname != null;
        lname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do some thing now
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                lastName = lname.getText().toString();

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

                editAge = age.getText().toString();

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

                editGender = gender.getText().toString();

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

                editEmail = email.getText().toString();

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

                editYear = year.getText().toString();

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

                editMajor = major.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do something at this time
            }
        });
        //</editor-fold>

        assert saveProfileButton != null;
        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                class NetworkCallTask extends AsyncTask<Object, Object, Object> {
                    @Override
                    protected void onPreExecute(){super.onPreExecute();}

                    @Override
                    protected Object doInBackground(Object... params){

                        editProfileReq.fname = firstName;
                        editProfileReq.lname = lastName;
                        editProfileReq.age = editAge;
                        editProfileReq.gender = editGender;
                        editProfileReq.year = editYear;
                        editProfileReq.major = editMajor;
                        editProfileReq.email = editEmail;
                        editProfileReq._id = profileRes._id;

                        WebResponse editProfileRes = (WebResponse) new Webutil().webRequest(editProfileReq);
                        return editProfileRes;
                    }

                    @Override
                    protected void onProgressUpdate(Object... values) {
                        super.onProgressUpdate(values);
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        try {
                            EditProfile.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Snackbar snackbar = Snackbar.make(findViewById(R.id.editProfileCoordinator),"Profile Saved",Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            });
                            Intent myIntent = new Intent(EditProfile.this, ProfileActivity.class);
                            startActivity(myIntent);
                            finish();

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
