package scholarstationandroid.scholarstation;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;

import WebUtil.FeedBack.DeletFeedBackRes;
import WebUtil.FeedBack.DeleteFeedbackReq;
import WebUtil.FeedBack.FeedBackReq;
import WebUtil.FeedBack.FeedBackRes;
import WebUtil.Reminder.Reminders;
import WebUtil.StudySession.StudyGroup;
import WebUtil.WebResponse;
import WebUtil.Webutil;
import layout.LoginInfo;

public class Feedback_form extends AppCompatActivity {

    String fbTopic;
    String fbRate;
    boolean fbPrepare = false;
    String fbCourse;
    String getExtras;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Feedback Form");

        final RatingBar rateStudyGroup = (RatingBar) findViewById(R.id.ratingBar);
        final CheckBox prepareCheckBox = (CheckBox) findViewById(R.id.prepareCheck);
        final Button sendFeedbackButton = (Button) findViewById(R.id.sendFeedback);
        final EditText topicTextView = (EditText) findViewById(R.id.topicEditText);
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            getExtras = extras.getString("FeedbackFormID");
            Reminders remind = new Gson().fromJson(getExtras, WebUtil.Reminder.Reminders.class);
            fbCourse = remind.course;
            id = remind._id;
        }

        assert rateStudyGroup != null;
        rateStudyGroup.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                fbRate = String.valueOf(rating);
            }
        });

        assert prepareCheckBox != null;
        prepareCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fbPrepare = isChecked;
            }
        });

        assert topicTextView != null;
        topicTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do some thing now
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                 fbTopic = topicTextView.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do something at this time
            }
        });

        assert sendFeedbackButton != null;
        sendFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                class NetworkCallTask extends AsyncTask<Object, Object, Object> {

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                    }

                    @Override
                    protected Object doInBackground(Object... params) {
                        FeedBackReq feedBackReq = new FeedBackReq();
                        feedBackReq.KEY = LoginInfo.KEY;
                        feedBackReq.helpful = fbRate;
                        feedBackReq.topic = fbTopic;
                        feedBackReq.prep = fbPrepare;
                        feedBackReq.course = fbCourse;

                        FeedBackRes feedBackRes = (FeedBackRes) new Webutil().webRequest(feedBackReq);

                        if(feedBackRes.success == true){
                            DeleteFeedbackReq deleteFeedbackReq = new DeleteFeedbackReq();
                            deleteFeedbackReq._id = id;
                            deleteFeedbackReq.KEY = LoginInfo.KEY;
                            deleteFeedbackReq.username = LoginInfo.username;

                            DeletFeedBackRes deletFeedBackRes = (DeletFeedBackRes) new Webutil().webRequest(deleteFeedbackReq);

                            Intent mIntent = new Intent(Feedback_form.this, FeedbackList.class);
                            startActivity(mIntent);
                            finish();
                        }
                        return feedBackRes;
                    }
                }
                new NetworkCallTask().execute(new Object());
            }
        });
    }

    public void onBackPressed(){
        Intent mIntent = new Intent(this, FeedbackList.class);
        startActivity(mIntent);
        finish();
    }
}
