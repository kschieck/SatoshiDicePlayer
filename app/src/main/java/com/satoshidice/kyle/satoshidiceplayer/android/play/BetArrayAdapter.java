package com.satoshidice.kyle.satoshidiceplayer.android.play;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.satoshidice.kyle.satoshidiceplayer.android.CustomArrayAdapter;
import com.satoshidice.kyle.satoshidiceplayer.http.api.Bet;
import com.satoshidice.kyle.satoshidiceplayer.R;

import java.util.ArrayList;

/**
 * Created by Kyle on 19/11/2014.
 * Version: 1
 */
public class BetArrayAdapter extends CustomArrayAdapter<Bet> {

    public BetArrayAdapter(android.content.Context context, int resource, ArrayList<Bet> values) {
        super(context, resource, values);
    }

    private final class ViewHolder {
        ImageView background;
        TextView result;
        TextView bet;
    }

    @Override
    public Object getViewHolder(View rowView) {

        ViewHolder viewHolder = new ViewHolder();

        viewHolder.background = (ImageView)rowView.findViewById(R.id.background);
        viewHolder.result = (TextView)rowView.findViewById(R.id.result);
        viewHolder.bet = (TextView)rowView.findViewById(R.id.bet);

        return viewHolder;
    }

    @Override
    public void fillViewHolder(Object viewHolder, Bet data) {

        ViewHolder vh = (ViewHolder)viewHolder;

        // Populate View Holder
        vh.bet.setText(Long.toString(data.getProfitInSatoshis()));
        vh.result.setText(data.getResult());
        if (data.getPayoutInSatoshis() > 0) {
            vh.background.setImageResource(R.drawable.greenbox);
        } else {
            vh.background.setImageResource(R.drawable.redbox);
        }
    }
}
