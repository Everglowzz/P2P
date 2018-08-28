package hzyj.come.p2p.copy.address;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import hzyj.come.p2p.R;
import hzyj.come.p2p.app.https.CallBack;
import hzyj.come.p2p.app.https.config.NetWorkConstant;
import hzyj.come.p2p.app.utils.ToastUtil;
import hzyj.come.p2p.copy.BaseActivity;
import hzyj.come.p2p.copy.CommonAdapter;
import hzyj.come.p2p.entity.EntityBase;
import hzyj.come.p2p.entity.appShippingAddress;
import hzyj.come.p2p.global.Constants;

public class AcAddressList extends BaseActivity {


    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.dragRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.rl_add_address)
    RelativeLayout mRlAddAddress;
    private InnerAdapter mInnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_address_list);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        mToolbarTitle.setText("常用地址");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRlAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AcAddressList.this, AcAddAddressActivity.class));
            }
        });
        mInnerAdapter = new InnerAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mInnerAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        requestNetWork();
    }

    public void requestNetWork() {
        mGsonRequest.function(NetWorkConstant.get_address_list, null, EntityBase.class, new CallBack<EntityBase>() {
            @Override
            public void onResponse(EntityBase userBean) {
                if (userBean.getResult() == Constants.SUCCESSCODE) {
                    if (userBean.getAppShippingAddressList() != null) {
                        mInnerAdapter.refresh(userBean.getAppShippingAddressList());
                    }

                } else {
                    ToastUtil.showMessage(userBean.getMsg());
                }
            }

            @Override
            public void onFailure(String error) {
                ToastUtil.showMessage(error);
            }
        });
    }

    public void deleteAdress(String addressId) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("appAddressId", addressId);
        mGsonRequest.function(NetWorkConstant.app_delete_address, params, EntityBase.class, new CallBack<EntityBase>() {
            @Override
            public void onResponse(EntityBase bean) {
                if (bean.getResult() == Constants.SUCCESSCODE) {
                    requestNetWork();
                }
                ToastUtil.showMessage(bean.getMsg());
            }

            @Override
            public void onFailure(String error) {
                ToastUtil.showMessage(error);
            }
        });
    }

    class InnerAdapter extends CommonAdapter<appShippingAddress> {



        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.acaddress_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;
            bindPosition(viewHolder, position);

        }

        private void bindPosition(ViewHolder viewHolder, int position) {
            viewHolder.mLine.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
            final appShippingAddress data = mTList.get(position);
            viewHolder.mTvName.setText(data.getAppConsignee());
            viewHolder.mTvPhone.setText(data.getAppPhone());
            viewHolder.mTvAddress.setText(data.getAppAddress()+data.getAppAddressInfo());
            viewHolder.mTvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteAdress(data.getAppAddressId());
                }
            });
            viewHolder.mTvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AcAddressList.this, AcAddAddressActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constants.DATA, data);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
       
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.line)
        View mLine;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_phone)
        TextView mTvPhone;
        @BindView(R.id.tv_address)
        TextView mTvAddress;
        @BindView(R.id.tv_edit)
        TextView mTvEdit;
        @BindView(R.id.tv_delete)
        TextView mTvDelete;
        @BindView(R.id.address_item)
        LinearLayout mAddressItem;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
