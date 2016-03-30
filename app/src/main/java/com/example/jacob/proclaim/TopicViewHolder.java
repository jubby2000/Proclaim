package com.example.jacob.proclaim;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

/**
 * Created by Jacob on 3/1/2016.
 */
public class TopicViewHolder extends ChildViewHolder {

    final String LOG_TAG = TopicViewHolder.class.getSimpleName();

    private TextView mTopicViewHolder;
    private Window mWindow = null;

    public TopicViewHolder(View itemView) {
        super(itemView);
        mTopicViewHolder = (TextView) itemView.findViewById(R.id.topic_list_text_view);

    }

    public void bind(final Topic topic) {

        final MainActivity main = (MainActivity) itemView.getContext();

        mTopicViewHolder.setText(topic.getName());
        mTopicViewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v(LOG_TAG, "onClick called");
                Intent intent = new Intent(main, DetailActivity.class);
                intent.putExtra("Topic", topic.getName());
                main.click(v, intent);

            }
        });
    }
}
