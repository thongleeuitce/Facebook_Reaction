package com.example.thongle.facebook_reaction.Models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.thongle.facebook_reaction.Utils.ConvertDp2Px;
import com.example.thongle.facebook_reaction.R;
import com.example.thongle.facebook_reaction.ReactionView;

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

       inital_TitleView(this.title);
    }

    private void inital_TitleView(String title) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View titleView = inflater.inflate(R.layout.title_view, null);
        ((TextView) titleView).setText(title);

        int w = (int) context.getResources().getDimension(R.dimen.width_title);
        int h = (int) context.getResources().getDimension(R.dimen.height_title);
        ratioWH = (w * 1.0f) / (h * 1.0f);
        imageTitle = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(imageTitle);
        titleView.layout(0, 0, w, h);
        titleView.draw(c);
    }
    public void drawTitle(Canvas canvas) {
        int width = (currentSize - NORMAL_SIZE);
        int height = (int) (width / ratioWH);

        titlePaint.setAlpha(ReactionView.MAX_ALPHA * width / MAX_WIDTH_TITLE);
        if (width <= 0 || height <= 0) return;
        float x = currentX + (currentSize - width) / 2;
        float y = currentY - DISTANCE - height;

        canvas.drawBitmap(imageTitle, null, new RectF(x, y, x + width, y + height), titlePaint);
    }
    public String getTitle() {
        return title;
    }

    public void drawEmotion(Canvas canvas) {
        canvas.drawBitmap(imageOrigin, null, new RectF(currentX, currentY, currentX + currentSize, currentY + currentSize), emotionPaint);
        drawTitle(canvas);
    }
    public int getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
        this.currentY = Board.BASE_LINE - this.currentSize;
    }
}
