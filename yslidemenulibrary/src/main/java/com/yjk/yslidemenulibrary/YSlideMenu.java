package com.yjk.yslidemenulibrary;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.yjk.yslidemenulibrary.data.ButtonInfomation;
import com.yjk.yslidemenulibrary.data.Data;
import com.yjk.yslidemenulibrary.util.Rotate3dAnimation;
import com.yjk.yslidemenulibrary.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yjk on 2017. 7. 27..
 * 화면전환, 애니메이션을 자동으로 설정해주는 클래스
 */

public class YSlideMenu extends Dialog {
    public static final String TAG = "YDrawAnimationMenu_LOG";

    private Activity activity;

    private Data data;
    private Utils utils;

    private LinearLayout layout; // 메뉴 버튼이 들어갈 레이아웃

    private List<ButtonInfomation> buttonInfoList;  // init에서 초기화 하지 말것!
    private List<ImageButton> buttonList;

    private int parentLayout;   // 변경할 레이아웃
    private boolean close = true;

    public YSlideMenu(Activity activity, List<ButtonInfomation> buttonInfoList) {
        super(activity);
        this.activity = activity;
        this.buttonInfoList = buttonInfoList;

        data = new Data();
        utils = new Utils();

        buttonList = new ArrayList<>();
    }

    public void setParentLayout(int id){
        parentLayout = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // layout 설정
        setContentView(R.layout.y_slide_menu_dialog);
        setCanceledOnTouchOutside(false);
        layout = (LinearLayout)findViewById(R.id.ySlideMenuDialog);
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
        addButton();

        // set ScrollBar
        ScrollView scrollView = (ScrollView)findViewById(R.id.ySlideMenuScrollView);
        scrollView.setVerticalScrollBarEnabled(data.scrollBar);
    }

    /****************************
      *  create menu button
      *  setting animation
      *  setting Click listener
      *  addView
     ****************************/
    private void addButton(){
        ImageButton button;
        int count = 0;

        for(ButtonInfomation info : buttonInfoList){
            int res = info.getRes();
            Fragment fragment = info.getFragment();

            // create menu button
            button = utils.createButton(activity, res, data);

            // set click listener
            button.setOnClickListener(new MenuButtonOnClickListener(activity, fragment));

            // set button background
            button.setBackgroundColor(Color.parseColor(data.menuBackground));

            // set start animation
            setOpenAnimation(button, count * data.delay);

            // addView
            layout.addView(button);
            buttonList.add(button);

            // for delay
            count++;
        }
    }


    /*
        화면 터치이벤트 발생 시
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Rect dialogBounds = new Rect();
        getWindow().getDecorView().getHitRect(dialogBounds);

        // 이벤트의 범위가 Dialog의 밖일 경우 && 종료 이벤트 중이 아닐 경우 && 스크롤 중이 아닐 경우
        if(!dialogBounds.contains((int)ev.getX(), (int)ev.getY()) && close && ev.getAction()==MotionEvent.ACTION_DOWN){
            startCloseAnimation();
        }
        return super.dispatchTouchEvent(ev);
    }

    /*
        시작 애니메이션
     */
    public void setOpenAnimation(View view, int delay){
        float start = 90f;
        float end = 0f;

        float centerX = view.getWidth()/2.0f;
        float centerY = view.getHeight()/2.0f;

        Rotate3dAnimation rotate = new Rotate3dAnimation(start, end, centerX, centerY, 0, false);
        rotate.setDuration(data.duration);
        rotate.setStartOffset( delay );
        view.setAnimation(rotate);
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
            rotate.setStartOffset( count * data.delay );
            button.setAnimation(rotate);

            //마지막 애니메이션에 종료이벤트 설정
            if(count >= buttonList.size()-1){
                rotate.setAnimationListener(new CloseAnimListener());
            }

            button.startAnimation(rotate);
            count++;
        }
    }


    /******************
     *
     *  리스너
     *
     ******************/

    /*
        메뉴 버튼 클릭 리스너 ( Fragment 전환 )
     */
    class MenuButtonOnClickListener implements View.OnClickListener{
        private Activity activity;
        private Fragment fragment;

        public MenuButtonOnClickListener(Activity activity, Fragment fragment){
            this.activity = activity;
            this.fragment = fragment;
        }

        @Override
        public void onClick(View view) {
            //한번 클릭 시 더 이상 클릭 불가
            for (ImageButton button : buttonList) {
                button.setClickable(false);
            }

            // 외부 화면 클릭 시 변동 없음
            close = false;

            // 화면 전환 애니메이션 효과
            View myView = activity.findViewById(parentLayout);
            int finalRadius = Math.max(myView.getWidth(), myView.getHeight());

            Animator animator = ViewAnimationUtils.createCircularReveal(myView, (int)view.getX(), (int)view.getY(), 0, finalRadius);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(data.circleDuration);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {}
                @Override
                public void onAnimationEnd(Animator animator) {
                    startCloseAnimation();
                }
                @Override
                public void onAnimationCancel(Animator animator) {}
                @Override
                public void onAnimationRepeat(Animator animator) {}
            });
            animator.start();

            // change fragment
            FragmentManager fragmentManager = activity.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(parentLayout, fragment);
            fragmentTransaction.commit();
        }
    }

    /*
        종료 감지 리스너 ( Animation )
     */
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


    /*****************************
     *
     * 사용자 설정 가능한 메소드
     *
     *****************************/
    public void setMenuButtonBackground(String color){
        data.menuBackground = color;
    }
    public void setMenuButtonSize(int size){
        data.menuLayoutWidth = size;
        data.menuButtonWidth = size;
        data.menuButtonHeight = size;
    }
    public void setMenuButtonIconSize(int size) {
        data.menuIconWidth = size;
        data.menuIconHeight = size;
    }
    public void setMenuButtonDelay(int delay){
        data.delay = delay;
    }
    public void setMenuButtonDuration(int duration){
        data.duration = duration;
    }
    public void setTransformDuration(int duration){
        data.circleDuration = duration;
    }
}
