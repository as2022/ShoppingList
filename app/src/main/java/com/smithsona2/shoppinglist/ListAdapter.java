package com.smithsona2.shoppinglist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smithsona2.shoppinglist.R;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.ServiceConfigurationError;

/**
 * This list supports two inner-class ViewHolders:
 * GroceryViewHolder for the TextViews of the MainActivity
 * CommonViewHolder for the clickable Buttons of the SecondActivity
 */
public class ListAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ClickListener listener;

    private final LinkedList<String> mList;

    public LayoutInflater mInflater;

    public ListAdapter(Context context, LinkedList<String> list, ClickListener listener) {
        mInflater = LayoutInflater.from(context);
        this.listener = listener != null ? listener : null;
        this.mList = list;

    }

    /**
     *  depending on the parent's context, returns a CommonViewHolder
     *  or a GroceryViewHolder
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView;
        int resource;

        if(parent.getContext().getClass().getSimpleName().equals("MainActivity")){
            resource = R.layout.grocery_list_item;
            mItemView = mInflater.inflate(resource, parent, false);
            return new GroceryViewHolder(mItemView, this);
        }else {
            resource = R.layout.common_list_item;
            mItemView = mInflater.inflate(resource, parent, false);
            return new CommonViewHolder(mItemView, this, listener);
        }
   }

    /**
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String mCurrent = mList.get(position);
        if(holder instanceof GroceryViewHolder){
            ((GroceryViewHolder)holder).groceryItemView.setText(mCurrent);
        }else if(holder instanceof CommonViewHolder){
            ((CommonViewHolder)holder).commonItemView.setText(mCurrent);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * ViewHolder of TextViews
     */
    class GroceryViewHolder extends RecyclerView.ViewHolder {

        public final TextView groceryItemView;
        final ListAdapter mAdapter;

        public GroceryViewHolder(View itemView, ListAdapter adapter) {
            super(itemView);
            groceryItemView = itemView.findViewById(R.id.grocery);
            this.mAdapter = adapter;
        }
    }

    /**
     * ViewHolder of clickable Buttons
     */
    class CommonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final Button commonItemView;
        final ListAdapter mAdapter;
        private WeakReference<ClickListener> listenerRef;

        public CommonViewHolder(View itemView, ListAdapter adapter, ClickListener listener) {
            super(itemView);
            commonItemView = itemView.findViewById(R.id.common);
            listenerRef = new WeakReference<>(listener);
            itemView.setOnClickListener(this);
            commonItemView.setOnClickListener(this);
            this.mAdapter = adapter;
        }

        /**
         * When a common food item button is clicked,
         * it is removed from the list of common food items to choose from
         *
         * Additional onClick behavior is denoted in SecondActivity's onCreate method
         * @param view
         */
        @Override
        public void onClick(View view) {
            listenerRef.get().onPositionClicked(getAdapterPosition());
            int mPosition = getLayoutPosition();
            String element = mList.remove(mPosition);
            mAdapter.notifyItemRemoved(mPosition);
        }
    }
}