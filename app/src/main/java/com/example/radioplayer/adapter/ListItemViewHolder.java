package com.example.radioplayer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.radioplayer.R;
import com.example.radioplayer.RadioPlayerApplication;
import com.example.radioplayer.event.OnClickEvent;
import com.example.radioplayer.model.Station;
import com.squareup.picasso.Picasso;

public class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView mItemTitle;
    private TextView mItemCountry;
    private ImageView mItemIcon;
    private int mPosition;

    public ListItemViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        mItemTitle = (TextView) itemView.findViewById(R.id.item_title);
        mItemCountry = (TextView) itemView.findViewById(R.id.item_country);
        mItemIcon = (ImageView) itemView.findViewById(R.id.item_icon);
    }

    public void bindStationItem(Station item, Context context, int position, int icon) {
        mPosition = position;
        mItemTitle.setText(item.getName());
        mItemCountry.setText(item.getCountry());

        // use picasso to download and set icon
        String url = item.getImage().getThumb().getUrl();
        if (url == null || url.isEmpty()) {
            // use the large image where thumb not available
            item.getImage().getUrl();
        }
        Picasso.with(context)
                .load(url)
                .resize(60, 60)
                .placeholder(icon)
                .error(icon)
                .into(mItemIcon);
    }

    @Override
    public void onClick(View v) {
        // propagate the click upto the hosting activity
        RadioPlayerApplication.postToBus(new OnClickEvent(OnClickEvent.LIST_ITEM_CLICK_EVENT, mPosition));
    }
}
