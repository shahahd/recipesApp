package com.example.recipesapp;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class userDataAdapter extends FirestoreRecyclerAdapter<getUserInfo,userDataAdapter.userDataHolder> {
    FirebaseStorage storage;
    StorageReference storageRef;
    private Context mContext;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public userDataAdapter(@NonNull FirestoreRecyclerOptions<getUserInfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull userDataAdapter.userDataHolder holder, int position, @NonNull getUserInfo model) {
        holder.txt1.setText(model.getRecipeName());
        holder.txt2.setText(model.getTime());
        //holder.img.s
        /*Picasso.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);*/
        storage = FirebaseStorage.getInstance();
      //  holder.crimg.setI(model.getImage());


    }

    @NonNull
    @Override
    public userDataAdapter.userDataHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclevhandler,viewGroup,false);
        return new userDataHolder(v);
    }

    class userDataHolder extends RecyclerView.ViewHolder{
        //CircleImageView crimg;
        TextView txt1,txt2;
        ImageView img;

        public userDataHolder(@NonNull View itemView) {
            super(itemView);
           // crimg=itemView.findViewById(R.id.imageView);
            txt1=itemView.findViewById(R.id.imageText);
            txt2=itemView.findViewById(R.id.NextTextView);
            img=itemView.findViewById(R.id.image_view_upload);

        }
    }
}
