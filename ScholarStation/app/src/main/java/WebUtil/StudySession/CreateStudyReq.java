package WebUtil.StudySession;

import java.util.Arrays;

import WebUtil.WebRequest;

/**
 * Created by Branden on 4/12/2016.
 */
public class CreateStudyReq extends WebRequest{
    public String owner;
    public String course;
    public String topic;
    public String date;
    public String time;
    public Boolean publicView;
    public String[] members;

    @Override
    public String toString() {
        return "CreateStudyReq{" +
                "owner='" + owner + '\'' +
                ", course='" + course + '\'' +
                ", topic='" + topic + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", publicView=" + publicView +
                ", members=" + Arrays.toString(members) +
                '}';
    }

}

