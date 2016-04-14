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

import WebUtil.FeedBack.FeedBackReq;
import WebUtil.FeedBack.FeedBackRes;
import WebUtil.StudySession.*;
import WebUtil.Webutil;
import layout.LoginInfo;

public class Feedback_form extends AppCompatActivity {

    FeedBackReq feedBackReq;
    String sendTopic = "";
    String helpfulness = "";
    boolean sendPrepared = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final RatingBar rateStudyGroup = (RatingBar) findViewById(R.id.ratingBar);
        final CheckBox prepareCheckBox = (CheckBox) findViewById(R.id.prepareCheck);
        final Button sendFeedbackButton = (Button) findViewById(R.id.sendFeedback);
        final EditText topic = (EditText) findViewById(R.id.topicEditText);


        setTitle("Feedback Form");

        assert topic != null;
        topic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do some thing now
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                sendTopic = topic.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do something at this time
            }
        });

        assert prepareCheckBox != null;
        prepareCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sendPrepared = true;
                }else{
                    sendPrepared = false;
                }

            }
        });

        assert rateStudyGroup != null;
        rateStudyGroup.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                helpfulness = String.valueOf(rating);
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
                        feedBackReq = new FeedBackReq();
                        feedBackReq.topicCovered = sendTopic;
                        feedBackReq.helpful = helpfulness;
                        feedBackReq.prepared = sendPrepared;
                        //feedBackReq.courses = ; //Get from reminders
                        FeedBackRes feedBackRes = (FeedBackRes) new Webutil().webRequest(feedBackReq);
                        if(feedBackRes.success == true){
                            Intent intent = new Intent(Feedback_form.this,Feedbacklist.class);
                            startActivity(intent);
                            finish();
                        }else{
                            System.out.println("Failed To Send FeedBack Form");
                        }
                        return feedBackRes;
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
