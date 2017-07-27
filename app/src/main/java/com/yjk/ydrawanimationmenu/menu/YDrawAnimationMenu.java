package com.yjk.ydrawanimationmenu.menu;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yjk.ydrawanimationmenu.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yjk on 2017. 7. 26..
 */

public class YDrawAnimationMenu extends Dialog {
    public static final String TAG = "YDrawAnimationMenu_LOG";

    public Context context;
    private int delayTime;

    private Data data;
    private Utils utils;

    private LinearLayout layout; // 메뉴 버튼이 들어갈 레이아웃
    private List<ImageButton> buttonList;

    private List<Integer> resList;  // init에서 초기화 하지 말것!
    private List<Map<Integer, Fragment>> initButtonList;

    public YDrawAnimationMenu(@NonNull Context context, List<Integer> resList) {
        super(context);
        this.context = context;
        this.resList = resList;
    }

    public void init(){
        data = new Data();
        utils = new Utils();

        delayTime = data.delay;

        buttonList = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        // layout 설정
        setContentView(R.layout.y_draw_animation_menu_activity);
        setCanceledOnTouchOutside(false);
        layout = (LinearLayout)findViewById(R.id.yDrawAnimationLayout);
        layout.setLayoutParams(new LinearLayout.LayoutParams(data.menuLayoutWidth, utils.getDisplayHeight(this)));


        // Dialog의 배경 없애기
        ColorDrawable dialogColor = new ColorDrawable(Color.GRAY);
        dialogColor.setAlpha(0);
        getWindow().setBackgroundDrawable(dialogColor);

        // Dialog의 뒷 배경 검정색으로 바뀌는 것 막기
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        // Dialog 왼쪽 정렬
        getWindow().setGravity(Gravity.LEFT);

        // layout에 버튼 추가
        addBasicButton();
    }



    // create menu button & setting animation & addView
    private void addBasicButton(){
        ImageButton button;

        int count = 0;
        float start = 90f;
        float end = 0f;

        for (Integer res : resList) {
            button = utils.createButton(context, res);
            float centerX = button.getWidth()/2.0f;
            float centerY = button.getHeight()/2.0f;

            Rotate3dAnimation rotate = new Rotate3dAnimation(start, end, centerX, centerY, 0, false);
            rotate.setDuration(data.duration);
            rotate.setStartOffset( count * delayTime );
            button.setAnimation(rotate);

            //버튼 호출 시 필요한 id 설정
            button.setId(count);

            layout.addView(button);
            buttonList.add(button);
            count++;
        }


    }



    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
        Rect dialogBounds = new Rect();
        getWindow().getDecorView().getHitRect(dialogBounds);

        // 이벤트의 범위가 Dialog의 밖일 경우
        if(!dialogBounds.contains((int)ev.getX(), (int)ev.getY())){
            startCloseAnimation();
        }
        return super.dispatchTouchEvent(ev);
    }

    /*
        종료 애니메이션
     */
    public void startCloseAnimation(){
        int count = 0;
        float start = 0f;
        float end = 90f;
        for (ImageButton button : buttonList) {
            float centerX = 0; // 좌측으로 사라지게
            float centerY = button.getHeight()/2.0f;

            Rotate3dAnimation rotate = new Rotate3dAnimation(start, end, centerX, centerY, 0, true);
            rotate.setDuration(data.duration);
            rotate.setFillAfter(true);
            rotate.setStartOffset( count * delayTime );
            button.setAnimation(rotate);

            //마지막 애니메이션에 종료이벤트 설정
            if(count >= buttonList.size()-1){
                rotate.setAnimationListener(new CloseAnimListener());
            }

            button.startAnimation(rotate);
            count++;
        }
    }

    class CloseAnimListener implements Animation.AnimationListener{
        @Override
        public void onAnimationStart(Animation animation) {}
        @Override
        public void onAnimationEnd(Animation animation) {
            dismiss();
        }
        @Override
        public void onAnimationRepeat(Animation animation) {}
    }

    public List<ImageButton> getButtonList(){
        return buttonList;
    }

    public void setBackground(String color){
        data.menuBackground = color;
    }

}
