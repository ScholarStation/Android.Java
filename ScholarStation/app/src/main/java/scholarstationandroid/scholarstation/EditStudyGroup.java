package scholarstationandroid.scholarstation;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import WebUtil.StudySession.*;
import WebUtil.StudySession.StudyGroup;
import WebUtil.Webutil;
import layout.LoginInfo;

public class EditStudyGroup extends AppCompatActivity {
    final Context context = this;
    String id = "";
    TextView members;
    EditStudyReq editstudy;
    String editMemberName = "";
    ArrayList<String> editMember = new ArrayList<String>();
    String[] memberString;
    boolean publicView = true;
    CheckBox papCheckbox;
    EditText time;
    EditText date;
    Calendar mCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_study_group);

        memberString = new String[editMember.size()];
        final EditText course = (EditText) findViewById(R.id.editStudyCourse);
        final EditText topic = (EditText) findViewById(R.id.editStudyTopic);
        time = (EditText) findViewById(R.id.editStudyTime);
        date = (EditText) findViewById(R.id.editStudyDate);
        members = (TextView) findViewById(R.id.editStudyMembers);
        final Button membersButton = (Button) findViewById(R.id.editInviteMembers);
        final Button editStudy = (Button) findViewById(R.id.editStudyButton);
        papCheckbox = (CheckBox) findViewById(R.id.createCheckBox);

        setTitle("Edit Study Group");
        String studyObject;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            studyObject = extras.getString("EditGroupInfo");
            StudyGroup group = new Gson().fromJson(studyObject, WebUtil.StudySession.StudyGroup.class);
            course.setText(group.course);
            topic.setText(group.topic);
            date.setText(group.date);
            time.setText(group.time);
            editstudy = new EditStudyReq();
            editstudy.username = LoginInfo.username;
            editstudy.owner = LoginInfo.username;
            editstudy.KEY = LoginInfo.KEY;
            editstudy._id = group._id;
            editstudy.course = group.course;
            editstudy.topic = group.topic;
            for (String s : group.members) {
                editMember.add(s);
            }
            editstudy.date = group.date;
            editstudy.time = group.time;
            editstudy.publicView = publicView;
            members.setText(Arrays.toString(group.members).replace("[", "").replace(',', '\n').replace("]", "").replace(" ", ""));
        }

        mCalendar  = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateText();
            }
        };

        final TimePickerDialog.OnTimeSetListener timePicker = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute){
                mCalendar.set(Calendar.HOUR_OF_DAY, hour);
                mCalendar.set(Calendar.MINUTE, minute);
                // formats hours to 12hr time
                if (hour == 0)
                    hour = hour + 12;
                else if (hour > 12)
                    hour = hour - 12;
                //corrects minutes to the correct format 0-> 00
                String formattedMinutes;
                //<editor-fold desc="switch for format minutes">
                switch (minute) {
                    case 0:
                        formattedMinutes = "00";
                        break;
                    case 1:
                        formattedMinutes = "01";
                        break;
                    case 2:
                        formattedMinutes = "02";
                        break;
                    case 3:
                        formattedMinutes = "03";
                        break;
                    case 4:
                        formattedMinutes = "04";
                        break;
                    case 5:
                        formattedMinutes = "05";
                        break;
                    case 6:
                        formattedMinutes = "06";
                        break;
                    case 7:
                        formattedMinutes = "07";
                        break;
                    case 8:
                        formattedMinutes = "08";
                        break;
                    case 9:
                        formattedMinutes = "09";
                        break;
                    default:
                        formattedMinutes = Integer.toString(minute);
                        break;
                }
                //</editor-fold>
                time.setText(hour + ":" + formattedMinutes);
            }
        };

        assert date!= null;
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog =  new DatePickerDialog(EditStudyGroup.this,datePicker,mCalendar.get(Calendar.YEAR),mCalendar.get(Calendar.MONTH),mCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setTitle("Set Date:");
                datePickerDialog.show();
            }
        });

        assert time!= null;
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(EditStudyGroup.this,timePicker,mCalendar.get(Calendar.HOUR_OF_DAY),mCalendar.get(Calendar.MINUTE),false);
                timePickerDialog.setTitle("Set Time:");
                timePickerDialog.show();
            }
        });

        //<editor-fold desc="Text change listener">
        assert course != null;
        course.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Will do something before text change
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                editstudy.course = course.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Will do something after text change
            }
        });
        assert topic != null;
        topic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Will do something before text change
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                editstudy.topic = topic.getText().toString();

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
                // Will do something before text change
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                editstudy.time = time.getText().toString();

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

                editstudy.date = date.getText().toString();

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
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.members_dialog);
                dialog.setTitle("Invite Members");
                final EditText memberName = (EditText) dialog.findViewById(R.id.inviteMemberText);
                Button inviteMember = (Button) dialog.findViewById(R.id.inviteMember);
                memberName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // Do some thing now
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        editMemberName = memberName.getText().toString();

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        // Do something at this time
                    }
                });
                inviteMember.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editMember.add(editMemberName);
                        System.out.println(editMember);
                        members.setText(Arrays.toString(editMember.toArray()).replace("[", "").replace(',', '\n').replace("]", "").replace(" ", ""));
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        assert editStudy != null;
        editStudy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                class NetworkCallTask extends AsyncTask<Object, Object, Object> {

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                    }

                    @Override
                    protected Object doInBackground(Object... params) {
                        String[] finalStringArray = new String [editMember.size()];
                        int i = 0;
                        for(String s :editMember){
                            finalStringArray[i]=s;i++;
                        }
                        editstudy.members=finalStringArray;
                        EditStudyRes editRes = (EditStudyRes) new Webutil().webRequest(editstudy);
                        if (editRes.success == true) {
                            Intent myIntent = new Intent(EditStudyGroup.this, StudyGroupActivity.class);
                            startActivity(myIntent);
                            finish();
                        } else {
                            System.out.println("Failed To Edit Study Group");
                        }
                        return editRes;
                    }

                    @Override
                    protected void onProgressUpdate(Object... values) {
                        super.onProgressUpdate(values);
                    }

                    @Override
                    protected void onPostExecute(Object o) {}
                }
                new NetworkCallTask().execute(new Object());
            }
        });
    }

    private void updateDateText(){
        String mFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(mFormat, Locale.US);
        date.setText(sdf.format(mCalendar.getTime()));
    }

    public void onBackPressed(){
        Intent mIntent = new Intent(this, StudyGroupActivity.class);
        startActivity(mIntent);
        finish();
    }
}
