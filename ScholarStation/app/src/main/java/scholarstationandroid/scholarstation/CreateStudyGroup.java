package scholarstationandroid.scholarstation;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import WebUtil.Login.LoginReq;
import WebUtil.Login.LoginRes;
import WebUtil.StudySession.*;
import WebUtil.StudySession.StudyGroup;
import WebUtil.Webutil;
import layout.LoginInfo;

public class CreateStudyGroup extends AppCompatActivity {
    final Context context = this;
    String createCourse = "";
    String createTopic = "";
    String createTime = "";
    String createDate = "";
    TextView members;
    CreateStudyReq study;
    String createMemberName = "";
    ArrayList<String> createMember = new ArrayList<>();
    String[] memberString;
    boolean publicView = true;
    CheckBox papCheckbox;
    EditText time;
    EditText date;
    Calendar mCalendar;
    Date dateReminder = new Date();

   // Creates study group object that is sent to the server and goes back to study group activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_study_group);
         memberString = new String[createMember.size()];
        final EditText course = (EditText) findViewById(R.id.createStudyCourse);
        final EditText topic = (EditText) findViewById(R.id.createStudyTopic);
        time = (EditText) findViewById(R.id.createStudyTime);
        date = (EditText) findViewById(R.id.createStudyDate);
        members = (TextView) findViewById(R.id.createStudyMembers);
        final Button membersButton = (Button) findViewById(R.id.createInviteMembers);
        final Button createStudy = (Button) findViewById(R.id.createStudyButton);
        papCheckbox = (CheckBox) findViewById(R.id.createCheckBox);

        setTitle("Create Study Group");

        //Listens for a checkbox event to happen and changes value of publicView
        papCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    publicView = false;
                }else{
                    publicView = true;
                }

            }
        });

        //If date EditText field is selected then a popup will appear allowing the user to select the date
        mCalendar  = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                mCalendar.set(Calendar.YEAR, year);
                dateReminder.setYear(year - 1900);//method setyear() adds 1900 to the year, so it needs to be subtracted
                mCalendar.set(Calendar.MONTH, monthOfYear);
                dateReminder.setMonth(monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dateReminder.setDate(dayOfMonth);
                updateDateText();
            }
        };

        //If time EditText field is selected then a popup will appear allowing the user to select the time
        final TimePickerDialog.OnTimeSetListener timePicker = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute){
                mCalendar.set(Calendar.HOUR_OF_DAY, hour);
                dateReminder.setHours(hour);
                mCalendar.set(Calendar.MINUTE, minute);
                dateReminder.setMinutes(minute);
                // formats hours to 12hr time
                if (hour == 0)
                    hour = hour + 12;
                else if (hour > 12)
                    hour = hour - 12;
                //
                /*
                    corrects minutes to the correct format 0-> 00
                    Have to change minute to string as 08 and 09 are hexadecimal and can not be used as a integer
                 */
                String formattedMinutes;
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
                time.setText(hour + ":" + formattedMinutes);
                createTime = time.getText().toString();
            }
        };

        //Listens for a click event on date EditText
        assert date!= null;
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog =  new DatePickerDialog(CreateStudyGroup.this,datePicker,mCalendar.get(Calendar.YEAR),mCalendar.get(Calendar.MONTH),mCalendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.setTitle("Set Date:");
                datePickerDialog.show();
            }
        });

        //Listens for a click event on time EditText
        assert time!= null;
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateStudyGroup.this,timePicker,mCalendar.get(Calendar.HOUR_OF_DAY),mCalendar.get(Calendar.MINUTE),false);
                timePickerDialog.setTitle("Set Time:");
                timePickerDialog.show();
            }
        });

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


        //</editor-fold>

        assert membersButton!= null;
        membersButton.setOnClickListener(new View.OnClickListener() {

            //If membersButton is click will show a dialog box allowing the user to add another member
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

                        createMemberName = memberName.getText().toString();

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        // Do something at this time
                    }
                });
                inviteMember.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createMember.add(createMemberName);
                        members.setText(Arrays.toString(createMember.toArray()).replace("[", "").replace(',', '\n').replace("]", "").replace(" ", ""));
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }

        });

        assert createStudy != null;
        createStudy.setOnClickListener(new View.OnClickListener() {

            //createStudy button is clicked will send a request to the server to make a study group and will go back
            //to StudyGroup activity
            @Override
            public void onClick(View view) {

                class NetworkCallTask extends AsyncTask<Object, Object, Object> {

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                    }

                    @Override
                    protected Object doInBackground(Object... params) {
                        memberString = createMember.toArray(memberString);
                        study = new CreateStudyReq();
                        study.username = LoginInfo.username;
                        study.owner = LoginInfo.username;
                        study.KEY = LoginInfo.KEY;
                        study.course = createCourse;
                        study.topic = createTopic;
                        study.members = memberString;
                        study.date = createDate;
                        study.time = createTime;
                        study.publicView = publicView;

                        CreateStudyRes createRes = (CreateStudyRes) new Webutil().webRequest(study);
                        if(createRes.success == true){
                            makeReminder(createTopic + " " +createCourse );
                            Intent myIntent = new Intent(CreateStudyGroup.this, StudyGroupActivity.class);
                            startActivity(myIntent);

                            finish();
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
                    protected void onPostExecute(Object o) {}
                }
                new NetworkCallTask().execute(new Object());
            }
        });
    }

    //formats the mCalender object into a readable string
    private void updateDateText(){
        String mFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(mFormat, Locale.US);
        date.setText(sdf.format(mCalendar.getTime()));
        createDate = date.getText().toString();
    }

    //when StudyGroup is created will send a notification when the studygroup starts
    private void makeReminder(String content){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,ReminderAlarmReceiver.class);
        intent.putExtra(ReminderAlarmReceiver.reminder_text, content);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,dateReminder.getTime(),pendingIntent);
    }
    
    /*
        if back button is pressed without creating a studygroup, this will go back to StudyGroup activity and
        will take it off the backstack
     */
    public void onBackPressed(){
        Intent mIntent = new Intent(this, StudyGroupActivity.class);
        startActivity(mIntent);
        finish();
    }
}
