package hzyj.come.p2p.app.https.config;



public interface NetWorkConstant {

    String host_value = "http:192.168.1.180:8080/";
    String appUserId = "appUserId";
    

    String token_key = "token";
    String token_value = "43378e1b35ae7858e82eba2b27ddefd7";
    String m_key = "&m=";
    String a_key = "&a=";
    String type_key = "type";

    String uuid_key = "uuid";
  
    String shopid_key = "shopid";
    String groupid_key = "GROUP_ID";
    String eid_key = "eid";
    String surperid_key = "superid";
    String managerid_key = "MANAGER_ID";

    String pagetype_key = "pagetype";
    String pagetime_key = "pagetime";
    String page_key = "page";

    String pagenumber_key = "pagenumber";
    String pagenumber_value = "10";

    String orderid_key = "orderid";
    String order_m_value = "OrderManager";
    String getOrder_m_values = "Order";
    String type_up = "up";
    String type_down = "down";
    String type_null = "null";
    String appUserAccount = "appUserAccount";
    String appPassword = "appPassword";

    int SUCCESS = 1;

    int ACCOUNT_EXCEPTION = 999;

    //注册
    String app_user_regist = host_value + "app_user_regist";
    //登录
    String app_login = host_value + "app_login";
    
    //上传头像 
    String app_upload_img = host_value + "app_upload_img";
    //修改昵称 //性别
    String app_edit_info = host_value + "app_edit_info";
    //添加收货地址
    String app_add_address = host_value + "app_add_address";
    //查询收货地址
    String get_address_list = host_value + "app_select_address";
    //编辑收货地址
    String app_edit_address = host_value + "app_edit_address";
    //删除收货地址
    String app_delete_address = host_value + "app_delete_address";
   
}
