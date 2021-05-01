package edu.brynmawr.cmsc353.elevateproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{

    Context context;
    List<String> requests;

    public NotificationAdapter(Context context, List<String> requests){
        this.context = context;
        this.requests = requests;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Log.d("NotificationAdapter", "onCreateViewHolder");
        View notificationView = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(notificationView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("NotificationAdapter", "onBindViewHolder " + position);
        String request = requests.get(position);
        holder.bind(request);
    }

    @Override
    public int getItemCount() { return requests.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout container;
        TextView requestName;
        Button connectBtn;
        Button rejectBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            requestName = itemView.findViewById(R.id.requestName);
            connectBtn = itemView.findViewById(R.id.connectBtn);
            rejectBtn = itemView.findViewById(R.id.rejectBtn);
        }

        public void bind(String request){
            String[] info = request.split(",");
            requestName.setText(info[0]);
            connectBtn.setTag(info[1]);
            rejectBtn.setTag(info[1]);
        }




    }


}
