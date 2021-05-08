package edu.brynmawr.cmsc353.elevateproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import edu.brynmawr.cmsc353.elevateproject.R;
import edu.brynmawr.cmsc353.elevateproject.holders.SingleQuestionHolder;
import edu.brynmawr.cmsc353.elevateproject.models.QuestionModel;

public class QuestionAdapter extends RecyclerView.Adapter {
    private final List<QuestionModel> models;
    private final ClickCallback callback;

    public QuestionAdapter(List<QuestionModel> models, ClickCallback callback) {
        this.models = models;
        this.callback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new SingleQuestionHolder(view, callback);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((SingleQuestionHolder) holder).bindData(models.get(position));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.single_question_view;
    }

    public interface ClickCallback {
        void onShowCommentClick(int pos);
    }
}
