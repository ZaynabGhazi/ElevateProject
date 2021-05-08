package edu.brynmawr.cmsc353.elevateproject.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import edu.brynmawr.cmsc353.elevateproject.R;
import edu.brynmawr.cmsc353.elevateproject.models.CommentModel;

public class SingleCommentHolder extends RecyclerView.ViewHolder {
    private final TextView userName;
    private final TextView content;
    private final TextView date;
    private final ImageView avatar;

    public SingleCommentHolder(final View itemView) {
        super(itemView);
        userName = itemView.findViewById(R.id.user_name);
        date = itemView.findViewById(R.id.date);
        content = itemView.findViewById(R.id.content);
        avatar = itemView.findViewById(R.id.avatar);
    }

    public void bindData(final CommentModel viewModel) {
        userName.setText(viewModel.getUserName());
        date.setText(viewModel.getDate());
        content.setText(viewModel.getContent());
        avatar.setImageResource(R.mipmap.avatar);
    }
}