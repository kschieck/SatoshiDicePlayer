package com.satoshidice.kyle.satoshidiceplayer.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/*
CustomArrayAdapter - Create a list adapter with a custom layout and data type

How to use:

Create a class which extends this class, but with a type:
    public class TaskArrayAdapter extends CustomArrayAdapter<Task> { ... }

Call the super constructor within your constructor:
    super(context, resource, values);

Override the following methods (Check the comments and example to see what they do):
    @Override public Object getViewHolder(View rowView) { ... }
    @Override public void fillViewHolder(Object viewHolder, Task data) { ... }

Set the adapter on a listView in your activity:
    listView.setAdapter(new TaskArrayAdapter(context, taskArrayList);

 */
public abstract class CustomArrayAdapter<T> extends ArrayAdapter<T> {

    // The layout passed in the constructor
    private int resource;

    // Constructor: make sure you call super(context resource, values) in your constructor
    public CustomArrayAdapter(Context context, int resource, ArrayList<T> values) {
        super(context, resource, values);

        this.resource = resource;
    }

    // getView recycles views, uses getViewHolder and fillViewHolder to display data in view
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        // Conditionally inflate view
        Object viewHolder;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            rowView = inflater.inflate(resource, parent, false);

            // Setup View Holder
            viewHolder = getViewHolder(rowView);

            // Set tag for recycling
            rowView.setTag(viewHolder);
        } else {
            viewHolder = rowView.getTag();
        }

        // Get data
        T data = getItem(position);

        // Populate View Holder
        fillViewHolder(viewHolder, data);

        // Return view (required by super class)
        return rowView;
    }

    /**
     * Get an object which contains variables that reference an item's views
     *
     * @param rowView The inflated view. Find views by doing rowView.findViewById(...)
     * @return The object with view references
     */
    public abstract Object getViewHolder(View rowView);

    /**
     * Fill a ViewHolder with data
     *
     * @param viewHolder Object configured in getViewHolder
     * @param data the data to add to the display
     */
    public abstract void fillViewHolder(Object viewHolder, T data);

}

/* Example

public class TaskArrayAdapter extends CustomArrayAdapter<Task> {

    // Date format for converting Calendar to String
    private SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);

    // Constructor
    public TaskArrayAdapter(Context context, ArrayList<Task> values) {
        super(context, R.layout.list_item_task, values);
    }

    // ViewHolder class contains references to views
    private final class ViewHolder {
        TextView title;
        TextView due;
    }

    @Override
    public Object getViewHolder(View rowView) {

        // Create a view holder for this view
        ViewHolder viewHolder = new ViewHolder() ;

        // Get references from rowView
        viewHolder.title = (TextView)rowView.findViewById(R.id.title);
        viewHolder.due = (TextView)rowView.findViewById(R.id.due);

        // Return viewHolder
        return viewHolder;
    }

    @Override
    public void fillViewHolder(Object viewHolder, Task data) {

        // Cast ViewHolder to correct type
        ViewHolder vh = (ViewHolder)viewHolder;

        // Fill in viewHolder with task data
        vh.title.setText(data.title);
        vh.due.setText(sdf.format(data.due.getTime()));
    }

}

*/