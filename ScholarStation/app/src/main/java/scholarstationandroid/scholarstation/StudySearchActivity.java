package scholarstationandroid.scholarstation;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import WebUtil.StudySession.*;
import WebUtil.Webutil;
import layout.LoginInfo;

public class StudySearchActivity extends AppCompatActivity {


    Button searchButton;
    int searchType=-1;
    ArrayList<StudyGroup> studyGroupsList = new ArrayList<>();
    final Context context = this;
    EditText queryTextField;
    SGAdapter adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_search);
        setTitle("Search Study Groups");

        adapter = new SGAdapter(this,studyGroupsList);
        listView = (ListView) findViewById(R.id.study_search_listView);
        listView.setAdapter(adapter);
        queryTextField =(EditText)findViewById(R.id.query_search);
        searchButton = (Button)findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchType==-1){
                    StudySearchActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Snackbar snackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout),"Select a search criteria",Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    });
                    //cant do search without raido buttons selected
                }else{
                    final String queryString = queryTextField.getText().toString();
                    //make web req and update UI
                    class NetworkCallTask extends AsyncTask<Object, Object, Object> {
                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                        }

                        @Override
                        protected Object doInBackground(Object... params) {
                            // declare and initinalize the webReq
                            SearchStudyReq searchStudyReq= new SearchStudyReq();
                            searchStudyReq.username=LoginInfo.username;
                            searchStudyReq.KEY=LoginInfo.KEY;
                            searchStudyReq.query=queryString;
                            searchStudyReq.filter=searchType;
                            //make web req
                            StudyGroupRes studyGroupRes = (StudyGroupRes)new Webutil().webRequest(searchStudyReq);
                            studyGroupsList=new ArrayList<StudyGroup>();
                            for(StudyGroup sg:studyGroupRes.studyGroups)
                                studyGroupsList.add(sg);
                            return params;
                        }

                        @Override
                        protected void onProgressUpdate(Object... values) {
                            super.onProgressUpdate(values);
                        }

                        @Override
                        protected void onPostExecute(Object o) {
                            updateUI();
                        }
                    }
                    new NetworkCallTask().execute(new Object());


                }


            }
        });

    }

    private void updateUI() {
        adapter.clear();
        adapter.addAll(studyGroupsList);


    }
    class SGAdapter extends ArrayAdapter<StudyGroup>{


        public SGAdapter(Context context, ArrayList<StudyGroup> objects) {

            super(context,R.layout.study_group_array_adapter_layout,objects);
            System.out.println();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
//            super.getView(position, convertView, parent);
            StudyGroup sg = getItem(position);
            if(convertView==null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.study_group_array_adapter_layout, parent, false);
                // convertView.inflate(getContext(),R.layout.study_group_array_adapter_layout,parent);
            }
            TextView topicView,courseView;
            topicView = (TextView)convertView.findViewById(R.id.studyCourse);
            courseView = (TextView)convertView.findViewById(R.id.studyCourse);
            if(studyGroupsList.get(0)!=null){
                topicView.setText(sg.topic);
                courseView.setText(sg.course);
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.join_study_group);
                    dialog.setTitle("Join Group");
                    Button yes = (Button) dialog.findViewById(R.id.yesButton);
                    Button no = (Button) dialog.findViewById(R.id.noButton);
                    dialog.show();
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            class NetworkCallTask extends AsyncTask<Object, Object, Object> {

                                @Override
                                protected void onPreExecute() {
                                    super.onPreExecute();
                                }

                                @Override
                                protected Object doInBackground(Object... params) {
                                    StudyJoinReq joinStudy = new StudyJoinReq();
                                    joinStudy.username = LoginInfo.username;
                                    joinStudy.KEY = LoginInfo.KEY;
                                    joinStudy.newMember = LoginInfo.username;
                                    joinStudy._id = studyGroupsList.get(position)._id;
                                    StudyJoinRes joinRes = (StudyJoinRes) new Webutil().webRequest(joinStudy);
                                    if(joinRes.success = true){
                                        System.out.println("!!!!!!! JOINED THE STUDY GROUP !!!!!!");
                                        Intent myIntent = new Intent(StudySearchActivity.this, StudyGroupActivity.class);
                                        startActivity(myIntent);
                                        finish();
                                    }else{
                                        System.out.println("!!!!!!!!! YOU DID NOT JOIN THE STUDY GROUP !!!!!!!!");
                                    }
                                    return joinRes;
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
            });

            return convertView;
        }
    }

    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()){
            case R.id.course_search:
                if(checked){
                    //course search
                    searchType=0;
                }
                break;
            case R.id.topic_search:
                if(checked){
                    //topic search
                    searchType=1;
                }
                break;
            case R.id.members_search:
                if(checked){
                    //member search
                    searchType=2;
                }
                break;
        }

    }

    public void onBackPressed(){
        Intent mIntent = new Intent(this,StudyGroupActivity.class);
        startActivity(mIntent);
        finish();
    }
}
