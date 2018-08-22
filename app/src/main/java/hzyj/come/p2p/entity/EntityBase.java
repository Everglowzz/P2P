package hzyj.come.p2p.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by EverGlow on 2018/8/10 14:49
 */

public    class EntityBase   {
    
    private int result;

    private String msg;
    
    
    public int getResult() {
        return result;
    }

    public String getMsg() {
        return msg==null?"":msg;
    }
    
    private  EntitiyUser appUsers;

    public EntitiyUser getAppUsers() {
        return appUsers == null ? new EntitiyUser():appUsers;
    }
    
    private ArrayList<appShippingAddress> appShippingAddressList;

    public ArrayList<appShippingAddress> getAppShippingAddressList() {
        return appShippingAddressList;
    }
}
