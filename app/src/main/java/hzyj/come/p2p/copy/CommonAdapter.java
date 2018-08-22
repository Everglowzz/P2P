package hzyj.come.p2p.copy;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public abstract class CommonAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> mTList = new ArrayList<>();

    @Override
    public int getItemCount() {
        return mTList.size();
    }

    public Object getItem(int position) {
        return mTList.get(position);
    }


    public Object getFirstItem() {
        return getItem(0);
    }


    public Object getLastItem() {
        return getItem(getItemCount() - 1);
    }

    public void requestNew(List<T> dataList) {
        if (dataList == null || dataList.size() == 0) return;
        this.mTList.addAll(0, dataList);
        notifyDataSetChanged();
    }

    public void refresh(List<T> dataList) {
        if (dataList == null) return;
        this.mTList.clear();
        this.mTList.addAll(dataList);
        notifyDataSetChanged();
    }
    
    
    public void clearData() {
        mTList.clear();
        notifyDataSetChanged();
    }

    public void loadMore(List<T> dataList) {
        if (dataList == null || dataList.size() == 0) return;
        this.mTList.addAll(mTList.size(), dataList);
        notifyDataSetChanged();
    }

    public List<T> getDataList() {
        return mTList;
    }

/* 使用代码 

    class InnerAdapter extends CommonAdapter<BaseBean> {


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), R.layout.item_marriage_circle, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;
            bindPosition(viewHolder,position);

        }
        private void bindPosition(ViewHolder viewHolder ,int position){

        }
    }

    class  ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    */
}
