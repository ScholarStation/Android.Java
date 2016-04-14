package scholarstationandroid.scholarstation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import WebUtil.Login.LoginReq;
import WebUtil.Login.LoginRes;
import WebUtil.StudySession.DeleteStudyReq;
import WebUtil.StudySession.DeleteStudyRes;
import WebUtil.StudySession.StudyGroupReq;
import WebUtil.StudySession.StudyGroupRes;
import WebUtil.Webutil;
import layout.LoginInfo;
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
                            Intent i = new Intent(StudyGroupActivity.this, CreateStudyGroup.class);
                            startActivity(i);
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
                        System.out.println("GROUP ID IS " + sg._id);
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
    class SGAdapter extends RecyclerView.Adapter<SGAdapter.MyViewHolder> {
        private List<StudyGroup> groupList;
        StudyGroup group;


        int Posistion;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title, subject, date;
            private View v;
            public View getView(){
                return v;
            }

            public MyViewHolder(View v) {
                super(v);
                this.v=v;

                title = (TextView) v.findViewById(R.id.title);
                subject = (TextView) v.findViewById(R.id.subject);
                date = (TextView) v.findViewById(R.id.date);
            }
        }

        public SGAdapter(List<StudyGroup> groupList) {
            this.groupList = groupList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.studygroup_list_row, parent, false);



            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {

            holder.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View itemView) {

                    Intent myIntent = new Intent(StudyGroupActivity.this , ViewStudyGroup.class);
                    Bundle bundle = new Bundle();
                    myIntent.putExtra("ViewGroupInfo", new Gson().toJson(groupList.get(position)));
                    myIntent.putExtras(bundle);
                    startActivity(myIntent);

                }
            });

            holder.getView().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View itemView) {

                    final CharSequence[] selectors = {"Edit", "Delete"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    builder.setTitle("Choose an action:");
                    AlertDialog.Builder builder1 = builder.setItems(selectors, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                System.out.println("Got to edit++++++++++++++++++++++++++++++++++");

                            } else {
                                //System.out.println("This is group ID " + groupList.get(viewType)._id + "=========================");
                                class NetworkCallTask extends AsyncTask<Object, Object, Object> {
                                    @Override
                                    protected void onPreExecute() {
                                        super.onPreExecute();
                                    }

                                    @Override
                                    protected Object doInBackground(Object... params) {
                                        DeleteStudyReq req = new DeleteStudyReq();
                                        req._id = groupList.get(position)._id;
                                        req.KEY = LoginInfo.KEY;
                                        req.username = LoginInfo.username;
                                        DeleteStudyRes deleteStudyRes = (DeleteStudyRes) new Webutil().webRequest(req);
                                        return deleteStudyRes;

                                    }

                                    @Override
                                    protected void onProgressUpdate(Object... values) {
                                        super.onProgressUpdate(values);
                                    }

                                    @Override
                                    protected void onPostExecute(Object o) {
                                        try {
                                            System.out.println("We Deleted IT==========================================");

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                new NetworkCallTask().execute(new Object());

                            }

                        }
                    });

                    builder.show();
                    return true;
                }
            });
            StudyGroup group = groupList.get(position);

            holder.title.setText(group.topic);
            holder.subject.setText(group.course);
            holder.date.setText(group.date);
        }

        @Override
        public int getItemCount() {
            return groupList.size();
        }


    }
}


