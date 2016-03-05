package com.example.jacob.proclaim;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

/**
 * Created by Jacob on 3/1/2016.
 */
public class TopicViewHolder extends ChildViewHolder {

    private TextView mTopicViewHolder;

    public TopicViewHolder(View itemView) {
        super(itemView);
        mTopicViewHolder = (TextView) itemView.findViewById(R.id.topic_list_text_view);


    }

    public void bind(final Topic topic) {
        mTopicViewHolder.setText(topic.getName());
        mTopicViewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                //TODO Add a slide scene transisiton animation here
                intent.putExtra("Topic", topic.getName());
                v.getContext().startActivity(intent);

            }
        });
    }
}
