package scholarstationandroid.scholarstation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import WebUtil.StudySession.StudyGroup;

import java.util.List;

/**
 * Created by jonat on 4/9/2016.
 */
public class SGAdapter extends RecyclerView.Adapter<SGAdapter.MyViewHolder>
{
    private List<StudyGroup> groupList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subject, date;

        public MyViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            subject = (TextView) v.findViewById(R.id.subject);
            date = (TextView) v.findViewById(R.id.date);
        }
    }

    public SGAdapter(List<StudyGroup> groupList) {
        this.groupList = groupList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.studygroup_list_row, parent, false);
        if(itemView != null) itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View itemView) {
                System.out.println("Button Works!");
            }
        });

        if(itemView != null) itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View itemView) {
                System.out.println("Long Button Works!");
                return  true;
            }
        });
        return new MyViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

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
