package com.example.thongle.facebook_reaction;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by thongle on 06/05/2017.
 */

public class Emotion {
    private Context context;

    private String title;

    public static final int MINIMAL_SIZE = ConvertDp2Px.dp_to_px(28);
    public static final int NORMAL_SIZE = ConvertDp2Px.dp_to_px(40);
    public static final int CHOOSE_SIZE = ConvertDp2Px.dp_to_px(100);
    public static final int DISTANCE = ConvertDp2Px.dp_to_px(15);
    public static final int MAX_WIDTH_TITLE = ConvertDp2Px.dp_to_px(70);

    public int currentSize = NORMAL_SIZE;
    public int beginSize;
    public int endSize;

    public float currentX;
    public float currentY;
    public float beginY;
    public float endY;

    public Bitmap imageOrigin;
    public Bitmap imageTitle;

    public Paint emotionPaint;
    public Paint titlePaint;
    private float ratioWH;

    public Emotion(Context context, String title, int imageResource) {
        this.context = context;
        this.title = title;

        imageOrigin = BitmapFactory.decodeResource(context.getResources(), imageResource);

        emotionPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
        emotionPaint.setAntiAlias(true);

        titlePaint = new Paint(Paint.FILTER_BITMAP_FLAG);
        titlePaint.setAntiAlias(true);
       // generateTitleView(title);
    }
    public String getTitle() {
        return title;
    }
    public void drawEmotion(Canvas canvas) {
        canvas.drawBitmap(imageOrigin, null, new RectF(currentX, currentY, currentX + currentSize, currentY + currentSize), emotionPaint);
        //drawTitle(canvas);
    }
    public int getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
        this.currentY = Board.BASE_LINE - this.currentSize;
    }
}
