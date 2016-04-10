package scholarstationandroid.scholarstation;

import android.content.Context;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class StudyGroupActivity extends AppCompatActivity {

    private List<StudyGroup> groupList = new ArrayList<>();
    private RecyclerView Rview;
    private Adapter SGAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Rview = (RecyclerView) findViewById(R.id.recycler_view);

        SGAdapter = new SGAdapter(groupList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        Rview.setLayoutManager(mLayoutManager);
        Rview.setItemAnimator(new DefaultItemAnimator());
        Rview.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        Rview.setAdapter(SGAdapter);

        prepareStudyData();
    }
    private void prepareStudyData() {
        StudyGroup group = new StudyGroup("Sally's Study Group", "Biology", "2/14/2017");
        groupList.add(group);

        group = new StudyGroup("Jimbo Fisher", "Calculus", "03/27/2017");
        groupList.add(group);

        group = new StudyGroup("For Retards", "Software Engineering I", "9/05/2017");
        groupList.add(group);

        group = new StudyGroup("Chillaxin", "Exercise Science", "8/16/2017");
        groupList.add(group);

        group = new StudyGroup("COP1427 Exam 3", "Computer Science", "2/14/2017");
        groupList.add(group);

        SGAdapter.notifyDataSetChanged();
    }
    /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    });*/

}


