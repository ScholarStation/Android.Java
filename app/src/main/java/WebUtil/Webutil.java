package WebUtil;


import com.google.gson.Gson;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import WebUtil.Login.LoginReq;
import WebUtil.Login.LoginRes;
import WebUtil.Profile.ProfileRes;
import WebUtil.Profile.ProfileReq;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



/**
 * Created by bjc90_000 on 3/20/2016.
 */
public class Webutil {

    private  String Login = "http://70.187.52.39:3000/LoginApp";
    private  String ProfileReq = "http://70.187.52.39:3000/ProfileApp";
    private  String ProfileEdt = "http://70.187.52.39:3000/ProfileUtility/EditByID";//not implemented
    private  String StudyReq = "http://70.187.52.39:3000/StudyUtility/GetStudyGroupsByMember";
    private  String StudyCrt = "http://70.187.52.39:3000/StudyUtility/Create";
    private  String StudyEdt = "http://70.187.52.39:3000/StudyUtility/EditByID";
    private  String StudyDel = "http://70.187.52.39:3000/StudyUtility/DeleteByID";
    private  String StudySrc = "...";//not implemented

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
        }


        try {
            StringEntity gsonString = new StringEntity(gson.toJson(payload));
            post.setEntity(gsonString);
            post.setHeader("Content-type", "application/json");
            HttpResponse response = client.execute(post);
            //System.out.println();
            return gson.fromJson(new BufferedReader(new InputStreamReader(response.getEntity().getContent())).readLine(),returnType.getClass());

        } catch (IOException e) {
            e.printStackTrace();
        }


        return "asdasd";
    }
}
