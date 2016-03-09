package com.example.jacob.proclaim;

import android.content.Intent;
import android.transition.Slide;
import android.transition.Transition;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

/**
 * Created by Jacob on 3/1/2016.
 */
public class TopicViewHolder extends ChildViewHolder {

    private TextView mTopicViewHolder;
    private Window mWindow = null;

    public TopicViewHolder(View itemView) {
        super(itemView);
        mTopicViewHolder = (TextView) itemView.findViewById(R.id.topic_list_text_view);

    }

    public void bind(final Topic topic) {
        final Transition slide = new Slide();
        slide.setDuration(5000);

        final MainActivity main = (MainActivity) itemView.getContext();
        mWindow = main.getWindow();

        mTopicViewHolder.setText(topic.getName());
        mTopicViewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(main, DetailActivity.class);
                intent.putExtra("Topic", topic.getName());
                //mWindow.setExitTransition(slide);
                main.click(mWindow.getCurrentFocus());
                v.getContext().startActivity(intent);


            }
        });
    }
}
