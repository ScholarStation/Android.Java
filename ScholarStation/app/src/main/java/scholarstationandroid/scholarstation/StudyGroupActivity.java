package scholarstationandroid.scholarstation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import WebUtil.Login.LoginReq;
import WebUtil.Login.LoginRes;
import WebUtil.StudySession.StudyGroupRes;
import WebUtil.Webutil;
import layout.LoginInfo;

public class StudyGroupActivity extends AppCompatActivity {

    private List<StudyGroup> groupList = new ArrayList<>();
    private RecyclerView Rview;
    private Adapter SGAdapter;
    private List<StudyGroup> mStudyGroup;

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

        class NetworkCallTask extends AsyncTask<Object, Object, Object> {



            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object... params) {


                return null;
            }

            @Override
            protected void onProgressUpdate(Object... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPostExecute(Object o) {
                try {
                    //System.out.println("UserName :: " + userName);
                    // System.out.println("Password :: " + passWord);

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
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
    /*
    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener
    {

        private GestureDetector gestureDetector;
        private MainActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MainActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildLayoutPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildLayoutPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }*/
}


