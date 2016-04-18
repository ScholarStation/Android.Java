package WebUtil;


import android.os.AsyncTask;

import com.google.gson.Gson;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import WebUtil.FeedBack.DeletFeedBackRes;
import WebUtil.FeedBack.DeleteFeedbackReq;
import WebUtil.FeedBack.FeedBackReq;
import WebUtil.FeedBack.FeedBackRes;
import WebUtil.Login.CreateLoginReq;
import WebUtil.Login.CreateLoginRes;
import WebUtil.Login.LoginReq;
import WebUtil.Login.LoginRes;
import WebUtil.Profile.CreateProfileReq;
import WebUtil.Profile.CreateProfileRes;
import WebUtil.Profile.EditProfileReq;
import WebUtil.Profile.EditProfileRes;
import WebUtil.Profile.ProfileRes;
import WebUtil.Profile.ProfileReq;
import WebUtil.Reminder.RemindersGetReq;
import WebUtil.Reminder.RemindersGetRes;
import WebUtil.StudySession.CreateStudyReq;
import WebUtil.StudySession.CreateStudyRes;
import WebUtil.StudySession.DeleteStudyReq;
import WebUtil.StudySession.DeleteStudyRes;
import WebUtil.StudySession.EditStudyReq;
import WebUtil.StudySession.EditStudyRes;
import WebUtil.StudySession.SearchStudyReq;
import WebUtil.StudySession.StudyGroupReq;
import WebUtil.StudySession.StudyGroupRes;
import WebUtil.StudySession.StudyJoinReq;
import WebUtil.StudySession.StudyJoinRes;
import scholarstationandroid.scholarstation.EditProfile;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Future;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


/**
 * Created by bjc90_000 on 3/20/2016.
 */
public class Webutil {

    private  String Login = "http://70.187.52.39:3000/LoginUtility";
    private  String ProfileReq = "http://70.187.52.39:3000/ProfileUtility";
    private  String CreateLogin = "http://70.187.52.39:3000/LoginUtility/Create";
    private  String CreateProfile = "http://70.187.52.39:3000/ProfileUtility/Create";
    private  String ProfileEdit = "http://70.187.52.39:3000/ProfileUtility/EditByID";//not implemented
    private  String StudyReq = "http://70.187.52.39:3000/StudyUtility/GetStudyGroupsByMember";
    private  String StudyCrt = "http://70.187.52.39:3000/StudyUtility/Create";
    private  String StudyEdt = "http://70.187.52.39:3000/StudyUtility/EditByID";
    private  String StudyDel = "http://70.187.52.39:3000/StudyUtility/DeleteByID";
    private  String StudySrc = "http://70.187.52.39:3000/StudyUtility/Search";
    private  String StudyJoin = "http://70.187.52.39:3000/StudyUtility/JoinByID";//need path
    private String Feedback = "http://70.187.52.39:3000/FeedBackUtility/Create";
    private String FeedbackDel = "http://70.187.52.39:3000/FeedBackUtility/DeleteByID";
    private String RemindersGet = "http://70.187.52.39:3000/FeedBackUtility/GetReminders";

    public Object webRequest(WebRequest payload){

        Gson gson = new Gson();
        HttpPost post = new HttpPost();
        HttpClient client = new DefaultHttpClient();
        Object returnType =WebResponse.class;

        //determine the path
        if (payload instanceof LoginReq){
            post = new HttpPost(Login);
            returnType = new LoginRes();
        }else if (payload instanceof ProfileReq){
            post = new HttpPost(ProfileReq);
            returnType = new ProfileRes();
        }else if (payload instanceof CreateLoginReq){
            post = new HttpPost(CreateLogin);
            returnType = new CreateLoginRes();
        }else if (payload instanceof CreateProfileReq){
            post = new HttpPost(CreateProfile);
            returnType = new CreateProfileRes();
        }else if (payload instanceof EditProfileReq){
            post = new HttpPost(ProfileEdit);
            returnType = new EditProfileRes();
        }else if (payload instanceof StudyGroupReq){
            post = new HttpPost(StudyReq);
            returnType = new StudyGroupRes();
        }else if(payload instanceof DeleteStudyReq){
            post = new HttpPost(StudyDel);
            returnType = new DeleteStudyRes();
        }else if(payload instanceof CreateStudyReq){
            post = new HttpPost(StudyCrt);
            returnType= new CreateStudyRes();
        }else if(payload instanceof EditStudyReq){
            post = new HttpPost(StudyEdt);
            returnType= new EditStudyRes();
        }else if (payload instanceof SearchStudyReq){
            post = new HttpPost(StudySrc);
            returnType = new StudyGroupRes();
        }else if (payload instanceof StudyJoinReq){
            post = new HttpPost(StudyJoin);
            returnType = new StudyJoinRes();
        }else if (payload instanceof FeedBackReq){
            post = new HttpPost(Feedback);
            returnType = new FeedBackRes();
        }else if(payload instanceof DeleteFeedbackReq){
            post = new HttpPost(FeedbackDel);
            returnType = new DeletFeedBackRes();
        }else if(payload instanceof RemindersGetReq){
            post = new HttpPost(RemindersGet);
            returnType  = new RemindersGetRes();
        }

        try {
            StringEntity gsonString = new StringEntity(gson.toJson(payload));
            post.setEntity(gsonString);
            post.setHeader("Content-type", "application/json");
            HttpResponse response = client.execute(post);
            return gson.fromJson(new BufferedReader(new InputStreamReader(response.getEntity().getContent())).readLine(),returnType.getClass()) ;

        } catch (Exception e) {
            e.printStackTrace();
            return e;
        }
    }
}
