package scholarstationandroid.scholarstation;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import WebUtil.Login.LoginReq;
import WebUtil.Login.LoginRes;
import WebUtil.StudySession.*;
import WebUtil.StudySession.StudyGroup;
import WebUtil.Webutil;
import layout.LoginInfo;

public class CreateStudyGroup extends AppCompatActivity {
    String createCourse = "";
    String createTopic = "";
    String createTime = "";
    String createDate = "";
    boolean isChecked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_study_group);

        final EditText course = (EditText) findViewById(R.id.createStudyCourse);
        final EditText topic = (EditText) findViewById(R.id.createStudyTopic);
        final EditText time = (EditText) findViewById(R.id.createStudyTime);
        final EditText date = (EditText) findViewById(R.id.createStudyDate);
        final TextView members = (TextView) findViewById(R.id.createStudyMembers);
        final Button membersButton = (Button) findViewById(R.id.createInviteMembers);
        final Button createStudy = (Button) findViewById(R.id.createStudyButton);
        final CheckBox papCheckbox = (CheckBox) findViewById(R.id.createCheckBox);

        if(papCheckbox.isChecked()){
            isChecked = false;
        }else{
            isChecked = true;
        }

        //<editor-fold desc="Get Text">
        assert course != null;
        course.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do some thing now
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                createCourse = course.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do something at this time
            }
        });
        assert topic != null;
        topic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do some thing now
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                createTopic = topic.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do something at this time
            }
        });
        assert time != null;
        time.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do some thing now
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                createTime = time.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do something at this time
            }
        });
        assert date != null;
        date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do some thing now
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                createDate = date.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do something at this time
            }
        });
        //</editor-fold>

        assert membersButton!= null;
        membersButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });

        assert createStudy != null;
        createStudy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                class NetworkCallTask extends AsyncTask<Object, Object, Object> {



                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                    }

                    @Override
                    protected Object doInBackground(Object... params) {
                        CreateStudyReq study = new CreateStudyReq();
                        study.username = LoginInfo.username;
                        study.owner = LoginInfo.username;
                        study.KEY = LoginInfo.KEY;
                        study.course = createCourse;
                        study.topic = createTopic;
                        study.members = null;
                        study.date = createDate;
                        study.time = createTime;
                        study.publicView = isChecked;

                        CreateStudyRes createRes = (CreateStudyRes) new Webutil().webRequest(study);
                        if(createRes.success == true){
                            Intent myIntent = new Intent(CreateStudyGroup.this, StudyGroupActivity.class);
                            startActivity(myIntent);
                        }else{
                            System.out.println("Failed To Create Study Group");
                        }
                        return createRes;

                    }

                    @Override
                    protected void onProgressUpdate(Object... values) {
                        super.onProgressUpdate(values);
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        try {

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
