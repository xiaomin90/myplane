package cn.minjs.com.myplane.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;
import cn.minjs.com.myplane.bean.ZhihuDailyNews;
import cn.minjs.com.myplane.interfaze.OnRecyclerViewOnClickListener;
import cn.minjs.com.myplane.R;

/**
 * Created by js.min on 2017/6/8.
 * mail : js.min@flyingwings.cn
 */

public class ZhihuDailyNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<ZhihuDailyNews.Question> mQuestionsList;
    private LayoutInflater mLayoutInflater;

    private OnRecyclerViewOnClickListener mOnRecyclerViewOnClickListener;

    public ZhihuDailyNewsAdapter(Context context, List<ZhihuDailyNews.Question> list) {
        this.mContext = context;
        this.mQuestionsList = list;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = new NormalViewHolder(mLayoutInflater.inflate(R.layout.home_list_item_layout,parent,false),mOnRecyclerViewOnClickListener);
        switch (viewType) {
            case  0:
                viewHolder =  new NormalViewHolder(mLayoutInflater.inflate(R.layout.home_list_item_layout,parent,false),mOnRecyclerViewOnClickListener);
                break;
            default:
                break;
        }

        return viewHolder;
    }


    public void setItemClickListener(OnRecyclerViewOnClickListener listener) {
        this.mOnRecyclerViewOnClickListener = listener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof  NormalViewHolder) {
                ZhihuDailyNews.Question item = this.mQuestionsList.get(position);
                if(item.getImages().get(0) == null) {
                    ((NormalViewHolder)holder).mImageView.setImageResource(R.drawable.placeholder);
                } else {
                    Glide.with(mContext)
                            .load(item.getImages().get(0))
                            .asBitmap()
                            .placeholder(R.drawable.placeholder)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .error(R.drawable.placeholder)
                            .centerCrop()
                            .into(((NormalViewHolder)holder).mImageView);
                }
                ((NormalViewHolder)holder).mTextView.setText(item.getTitle());
            }
    }

    @Override
    public int getItemCount() {
        return mQuestionsList.size();
    }

    public class NormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView mImageView;
        public TextView mTextView;
        private OnRecyclerViewOnClickListener mOnRecyclerViewOnClickListener;

        public NormalViewHolder(View itemView,OnRecyclerViewOnClickListener listener) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.imageViewCover);
            mTextView = (TextView) itemView.findViewById(R.id.textViewTitle);
            this.mOnRecyclerViewOnClickListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mOnRecyclerViewOnClickListener != null) {
                mOnRecyclerViewOnClickListener.OnItemClick(v,getLayoutPosition());
            }
        }
    }


}
