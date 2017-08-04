package com.yjk.ydrawanimationmenu;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.yjk.ydrawanimationmenu.data.ButtonInfomation;
import com.yjk.ydrawanimationmenu.menu.YBasicMenu;
import com.yjk.ydrawanimationmenu.menu.YSlideMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    public static final String TAG = "YDrawAnimationMenu_LOG";

//    YDrawAnimationMenu menu;
//    List<ImageButton> buttonList;

    //YSlideMenu menu;
    YBasicMenu menu;
    List<ButtonInfomation> buttonInfoList;


    Button menuBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        naviInit();

        init();
    }

    public void naviInit(){
        buttonInfoList = new ArrayList<ButtonInfomation>();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.write_pen);

        buttonInfoList.add(new ButtonInfomation(bitmap, new Fragment01()));
        buttonInfoList.add(new ButtonInfomation(R.drawable.icn_1, new Fragment01()));
        buttonInfoList.add(new ButtonInfomation(R.drawable.icn_2, new Fragment02()));
        buttonInfoList.add(new ButtonInfomation(R.drawable.icn_3, new Fragment03()));
        buttonInfoList.add(new ButtonInfomation(R.drawable.icn_4, new Fragment04()));
        buttonInfoList.add(new ButtonInfomation(R.drawable.icn_5, new Fragment05()));

        menu = new YBasicMenu(MainActivity.this, buttonInfoList);
        menu.setParentLayout(R.id.parentFrgment);
        menu.setMarginTop(120);
    }

    public void init(){
        menuBtn = (Button)findViewById(R.id.menubutton);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.show();
            }
        });
    }


}
