package com.makethings.infoplatform;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by elevenLianm on 2017/3/8.
 */

public class Publicdata {
    public static String session, uId, userName, uPicture, uGender, uPhone, uAddress, uEmail, uQq, uInterest, uDepartment, uAge,/*uJoinCom,uJoinAct,uCollectCom,uCollectAct,*/
            uCommBoss, uCommAdmin;
    public static List<String> uJoinCom =new ArrayList<String>();
    public static List<String> uJoinAct = new ArrayList<String>();
    public static List<String> uCollectCom = new ArrayList<String>();
    public static List<String> uCollectAct = new ArrayList<String>();

    public static Byte uPrivate;
    //static int uAge;
    public static JSONObject userCommunity, communityName, activityName, userBasic;
}
