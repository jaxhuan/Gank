package com.skyzone.gank.MeiZi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.skyzone.gank.App;
import com.skyzone.gank.Data.Bean.MeiZi;
import com.skyzone.gank.R;
import com.skyzone.gank.Util.PlaceHolderDrawableHelper;
import com.skyzone.gank.View.DynamicHeightImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Skyzone on 11/30/2016.
 */
public class MeiZiAdapter extends RecyclerView.Adapter<MeiZiAdapter.mViewHolder> {

    private List<MeiZi> mMeiZiList;

    public MeiZiAdapter(List<MeiZi> meiZiList) {
        mMeiZiList = meiZiList;
    }

    @Override
    public MeiZiAdapter.mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(App.mContext).inflate(R.layout.adapter_meizi, parent, false);
        return new mViewHolder(view);
    }

    public void refresh(List<MeiZi> meiZiList) {
        this.mMeiZiList = meiZiList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final mViewHolder holder, int position) {
        holder.name.setText(mMeiZiList.get(position).getDesc());
        Glide.with(App.mContext).load(mMeiZiList.get(position).getUrl()).bitmapTransform(new CenterCrop(App.mContext), new RoundedCornersTransformation(App.mContext, 20, 0, RoundedCornersTransformation.CornerType.TOP)).crossFade(700).placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable(position)).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return mMeiZiList.size();
    }

    class mViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.adapter_meizi_girl)
        DynamicHeightImageView img;
        @BindView(R.id.adapter_meizi_parent)
        LinearLayout parent;
        @BindView(R.id.adapter_meizi_name)
        TextView name;

        public mViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            img.setSize(50, 50);
        }
    }
}
