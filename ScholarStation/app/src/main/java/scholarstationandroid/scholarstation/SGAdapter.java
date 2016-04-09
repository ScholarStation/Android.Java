package scholarstationandroid.scholarstation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_study_group, parent, false);

        return new MyViewHolder (v);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        StudyGroup group = groupList.get(position);
        holder.title.setText(group.getTitle());
        holder.subject.setText(group.getSubject());
        holder.date.setText(group.getDate());
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }
}
