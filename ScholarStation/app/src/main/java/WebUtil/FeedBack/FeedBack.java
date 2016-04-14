package WebUtil.FeedBack;

import WebUtil.WebRequest;

/**
 * Created by Jason on 4/14/2016.
 */
public class FeedBack extends WebRequest {

    public String helpful;
    public boolean prepared;
    public String topicCovered;

    @Override
    public String toString() {
        return "FeedBack{" +
                "helpful='" + helpful + '\'' +
                ", prepared=" + prepared +
                ", topicCovered='" + topicCovered + '\'' +
                '}';
    }
}
