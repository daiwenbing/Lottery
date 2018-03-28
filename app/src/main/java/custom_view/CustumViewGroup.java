package custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dwb on 2018/3/28.
 */

public class CustumViewGroup extends ViewGroup{
    private Context contexts;
    private int widthPixels;
    public CustumViewGroup(Context context,int width) {
        super(context);
        this.contexts = context;
        this.widthPixels=width;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        System.out.println("-onMeasure--" + getWidth());

        //将所有的子View进行测量，这会触发每个子View的onMeasure函数
        //注意要与measureChild区分，measureChild是对单个view进行测量
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int childCount = getChildCount();

        if (childCount == 0) {//如果没有子View,当前ViewGroup没有存在的意义，不用占用空间
            setMeasuredDimension(0, 0);
        } else {
            if (heightMode == MeasureSpec.AT_MOST) {//如果只有高度是包裹内容
                //宽度设置为ViewGroup自己的测量宽度，高度设置为所有子View的高度总和
                setMeasuredDimension(widthPixels, getMaxHeight());
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        //记录当前的高度位置
        int curHeight = t;
        int curWidth = l;
        boolean isFirst = false;
        boolean isLineFead = false;
        int heights = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int height = child.getMeasuredHeight();
            int width = child.getMeasuredWidth();
//            System.out.println("-onLayout--。。》" + width);
            if (widthPixels - curWidth > width) {//计算行的剩余宽度判断是否换行
                if (isLineFead) {
                    curHeight = heights;
                }
                child.layout(curWidth, curHeight, curWidth + width, heights + height);
                curWidth += width;
                isLineFead = false;
                isFirst = true;
            } else {//此处进行换行处理
                if (isFirst) {
                    curWidth = 0;
                }
                heights += height;
                child.layout(l, heights, curWidth + width, heights + height);
                curWidth += width;
                isFirst = false;
                isLineFead = true;
            }


        }
    }

    /**
     * 计算子view所占的高度，以便父容器自适应包裹所有子view
     * @return
     */
    private int getMaxHeight() {
        int count = getChildCount();
        //记录当前的高度位置
        int curWidth = 0;
        boolean isFirst = false;
        int heights = 0;
        int widthM = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int height = child.getMeasuredHeight();
            int width = child.getMeasuredWidth();
            if (widthPixels - curWidth > width) {
                curWidth += width;
                isFirst = true;
            } else {
                if (isFirst) {
                    curWidth = 0;
                }
                heights += height;
                curWidth += width;
                isFirst = false;
                widthM = heights + height;
            }
        }
        return widthM;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
