package scholarstationandroid.scholarstation;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends Activity {

    private List<StudyGroup> studyGroups;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_study_group);

        rv=(RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();
    }

    private void initializeData(){
        studyGroups = new ArrayList<>();
        studyGroups.add(new StudyGroup("Emma Wilson", "23 years old", R.drawable.fab_button));
        studyGroups.add(new StudyGroup("Lavery Maiss", "25 years old", R.drawable.ic_menu_camera));
        studyGroups.add(new StudyGroup("Lillie Watts", "35 years old", R.drawable.ic_menu_send));
    }

    private void initializeAdapter(){
        StudyCardAdaptor adapter = new StudyCardAdaptor(studyGroups);
        rv.setAdapter(adapter);
    }
}
