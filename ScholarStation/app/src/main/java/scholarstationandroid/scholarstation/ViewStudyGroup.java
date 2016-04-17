package scholarstationandroid.scholarstation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import WebUtil.StudySession.*;
import WebUtil.StudySession.StudyGroup;

public class ViewStudyGroup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_study_group);
        final TextView vOwner = (TextView) findViewById(R.id.viewStudyOwner);
        final TextView vCourse = (TextView) findViewById(R.id.viewStudyCourse);
        final TextView vTopic = (TextView) findViewById(R.id.viewStudyTopic);
        final TextView vDate = (TextView) findViewById(R.id.viewStudyDate);
        final TextView vTime = (TextView) findViewById(R.id.viewStudyTime);
        final TextView vMember = (TextView) findViewById(R.id.viewStudyMembers);
        final TextView vPublic = (TextView) findViewById(R.id.viewPublic);

        String studyObject;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            studyObject = extras.getString("ViewGroupInfo");
            StudyGroup group = new Gson().fromJson(studyObject, StudyGroup.class);
            vOwner.setText(group.owner);
            setTitle(group.course);
            vCourse.setText(group.course);
            vTopic.setText(group.topic);
            vDate.setText(group.date);
            vTime.setText(group.time);
            vMember.setText(Arrays.toString(group.members).replace("[", "").replace(',', '\n').replace("]", "").replace(" ", ""));
            if(group.publicView == true){
                vPublic.setText("Public Study Group");
            }else{
                vPublic.setText("Private Study Group");
            }

        }






    }
}
