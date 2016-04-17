package WebUtil.Reminder;

import java.util.Arrays;
import WebUtil.WebResponse;

/**
 * Created by Jason on 4/15/2016.
 */
//Currently not needed
public class ReminderRes extends WebResponse {
    public Reminders[] reminder;

    @Override
    public String toString(){
        String returnS = "";
        for(Reminders item : reminder) {
            returnS += item.toString();
        }
        return returnS;
    }
}
