package scholarstationandroid.scholarstation;


import android.content.DialogInterface;
import android.content.Intent;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import WebUtil.StudySession.DeleteStudyReq;
import WebUtil.StudySession.DeleteStudyRes;
import WebUtil.StudySession.StudyGroupReq;
import WebUtil.StudySession.StudyGroupRes;
import WebUtil.Webutil;
import layout.LoginInfo;
import WebUtil.StudySession.StudyGroup;


public class StudyGroupActivity extends AppCompatActivity {

    private List<StudyGroup> groupList = new ArrayList<>();
    private RecyclerView rView;
    private Adapter SGAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Study Groups");
        setContentView(R.layout.activity_study_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rView = (RecyclerView) findViewById(R.id.recycler_view);
        final Button searchButton = (Button)findViewById(R.id.studySearchButton);
        SGAdapter = new SGAdapter(groupList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rView.setLayoutManager(mLayoutManager);
        rView.setItemAnimator(new DefaultItemAnimator());
        rView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rView.setAdapter(SGAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(
                    new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            Intent i = new Intent(StudyGroupActivity.this, CreateStudyGroup.class);
                            startActivity(i);
                            finish();
                        }
                    }
            );
        }

        assert searchButton != null;
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(StudyGroupActivity.this, StudySearchActivity.class);
                startActivity(myIntent);
                finish();
            }
        });

        class NetworkCallTask extends AsyncTask<Object, Object, Object> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object... params) {return updateUI();}

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
                    System.out.println("NEW GROUP LIST IS !!!!!! "+groupList.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        new NetworkCallTask().execute(this);
    }

    class SGAdapter extends RecyclerView.Adapter<SGAdapter.MyViewHolder> {
        private List<StudyGroup> groupList;

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
                                Intent myIntent = new Intent(StudyGroupActivity.this , EditStudyGroup.class);
                                Bundle bundle = new Bundle();
                                myIntent.putExtra("EditGroupInfo", new Gson().toJson(groupList.get(position)));
                                myIntent.putExtras(bundle);
                                startActivity(myIntent);
                                finish();

                            } else {
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
                                        return updateUI() ;
                                    }

                                    @Override
                                    protected void onProgressUpdate(Object... values) {
                                        super.onProgressUpdate(values);
                                    }

                                    @Override
                                    protected void onPostExecute(Object o) {
                                        try {
                                            StudyGroupRes studyGroupRes = (StudyGroupRes)o;
                                            groupList=new ArrayList<>();
                                            try {
                                                for(WebUtil.StudySession.StudyGroup sg: studyGroupRes.studyGroups){
                                                    groupList.add(sg);
                                                    SGAdapter.notifyDataSetChanged();
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            StudyGroupActivity.this.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    SGAdapter.notifyDataSetChanged();
                                                    Snackbar snackbar = Snackbar.make(findViewById(R.id.studyGroupCoordinator),"Deleted Study Group",Snackbar.LENGTH_LONG);
                                                    snackbar.show();
                                                }
                                            });
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
    //Makes web req and updates the UI with new SG
    public StudyGroupRes updateUI(){

        StudyGroupRes studyGroupRes;
        StudyGroupReq studyGroupReq = new StudyGroupReq();
        studyGroupReq.username=LoginInfo.username;
        studyGroupReq.KEY= LoginInfo.KEY;
        studyGroupRes = (StudyGroupRes) new Webutil().webRequest(studyGroupReq);
        return studyGroupRes;
    }
}


