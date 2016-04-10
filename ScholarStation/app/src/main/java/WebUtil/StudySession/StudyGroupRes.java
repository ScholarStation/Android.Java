package WebUtil.StudySession;

import WebUtil.WebRequest;
import WebUtil.WebResponse;

/**
 * Created by Branden on 3/30/2016.
 */
public class StudyGroupRes extends WebResponse{
    public StudyGroup[] studyGroups;

    public String toString(){
        String returnS = "";
        for(StudyGroup item : studyGroups) {
            returnS += item.toString();
        }
        return returnS;
    }
}
