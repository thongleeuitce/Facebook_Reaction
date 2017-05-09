package com.example.thongle.facebook_reaction.Animations;

import android.content.res.Resources;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.example.thongle.facebook_reaction.Models.Board;
import com.example.thongle.facebook_reaction.Models.Emotion;
import com.example.thongle.facebook_reaction.ReactionView;

import java.util.ArrayList;

/**
 * Created by thongle on 10/05/2017.
 */

public class EndEmotionAnimation extends Animation {
    private View view;
    private Board board;
    private ArrayList<Emotion> emotions;
    private int currentPosition;

    public  EndEmotionAnimation(View view, Board board, ArrayList<Emotion> emotions, int currentPosition){
        this.view = view;
        this.board = board;
        this.emotions = emotions;
        this.currentPosition = currentPosition;
        setvalues_beforeAnimateEnding();
        setDuration(ReactionView.DURATION_ENDING_ANIMATION);

    }
    public void setvalues_beforeAnimateEnding(){
        board.beginY = board.currentY;
        board.endY = ReactionView.HEIGHT_VIEW_REACTION - Board.BOARD_HEIGHT_MINIMAL;

        for(int i = 0; i < 6; i++){
            if(i == currentPosition){
                emotions.get(i).beginY = emotions.get(i).currentY;
                emotions.get(i).endY = emotions.get(i).currentY;
            }
            else {
                emotions.get(i).beginY = emotions.get(i).currentY;
                emotions.get(i).endY = ReactionView.HEIGHT_VIEW_REACTION - ReactionView.DIVIDE - emotions.get(i).currentSize;
            }
        }
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {

        board.currentY = board.beginY + interpolatedTime * (board.endY - board.beginY);
        for(int i = 0; i < 6; i++){
            emotions.get(i).currentY = emotions.get(i).beginY + interpolatedTime * (emotions.get(i).endY - emotions.get(i).beginY);
        }
        board.boardPaint.setAlpha(10);
        if(interpolatedTime != 1){
            board.boardPaint.setAlpha((int) ((1 - interpolatedTime)* ReactionView.MAX_ALPHA));
        }
        else
            board.boardPaint.setAlpha(ReactionView.MIN_ALPHA);
        view.invalidate();
    }
}
