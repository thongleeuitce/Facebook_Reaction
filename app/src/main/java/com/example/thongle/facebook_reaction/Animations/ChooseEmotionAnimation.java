package com.example.thongle.facebook_reaction.Animations;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.example.thongle.facebook_reaction.Models.Board;
import com.example.thongle.facebook_reaction.Models.Emotion;
import com.example.thongle.facebook_reaction.ReactionView;

import java.util.ArrayList;

/**
 * Created by thongle on 09/05/2017.
 */

public class ChooseEmotionAnimation extends Animation {

    private  int currentposition;
    private View view;
    private ArrayList<Emotion> emotions = new ArrayList<>();
    private Board board;

    public ChooseEmotionAnimation(View view, Board board, ArrayList<Emotion> emotions, int currentposition) {
        this.view = view;
        this.board = board;
        this.emotions = emotions;
        this.currentposition = currentposition;

        setvalues_beforeAnimateChoosing();
        setDuration(ReactionView.DURATION_ANIMATION);
    }

    private void setvalues_beforeAnimateChoosing() {
        board.beginHeight = board.getCurrentHeight();
        board.endHeight = board.BOARD_HEIGHT_MINIMAL;
        for (int i = 0; i < 6; i++) {
            if (i == currentposition) {
                emotions.get(i).beginSize = emotions.get(i).getCurrentSize();
                emotions.get(i).endSize = Emotion.CHOOSE_SIZE;
            } else {
                emotions.get(i).beginSize = emotions.get(i).getCurrentSize();
                emotions.get(i).endSize = Emotion.MINIMAL_SIZE;
            }
        }
    }
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {

        // caculate size and coordinate X of Board and Emotions throught interpolated Time
        super.applyTransformation(interpolatedTime, t);
        board.setCurrentHeight(board.beginHeight + interpolatedTime * (board.endHeight - board.beginHeight));
        for (int i = 0; i < 6; i++)
            emotions.get(i).setCurrentSize((int) (emotions.get(i).beginSize + interpolatedTime * (emotions.get(i).endSize - emotions.get(i).beginSize)));

        // caculate Coodirnate Y of Emotions throught interpolated Time
//            emotions.get(0).currentX = Board.BOARD_X + DIVIDE;
//            emotions.get(5).currentX = Board.BOARD_X + Board.BOARD_WIDTH - DIVIDE - emotions.get(5).getCurrentSize();
        for (int i = 1; i < 6; i++)
            emotions.get(i).currentX = emotions.get(i - 1).currentX + emotions.get(i - 1).getCurrentSize() + ReactionView.DIVIDE;
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
        view.invalidate();
    }
}