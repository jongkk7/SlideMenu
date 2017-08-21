package com.yjk.yslidemenulibrary.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.yjk.yslidemenulibrary.data.Data;

/**
 * Created by yjk on 2017. 7. 26..
 * 화면크기
 * 비트맵 리사이즈
 * 버튼 생성
 */

public class Utils {
    Data data;

    public Utils(){
    }

    /*
        화면 세로 크기 구하는 메소드
        네비게이션 메뉴의 세로 크기 지정을 위해 사용
     */
    public int getDisplayHeight(Dialog activity){
        Display display = activity.getWindow().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size.y;
    }

    /*
        get a bitmap from resource
     */
    public Bitmap getBitmap(Context context, int res){
        return BitmapFactory.decodeResource(context.getResources(), res);
    }

    /*
        original 비트맵을 가로, 세로의 크기로 리사이즈
     */
    public Bitmap resizeBitmap(Bitmap origin, int w, int h){
        Bitmap result = null;

        result = Bitmap.createScaledBitmap(origin, w, h, false);
        if(result != origin){
            origin.recycle();
        }
        return result;
    }

    /*
        이미지로 버튼 생성
     */
    // 리소스로 버튼 생성
    public ImageButton createButton(Context context, int drawable, Data data){
        ImageButton button = new ImageButton(context);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(data.menuIconWidth, data.menuIconHeight);

        button.setLayoutParams(params);

        //이미지 크기 조절 후 삽입
        int w = data.menuIconWidth;
        int h = data.menuIconHeight;
        Bitmap bitmap = resizeBitmap(getBitmap(context, drawable), w, h);
        button.setImageBitmap(bitmap);
        button.setBackgroundColor(Color.parseColor("#00000000"));
        button.setClickable(false);

        return button;
    }
    // 비트맵으로 버튼 생성
    public ImageButton createButton(Context context, Bitmap bitmap, Data data){
        ImageButton button = new ImageButton(context);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(data.menuIconWidth, data.menuIconHeight);

        button.setLayoutParams(params);

        //이미지 크기 조절 후 삽입
        int w = data.menuIconWidth;
        int h = data.menuIconHeight;
        bitmap = resizeBitmap(bitmap, w, h);
        button.setImageBitmap(bitmap);
        button.setBackgroundColor(Color.parseColor("#00000000"));
        button.setClickable(false);

        return button;
    }
}
