package com.example.thongle.facebook_reaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button_like;
    private ReactionView reactionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_like = (Button) findViewById(R.id.btn_like);
        reactionView = (ReactionView) findViewById(R.id.reaction_view);
        reactionView.setVisibility(View.INVISIBLE);

        button_like.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                reactionView.show();
                return false;
            }
        });
    }
}
