package WebUtil.FeedBack;

import java.util.Arrays;

import WebUtil.WebResponse;

/**
 * Created by Jason on 4/13/2016.
 */
public class FeedBackRes extends WebResponse {
    public FeedBack[] feedBacks;

    @Override
    public String toString() {
        String returnS = "";
        for(FeedBack item : feedBacks){
            returnS += item.toString();
        }
        return returnS;
    }
}
