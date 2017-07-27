package com.yjk.ydrawanimationmenu.menu;

import android.app.Fragment;
import android.content.Intent;
import android.hardware.camera2.params.Face;

/**
 * Created by yjk on 2017. 7. 27..
 */

public class ButtonInfomation {
    private int res;
    private Fragment fragment;

    public ButtonInfomation(int res, Fragment fragment){
        this.res = res;
        this.fragment = fragment;
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
    public Fragment getFragment(){
        return fragment;
    }

}
