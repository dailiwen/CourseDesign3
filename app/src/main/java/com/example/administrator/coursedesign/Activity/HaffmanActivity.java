package com.example.administrator.coursedesign.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.coursedesign.Entity.CustomPopWindow;
import com.example.administrator.coursedesign.Entity.DrawLine;
import com.example.administrator.coursedesign.Entity.DrawView;
import com.example.administrator.coursedesign.Entity.HuffmanTree;
import com.example.administrator.coursedesign.R;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author dailiwen
 */
public class HaffmanActivity extends AppCompatActivity {

    FloatingActionButton btn1;
    FloatingActionButton btn2;
    DrawView view;
    DrawLine viewLine;
    RelativeLayout layout;

    View contentView;
    View btnView;
    EditText popwinInput;
    Button popwinDemine;
    Button popwinCancle;

    private CustomPopWindow mPopwindow;
    private int width;
    private int height;

    private int directionJud = 1;

    /**
     * 文件路径
     */
    private String commonFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_haffman);
        ButterKnife.bind(this);
        initComponent();
        initListener();
    }

    private void initComponent() {
        layout = (RelativeLayout) findViewById(R.id.root);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LayoutInflater inflater = LayoutInflater.from(this);
        btnView = this.getLayoutInflater().inflate(R.layout.haffman_button, null);
        btnView.setLayoutParams(lp);
        layout.addView(btnView);
        btn1 = btnView.findViewById(R.id.Btn1);
        btn2 = btnView.findViewById(R.id.Btn2);

        contentView = LayoutInflater.from(HaffmanActivity.this).inflate(R.layout.haffman_input,null);
        popwinInput = contentView.findViewById(R.id.popinput);
        popwinDemine = contentView.findViewById(R.id.popin);
        popwinCancle = contentView.findViewById(R.id.popcancle);

        //获取屏幕大小，以合理设定按钮大小及位置
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopListView();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                //设置类型，我这里是任意类型，任意后缀的可以这样写。
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent,1);
            }
        });

        popwinDemine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = popwinInput.getText().toString();
                List<HuffmanTree.Node> nodes = new ArrayList<HuffmanTree.Node>();
                Map<String,Integer> map = letterCount(input);
                Set<String> keys = map.keySet();
                for(String key : keys){
                    nodes.add(new HuffmanTree.Node(key,map.get(key)));
                }
                HuffmanTree.Node root = HuffmanTree.createTree(nodes);
                Log.d("dailiwen", String.valueOf(HuffmanTree.breadthFirst(root)));
                drawTree(HuffmanTree.breadthFirst(root));
                mPopwindow.onDismiss();
            }
        });

        popwinCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopwindow.onDismiss();
            }
        });
    }

    /**
     * 显示Toast信息
     */
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 展示PopListView
     */
    private void showPopListView() {
        mPopwindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                //显示大小
                .size(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
                .enableBackgroundDark(true)
                .create()
                .showAtLocation(contentView,Gravity.CENTER,0,0);
    }

    /**
     * 获取输入框字符串，每个字符的出现次数
     */
    public Map letterCount(String s) {
        s=s.replaceAll(" +", "");
        String[] strs = s.split("");
        Map<String,Integer> map = new TreeMap<String, Integer>();
        for (String str : strs) {
            if (!"".equals(str)) {
                map.put(str, stringCount(s, str));
            }
        }
        Log.d("dailiwen",map.toString());
        return map;
    }

    /**
     * 巧用split,进行计数
     */
    public int stringCount(String maxstr, String substr) {
        // 注意
        // 1.比如qqqq,没有找到,则直接返回这个字符串
        // 2.比如qqqjava,末尾没有其他字符,这时也不会分割,所以可以添加一个空格
        // 3.java11开头没有字符，没有关系，自动空填充
        // 4.对于特殊字符,要注意使用转义符
        int count = (maxstr + " ").split(substr).length - 1;
        // System.out.println("\"" + minstr + "\"" + "字符串出现次数：" + count);
        return count;
    }

    /**
     * 绘画哈夫曼树的起始
     */
    public void drawTree(List<HuffmanTree.Node> list) {
        layout.removeAllViews();
        displayTree(list.get(0), width / 2, 180, 400, directionJud);
        layout.addView(btnView);
    }

    /**
     * 利用递归，逐渐调整X,Y的坐标，实现绘制哈夫曼树的算法
     */
    private void displayTree(HuffmanTree.Node root,int x, int y, int xGap, int directionJud) {
        if ("null".equals(root.getData() + "")) {
            view = new DrawView(this, root.getWeight(), x, y, xGap, directionJud);
            //通知view组件重绘
            view.invalidate();
            layout.addView(view);
        } else {
            view = new DrawView(this, root.getData() + "", root.getWeight(), x, y, xGap, directionJud);
            //通知view组件重绘
            view.invalidate();
            layout.addView(view);
        }

        if (root.getLeftChild() != null) {
            directionJud = 1;
            displayTree(root.getLeftChild(), x - xGap, y + 180, xGap / 2, directionJud);
            viewLine = new DrawLine(this, x, y, xGap, directionJud);
            view.invalidate();
            layout.addView(viewLine);
        }

        if (root.getRightChild() != null) {
            directionJud = 2;
            displayTree(root.getRightChild(),x + xGap, y + 180, xGap / 2, directionJud);
            viewLine = new DrawLine(this, x, y, xGap, directionJud);
            view.invalidate();
            layout.addView(viewLine);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //是否选择，没选择就不会继续
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            String scheme = uri.getScheme();
            //从系统文件管理器中查找文件
            if (requestCode == 1) {
                //得到文件的真实路径
                if (scheme == null) {
                    commonFilePath = uri.getPath();
                } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
                    Cursor cursor = this.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
                    if (null != cursor) {
                        if (cursor.moveToFirst()) {
                            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                            if (index > -1) {
                                commonFilePath = cursor.getString(index);
                            }
                        }
                        cursor.close();
                    }
                }
                //读取文件
                readFile(commonFilePath);
            }
        }
    }

    /**
     * 根据指定路径，读取文件
     * @param path
     */
    public void readFile(String path){
        File file = new File(path);
        StringBuilder content = new StringBuilder();
        try{
            FileInputStream is = new FileInputStream(file);
            if(null != is){
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                //临时变量，用于暂时存储读取出来的一行数据
                String line = "";
                //逐行读取
                while (true){
                    line = br.readLine();
                    if(line == null){
                        break;
                    }
                    content.append(line);
                }
                List<HuffmanTree.Node> nodes = new ArrayList<HuffmanTree.Node>();
                Map<String,Integer> map = letterCount(content.toString());
                Set<String> keys = map.keySet();
                for(String key : keys){
                    nodes.add(new HuffmanTree.Node(key,map.get(key)));
                }
                HuffmanTree.Node root = HuffmanTree.createTree(nodes);
                HuffmanTree.breadthFirst(root);
                isr.close();
                br.close();
                is.close();
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            Toast.makeText(this, "读取错误", Toast.LENGTH_SHORT).show();
        }
    }

}
