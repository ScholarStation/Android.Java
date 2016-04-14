package scholarstationandroid.scholarstation;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    //RaidoButton topicRB,courseRB,membersRB;
    int searchType=-1;
    StudyGroup[] studyGroupsList;
    EditText queryTextField;
    SGAdapter adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_search);

        adapter = new SGAdapter(this,studyGroupsList);
        listView = (ListView) findViewById(R.id.study_search_listView);
        listView.setAdapter(adapter);
        queryTextField =(EditText)findViewById(R.id.query_search);
        searchButton = (Button)findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchType==-1){
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
                            studyGroupsList= studyGroupRes.studyGroups;
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
        adapter.clear();adapter.addAll(studyGroupsList);

    }
    class SGAdapter extends ArrayAdapter<StudyGroup>{


        public SGAdapter(Context context, StudyGroup[] objects) {
            super(context, 0,objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            super.getView(position, convertView, parent);
            StudyGroup sg = getItem(position);
            if(convertView==null){
                convertView.inflate(getContext(),R.layout.study_group_array_adapter_layout,parent);
            }
            TextView topicView,courseView;
            topicView = (TextView)convertView.findViewById(R.id.studyCourse);
            courseView = (TextView)convertView.findViewById(R.id.studyCourse);
            topicView.setText(sg.topic);
            courseView.setText(sg.course);
            return convertView;
        }
    }

    public void onRaidoButtonClicked(View view){
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
}
