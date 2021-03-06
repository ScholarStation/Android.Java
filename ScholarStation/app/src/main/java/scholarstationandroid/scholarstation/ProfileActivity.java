package scholarstationandroid.scholarstation;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import WebUtil.Login.LoginReq;
import WebUtil.Login.LoginRes;
import WebUtil.Profile.ProfileReq;
import WebUtil.Profile.ProfileRes;
import WebUtil.Webutil;
import layout.LoginInfo;


public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final EditText fname = (EditText) findViewById(R.id.fname);
        final EditText lname = (EditText) findViewById(R.id.profile_lname_text);
        final EditText age = (EditText) findViewById(R.id.profile_age_text);
        final EditText gender = (EditText) findViewById(R.id.profile_gender_text);
        final EditText email = (EditText) findViewById(R.id.profile_email_text);
        final EditText year = (EditText) findViewById(R.id.profile_year_text);
        final EditText major = (EditText) findViewById(R.id.profile_major_text);
        final Button editButton = (Button) findViewById(R.id.profile_edit_button);

        setTitle(LoginInfo.username + "'s Profile");

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
                ProfileRes profileRes = (ProfileRes) new Webutil().webRequest(pr);
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
                } catch(Exception e){
                        e.printStackTrace();
                    }
            }
        }
        new NetworkCallTask().execute(new Object());

        assert editButton != null;
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,EditProfile.class);
                startActivity(intent);
                finish();
            }
        });

    }
}