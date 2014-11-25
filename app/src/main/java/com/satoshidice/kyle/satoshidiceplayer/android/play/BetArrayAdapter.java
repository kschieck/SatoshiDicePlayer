package com.satoshidice.kyle.satoshidiceplayer.android.play;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.satoshidice.kyle.satoshidiceplayer.http.api.Bet;
import com.satoshidice.kyle.satoshidiceplayer.R;

import java.util.ArrayList;

/**
 * Created by Kyle on 19/11/2014.
 * Version: 1
 */
public class BetArrayAdapter extends ArrayAdapter<Bet> {

    public BetArrayAdapter(android.content.Context context, int resource, ArrayList<Bet> values) {
        super(context, resource, values);
    }

    private final class ViewHolder {
        ImageView background;
        TextView result;
        TextView bet;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        // Conditionally inflate view
        ViewHolder viewHolder;
        if (rowView == null) {
            LayoutInflater inflator = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            rowView = inflator.inflate(R.layout.listitem_bet_result, parent, false);

            // Setup View Holder
            viewHolder = new ViewHolder();
            viewHolder.background = (ImageView)rowView.findViewById(R.id.background);
            viewHolder.result = (TextView)rowView.findViewById(R.id.result);
            viewHolder.bet = (TextView)rowView.findViewById(R.id.bet);

            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)rowView.getTag();
        }

        // Get data
        Bet bet = getItem(position);

        // Populate View Holder
        viewHolder.bet.setText(Long.toString(bet.getProfitInSatoshis()));
        viewHolder.result.setText(bet.getResult());
        if (bet.getPayoutInSatoshis() > 0) {
            viewHolder.background.setImageResource(R.drawable.greenbox);
        } else {
            viewHolder.background.setImageResource(R.drawable.redbox);
        }

        return rowView;
    }

}
