package WebUtil.StudySession;

import java.util.Arrays;

import WebUtil.WebRequest;

/**
 * Created by Branden on 3/30/2016.
 */
public class EditStudyReq extends WebRequest {

    public String owner;
    public String _id;
    public String course;
    public String topic;
    public String date;
    public String time;
    public Boolean publicView;
    public String[] members;

    @Override
    public String toString() {
        return "EditStudyReq{" +
                "owner='" + owner + '\'' +
                ", _id='" + _id + '\'' +
                ", course='" + course + '\'' +
                ", topic='" + topic + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", publicView=" + publicView +
                ", members=" + Arrays.toString(members) +
                '}';
    }


}
