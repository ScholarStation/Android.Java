package scholarstationandroid.scholarstation;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import WebUtil.Reminder.ReminderReq;
import WebUtil.Reminder.Reminders;
import WebUtil.Webutil;
import layout.LoginInfo;

public class FeedbackList extends AppCompatActivity {

    ListView listView;
    FBAdapter fbAdapter;
    ArrayList<Reminders> remindersArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_list);

        fbAdapter = new FBAdapter(this,remindersArrayList);
        listView = (ListView) findViewById(R.id.feedbackList);
        listView.setAdapter(fbAdapter);

        //System.out.println(LoginInfo.reminders);
        for(Reminders rm:LoginInfo.reminders)
            remindersArrayList.add(rm);
    }

    class FBAdapter extends ArrayAdapter<Reminders>{

        public FBAdapter(Context context, ArrayList<Reminders> objects){
            super(context,R.layout.feedback_array_adapter_layout,objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            Reminders remind = getItem(position);
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.feedback_array_adapter_layout,parent,false);
            }
            TextView courseView,dateView;
            courseView = (TextView)convertView.findViewById(R.id.feedbackCourse);
            dateView = (TextView)convertView.findViewById(R.id.feedbackDate);
            if(remindersArrayList.get(0) != null)
            {
                courseView.setText(remind.course);
                dateView.setText(String.valueOf(remind.date));

            }
            return convertView;
        }

    }

}


