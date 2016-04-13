package scholarstationandroid.scholarstation;

/**
 * Created by jonat on 4/9/2016.
 */
public class StudyGroup {
    private String title;
    private String subject;
    private String date;

    public StudyGroup(String title, String subject, String date) {
        this.title = title;
        this.subject = subject;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

}
