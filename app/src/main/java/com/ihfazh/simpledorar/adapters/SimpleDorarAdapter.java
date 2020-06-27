package com.ihfazh.simpledorar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ihfazh.simpledorar.R;
import com.ihfazh.simpledorar.models.SimpleDorar;

import java.util.List;

public class SimpleDorarAdapter extends RecyclerView.Adapter<SimpleDorarAdapter.ViewHolder> {

    private List<SimpleDorar> mSimpleDorarList;

    public SimpleDorarAdapter(List<SimpleDorar> simpleDorars){
        mSimpleDorarList = simpleDorars;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        // inflate the custom layout
        View simpleDorarView = layoutInflater.inflate(R.layout.simple_dorar, parent, false);

        // return the holder instance
        ViewHolder viewHolder = new ViewHolder(simpleDorarView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get the data model from position
        SimpleDorar simpleDorar = mSimpleDorarList.get(position);
        TextView textView = holder.textView;

        textView.setText(simpleDorar.getSpannableString());

    }

    @Override
    public int getItemCount() {
        return mSimpleDorarList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public CardView cardView;

        public ViewHolder(View view){
            super(view);

            cardView = (CardView) view.findViewById(R.id.cv);
            textView = (TextView) view.findViewById(R.id.txt);
        }
    }

}
