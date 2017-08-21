package com.yjk.yslidemenulibrary.util;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yjk.yslidemenulibrary.R;
import com.yjk.yslidemenulibrary.data.ButtonInfomation;
import com.yjk.yslidemenulibrary.data.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yjk on 2017. 8. 21..
 */

public class BasicMenu extends Dialog {
    public final static String TAG = "BasicMenu";

    private Activity activity;
    private LinearLayout contentView;
    private int parentLayout;

    private boolean close;

    private Data data;
    private Utils utils;

    private List<ButtonInfomation> buttonInfoList;  // init에서 초기화 하지 말것!
    private List<RelativeLayout> buttonlayout;
    private List<ImageButton> buttonList;

    public BasicMenu(Activity activity, List<ButtonInfomation> buttonInfoList) {
        super(activity);
        this.activity = activity;
        this.buttonInfoList = buttonInfoList;

        init();

        LayoutInit();
    }

    public void init(){
        buttonList = new ArrayList<>();
        buttonlayout = new ArrayList<>();
        data = new Data();
        utils = new Utils();
    }

    public void LayoutInit(){
        // layout setting
        LinearLayout linearLayout = new LinearLayout(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        addButton(linearLayout);

        contentView = linearLayout;
    }

    public void addButton(LinearLayout layout){
        RelativeLayout relativeLayout;
        ImageButton button;

        for(ButtonInfomation info : buttonInfoList){
            int res = info.getRes();
            Bitmap bitmap = info.getBitmap();
            Fragment fragment = info.getFragment();

            /*
                리소스나 비트맵에 따라서 버튼의 이미지 설정
             */
            button = null;
            if(res == 0){
                // bitmap imagebutton
                button = utils.createButton(activity, bitmap, data);
            }else{
                // resource imagebutton
                button = utils.createButton(activity, res, data);
            }

            /*
                이미지뷰 ( 메뉴 아이콘 )의 크기 설정
             */
            LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(data.menuIconWidth, data.menuIconHeight);
            button.setLayoutParams(iconParams);

            /*
                아이콘의 크기가 변경되어도 메뉴의 크기가 변경되지 않도록
                아이콘의 외부를 레이아웃으로 감싸서 추가한다.
             */
            relativeLayout = new RelativeLayout(activity);
            RelativeLayout.LayoutParams menuButtonParams = new RelativeLayout.LayoutParams(data.menuButtonWidth, data.menuButtonHeight);
            relativeLayout.setLayoutParams(menuButtonParams);
            relativeLayout.setGravity(Gravity.CENTER);
            relativeLayout.setBackgroundColor(Color.parseColor(data.menuBackground));
            relativeLayout.addView(button);

            // set start animation
            setOpenAnimation(relativeLayout);

            // add Click listener
            relativeLayout.setOnClickListener(new MenuButtonOnClickListener(activity, fragment));

            // addView
            layout.addView(relativeLayout);
            buttonlayout.add(relativeLayout);
            buttonList.add(button);
        }
    }

    /*
        화면 터치이벤트 발생 시
    */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Rect dialogBounds = new Rect();
        getWindow().getDecorView().getHitRect(dialogBounds);

        // 이벤트의 범위가 Dialog의 밖일 경우
        if(!dialogBounds.contains((int)ev.getX(), (int)ev.getY()) && close && ev.getAction()==MotionEvent.ACTION_DOWN){
            close = !close;
            startCloseAnimation();
        }
        return super.dispatchTouchEvent(ev);
    }

    /*
        시작 애니메이션
     */
    public void setOpenAnimation(View view){

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.open_left);
        animation.setDuration(data.duration);
        animation.setFillAfter(true);
        view.setAnimation(animation);
    }

    /*
        종료 애니메이션
     */
    public void startCloseAnimation(){
        int count = 0;
        for (RelativeLayout layout : buttonlayout) {
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.close_left);
            animation.setDuration(data.duration);
            animation.setFillAfter(true);

            //마지막 애니메이션에 종료이벤트 설정
            if(count >= buttonlayout.size()-1){
                animation.setAnimationListener(new BasicMenu.CloseAnimListener());
            }

            layout.startAnimation(animation);
            count++;
        }
    }

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
            for (RelativeLayout layout : buttonlayout) {
                layout.setClickable(false);
                layout.setBackgroundColor(Color.parseColor(data.menuBackground));
            }

            // 클릭된 버튼 색깔 변경
            view.setBackgroundColor(Color.parseColor(data.menuButtonBackground));

            // 외부 화면 클릭 시 변동 없음
            close = false;

            // 화면 전환 애니메이션 효과
            View myView = activity.findViewById(parentLayout);
            int finalRadius = Math.max(myView.getWidth(), myView.getHeight());

            int[] location = new int[2];
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];

            Animator animator = ViewAnimationUtils.createCircularReveal(myView, x, y, 0, finalRadius);
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

            // change fragment
            FragmentManager fragmentManager = activity.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(parentLayout, fragment);
            fragmentTransaction.commit();

            try {
                animator.start();
            }catch (Exception e){
                Log.d(TAG,"error :" +e.getMessage());
            }

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // layout 설정
        setContentView(contentView);
        setCanceledOnTouchOutside(false);

        // Dialog의 배경 없애기
        ColorDrawable dialogColor = new ColorDrawable(Color.GRAY);
        dialogColor.setAlpha(0);
        getWindow().setBackgroundDrawable(dialogColor);

        // Dialog의 뒷 배경 검정색으로 바뀌는 것 막기
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        // Dialog 정렬 위치 ( default : Top | Left )
        getWindow().setGravity(data.gravity);

        // layout top margin
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.y = data.marginTop;
        getWindow().setAttributes(params);


        // 클릭 초기화
        close = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 클릭 초기화
        close = true;

        //시작 애니매이션 실행
        int count = 0;
        for (RelativeLayout layout : buttonlayout) {
            layout.setClickable(true);

            // set start animation
            setOpenAnimation(layout);
        }

    }

    public void setParentLayout(int id){
        parentLayout = id;
    }
}
