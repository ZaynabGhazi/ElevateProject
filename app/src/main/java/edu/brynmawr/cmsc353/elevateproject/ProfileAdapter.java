package edu.brynmawr.cmsc353.elevateproject;

import android.content.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.*;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder>{
    
    List<String> profiles;
    Context context;
    
    public ProfileAdapter(Context context, List<String> results){
        this.context = context;
        this.profiles = results;
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        Log.d("ProfileAdapter", "onCreteViewHolder");
        View profileView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_findprofile, parent, false);
        return new ViewHolder(profileView);
    }

    @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        Log.d("ProfileAdapter", "onBindViewHolder " + position);
        String result = profiles.get(position);
        String[] keys = result.split(" ");
        String profile = keys[0] + " " + keys[1];
        holder.bind(profile);
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout container;
        TextView name;
        Button requestConnection;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void ViewHolder(@NonNull View itemView) {
            container = itemView.findViewById(R.id.container);
            name = itemView.findViewById(R.id.name);
            requestConnection = itemView.findViewById(R.id.requestConnection);
        }

        public void bind(String profile){
            name.setText(profile);
            
            requestConnection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context.getApplicationContext(),
                            "Request Sent!",
                            Toast.LENGTH_SHORT).show();
                }
            });
            
        }

    }


}
