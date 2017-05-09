package com.example.thongle.facebook_reaction.Animations;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.example.thongle.facebook_reaction.Models.Board;
import com.example.thongle.facebook_reaction.Utils.EaseOutBack;
import com.example.thongle.facebook_reaction.Models.Emotion;
import com.example.thongle.facebook_reaction.ReactionView;

import java.util.ArrayList;

/**
 * Created by thongle on 09/05/2017.
 */


public class BeginEmotionAnimation extends Animation {
    View view;
    private Board board;
    private ArrayList<Emotion> emotions = new ArrayList<>();
    private EaseOutBack easeOutBack;

    public BeginEmotionAnimation(View view, Board board, ArrayList<Emotion> emotions) {
        this.view = view;
        this.board = board;
        this.emotions = emotions;
        setvalues_beforeAnimateBeginning();
        setDuration(ReactionView.DURATION_BEGINNING_ANIMATION);
    }

    public void setvalues_beforeAnimateBeginning() {
        board.beginHeight = Board.BOARD_HEIGHT_NORMAL;
        board.endHeight = Board.BOARD_HEIGHT_NORMAL;

        board.beginY = ReactionView.HEIGHT_VIEW_REACTION + 1;
        board.endY = Board.BOARD_Y;

        easeOutBack = EaseOutBack.newInstance(ReactionView.DURATION_BEGINNING_EACH_ITEM, Math.abs(board.beginY - board.endY), 0);

        for (int i = 0; i < 6; i++) {
            emotions.get(i).endY = Board.BASE_LINE - Emotion.NORMAL_SIZE;
            emotions.get(i).beginY = ReactionView.HEIGHT_VIEW_REACTION + 1;
            if (i == 0)
                emotions.get(i).currentX = Board.BOARD_X + ReactionView.DIVIDE;
            else
                emotions.get(i).currentX = emotions.get(i - 1).currentX + emotions.get(i - 1).currentSize + ReactionView.DIVIDE;
        }
    }
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float currentTime = interpolatedTime * ReactionView.DURATION_BEGINNING_ANIMATION;
        for (int i = 0; i < 6; i++) {
            if (currentTime > 0) {
                board.currentY = board.endY + easeOutBack.getCoordinateYFromTime(Math.min(currentTime, ReactionView.DURATION_BEGINNING_EACH_ITEM));
            }
            if (currentTime >= (i + 1) * 100) {
                emotions.get(i).currentY = emotions.get(i).endY + easeOutBack.getCoordinateYFromTime(Math.min(currentTime - (i + 1) * 100, ReactionView.DURATION_BEGINNING_EACH_ITEM));
            }
        }
        view.invalidate();
    }
}
