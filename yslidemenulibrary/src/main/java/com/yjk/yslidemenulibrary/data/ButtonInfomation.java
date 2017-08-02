package com.yjk.yslidemenulibrary.data;

import android.app.Fragment;
import android.graphics.Bitmap;

/**
 * Created by yjk on 2017. 7. 27..
 * res : 아이콘
 * fragment : 다음에 올 화면
 */

public class ButtonInfomation {
    private int res;
    private Bitmap bitmap;
    private Fragment fragment;

    public ButtonInfomation(int res, Fragment fragment){
        this.res = res;
        this.fragment = fragment;
        this.bitmap = null;
    }

    public ButtonInfomation(Bitmap bitmap, Fragment fragment){
        this.bitmap = bitmap;
        this.fragment = fragment;
        this.res = 0;
    }

    public void setRes(int res){
        this.res = res;
    }
    public void setFragment(Fragment fragment){
        this.fragment = fragment;
    }

    public int getRes(){
        return res;
    }
    public Bitmap getBitmap() { return bitmap; }
    public Fragment getFragment(){
        return fragment;
    }

}
