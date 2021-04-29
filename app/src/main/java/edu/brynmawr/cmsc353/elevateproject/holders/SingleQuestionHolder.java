package edu.brynmawr.cmsc353.elevateproject.holders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import edu.brynmawr.cmsc353.elevateproject.R;
import edu.brynmawr.cmsc353.elevateproject.adapters.QuestionAdapter;
import edu.brynmawr.cmsc353.elevateproject.models.QuestionModel;

public class SingleQuestionHolder extends RecyclerView.ViewHolder {
    private final TextView userName;
    private final TextView date;
    private final TextView title;
    private final TextView content;
    private final TextView numLikes;
    private final TextView numComments;
    private final ImageView avatar;

    public SingleQuestionHolder(final View itemView, QuestionAdapter.ClickCallback callback) {
        super(itemView);
        userName = itemView.findViewById(R.id.user_name);
        date = itemView.findViewById(R.id.date);
        title = itemView.findViewById(R.id.title);
        content = itemView.findViewById(R.id.content);
        numLikes = itemView.findViewById(R.id.num_likes);
        numComments = itemView.findViewById(R.id.num_comments);
        avatar = itemView.findViewById(R.id.avatar);
        ImageButton commentBtn = itemView.findViewById(R.id.comment_button);
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onShowCommentClick(getAdapterPosition());
            }
        });
    }

    public void bindData(final QuestionModel viewModel) {
        userName.setText(viewModel.getUserName());
        date.setText(viewModel.getDate());
        title.setText(viewModel.getTitle());
        content.setText(viewModel.getContent());
        numLikes.setText(String.valueOf(viewModel.getNumLikes()));
        numComments.setText(String.valueOf(viewModel.getNumComments()));
        avatar.setImageResource(R.mipmap.avatar);
    }
}