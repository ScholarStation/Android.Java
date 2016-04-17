package WebUtil.FeedBack;

import WebUtil.WebRequest;

/**
 * Created by Jason on 4/13/2016.
 */
public class FeedBackReq extends WebRequest {
    public String helpful;
    public String topic;
    public String course;
    public boolean prep;

    @Override
    public String toString() {
        return "FeedBackReq{" +
                "helpful='" + helpful + '\'' +
                ", topic='" + topic + '\'' +
                ", course='" + course + '\'' +
                ", prep='" + prep + '\'' +
                '}';
    }
}
