package com.yjk.ydrawanimationmenu.data;

import android.view.Gravity;

/**
 * Created by yjk on 2017. 7. 26..
 * 사용자가 변경 가능한 변수 클래스
 */

public class Data {
    public int menuLayoutWidth = 280;  // 메뉴 아이콘이 들어가는 레이아웃
    public int menuButtonWidth = 280;  // 메뉴 버튼 배경의 가로
    public int menuButtonHeight = 280; // 메뉴 버튼 배경의 세로
    public int menuIconWidth = 150;    // 메뉴 아이콘의 가로
    public int menuIconHeight = 150;   // 메뉴 아이콘의 세로

    public String menuBackground = "#FF424859";     // 메뉴 버튼의 배경 ( 레이아웃의 배경이 아님 )
    public String menuButtonBackground = "#0377d2"; // 클릭된 버튼 배경

    public int delay = 100;             // 메뉴 버튼과 버튼 사이의 애니메이션 딜레이
    public int duration = 500;          // 애니메이션 시간
    public int circleDuration = 1000;   // 화면 전환 시

    public boolean scrollBar = false;   // 스크롤바의 막대 유무
    public int center = Gravity.TOP;    // 레이아웃의 정렬 ( 위쪽, 중앙 )
    public int point = Gravity.LEFT;    // 레이아웃의 위치 ( 왼쪽, 오른쪽 )

    public int openCenterX = 0;
    public int closeCenterX = 0;

}
