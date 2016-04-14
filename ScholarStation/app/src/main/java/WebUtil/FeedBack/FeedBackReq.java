package WebUtil.FeedBack;

import WebUtil.WebRequest;

/**
 * Created by Jason on 4/13/2016.
 */
public class FeedBackReq extends WebRequest {
    public String helpful;
    public boolean prepared;
    public String topicCovered;
    public String courses;

    @Override
    public String toString() {
        return "FeedBack{" +
                "helpful='" + helpful + '\'' +
                ", prepared=" + prepared +
                ", topicCovered='" + topicCovered + '\'' +
                "courses='" + courses + '\'' +
                '}';
    }
}
