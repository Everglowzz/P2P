package hzyj.come.p2p.app.https.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import javax.inject.Inject;

import hzyj.come.p2p.entity.EntitiyUser;

/**
 * Created by Youga on 2016/3/21.
 */
public class CommonPreferences {

    private final SharedPreferences mSharedPreferences;
    private EntitiyUser mUserForm;

    @Inject
    public CommonPreferences(Context app) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(app);
    }

    public <T> void setCommon(String key, T value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        }
        editor.apply();
    }

    public String getString(String key) {
        return mSharedPreferences.getString(key, "");
    }

    public boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    public int getInt(String key) {
        return mSharedPreferences.getInt(key, 0);
    }

    public long getLong(String key) {
        return mSharedPreferences.getLong(key, 0);
    }

    public <T> void setModel(T entity) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        
        try {
            ByteArrayOutputStream toByte = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(toByte);
            oos.writeObject(entity);
            // 对byte[]进行Base64编码
            String obj64 = new String(Base64.encode(toByte.toByteArray(), Base64.DEFAULT));
            editor.putString(entity.getClass().getSimpleName(), obj64);
            editor.apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getModel(Class clazz) {
        try {
            String obj64 = mSharedPreferences.getString(clazz.newInstance().getClass().getSimpleName(), "");
            if (TextUtils.isEmpty(obj64)) {
                return null;
            }
            byte[] base64Bytes = Base64.decode(obj64, Base64.DEFAULT);
            ByteArrayInputStream stream = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(stream);
            return (T) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cleanCache(String... keys) {
        HashMap<String, String> params = new HashMap<>();
        for (String key : keys) {
            params.put(key, mSharedPreferences.getString(key, ""));
        }
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        for (String key : keys) {
            editor.putString(key, params.get(key));
        }
        mUserForm = null;
        editor.apply();
    }

    public EntitiyUser getUserFrom() {
        return mUserForm != null ? mUserForm : getModel(EntitiyUser.class);
    }

 
}
