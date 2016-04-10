package scholarstationandroid.scholarstation;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import WebUtil.Login.LoginReq;
import WebUtil.Login.LoginRes;
import WebUtil.StudySession.StudyGroupReq;
import WebUtil.StudySession.StudyGroupRes;
import WebUtil.Webutil;
import layout.LoginInfo;
import android.app.Fragment;
import WebUtil.StudySession.StudyGroup;


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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(
                    new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            CreateStudyFrag details = new CreateStudyFrag();
                            details.setArguments(getIntent().getExtras());

                           // ft.replace(R.id.createStudyFrag, details);
                            ft.commit();

                        }
                    }
            );
        }

        class NetworkCallTask extends AsyncTask<Object, Object, Object> {



            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object... params) {
                System.out.println("MAKING A WEB REQUEST FOR STUDY GROUPS *******************************");
                StudyGroupRes studyGroupRes;
                StudyGroupReq studyGroupReq = new StudyGroupReq();

                studyGroupReq.username=LoginInfo.username;
                studyGroupReq.KEY= LoginInfo.KEY;

                studyGroupRes = (StudyGroupRes) new Webutil().webRequest(studyGroupReq);
                System.out.println("GOT STUDY GTOUPS!!!! !!! !!! !!! !!! !!! !!! !!! !! ! ! ! ! ! ! ! ! ! ! !"+studyGroupRes.toString());
                return studyGroupRes;
            }

            @Override
            protected void onProgressUpdate(Object... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPostExecute(Object o) {

                StudyGroupRes studyGroupRes = (StudyGroupRes)o;
                try {
                    System.out.println("Gotten study Groups"+studyGroupRes.toString());

                    for(WebUtil.StudySession.StudyGroup sg: studyGroupRes.studyGroups){
                        System.out.println("ADDING THEM TO THE LIST ");
                        groupList.add(sg);
                        SGAdapter.notifyDataSetChanged();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        new NetworkCallTask().execute(new Object());

        /*Rview.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), Rview, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                StudyGroup group = groupList.get(position);
                Toast.makeText(getApplicationContext(), group.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));*/

       // prepareStudyData();
    }

    public void setAddGroupButton(){

    }

}


