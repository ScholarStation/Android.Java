package WebUtil.Reminder;

import WebUtil.WebResponse;

/**
 * Created by Jason on 4/17/2016.
 */
public class RemindersGetRes extends WebResponse {
    public Reminders[] reminders;

    @Override
    public String toString(){
        String returnS = "";
        for(Reminders item : reminders) {
            returnS += item.toString();
        }
        return returnS;
    }
}
