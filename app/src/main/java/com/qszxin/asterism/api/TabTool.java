package com.qszxin.asterism.api;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by 倾水折心 on 2016/3/4.
 * 自定义Tab
 */
public class TabTool {
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private RelativeLayout[] RL;
    private ImageView[] Iv;

    private int tabNum;
    private int theme_color;
    private int old_color;
    private static int currentIndex = 0;

    public TabTool(int[] rl_id,int[] iv_id){

    }
    public void setTabIcon(int[] icon){

    }
    public void setTabTitle(String[] title){

    }

    private void initListener()
    {
        for(int i = 0;i<this.RL.length;i++)
            RL[i].setOnClickListener(new btnListener(i));
    }

    public void setColor(int[] color_id){
        this.old_color = color_id[0];
        this.theme_color = color_id[1];
        Iv[currentIndex].setColorFilter(this.theme_color);
    }
    public class btnListener implements LinearLayout.OnClickListener{
        private int index=0;
        public btnListener(int i) {
            index =i;
        }
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            mViewPager.setCurrentItem(index,false);
        }
    }
    protected void resetTabBtnColor(int lastPos,int currentPos) {
        Iv[lastPos].setColorFilter(this.old_color);
        Iv[currentPos].setColorFilter(this.theme_color);
    }

}
