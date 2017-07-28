# YSlideMenu
-----
This project is Sliding menu.<br>
I made a project that was made open source but it was hard to use.. So I made this project<br>
This YSlideMenu is made up of dialog. Thanks!<br>

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
    compile 'com.github.jjongkwon2:YSlideMenu:1.0.1'
}
```


<br>

### Basic Usage
-----
step 1. create button list and add button icon, fragment<br>
- icon is menu button icon<br>
- fragment is next screen
``` java
List<ButtonInfomation> buttonList = new ArrayList<>();
buttonList.add(new ButtonInfomation(R.drawable.icn_1, new Fragment1()));
buttonList.add(new ButtonInfomation(R.drawable.icn_1, new Fragment2()));
buttonList.add(new ButtonInfomation(R.drawable.icn_1, new Fragment3()));
buttonList.add(new ButtonInfomation(R.drawable.icn_1, new Fragment4()));
buttonList.add(new ButtonInfomation(R.drawable.icn_1, new Fragment5()));
```

step 2. Prepare the layout Id for the next screen
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
menu.show();
```
<br>
*Run !*

Full source
``` java
public class MainActivity extends AppCompatActivity {

    YSlideMenu menu;
    List<ButtonInfomation> buttonList;

    Button menuBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuBtn = (Button)findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNavigationMenu();
            }
        });
    }

    public void showNavigationMenu(){
        buttonList = new ArrayList<>();
        buttonList.add(new ButtonInfomation(R.drawable.icn_1, new Fragment1()));
        buttonList.add(new ButtonInfomation(R.drawable.icn_1, new Fragment2()));
        buttonList.add(new ButtonInfomation(R.drawable.icn_1, new Fragment3()));
        buttonList.add(new ButtonInfomation(R.drawable.icn_1, new Fragment4()));
        buttonList.add(new ButtonInfomation(R.drawable.icn_1, new Fragment5()));

        menu = new YSlideMenu(this, buttonList);
        menu.setParentLayout(R.id.parentLayout);
        menu.show();
    }
}
```

<br>

### Custom Menu Usage
-----
Comming soon..


### other
-----
- settings
``` java
public void setMenuButtonBackground(String color)
public void setMenuButtonSize(int size)
public void setMenuButtonIconSize(int size)
public void setMenuButtonDelay(int delay)
public void setMenuButtonDuration(int duration)
public void setTransformDuration(int duration)
```
