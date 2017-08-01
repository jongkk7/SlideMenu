package com.yjk.ydrawanimationmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.yjk.ydrawanimationmenu.data.ButtonInfomation;
import com.yjk.ydrawanimationmenu.menu.YSlideMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "YDrawAnimationMenu_LOG";

//    YDrawAnimationMenu menu;
//    List<ImageButton> buttonList;

    YSlideMenu menu;
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
        buttonInfoList.add(new ButtonInfomation(R.drawable.icn_1, new Fragment01()));
        buttonInfoList.add(new ButtonInfomation(R.drawable.icn_2, new Fragment02()));
        buttonInfoList.add(new ButtonInfomation(R.drawable.icn_3, new Fragment03()));
        buttonInfoList.add(new ButtonInfomation(R.drawable.icn_4, new Fragment04()));
        buttonInfoList.add(new ButtonInfomation(R.drawable.icn_5, new Fragment05()));

        menu = new YSlideMenu(MainActivity.this, buttonInfoList);
        menu.setParentLayout(R.id.parentFrgment);
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
