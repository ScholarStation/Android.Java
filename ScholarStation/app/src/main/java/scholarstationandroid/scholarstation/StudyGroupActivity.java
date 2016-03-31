package scholarstationandroid.scholarstation;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * Created by jonat on 3/30/2016.
 */

public class StudyGroupActivity extends Activity {

    TextView personName;
    TextView personAge;
    ImageView personPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_study_group);
        personName = (TextView)findViewById(R.id.person_name);
        personAge = (TextView)findViewById(R.id.person_age);
        personPhoto = (ImageView)findViewById(R.id.person_photo);

        personName.setText("Jimmy Dean");
        personAge.setText("45 years old");
        personPhoto.setImageResource(R.drawable.fab_button);
    }
}
