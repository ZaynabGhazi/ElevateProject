package edu.brynmawr.cmsc353.elevateproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import edu.brynmawr.cmsc353.elevateproject.R;
import edu.brynmawr.cmsc353.elevateproject.holders.SingleCommentHolder;
import edu.brynmawr.cmsc353.elevateproject.models.CommentModel;

public class CommentAdapter extends RecyclerView.Adapter {
    private final List<CommentModel> models;

    public CommentAdapter(List<CommentModel> models) {
        this.models = models;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new SingleCommentHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((SingleCommentHolder) holder).bindData(models.get(position));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.single_comment_view;
    }

}
