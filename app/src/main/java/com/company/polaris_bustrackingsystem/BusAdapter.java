package com.company.polaris_bustrackingsystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class BusAdapter extends FirebaseRecyclerAdapter<BusModel, BusAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public BusAdapter(@NonNull FirebaseRecyclerOptions<BusModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull BusModel model) {
    holder.BusNo.setText(model.getBusno());
    holder.BusRoute.setText(model.getBusroute());
    holder.BusStop.setText(model.getStops());

    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_card,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
       ImageView img;
        TextView BusNo,BusStop,BusRoute;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.ivBus);
            BusNo = (TextView) itemView.findViewById(R.id.tvBusNo);
            BusRoute = (TextView) itemView.findViewById(R.id.tvBusRoute);
            BusStop = (TextView) itemView.findViewById(R.id.tvBusStops);
        }
    }
}
