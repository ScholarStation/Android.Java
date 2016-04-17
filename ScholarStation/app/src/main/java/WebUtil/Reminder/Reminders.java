package WebUtil.Reminder;

import java.util.Date;

/**
 * Created by Jason on 4/15/2016.
 */
public class Reminders {
    public String _id;
    public String course;
    public Date date;

    @Override
    public String toString() {
        return "Reminders{" +
                "_id='" + _id + '\'' +
                ", course='" + course + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
