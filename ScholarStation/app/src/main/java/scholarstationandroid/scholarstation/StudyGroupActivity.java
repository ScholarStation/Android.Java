package scholarstationandroid.scholarstation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import WebUtil.StudySession.StudyGroup;

public class StudyGroupActivity extends AppCompatActivity {

    private RecyclerView Rview;
    private Adapter SGAdapter;
    private RecyclerView.LayoutManager SGLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Rview = (RecyclerView) findViewById(R.id.StudyGroup_recycler_view);
        Rview.setHasFixedSize(true);

        SGLayoutManager = new LinearLayoutManager(this);
        Rview.setLayoutManager(SGLayoutManager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
 class SGAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private StudyGroup[] SGList;

     public static class ViewHolder extends RecyclerView.ViewHolder {
         // each data item is just a string in this case ????????????????????????????????????
         public TextView mTextView;
         public ViewHolder(TextView v) {
             super(v);
             mTextView = v;
         }
     }

     public SGAdapter(StudyGroup[] SGList) {
         this.SGList = SGList;
     }



     @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         View v = LayoutInflater.from(parent.getContext())
                 .inflate(R.layout.content_study_group, parent, false);
         // set the view's size, margins, paddings and layout parameters

         ViewHolder vh = new ViewHolder((TextView) v);
         return vh;
    }


     @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
