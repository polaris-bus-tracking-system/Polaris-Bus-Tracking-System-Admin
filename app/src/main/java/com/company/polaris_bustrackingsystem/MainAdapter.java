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

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, final int position, @NonNull MainModel model) {
holder.name.setText(model.getName());
holder.enroll.setText(model.getEnroll());
holder.busstop.setText(model.getBusstop());

        Glide.with(holder.imgurl.getContext())
                .load(model.getImgurl())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.imgurl);

        holder.btnStudentEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus= DialogPlus.newDialog(holder.imgurl.getContext())
                        .setContentHolder(new ViewHolder(R.layout.student_update_popup))
                        .setExpanded(true,1200)
                        .create();

                View view=dialogPlus.getHolderView();
                EditText name=view.findViewById(R.id.etStudentName);
                EditText enroll=view.findViewById(R.id.etEnroll);
                EditText email=view.findViewById(R.id.etStudentEmail);
                EditText busstop=view.findViewById(R.id.etStudentBusStop);
                EditText imgurl=view.findViewById(R.id.etImageUrl);

                Button btnUpdate=view.findViewById(R.id.btnUpdate);

                name.setText(model.getName());
                enroll.setText(model.getEnroll());
                email.setText(model.getEmail());
                busstop.setText(model.getBusstop());
                imgurl.setText(model.getImgurl());
                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view1) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("name",name.getText().toString());
                        map.put("enroll",enroll.getText().toString());
                        map.put("email",email.getText().toString());
                        map.put("busstop",busstop.getText().toString());
                        map.put("imgurl",imgurl.getText().toString());


                        FirebaseDatabase.getInstance().getReference().child("Students")
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
        holder.btnStudentDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are you sure");
                builder.setMessage("once deleted cant be undone");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                         FirebaseDatabase.getInstance().getReference().child("Students")
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
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.student_card,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        CircleImageView imgurl;
        TextView name,enroll,busstop;

        Button btnStudentEdit,btnStudentDelete;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            imgurl=(CircleImageView) itemView.findViewById(R.id.ivStudent);
            name=(TextView) itemView.findViewById(R.id.tvStudentName);
            enroll=(TextView) itemView.findViewById(R.id.tvStudentEnroll);
            busstop=(TextView) itemView.findViewById(R.id.tvStudentBusStop);

            btnStudentEdit=(Button) itemView.findViewById(R.id.btnStudentEdit);
            btnStudentDelete=(Button) itemView.findViewById(R.id.btnStudentDelete);
        }
    }
}
