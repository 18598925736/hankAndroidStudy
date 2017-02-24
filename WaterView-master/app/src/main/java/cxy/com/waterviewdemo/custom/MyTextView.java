package cxy.com.waterviewdemo.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Hank on 2017/2/22.
 */

public class MyTextView extends TextView {


    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("MyTag", "onMeasure");
        setMeasuredDimension(myMeasure(widthMeasureSpec, 400), myMeasure(heightMeasureSpec, 100));
    }

    /**
     * 如果写自定义组件，那么这段代码基本可以作为 measure 的模板
     *
     * @param measureSpec
     * @param defaultValue
     * @return
     */
    private int myMeasure(int measureSpec, int defaultValue) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        // 精确模式.
        // 如果在xml内部，定义组件的宽/高的时候，使用的是具体的数字+单位，这种组合。
        // 那么就是精确模式，因为在xml里面明确指定了组件的宽/高。
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            //接着上面的说，如果系统指定宽/高的是wrap_content,
            // 那这个就是AT_MOST模式，在这里就必须给它指定一个默认值defaultValue，这样，就优化了代码，
            // 如果这个组件目前还没有内容，所以系统就不知道该分配多大空间给它，于是就默认这个组件的宽/高充满父组件，这个显然不合理，很浪费资源的。
            // 所以这里指定一个默认的最大值，就算组件没有内容，系统也知道最大应该给他多少空间，不会造成浪费
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(defaultValue, specSize);//这里获得的specSize其实是父组件的size，所以要取二者之小
            }
        }
        return result;
    }

    Paint paint1, paint2;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //下面的代码是画边框
        drawRect(canvas);
        super.onDraw(canvas);
        makeWordsBlink();
        canvas.restore();
    }

    //上午先解密一下这个渐变器是怎么起作用的
    private int mTranslate;//这里应该是横坐标，默认为0
    private int frequency = 20;//渐变频率：整个组件的长度分成多少等分来进行渐变

    private void makeWordsBlink() {
        Log.d("MyTag", "makeWordsBlink：mTranslate=" + mTranslate);
        if (mGradientMatrix != null) {//如果渐变器不为空，就执行渐变过程
            mTranslate += mViewWidth / frequency;//这个变量也有点怪怪的，看不懂,先来分析这里
            //将整个view的长度分成50份，每一次向右移动1/50;
            if (mTranslate > 2 * mViewWidth) {//这个是啥意思，我还得琢磨一下,没试验出什么作用，先保留吧
                mTranslate = -mViewWidth;
            }
            mGradientMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            postInvalidateDelayed(100);
        }
    }

    /**
     * 改变textView的边框效果
     *
     * @param canvas
     */
    private void drawRect(Canvas canvas) {
        //初始化画笔
        paint1 = new Paint();
        paint1.setColor(Color.BLACK);
        paint1.setStyle(Paint.Style.FILL);//"充满"模式

        paint2 = new Paint();
        paint2.setColor(Color.GREEN);
        paint2.setStyle(Paint.Style.FILL);//"充满"模式

        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint1);//外边框
        canvas.drawRect(10, 10, getMeasuredWidth() - 10, getMeasuredHeight() - 10, paint2);//内边框
        canvas.save();
    }

    private int mViewWidth;
    private Paint mPaint;
    private LinearGradient mLinearGradient;
    private Matrix mGradientMatrix;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("MyTag", "onSizeChanged");//这里的onSizeChanged只执行了一次
        createLinearGradientAndMatrix();//原来这里搞一个onSizeChanged只是为了创建相关的类？
    }

    private void createLinearGradientAndMatrix() {
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();//经过测量之后确定下来的组件宽度
            if (mViewWidth > 0) {
                mPaint = getPaint();//写text的那个画笔
                Log.d("MyTag", "createLinearGradientAndMatrix:mViewWidth=" + mViewWidth);
                mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0, new int[]{Color.BLACK, Color.CYAN, Color.BLUE}, null, Shader.TileMode.REPEAT);//这个类有点奇怪
                mPaint.setShader(mLinearGradient);//给画笔设置渐变器
                mGradientMatrix = new Matrix();//这里只是为了创建这个“线性渐变器” LinearGradient和“矩阵” Matrix
            }
        }
    }
}
