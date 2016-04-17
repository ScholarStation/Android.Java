package scholarstationandroid.scholarstation;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import WebUtil.Reminder.ReminderReq;
import WebUtil.Reminder.Reminders;
import WebUtil.Reminder.RemindersGetReq;
import WebUtil.Reminder.RemindersGetRes;
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
        setTitle("Feedback Forms");

        class NetworkCallTask extends AsyncTask<Object, Object, Object>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object... params) {
                return updateUI();
            }

            @Override
            protected void onProgressUpdate(Object... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPostExecute(Object o) {
                RemindersGetRes remindersGetRes = (RemindersGetRes)o;
                try{
                    for(Reminders rm: remindersGetRes.reminders){
                        remindersArrayList.add(rm);
                        fbAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                FeedbackList.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fbAdapter.notifyDataSetChanged();
                    }
                });
            }
        }
        new NetworkCallTask().execute(this);
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
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent mIntent = new Intent(FeedbackList.this, Feedback_form.class);
                    Bundle bundle = new Bundle();
                    mIntent.putExtra("FeedbackFormID", new Gson().toJson(remindersArrayList.get(position)));
                    mIntent.putExtras(bundle);
                    startActivity(mIntent);
                    finish();
                }
            });
            return convertView;
        }
    }

    public RemindersGetRes updateUI(){

        RemindersGetRes remindersGetRes;
        RemindersGetReq remindersGetReq = new RemindersGetReq();
        remindersGetReq.username = LoginInfo.username;
        remindersGetReq.KEY = LoginInfo.KEY;
        remindersGetRes  = (RemindersGetRes) new Webutil().webRequest(remindersGetReq);
        return remindersGetRes;
    }
}


