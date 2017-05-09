package com.example.thongle.facebook_reaction;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.thongle.facebook_reaction.Animations.BeginEmotionAnimation;
import com.example.thongle.facebook_reaction.Animations.ChooseEmotionAnimation;
import com.example.thongle.facebook_reaction.Animations.EndEmotionAnimation;
import com.example.thongle.facebook_reaction.Models.Board;
import com.example.thongle.facebook_reaction.Models.Emotion;
import com.example.thongle.facebook_reaction.Utils.ConvertDp2Px;
import com.example.thongle.facebook_reaction.Utils.EaseOutBack;

import java.util.ArrayList;

/**
 * Created by thongle on 05/05/2017.
 */

public class ReactionView extends View {

    enum States {
        BEGIN,
        NORMAL,
        CHOOSING,
        END
    }

    Context context;
    private ArrayList<Emotion> emotions;
    private Board board;
    private States state;

    public static final long DURATION_ANIMATION = 200;
    public static final long DURATION_BEGINNING_EACH_ITEM = 300;
    public static final long DURATION_BEGINNING_ANIMATION = 900;
    public  static final long DURATION_ENDING_ANIMATION = 600;

    public static int DIVIDE = ConvertDp2Px.dp_to_px(5);                        // divide = 5dp
    public static int HEIGHT_VIEW_REACTION = ConvertDp2Px.dp_to_px(250);       // height view of Reaction = 250dp
    public static int WIDHT_VIEW_REACTION = ConvertDp2Px.dp_to_px(300);        // width view of reaction = 300dp

    public static final int MAX_ALPHA = 255;
    public static final int MIN_ALPHA = 0;

    public int currentPosition = 0;

    public ReactionView(Context context) {
        super(context);
        this.context = context;
        initial_board_emotion();
    }
    public ReactionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initial_board_emotion();
    }
    public ReactionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initial_board_emotion();
    }

    public void initial_board_emotion() {
        emotions = new ArrayList<>();
        board = new Board(this.context);
        setLayerType(LAYER_TYPE_SOFTWARE, board.boardPaint);
        emotions.add(new Emotion(this.context, getResources().getString(R.string.like), R.drawable.like));
        emotions.add(new Emotion(this.context, getResources().getString(R.string.love), R.drawable.love));
        emotions.add(new Emotion(this.context, getResources().getString(R.string.haha), R.drawable.haha));
        emotions.add(new Emotion(this.context, getResources().getString(R.string.wow), R.drawable.wow));
        emotions.add(new Emotion(this.context, getResources().getString(R.string.cry), R.drawable.cry));
        emotions.add(new Emotion(this.context, getResources().getString(R.string.angry), R.drawable.angry));

        board.currentY = HEIGHT_VIEW_REACTION + 1;
        for (Emotion e : emotions) {
            e.currentY = board.currentY + DIVIDE;
        }
//        for (int i = 0; i < 6; i++) {
//            emotions.get(i).currentX = board.BOARD_X + (i + 1) * DIVIDE + i * Emotion.NORMAL_SIZE;
//            emotions.get(i).currentY = board.BASE_LINE - Emotion.NORMAL_SIZE;
//        }
    }
    public void show() {
        state = States.BEGIN;
        BacktoNormal();
        setVisibility(VISIBLE);
        startAnimation(new BeginEmotionAnimation(this, board, emotions));
    }
    public void emotion_selected(int position) {
        if (currentPosition != position) {
            Log.v("selected", String.valueOf(position));
            currentPosition = position;
            startAnimation(new ChooseEmotionAnimation(this, board, emotions, currentPosition));
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handler = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handler = true;
                break;
            case MotionEvent.ACTION_UP:
                startAnimation(new EndEmotionAnimation(this, board, emotions, currentPosition));
                setVisibility(INVISIBLE);
                handler = true;
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < 6; i++) {
                    if (event.getX() > emotions.get(i).currentX && event.getX() < (emotions.get(i).currentX + emotions.get(i).currentSize)) {
                        state = States.CHOOSING;
                        emotion_selected(i);
                        break;
                    }
                }
                break;
        }
        return handler;
    }
    public void BacktoNormal(){
        board.currentY = HEIGHT_VIEW_REACTION + 1;
        board.currentHeight = Board.BOARD_HEIGHT_NORMAL;
        board.boardPaint.setAlpha(MAX_ALPHA);
        for (int i = 0; i< 6; i++) {
            if (i == 0)
                emotions.get(i).currentX = Board.BOARD_X + DIVIDE;
            else
                emotions.get(i).currentX = emotions.get(i - 1).currentX + emotions.get(i - 1).currentSize + DIVIDE;
            emotions.get(i).currentSize = Emotion.NORMAL_SIZE;
            emotions.get(i).currentY = board.currentY + DIVIDE;
        }
        invalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {

        board.drawBoard(canvas);
        for (int i = 0; i < 6; i++) {
            emotions.get(i).drawEmotion(canvas);
        }
    }
}