package hzyj.come.p2p.entity;

import java.io.Serializable;

/**
 * Created by EverGlow on 2018/8/10 14:48
 */

public    class EntitiyUser   implements Serializable {
    
    private  String appUserId;
    private String imgURL;
    private String appNickname;
    private int appSex;

    public int getAppSex() {
        return appSex;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getAppNickname() {
        return appNickname == null ? "" : appNickname;
    }

    public void setAppNickname(String appNickname) {
        this.appNickname = appNickname;
    }




    public void setAppUserId(String appUserId) {
        this.appUserId = appUserId;
    }

    public String getImgURL() {
        return imgURL == null ? "" : imgURL;
    }

 
    public String getAppUserId() {
        return appUserId == null ? "" : appUserId;
    }
}
