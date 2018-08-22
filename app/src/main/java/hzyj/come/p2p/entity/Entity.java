package hzyj.come.p2p.entity;

/**
 * Created by Youga on 2015/7/1.
 */
public class Entity {
    private int code;
    private String message;
    private String url;
    private String savepath;
    private String last_id;
    private int a0;
    private String creditsid;

    public String getCreditsid() {
        return creditsid == null ? "" : creditsid;
    }

    private int a1;
    private String signplaceid;
    private String orderauthid;

    public String getOrderauthid() {
        return orderauthid == null ? "" : orderauthid;
    }

    public void setOrderauthid(String orderauthid) {
        this.orderauthid = orderauthid;
    }

    public Entity() {
    }

    public Entity(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getA0() {
        return a0;
    }

    public int getA1() {
        return a1;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }

    public String getSavepath() {
        return savepath;
    }

    public String getLast_id() {
        return last_id;
    }

    public String getSignplaceid() {
        return signplaceid;
    }
}
