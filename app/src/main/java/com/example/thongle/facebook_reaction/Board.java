package com.example.thongle.facebook_reaction;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by thongle on 06/05/2017.
 */

public class Board {

    public static final int BOARD_WIDTH = 6 * Emotion.NORMAL_SIZE + 7 * ReactionView.DIVIDE;    //DIVIDE = 5dp, Emotion.NORMAL_SIZE = 40dp
    public static final int BOARD_HEIGHT_NORMAL = ConvertDp2Px.dp_to_px(50);                    // height of Board (normal) = 50dp
    public static final int BOARD_HEIGHT_MINIMAL = ConvertDp2Px.dp_to_px(38);                   // height of Board (minimal) = 38dp

    public static final float BOARD_X = 10;
    public static final float BOARD_BOTTOM = ReactionView.HEIGHT_VIEW_REACTION - 200;
    public static final float BOARD_Y = BOARD_BOTTOM - BOARD_HEIGHT_NORMAL;
    public static final float BASE_LINE = BOARD_Y + Emotion.NORMAL_SIZE + ReactionView.DIVIDE;

    public Paint boardPaint;

    public float currentHeight = BOARD_HEIGHT_NORMAL;
    public float currentY = BOARD_Y;

    public float beginHeight;
    public float endHeight;

    public float beginY;
    public float endY;

    public Board(Context context) {
        initPaint(context);
    }
    private void initPaint(Context context) {
        boardPaint = new Paint();
        boardPaint.setAntiAlias(true);
        boardPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        boardPaint.setColor(context.getResources().getColor(R.color.white));
        boardPaint.setShadowLayer(5.0f, 0.0f, 2.0f, 0xFF000000);
    }
    public void setCurrentHeight(float newHeight) {
        currentHeight = newHeight;
        currentY = BOARD_BOTTOM - currentHeight;
    }

    public float getCurrentHeight() {
        return currentHeight;
    }

    public void drawBoard(Canvas canvas) {
        float radius = currentHeight / 2;
        RectF board = new RectF(BOARD_X, currentY, BOARD_X + BOARD_WIDTH, currentY + currentHeight);
        canvas.drawRoundRect(board, radius, radius, boardPaint);
    }
}
