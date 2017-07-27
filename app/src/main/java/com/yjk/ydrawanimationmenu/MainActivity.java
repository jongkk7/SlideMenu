package com.yjk.ydrawanimationmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.yjk.ydrawanimationmenu.menu.ButtonInfomation;
import com.yjk.ydrawanimationmenu.menu.YDrawAnimationMenu;
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

        init();
    }

    public void init(){
        menuBtn = (Button)findViewById(R.id.menubutton);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    buttonInfoList = new ArrayList<ButtonInfomation>();
                    buttonInfoList.add(new ButtonInfomation(R.drawable.icn_1, new Fragment01()));
                    buttonInfoList.add(new ButtonInfomation(R.drawable.icn_2, new Fragment02()));
                    buttonInfoList.add(new ButtonInfomation(R.drawable.icn_3, new Fragment03()));
                    buttonInfoList.add(new ButtonInfomation(R.drawable.icn_4, new Fragment04()));
                    buttonInfoList.add(new ButtonInfomation(R.drawable.icn_5, new Fragment05()));
                    buttonInfoList.add(new ButtonInfomation(R.drawable.icn_6, new Fragment01()));
                    buttonInfoList.add(new ButtonInfomation(R.drawable.icn_7, new Fragment02()));
                    buttonInfoList.add(new ButtonInfomation(R.drawable.icn_1, new Fragment03()));

                    menu = new YSlideMenu(MainActivity.this, buttonInfoList);
                    menu.setParentLayout(R.id.parentFrgment);
                    menu.show();

                }catch (Exception e){
                    Log.d(TAG,"error : " + e.getMessage());
                }
            }
        });
    }


}
