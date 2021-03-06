package akiyama.mykeep.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import akiyama.mykeep.AppContext;
import akiyama.mykeep.R;
import akiyama.mykeep.common.DbConfig;
import akiyama.mykeep.db.model.RecordModel;
import akiyama.mykeep.util.DateUtil;
import akiyama.mykeep.util.LogUtil;
import akiyama.mykeep.util.ResUtil;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private static final String TAG="RecyclerAdapter";
   private List<RecordModel> mDataset;
   private OnItemClick mOnItemClick;
   public RecyclerAdapter(List<RecordModel> mDataset){
       this.mDataset=mDataset;
   }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_list, parent, false);
        final ViewHolder vh = new ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClick != null) {
                    mOnItemClick.onItemClick(v, vh.getPosition());
                }
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(mDataset!=null && mDataset.size()>0){
            RecordModel recordModel=mDataset.get(position);
            if(recordModel!=null){
                holder.mTitleTv.setText(recordModel.getTitle());
                String content = recordModel.getContent();
                //移除内容中特殊的标记
                if(recordModel.getRecordType() ==RecordModel.RECORD_TYPE_LIST){
                    content = content.replace(DbConfig.BIG_SPLIT_SYMBOL,"");
                    content = content.replace(DbConfig.SMAIL_SPLIT_SYMBOL,"");
                }
                holder.mSubTitleTv.setText(content);
                holder.mUpdateTv.setText(DateUtil.getDate(recordModel.getUpdateTime()));

                holder.mTitleTv.setTypeface(AppContext.getRobotoSlabBold());
                holder.mSubTitleTv.setTypeface(AppContext.getRobotoSlabLight());
                holder.mUpdateTv.setTypeface(AppContext.getRobotoSlabThin());
            }
        }


    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitleTv;
        public TextView mSubTitleTv;
        public TextView mUpdateTv;
        public ViewHolder(View v) {
            super(v);
            mTitleTv =(TextView) v.findViewById(R.id.title);
            mSubTitleTv =(TextView) v.findViewById(R.id.subtitle);
            mUpdateTv =(TextView) v.findViewById(R.id.update_time_tv);
        }
    }

    public void setOnItemClick(OnItemClick mOnItemClick) {
        this.mOnItemClick = mOnItemClick;
    }

    public void refreshDate(List<RecordModel> dataset){
        this.mDataset = dataset;
        notifyDataSetChanged();
    }
    /**
     * item接口点击回调
     */
    public interface OnItemClick{
        public void onItemClick(View v,int position);
    }
}


