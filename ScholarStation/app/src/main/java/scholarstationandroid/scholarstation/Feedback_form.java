package scholarstationandroid.scholarstation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;

public class Feedback_form extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final RatingBar rateStudyGroup = (RatingBar) findViewById(R.id.ratingBar);
        final CheckBox prepareCheckBox = (CheckBox) findViewById(R.id.prepareCheck);
        final Button sendFeedbackButton = (Button) findViewById(R.id.sendFeedback);

        setTitle("Feedback Form");




    }

}
