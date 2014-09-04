package com.explore.lapometer.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TableLayout;

import com.explore.lapometer.R;

/**
 * ParticipantNodeView is a custom view to represent a participant. We can edit the chest code,
 * opacity and the color of the node and chest code etc.
 */
public class ParticipantNodeView extends View {
    private int chestCode; // Chest code of the participant
    private int nodeColor = Color.RED; // Color of the ring used to represent the node
    private int chestCodeColor = Color.RED; // Color of the chest code.
    private int chestCodeOpacity = 255; // Opacity of the chest code.
    private int nodeOpacity = 255; // Opacity of the ring.
    private int fontSize = 50; // Opacity of the chest code.

    private TextPaint textPaintChessCode;
    private Paint nodePainter;
    private float mTextWidth;
    private float mTextHeight;

    public ParticipantNodeView(Context context) {
        super(context);
        init(null, 0);
    }

    public ParticipantNodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ParticipantNodeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ParticipantNodeView, defStyle, 0);

        chestCode = a.getInteger(
                R.styleable.ParticipantNodeView_chestCode, 0);
        chestCodeColor = a.getColor(
                R.styleable.ParticipantNodeView_chestCodeColor,
                Color.WHITE);
        nodeColor = a.getColor(
                R.styleable.ParticipantNodeView_nodeColor,
                Color.WHITE);
        chestCodeOpacity = a.getColor(
                R.styleable.ParticipantNodeView_chestCodeOpacity,
                255);
        nodeOpacity = a.getColor(
                R.styleable.ParticipantNodeView_nodeOpacity,
                255);
            a.recycle();
        fontSize = a.getInteger(
                R.styleable.ParticipantNodeView_fontSize, 50);
        mTextHeight = getHeight();
        mTextWidth = getWidth();

        // Set up a default TextPaint object
        textPaintChessCode = new TextPaint();
        textPaintChessCode.setFlags(Paint.ANTI_ALIAS_FLAG);
        textPaintChessCode.setTextAlign(Paint.Align.CENTER);
        textPaintChessCode.setColor(chestCodeColor);
        textPaintChessCode.setTypeface(Typeface.DEFAULT_BOLD);
        textPaintChessCode.setTextSize(fontSize);

        textPaintChessCode.setAlpha(chestCodeOpacity);

        //Set up a default NodePaint object
        nodePainter = new Paint();
        nodePainter.setFlags(Paint.ANTI_ALIAS_FLAG);
        nodePainter.setStyle(Paint.Style.STROKE);
        nodePainter.setStrokeWidth(3);
        nodePainter.setColor(nodeColor);
        nodePainter.setAlpha(nodeOpacity);

        // Update TextPaint and text measurements from attributes
        invalidate();
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;
        int radius = contentHeight > contentWidth ? contentWidth/2: contentHeight/2;
        radius -= 5;
        // Draw the text.
        Rect textBounds = new Rect();
        textPaintChessCode.getTextBounds("88", 0, 2, textBounds);
        canvas.drawText("" +chestCode,
                paddingLeft + (contentWidth - mTextWidth) / 2,
                paddingTop + (contentHeight + mTextHeight) / 2 + 30,
                textPaintChessCode);
        canvas.drawCircle(
                paddingLeft + (contentWidth - mTextWidth) / 2,
                paddingTop + (contentHeight + mTextHeight) / 2,
                radius,
                nodePainter);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec - getPaddingRight() - getPaddingLeft(),
                heightMeasureSpec - getPaddingBottom() - getPaddingTop());
    }

    /**
     * Returns the chest code of participant.
    */
    public int getChestCode() {
        return chestCode;
    }

    /**
     * Sets the chest code of the participant.
     * @param chestCode
     * The chest code of the participant
     */
    public void setChestCode(int chestCode) {
        this.chestCode = chestCode;
        invalidate();
        requestLayout();
    }

    /**
     * Returns the color used to draw chest code.
     */
    public int getChestCodeColor() {
        return chestCodeColor;
    }

    /**
     * Sets the colot to draw the chest code.
     * @param chestCodeColor
     * The color of the chest code in integer format.
     */
    public void setChestCodeColor(int chestCodeColor) {
        this.chestCodeColor = chestCodeColor;
        invalidate();
        requestLayout();
    }

    /**
     * Returns color of the node.
     */
    public int getNodeColor() {
        return nodeColor;
    }

    /**
     * Sets the color used to draw the node.
     * @param nodeColor
     * The color of the node in integer format.
     */
    public void setNodeColor(int nodeColor) {
        this.nodeColor = nodeColor;
        invalidate();
        requestLayout();
    }

    /**
     * Returns opacity of the node.
     */
    public int getNodeOpacity() {
        return nodeOpacity;
    }

    /**
     * Sets the opacity of the node.
     * @param nodeOpacity
     * Opacity of the node (From 0 to 255).
     */
    public void setNodeOpacity(int nodeOpacity) {
        this.nodeOpacity = nodeOpacity;
        invalidate();
        requestLayout();
    }


    /**
     * Returns opacity of the chest code.
     */
    public int getChestCodeOpacity() {
        return chestCodeOpacity;
    }

    /**
     * Sets the opacity of the chest code.
     * @param chestCodeOpacity
     * Opacity of the node (From 0 to 255).
     */
    public void setChestCodeOpacity(int chestCodeOpacity) {
        this.chestCodeOpacity = chestCodeOpacity;
        invalidate();
        requestLayout();
    }
}
