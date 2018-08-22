package hzyj.come.p2p.copy.dialog;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;



import java.util.ArrayList;

import hzyj.come.p2p.R;
import hzyj.come.p2p.app.utils.Tools;

/**
 * Created by Youga on 2015/11/6.
 */
public class LiveSelectDialog extends BaseDialog {
    private ListView lv_list;
    private TextView tv_title;
    private TextView tv_confirm;
    private TextView tv_cancel;
    private SexAdapter sexAdapter;
    private ArrayList<String> sexlist = new ArrayList<>();
    private int state;
    private Context context;
    private CustomOnClick onClick;
    public LiveSelectDialog(Context context, ArrayList<String> sexlist, int state, CustomOnClick onClick) {
        super(context);
        this.context = context;
        this.state = state;
        this.onClick = onClick;
        if (Tools.isEmpty(sexlist)) {
            this.sexlist.add("保密");
            this.sexlist.add("男");
            this.sexlist.add("女");
        }else{
            this.sexlist = sexlist;
        }
       
        init();
    }

    private void init() {
        setContentView(R.layout.selectdialog);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
     
        sexAdapter = new SexAdapter(context,sexlist,this.state);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_confirm = (TextView) findViewById(R.id.tv_confirm);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        lv_list = (ListView) findViewById(R.id.lv_list);
        lv_list.setAdapter(sexAdapter);
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                state = position;
                sexAdapter.setState(position);
            }
        });
        tv_title.setText("温馨提示");
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.click(v,state);
                dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setTitle(String title) {
        tv_title.setText(title);
    }



    public View getConfirm() {
        return tv_confirm;
    }

    public View getCancel() {
        return tv_cancel;
    }

    public void setCancelText(String str) {
        tv_cancel.setText(str);
    }

    public void setConfirmText(String str) {
        tv_confirm.setText(str);
    }
}
