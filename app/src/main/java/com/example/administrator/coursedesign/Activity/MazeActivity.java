package com.example.administrator.coursedesign.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.coursedesign.Entity.CustomPopWindow;
import com.example.administrator.coursedesign.Entity.Maze;
import com.example.administrator.coursedesign.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class MazeActivity extends AppCompatActivity implements MyAdapter.LCallBack{
    private Button[][] Btn = new Button[8][8];
    private Button get;

    private int[][] maze = new int[8][8];
    private int[][] path = new int[8][8];
    private int[][] wall = new int[8][8];

    private CustomPopWindow mListPopWindow;
    private MyAdapter myAdapter;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initMaze();
        initComponent();
    }

    /**
     * 初始化迷宫状态
     */
    public void initMaze() {
        for (int i = 0; i < 8; i++) {
            for (int n = 0; n < 8; n++) {
                maze[i][n] = 0;
            }
        }
    }

    /**
     * 初始化相关组件
     */
    private void initComponent() {
        //获取屏幕大小，以合理设定 按钮 大小及位置
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        //自定义layout组件
        RelativeLayout layout = new RelativeLayout(this);
        //这里创建64个按钮，每行放置8个按钮

        int count = 0;
        for (int i = 0; i < 8; i++) {
            for(int n = 0; n < 8; n++) {
                Btn[i][n] = new Button(this);
                Btn[i][n].setId(2000 + i);
                if (count == 0) {
                    Btn[i][n].setText("入口");
                } else if (count == 63) {
                    Btn[i][n].setText("出口");
                }
                Btn[i][n].setBackgroundResource(R.drawable.maze_button);
                //设置按钮的宽度和高度
                RelativeLayout.LayoutParams btParams = new RelativeLayout.LayoutParams(width / 8 - 5, width / 8 - 5);
                //横坐标定位
                btParams.leftMargin = width / 8 * (n % 8);
                //纵坐标定位
                btParams.topMargin = width / 8 * i;
                //将按钮放入layout组件
                layout.addView(Btn[i][n], btParams);
                count++;
            }
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.maze_button, null);
        view.setLayoutParams(lp);
        layout.addView(view);
        this.setContentView(layout);

        count = 0;
        //批量设置监听,这里不需要findId，因为创建的时候已经确定哪个按钮对应哪个Id
        for (int k = 0; k < 8; k++) {
            for (int n = 0; n < 8; n++) {
                //为按钮设置一个标记，来确认是按下了哪一个按钮，但不设置入口和出口的点击事件
                Btn[k][n].setTag(count);
                if (count > 0 && count < 63) {
                    Btn[k][n].setOnClickListener(new Button.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int i = (Integer) v.getTag();
                            //根据i算出二维数组的x和y
                            int x = i / 8;
                            int y = i % 8;
                            if (maze[x][y] == 1) {
                                Btn[x][y].setBackgroundColor(getResources().getColor(R.color.mazeBackground));
                                maze[x][y] = 0;
                            } else {
                                Btn[x][y].setBackgroundColor(getResources().getColor(R.color.button));
                                maze[x][y] = 1;
                            }
                        }
                    });
                }
                count++;
            }
        }

        //随机生成迷宫按钮
        Button random = view.findViewById(R.id.random);
        random.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                resetMaze();
                for (int i = 0; i < 20; i++) {
                    int row = (int)(Math.random() * 8);
                    int col = (int)(Math.random() * 8);
                    //不能再起点和终点添加内容
                    if((row == 0 && col == 0)||(row == 7 && col == 7)) {
                        continue;
                    }
                    Btn[row][col].setBackgroundColor(getResources().getColor(R.color.button));
                    maze[row][col] = 1;
                }
            }
        });

        //清空迷宫按钮
        Button clear = view.findViewById(R.id.clear);
        clear.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                resetMaze();
            }
        });

        //保存迷宫数据按钮
        Button save = view.findViewById(R.id.save);
        save.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Maze mazesave = new Maze();
                mazesave.setMaze(mazeString(maze));
                if (mazesave.save()) {
                    showToast("存储成功");
                } else {
                    showToast("存储失败");
                }
            }
        });

        //取出迷宫数据按钮
        get = view.findViewById(R.id.get);
        get.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                showPopListView();
            }
        });

        //得出通路按钮
        Button result = view.findViewById(R.id.result);
        result.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 8; i++) {
                    for (int n = 0; n < 8; n++) {
                        path[i][n] = 1;
                        wall[i][n] = maze[i][n];
                    }
                }
                showMaze();
                if (findWay(0, 0)) {
                    for (int i = 0; i < 8; i++) {
                        for (int n = 0; n < 8; n++) {
                            Btn[i][n].setClickable(false);
                            if (path[i][n] == 0) {
                                Btn[i][n].setBackgroundColor(getResources().getColor(R.color.red));
                            }
                        }
                    }
                    showPath();
                } else {
                    showToast("抱歉，此迷宫无解");
                }
            }
        });

        //回上界面按钮
        Button back = view.findViewById(R.id.back);
        back.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                openActivity(MainActivity.class);
                finish();
            }
        });
    }

    /**
     * 通过Path找到找到出路
     */
    public boolean findWay(int row, int col) {
        // 将当前的位置设置为不可再走
        wall[row][col] = 1;
        if (row == 7 && col == 7) {
            path[row][col] = 0;
            return true;
        }
        else {
            // 如果可以往右走，而且右方没有越界
            if ((col + 1 < 8) && wall[row][col + 1] == 0) {
                // 如果往这条路能够走到结尾
                if (findWay(row, col + 1)) {
                    path[row][col] = 0;
                    Btn[row][col].setText("→");

                    return true;
                }
            }
            // 如果可以往下走，而且下方没有越界
            if ((row + 1 < 8) && wall[row + 1][col] == 0) {
                // 如果往这条路能够走到结尾
                if (findWay(row + 1, col)) {
                    path[row][col] = 0;
                    Btn[row][col].setText("↓");

                    return true;
                }
            }
            // 如果可以往左走，而且左方没有越界
            if ((col - 1 >= 0) && wall[row][col - 1] == 0) {
                // 如果往这条路能够走到结尾
                if (findWay(row, col - 1)) {
                    path[row][col] = 0;
                    Btn[row][col].setText("←");

                    return true;
                }
            }
            // 如果可以往上走，而且上方没有越界
            if ((row - 1 >= 0) && wall[row - 1][col] == 0) {
                // 如果往这条路能够走到结尾
                if (findWay(row - 1, col)) {
                    path[row][col] = 0;
                    Btn[row][col].setText("↑");

                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 测试用，控制台显示迷宫
     */
    public void showMaze() {
        for (int i = 0; i < 8; i++) {
            for (int n = 0; n < 8;n++) {
                System.out.print(maze[i][n]);
            }
            System.out.println();
        }
    }

    /**
     * 测试用，控制台显示通路
     */
    public void showPath() {
        for (int i = 0; i < 8; i++) {
            for (int n = 0; n < 8;n++) {
                System.out.print(path[i][n]);
            }
            System.out.println();
        }
    }

    /**
     * RecyclerView配置
     */
    private void handleListView(View contentView){
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        MyAdapter adapter = new MyAdapter(mContext,MazeActivity.this);
        adapter.setData(mockData());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    /**
     * 获取迷宫个数加入RecyclerView中
     */
    private List<String> mockData(){
        List<Maze> mazeget = DataSupport.findAll(Maze.class);
        List<String> data = new ArrayList<String>();
        if (mazeget != null && mazeget.size() != 0) {
            for (int i = 0; i < mazeget.size(); i++) {
                data.add(mazeget.get(i).getMaze());
            }
            return data;
        } else {
            return null;
        }
    }

    /**
     * 展示PopListView
     */
    private void showPopListView(){
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_list,null);
        //处理popWindow 显示内容
        handleListView(contentView);
        //创建并显示popWindow
        mListPopWindow= new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                //显示大小
                .size(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
                .create()
                .showAsDropDown(get,0,20);
    }

    /**
     * 重置迷宫
     */
    public void resetMaze() {
        for (int i = 0; i < 8; i++) {
            for (int n = 0; n < 8; n++) {
                Btn[i][n].setClickable(true);
                Btn[i][n].setBackgroundColor(getResources().getColor(R.color.mazeBackground));
                if (i == 0 && n == 0) {
                    Btn[i][n].setText("入口");
                } else if (i == 7 && n == 7) {
                    Btn[i][n].setText("出口");
                } else {
                    Btn[i][n].setText("");
                }
                maze[i][n] = 0;
            }
        }
    }

    /**
     * 因为LitePal的存储不能存储二维数组，只有将maze化成一个字符串来存储
     */
    public String mazeString(int[][] temp) {
        String turn = "";
        for (int i = 0; i < 8; i++) {
            for (int n = 0; n < 8; n++) {
                turn = "" + turn + temp[i][n];
            }
        }
        return turn;
    }

    /**
     * 显示Toast信息
     */
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 页面跳转
     */
    public void openActivity(Class<?> clz) {
        openActivity(clz, null);
    }

    /**
     * 携带数据的页面跳转
     */
    public void openActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 回调接口方法，响应ListView按钮点击事件
     */
    @Override
    public void answer(String oldmaze) {
        mListPopWindow.onDismiss();
        resetMaze();
        for (int i = 0; i < 8; i++) {
            for (int n = 0; n < 8; n++) {
                maze[i][n] = (int) oldmaze.charAt(i * 8 + n);
                if (maze[i][n] == 49) {
                    Btn[i][n].setBackgroundColor(getResources().getColor(R.color.button));
                    maze[i][n] = 1;
                } else {
                    maze[i][n] = 0;
                }
            }
        }
    }
}
