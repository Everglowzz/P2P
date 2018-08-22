package hzyj.come.p2p.copy.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import hzyj.come.p2p.R;

/**
 * Created by Ailen on 2015/11/20.
 */
public class SexAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> list;
    private  int state;

    public SexAdapter(Context context, ArrayList<String> lists, int state) {
        this.mContext = context;
        this.list = lists;
        this.state = state;
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setList(ArrayList<String> lists) {
        this.list = lists;
        this.notifyDataSetChanged();
    }

    public void setState(int state) {
        this.state = state;
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        String bean = list.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_sex, null);
            holder.iv_sel = (ImageView) convertView.findViewById(R.id.iv_sel);
            holder.tv_sexname = (TextView) convertView.findViewById(R.id.tv_sexname);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_sexname.setText(bean);
        if(state==position){
            holder.iv_sel.setVisibility(View.VISIBLE);
        }else{
            holder.iv_sel.setVisibility(View.GONE);
        }

        return convertView;
    }

    class ViewHolder {
        ImageView iv_sel;
        TextView tv_sexname;
    }
}
