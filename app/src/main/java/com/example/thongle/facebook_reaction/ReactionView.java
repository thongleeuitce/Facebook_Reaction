package com.example.thongle.facebook_reaction;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import java.util.ArrayList;

/**
 * Created by thongle on 05/05/2017.
 */

public class ReactionView extends View{

    Context context;
    private ArrayList<Emotion> emotions;
    private Board board;

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
//
//    private void beforeAnimateBeginning() {
//
//    }
//
//    private void beforeAnimateChoosing() {
//
//    }
//
//    private void beforeAnimateNormalBack() {
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
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        return true;
//    }
//
//    class ChooseEmotionAnimation extends Animation {
//        public ChooseEmotionAnimation() {
//
//        }
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
