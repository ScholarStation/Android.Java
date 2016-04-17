package WebUtil.Profile;

import WebUtil.WebResponse;

/**
 * Created by bjc90_000 on 3/20/2016.
 */
public class ProfileRes extends WebResponse {
    public String fname;
    public String lname;
    public String major;
    public String email;
    public String gender;
    public String year;
    public int age;
    public String _id;

    @Override
    public String toString() {
        return "ProfileRes{" +
                "fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", major='" + major + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", year='" + year + '\'' +
                ", age=" + age + '\''+
                ", id= "+_id+
                '}';
    }
}
