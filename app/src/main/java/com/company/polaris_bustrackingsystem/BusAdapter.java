package com.company.polaris_bustrackingsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;


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
    protected void onBindViewHolder(@NonNull myViewHolder holder,final int position, @NonNull BusModel model) {
    holder.BusNo.setText(model.getBusno());
    holder.BusRoute.setText(model.getBusroute());
    holder.BusStop.setText(model.getStops());

    holder.butedit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                    .setContentHolder(new ViewHolder(R.layout.bus_update_popup))
                    .setExpanded(true,1550)
                    .create();

            //dialogPlus.show();

            View view1 = dialogPlus.getHolderView();

            EditText Busno = view1.findViewById(R.id.etBusNo);
            EditText Busrout = view1.findViewById(R.id.etBusRoute);
            EditText Busstop = view1.findViewById(R.id.etBusStops);
            EditText driverID = view1.findViewById(R.id.etBusDriverID);

            Button btn = view1.findViewById(R.id.btnUpdate);

            Busno.setText(model.getBusno());
            Busrout.setText(model.getBusroute());
            Busstop.setText(model.getStops());
            driverID.setText(model.getDriverid());

            dialogPlus.show();

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Map<String,Object> map = new HashMap<>();
                    map.put("busno",Busno.getText().toString());
                    map.put("busroute",Busrout.getText().toString());
                    map.put("driverid",driverID.getText().toString());
                    map.put("stops",Busstop.getText().toString());



                    FirebaseDatabase.getInstance().getReference().child("Bus")
                            .child(getRef(position).getKey()).updateChildren(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(holder.BusNo.getContext(),"Data Updated Succsessfuly",Toast.LENGTH_SHORT).show();
                                    dialogPlus.dismiss();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(Exception e) {
                                    Toast.makeText(holder.BusNo.getContext(),"Fail to upload",Toast.LENGTH_SHORT).show();
                                }
                            });

                }
            });
        }
    });

        holder.butdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =new AlertDialog.Builder(holder.BusNo.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Once deleted can't be undone");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Bus")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.BusNo.getContext(),"Cancelled",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();

            }
        });

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
        Button butedit,butdelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.ivBus);
            BusNo = (TextView) itemView.findViewById(R.id.tvBusNo);
            BusRoute = (TextView) itemView.findViewById(R.id.tvBusRoute);
            BusStop = (TextView) itemView.findViewById(R.id.tvBusStops);
            butedit = (Button) itemView.findViewById(R.id.btnBusEdit);
            butdelete = (Button) itemView.findViewById(R.id.btnBusDelete);
        }
    }
}
