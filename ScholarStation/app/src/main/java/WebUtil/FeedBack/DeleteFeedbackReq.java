package WebUtil.FeedBack;

import WebUtil.WebRequest;

/**
 * Created by Jason on 4/17/2016.
 */
public class DeleteFeedbackReq extends WebRequest {

    public String _id;

    @Override
    public String toString() {
        return "DeleteFeedbackReq{" +
                "_id='" + _id + '\'' +
                '}';
    }
}
