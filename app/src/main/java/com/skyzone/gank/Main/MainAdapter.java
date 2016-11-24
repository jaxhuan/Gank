package com.skyzone.gank.Main;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.skyzone.gank.App;
import com.skyzone.gank.Data.Bean.Info;
import com.skyzone.gank.R;
import com.skyzone.gank.Util.DateUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Skyzone on 11/24/2016.
 */
public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 0x01;

    private static final int VIEW_TYPE_HAS_PHOTO = 0x00;

    private List<Info> mInfos;
    private LayoutInflater mInflater;

    private InfoItemListener mItemListener;

    public MainAdapter(Context context, List<Info> infos) {
        mInfos = infos;
        mInflater = LayoutInflater.from(context);
    }

    public void refresh(List<Info> infos) {
        this.mInfos = infos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return mInfos.get(position).getImages().size() == 0 ? VIEW_TYPE_NORMAL : VIEW_TYPE_HAS_PHOTO;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new mViewHolder(mInflater.inflate(R.layout.adapter_main_item_normal, parent, false));
            case VIEW_TYPE_HAS_PHOTO:
                return new mViewHolderHasPhoto(mInflater.inflate(R.layout.adapter_main_item_photo, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof mViewHolder) {
            ((mViewHolder) holder).BindItem(mInfos.get(position));
        } else if (holder instanceof mViewHolderHasPhoto) {
            ((mViewHolderHasPhoto) holder).BindItem(mInfos.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mInfos.size();
    }

    class mViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.adapter_main_item_normal_root_view)
        View rootView;
        @BindView(R.id.adapter_main_item_normal_title)
        TextView title;
        @BindView(R.id.adapter_main_item_normal_author)
        TextView author;
        @BindView(R.id.adapter_main_item_normal_time)
        TextView time;

        public mViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void BindItem(final Info info) {
            title.setText(info.getDesc());
            author.setText(info.getWho());
            time.setText(DateUtil.parseDate(info.getPublishedAt()));

            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemListener.onInfoClick(info);
                }
            });
        }
    }

    class mViewHolderHasPhoto extends RecyclerView.ViewHolder {
        @BindView(R.id.adapter_main_item_photo_root_view)
        View rootView;
        @BindView(R.id.adapter_main_item_photo_view_pager)
        ViewPager imgs;
        @BindView(R.id.adapter_main_item_photo_title)
        TextView title;
        @BindView(R.id.adapter_main_item_photo_author)
        TextView author;
        @BindView(R.id.adapter_main_item_photo_time)
        TextView time;
        @BindView(R.id.adapter_main_item_photo_indicator)
        RadioGroup indicators;
        @BindView(R.id.adapter_main_item_photo_img)
        ImageView img;

        public mViewHolderHasPhoto(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void BindItem(final Info info) {
            title.setText(info.getDesc());
            author.setText(info.getWho());
            time.setText(DateUtil.parseDate(info.getPublishedAt()));

            Glide.with(App.mContext).load(info.getImages().get(0)).asBitmap().centerCrop().into(img);

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemListener.onInfoImgClick(info);
                }
            });
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemListener.onInfoClick(info);
                }
            });
        }
    }

    public void setItemListener(InfoItemListener itemListener) {
        mItemListener = itemListener;
    }

    interface InfoItemListener {

        void onInfoClick(Info info);

        void onInfoImgClick(Info info);
    }
}
