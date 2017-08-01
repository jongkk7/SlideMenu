# YSlideMenu
-----
This project is Sliding menu.<br>
There is  a ready-made open source but I made it by myself because it is hard to use.<br>
This YElideMenu is made up of dialogue. Thanks. <br>


![gif](https://github.com/jjongkwon2/YSlideMenu/blob/master/image/yslidemenu_anim.gif)

<br>

### installation
-----
step 1. Add the following to your build.gradle file
``` java
repositories {
        jcenter()
        maven {
            url "https://jitpack.io"   // add code here!
        }
    }

```

step 2. Add the dependency
``` java
dependencies {
    compile 'com.github.jjongkwon2:YSlideMenu:<last version>'
}

// compile 'com.github.jjongkwon2:YSlideMenu:1.0.8'
```

### Basic Usage
-----
step 1. create button list and add button icon, fragment<br>
- icon is menu button icon
- fragment is next screen
``` java
List<ButtonInfomation> buttonList = new ArrayList<>();
buttonList.add(new ButtonInfomation(R.drawable.icn_1, new Fragment1()));
buttonList.add(new ButtonInfomation(R.drawable.icn_1, new Fragment2()));
buttonList.add(new ButtonInfomation(R.drawable.icn_1, new Fragment3()));
buttonList.add(new ButtonInfomation(R.drawable.icn_1, new Fragment4()));
buttonList.add(new ButtonInfomation(R.drawable.icn_1, new Fragment5()));
```

step 2. Prepare the layout Id for the next screen<br>
- This layout is the layout that changes when the button is clicked.
``` xml
<RelativeLayout
        android:id="@+id/parentLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```

step 3. create YSlideMenu<br>
- Once again, this menu is dialog.
``` java
YSlideMenu menu = new YSlideMenu(this, buttonList);
menu.setParentLayout(R.id.parentLayout);
```

step 4. slide menu show!<br>
- ex) menuBtn clicked
``` java
menuBtn = (Button)findViewById(R.id.menuBtn);
menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.show();
            }
        });
```
*Run !*

Full source
``` java
public class MainActivity extends Activity {
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

```

### Custom Menu Usage
-----
Comming soon..

### other
-----
- settings
``` java
public void setMenuButtonBackground(String color) // "#FF424859"
public void setMenuButtonSize(int size)           // 280
public void setMenuButtonIconSize(int size)       // 150
public void setMenuButtonDelay(int delay)         // 100
public void setMenuButtonDuration(int duration)   // 500
public void setTransformDuration(int duration)    // 1000
public void setScrollBar(boolean scrollBar)       // false
public void setLayoutPoint(int gravity)           // Gravity.LEFT || Gravity.RIGHT
public void setCenter(int gravity)                // Gravity.TOP || Gravity.CENTER_VERTICAL || Gravity.BOTTOM
```

### Thanks for
