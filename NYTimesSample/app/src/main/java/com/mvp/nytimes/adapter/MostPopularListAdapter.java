package com.mvp.nytimes.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.mvp.nytimes.R;
import com.mvp.nytimes.model.MostViewedModel;
import java.util.ArrayList;
import java.util.List;

public class MostPopularListAdapter extends RecyclerView.Adapter<MostPopularListAdapter.MostPopularListViewHolder>
    implements Filterable
{
    private ArrayList<MostViewedModel> mMostViewedData;
    private ArrayList<MostViewedModel> mAllData;
    private LayoutInflater mLayoutInflator;
    public Context mContext;

    public MostPopularListAdapter(ArrayList<MostViewedModel> allPopularArticles, Context context) {
        mAllData= new ArrayList<>();
        this.mMostViewedData = allPopularArticles;
        this.mAllData = allPopularArticles;
        this.mLayoutInflator = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public MostPopularListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.list_item_row,parent,false);
        MostPopularListViewHolder viewHolder = new MostPopularListViewHolder(view);
        return viewHolder;
    }

    /*
    * This method is called for each row in the recycler view.
    * Handling the display of each item in the row using setData() method
    **/
    @Override
    public void onBindViewHolder(@NonNull MostPopularListViewHolder holder, int position) {
        MostViewedModel currentRow = mMostViewedData.get(position);
        holder.setData(currentRow,position);
    }

    @Override
    public int getItemCount() {
        return mMostViewedData.size();
    }

    /*
    This method takes care of the filtering items in the recyclerview
    */
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                ArrayList<MostViewedModel> filteredItems = new ArrayList<>();
                String charStr = charSequence.toString();
                if(charStr.isEmpty()){
                    filteredItems = mAllData;
                }
                else
                {
                    List<MostViewedModel> filterlist = new ArrayList<>();
                    for (MostViewedModel row : mAllData)
                    {
                        if(row.getTitle().toLowerCase().contains(charStr.toLowerCase()) ||
                                row.getAuthor().toLowerCase().contains(charStr.toLowerCase())){
                            filteredItems.add(row);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredItems;
                return filterResults;
            }

            /*
            This method is automatically called whenever a filtering happens
             */
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mMostViewedData = (ArrayList<MostViewedModel>) results.values;
                //refreshing the recyclerview
                notifyDataSetChanged();
            }
        };
    }

    class MostPopularListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView titleTv,dateTv,byTv;
        MostViewedModel currentRow;
        int position;

        public MostPopularListViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            titleTv = itemView.findViewById(R.id.title_id_textview);
            dateTv = itemView.findViewById(R.id.date_text_view);
            byTv = itemView.findViewById(R.id.byTextView);
        }

        public void setData(MostViewedModel currentArticleRow, int position) {
            this.currentRow = currentArticleRow;
            this.position = position;
            this.titleTv.setText(this.currentRow.getTitle());
            this.dateTv.setText(this.currentRow.getPublishedDate());
            this.byTv.setText(this.currentRow.getAuthor());
        }

        @Override
        public void onClick(View v) {
            Log.d("MY_APP","List item clicked...");
        }
    }
}
