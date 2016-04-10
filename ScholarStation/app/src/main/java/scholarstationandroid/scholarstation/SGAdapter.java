package scholarstationandroid.scholarstation;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import WebUtil.StudySession.DeleteStudyReq;
import WebUtil.StudySession.DeleteStudyRes;
import WebUtil.StudySession.StudyGroup;
import WebUtil.StudySession.StudyGroupRes;
import WebUtil.WebResponse;
import WebUtil.Webutil;
import layout.LoginInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonat on 4/9/2016.
 */
public class SGAdapter extends RecyclerView.Adapter<SGAdapter.MyViewHolder> {
    private List<StudyGroup> groupList;
    StudyGroup group;

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
    public MyViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.studygroup_list_row, parent, false);
        if (itemView != null) itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View itemView) {
                System.out.println("Button Works!");
            }
        });

        if (itemView != null) itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View itemView) {
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
                                    req._id = groupList.get(viewType)._id;
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
        return new MyViewHolder(itemView);
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
