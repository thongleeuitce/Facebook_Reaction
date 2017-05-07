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
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Toast;

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

    public static int DIVIDE = ConvertDp2Px.dp_to_px(5);                        // divide = 5dp
    public static int HEIGHT_VIEW_REACTION = ConvertDp2Px.dp_to_px(250);       // height view of Reaction = 250dp
    public static int WIDHT_VIEW_REACTION = ConvertDp2Px.dp_to_px(300);        // width view of reaction = 300dp

    public static final int MAX_ALPHA = 255;
    public static final int MIN_ALPHA = 150;

//    private Board board;
//    private Emotion[] emotions = new Emotion[6];
//    private StateDraw state = StateDraw.BEGIN;

    private int currentPosition = 0;

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ReactionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        initial_board_emotion();
    }

    public void initial_board_emotion(){
        emotions = new ArrayList<>();
        board = new Board(this.context);
        setLayerType(LAYER_TYPE_SOFTWARE, board.boardPaint);
        emotions.add(new Emotion(this.context, getResources().getString(R.string.like), R.drawable.like));
        emotions.add(new Emotion(this.context, getResources().getString(R.string.love), R.drawable.love));
        emotions.add(new Emotion(this.context, getResources().getString(R.string.haha), R.drawable.haha));
        emotions.add(new Emotion(this.context, getResources().getString(R.string.wow), R.drawable.wow));
        emotions.add(new Emotion(this.context, getResources().getString(R.string.cry), R.drawable.cry));
        emotions.add(new Emotion(this.context, getResources().getString(R.string.angry), R.drawable.angry));
        for(int i = 0; i < 6; i++){
            emotions.get(i).currentX = board.BOARD_X + (i + 1)*DIVIDE + i*Emotion.NORMAL_SIZE;
            emotions.get(i).currentY = board.BASE_LINE - Emotion.NORMAL_SIZE;
        }

    }

    //    private void init() {
//
//    }
//
//    private void initElement() {
//
//    }
//
    @Override
    protected void onDraw(Canvas canvas) {

        board.drawBoard(canvas);
        for(int i=0; i < 6; i++){
            emotions.get(i).drawEmotion(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handler = false;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                handler = true;
                break;
            case  MotionEvent.ACTION_UP:
                handler = true;
                break;
            case MotionEvent.ACTION_MOVE:
                for(int i = 0; i< 6; i++) {
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

    public void emotion_selected(int position){
        if(currentPosition != position){
            Log.v("selected", String.valueOf(position));
            currentPosition = position;
            startAnimation(new ChooseEmotionAnimation());
        }

    }

    private void setvalues_beforeAnimateBeginning() {

    }

    private void setvalues_beforeAnimateChoosing() {
        board.beginHeight = board.getCurrentHeight();
        board.endHeight = board.BOARD_HEIGHT_MINIMAL;
        for(int i = 0; i < 6; i++){
            if(i == currentPosition){
                emotions.get(i).beginSize = emotions.get(i).getCurrentSize();
                emotions.get(i).endSize = Emotion.CHOOSE_SIZE;
            }
            else{
                emotions.get(i).beginSize = emotions.get(i).getCurrentSize();
                emotions.get(i).endSize = Emotion.MINIMAL_SIZE;
            }
        }
    }

    class ChooseEmotionAnimation extends Animation {
        public ChooseEmotionAnimation() {
            if(state == States.CHOOSING)
                setvalues_beforeAnimateChoosing();
            setDuration(DURATION_ANIMATION);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {

            // caculate size and coordinate X of Board and Emotions throught interpolated Time
            super.applyTransformation(interpolatedTime, t);
            board.setCurrentHeight(board.beginHeight + interpolatedTime*(board.endHeight - board.beginHeight));
            for(int i = 0; i < 6; i++)
                    emotions.get(i).setCurrentSize((int)(emotions.get(i).beginSize + interpolatedTime*(emotions.get(i).endSize - emotions.get(i).beginSize)));

            // caculate Coodirnate Y of Emotions throught interpolated Time
//            emotions.get(0).currentX = Board.BOARD_X + DIVIDE;
//            emotions.get(5).currentX = Board.BOARD_X + Board.BOARD_WIDTH - DIVIDE - emotions.get(5).getCurrentSize();
            for(int i = 1; i < 6; i++)
                emotions.get(i).currentX = emotions.get(i - 1).currentX + emotions.get(i - 1).getCurrentSize() + DIVIDE;
//            for(int i = 4; i > currentPosition; i--){
//                emotions.get(i).currentX = emotions.get(i + 1).currentX - DIVIDE - emotions.get(i).getCurrentSize();
//            }
//            if (currentPosition != 0 && currentPosition != 5) {
//                if (currentPosition <= 2) {
//                    emotions.get(currentPosition).currentX = emotions.get(currentPosition-1).currentX + emotions.get(currentPosition-1).currentSize + DIVIDE;
//                } else {
//                    emotions.get(currentPosition).currentX = emotions.get(currentPosition+1).currentX - emotions.get(currentPosition).currentSize - DIVIDE;
//                }
//            }
            invalidate();
        }
    }
//
//    private void setvalues_beforeAnimateNormalBack() {
//
//    }
//
//    private void calculateInSessionChoosingAndEnding(float interpolatedTime) {
//
//    }
//
//    private void calculateInSessionBeginning(float interpolatedTime) {
//
//    }
//
//    private int calculateSize(int position, float interpolatedTime) {
//        return 0;
//    }
//
//    private void calculateCoordinateX() {
//
//    }
//
//    public void show() {
//
//    }
//
//    private void selected(int position) {
//
//    }
//
//    public void backToNormal() {
//
//    }
//
//
//
//        @Override
//        protected void applyTransformation(float interpolatedTime, Transformation t) {
//
//        }
//    }
//    class BeginningAnimation extends Animation {
//        public BeginningAnimation() {
//
//        }
//        @Override
//        protected void applyTransformation(float interpolatedTime, Transformation t) {
//
//        }
//    }
}
