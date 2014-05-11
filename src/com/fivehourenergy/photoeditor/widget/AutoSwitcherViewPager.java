package com.fivehourenergy.photoeditor.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 
 * @author HuanND
 *
 */
public class AutoSwitcherViewPager extends ViewPager{

    private int TIME_TASK = 5000;
    private Runnable  mSwither = new Runnable() {

        /*
         * (non-Javadoc)
         * @see java.lang.Runnable#run()
         * @author HuanND
         */
        @Override
        public void run() {
            if( AutoSwitcherViewPager.this.getAdapter() != null )
            {
                int count = AutoSwitcherViewPager.this.getCurrentItem();

                if( count ==  (AutoSwitcherViewPager.this.getAdapter().getCount() - 1) )
                {
                    count = 0;
                }else
                {
                    count++;
                }

                AutoSwitcherViewPager.this.setCurrentItem(count, true);
            }
            AutoSwitcherViewPager.this.postDelayed(this, TIME_TASK);
        }
    };


    /**
     * @param context  
     * @return of type AutoSwitcherViewPager
     * Constructor function
     * @author HuanND
     */
    public AutoSwitcherViewPager(Context context) {
        this(context, null);
    }

    /**
     * @param context
     * @param attrs  
     * @return of type AutoSwitcherViewPager
     * Constructor function 
     * @author HuanND
     */
    public AutoSwitcherViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        postDelayed(mSwither, TIME_TASK);
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.view.ViewPager#onTouchEvent(android.view.MotionEvent)
     * @author HuanND
     */
    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        switch (arg0.getAction()) {
        case MotionEvent.ACTION_CANCEL:
        case MotionEvent.ACTION_UP :
            postDelayed(mSwither, TIME_TASK);
            break;

        default:
            removeCallbacks(mSwither);
            break;
        }
        return super.onTouchEvent(arg0);
    }
    
    @Override
    public void setAdapter(PagerAdapter arg0) {
        super.setAdapter(arg0);
    }
    
    public void setTimeTask(int time){
        TIME_TASK = time;
    }

}
