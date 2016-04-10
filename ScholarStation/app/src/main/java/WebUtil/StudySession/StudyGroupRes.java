package WebUtil.StudySession;

import WebUtil.WebRequest;

/**
 * Created by Branden on 3/30/2016.
 */
public class StudyGroupRes extends WebRequest{
    public StudyGroup[] studyGroups;

    public String ToString(){
        String returnS = "";
        for(StudyGroup item : studyGroups) {
            returnS += item.toString();
        }
        return returnS;
    }
}
