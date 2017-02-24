package cxy.com.waterviewdemo.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cxy.com.waterviewdemo.R;

/**
 * Created by Hank on 2017/2/23.
 */

public class MyTopBar extends RelativeLayout {

    private Context ctx;

    //定义属性
    private int mLeftTextColor;
    private Drawable mLeftBackground;
    private String mLeftText;
    private float mLeftTextSize;

    private String title;
    private float titltTextSize;
    private int titleTextColor;

    private int mRightTextColor;
    private Drawable mRightBackground;
    private String mRightText;
    private float mRightTextSize;

    //定义子view
    private Button mLeftButton, mRightButton;
    private TextView mTitleView;

    //子view的布局方式
    private RelativeLayout.LayoutParams mLeftLayoutParams, mRightLayoutParams, mTitleLayoutParams;


    public MyTopBar(Context context) {
        this(context, null);
    }

    public MyTopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.ctx = context;
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyTopBar);//拿到配置

            //从左到右的顺序，并且取出所有对应的属性值
            mLeftTextColor = ta.getColor(R.styleable.MyTopBar_leftTextColor, 0);
            mLeftBackground = ta.getDrawable(R.styleable.MyTopBar_leftBackground);
            mLeftText = ta.getString(R.styleable.MyTopBar_leftText);
            mLeftTextSize = ta.getDimension(R.styleable.MyTopBar_leftTextSize, 10);

            title = ta.getString(R.styleable.MyTopBar_title);
            titltTextSize = ta.getDimension(R.styleable.MyTopBar_titleTextSize, 10);
            titleTextColor = ta.getColor(R.styleable.MyTopBar_titleTextColor, 0);

            mRightTextColor = ta.getColor(R.styleable.MyTopBar_rightTextColor, 0);
            mRightBackground = ta.getDrawable(R.styleable.MyTopBar_rightBackground);
            mRightText = ta.getString(R.styleable.MyTopBar_rightText);
            mRightTextSize = ta.getDimension(R.styleable.MyTopBar_rightTextSize, 10);

            ta.recycle();//回收资源
        }

        addSubViews();
    }

    private void addSubViews() {
        mLeftButton = new Button(ctx);
        mRightButton = new Button(ctx);
        mTitleView = new MyTextView(ctx);

        mLeftButton.setText(mLeftText);
        mLeftButton.setTextSize(mLeftTextSize);
        mLeftButton.setBackground(mLeftBackground);
        mLeftButton.setTextColor(mLeftTextColor);

        mTitleView.setText(title);
        mTitleView.setTextSize(titltTextSize);
        mTitleView.setTextColor(titleTextColor);

        mRightButton.setText(mRightText);
        mRightButton.setTextSize(mRightTextSize);
        mRightButton.setBackground(mRightBackground);
        mRightButton.setTextColor(mRightTextColor);

        //然后设定布局方式
        mLeftLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mLeftLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        addView(mLeftButton, mLeftLayoutParams);//按照这种规则进行添加

        mTitleLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mTitleLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        mTitleView.setGravity(Gravity.CENTER);
        addView(mTitleView, mTitleLayoutParams);

        //然后设定布局方式
        mRightLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRightLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        addView(mRightButton, mRightLayoutParams);//按照这种规则进行添加

        mLeftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                myToolbarClickListener.leftClick();
            }
        });
        mRightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                myToolbarClickListener.rightClick();
            }
        });
    }

    /**
     * 暴露给外界一个设置监听事件的方法
     *
     * @param myToolbarClickListener
     */
    public void setButtonClickListener(MyToolbarClickListener myToolbarClickListener) {
        this.myToolbarClickListener = myToolbarClickListener;
    }

    private MyToolbarClickListener myToolbarClickListener;//定义一个监听器事件

    public interface MyToolbarClickListener {//回调接口

        void leftClick();

        void rightClick();
    }

    public final static int TAG_LEFT_BUTTON = 0;
    public final static int TAG_RIGHT_BUTTON = 1;

    /**
     * 暴露给外界的接口：动态设置左右按钮是否可见
     *
     * @param tag           TAG_LEFT_BUTTON 设置左键
     *                      TAG_LEFT_BUTTON 设置右键
     * @param visiableParam 取值如下:
     *                      View.VISIBLE
     *                      View.INVISIBLE
     *                      View.GONE
     */
    public void setButtonVisble(int tag, int visiableParam) {
        if (tag == TAG_LEFT_BUTTON) {
            mLeftButton.setVisibility(visiableParam);
        } else if (tag == TAG_RIGHT_BUTTON) {
            mRightButton.setVisibility(visiableParam);
        }
    }

}
