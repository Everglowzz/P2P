package hzyj.come.p2p.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by EverGlow on 2018/3/14 16:28
 */

public    class BaseBean {
    public BaseBean() {
    }

    public int code;
    public String msg;
    public String name;
    public String savepath;
    public String head_pic;
    public String price_count;
    public String img;
    public String url;
    public String order_info;

    public String getOrder_info() {
        return order_info == null ? "" : order_info;
    }

    public Dada result;

    public Dada getResult() {
        return result;
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public String getImg() {
        return img == null ? "" : img;
    }

    public String getPrice_count() {
        return price_count == null ? "" : price_count;
    }

    public ArrayList<Deposit> result_deposit;

    public ArrayList<Deposit>getResult_deposit() {
        return result_deposit;
    }

    public String getHead_pic() {
        return head_pic == null ? "" : head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public String getSavepath() {
        return savepath == null ? "" : savepath;
    }

    public void setSavepath(String savepath) {
        this.savepath = savepath;
    }

    public int size;

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return  size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public BaseBean(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return  code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public ArrayList<Addresslist> addressList;

    public ArrayList<Addresslist> getAddressList() {
        return addressList;
    }

   
    
    public  static class Addresslist implements Serializable{


        private String consignee;
        private String address;
        private String mobile;
        private String address_id;
        private String is_default;
        private String province_name;
        private String city_name;
        private String district_name;

        public String getConsignee() {
            return consignee == null ? "" : consignee;
        }

        public String getAddress() {
            return address == null ? "" : address;
        }

        public String getMobile() {
            return mobile == null ? "" : mobile;
        }

        public String getAddress_id() {
            return address_id == null ? "" : address_id;
        }

        public String getIs_default() {
            return is_default == null ? "" : is_default;
        }

        public String getProvince_name() {
            return province_name == null ? "" : province_name;
        }

        public String getCity_name() {
            return city_name == null ? "" : city_name;
        }

        public String getDistrict_name() {
            return district_name == null ? "" : district_name;
        }
    }
    
    public static class Deposit implements Serializable, Parcelable {
       
        private int id;
        private int status;
        private String package_id;
        private String price;
        private String acreage;
        private boolean isSelect;
        private boolean isDepostiSelect;

        public void setDepostiSelect(boolean depostiSelect) {
            isDepostiSelect = depostiSelect;
        }

        public boolean isDepostiSelect() {
            return isDepostiSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public int getId() {
            return  id;
        }

        public int getStatus() {
            return  status;
        }

        public String getPackage_id() {
            return package_id == null ? "" : package_id;
        }

        public String getPrice() {
            return price == null ? "" : price;
        }

        public String getAcreage() {
            return acreage == null ? "" : acreage;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeInt(this.status);
            dest.writeString(this.package_id);
            dest.writeString(this.price);
            dest.writeString(this.acreage);
        }

        public Deposit() {
        }

        protected Deposit(Parcel in) {
            this.id = in.readInt();
            this.status = in.readInt();
            this.package_id = in.readString();
            this.price = in.readString();
            this.acreage = in.readString();
        }

        public static final Creator<Deposit> CREATOR = new Creator<Deposit>() {
            @Override
            public Deposit createFromParcel(Parcel source) {
                return new Deposit(source);
            }

            @Override
            public Deposit[] newArray(int size) {
                return new Deposit[size];
            }
        };
    }
    private Contactlist contactlist;

    public Contactlist getContactlist() {
        return contactlist;
    }

    public static class Contactlist implements   Serializable{
        private String mobile;
        private String email;
        private String address;
        private double longitude;
        private double latitude;

        public double getLongitude() {
            return longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public String getMobile() {
            return mobile == null ? "" : mobile;
        }

        public String getEmail() {
            return email == null ? "" : email;
        }

        public String getAddress() {
            return address == null ? "" : address;
        }
        //        "mobile": "13720400145",
//                "email": "305768954@qq.com"
//                "address": "陕西省西安市沣东新城协同创协大
//                "longitude": 108.78,
//                "latitude": 34.26
    }
    private ShareInfo share_info;

    public ShareInfo getShare_info() {
        return share_info;
    }

    public static class ShareInfo{
       
        private  String title;
        private  String content;
        private  String icon;
        private  String url;

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content == null ? "" : content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getIcon() {
            return icon == null ? "" : icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getUrl() {
            return url == null ? "" : url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
    private ArrayList<Dada> data;

    public ArrayList<Dada> getData() {
        return data;
    }

    public static class Dada{
        private String goods_id;
        private String goods_name;
        private String original_img;
        private String appid;
        private String mch_id;
        private String prepay_id;
        private String package1;
        private String nonce_str;
        private String timestamp;
        private String sign;

        public String getSign() {
            return sign == null ? "" : sign;
        }

        public String getAppid() {
            return appid == null ? "" : appid;
        }

        public String getMch_id() {
            return mch_id == null ? "" : mch_id;
        }

        public String getPrepay_id() {
            return prepay_id == null ? "" : prepay_id;
        }

        public String getPackage1() {
            return package1 = "Sign=WXPay";
        }

        public String getNonce_str() {
            return nonce_str == null ? "" : nonce_str;
        }

        public String getTimestamp() {
            return timestamp == null ? "" : timestamp;
        }

        public String getGoods_id() {
            return goods_id == null ? "" : goods_id;
        }

        public String getGoods_name() {
            return goods_name == null ? "" : goods_name;
        }

        public String getOriginal_img() {
            return original_img == null ? "" : original_img;
        }
    }
    
    
}
