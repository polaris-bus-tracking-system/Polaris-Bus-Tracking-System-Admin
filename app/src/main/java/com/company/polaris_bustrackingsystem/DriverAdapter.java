package com.company.polaris_bustrackingsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class DriverAdapter extends FirebaseRecyclerAdapter<DriverModel,DriverAdapter.myDriverViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public DriverAdapter(@NonNull FirebaseRecyclerOptions<DriverModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myDriverViewHolder holder,final int position, @NonNull DriverModel model) {
        holder.name.setText(model.getName());
        holder.phone.setText(model.getPhone());
        holder.email.setText(model.getEmail());

        Glide.with(holder.img.getContext())
                .load(model.getImgurl())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);

        holder.butEidt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.driver_update_popup))
                        .setExpanded(true,1200)
                        .create();

                //dialogPlus.show();

                View view1 = dialogPlus.getHolderView();

                EditText name = view1.findViewById(R.id.etDriverName);
                EditText phone = view1.findViewById(R.id.etDriverPhone);
                EditText mail = view1.findViewById(R.id.etDriverEmail);
                EditText url = view1.findViewById(R.id.etImageUrl);

                Button btn = view1.findViewById(R.id.btnDriverUpdate);

                name.setText(model.getName());
                phone.setText(model.getPhone());
                mail.setText(model.getEmail());
                url.setText(model.getImgurl());

                dialogPlus.show();

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("name",name.getText().toString());
                        map.put("email",mail.getText().toString());
                        map.put("phone",phone.getText().toString());
                        map.put("imgurl",url.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Driver")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.name.getContext(),"Data Updated Succsessfuly",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(holder.name.getContext(),"Fail to upload",Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                });
            }
        });

        holder.butDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are you sure");
                builder.setMessage("once deleted cant be undone");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Driver")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.name.getContext(),"Cancelled",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();

            }
        });
    }

    @NonNull
    @Override
    public myDriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_card,parent,false);
        return new myDriverViewHolder(view);
    }

    class myDriverViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView name,email,phone;
        Button butEidt,butDelete;


        public myDriverViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (CircleImageView) itemView.findViewById(R.id.ivDriver);
            name = (TextView) itemView.findViewById(R.id.tvDriverName);
            email = (TextView) itemView.findViewById(R.id.tvDriverEmail);
            phone = (TextView) itemView.findViewById(R.id.tvDriverPhone);
            butEidt = (Button) itemView.findViewById(R.id.btnDriverEdit);
            butDelete = (Button) itemView.findViewById(R.id.btnDriverDelete);

        }
    }
}
