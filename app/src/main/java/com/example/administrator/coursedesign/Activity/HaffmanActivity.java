package com.example.administrator.coursedesign.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Process;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.administrator.coursedesign.Entity.HuffmanTree.Node1;

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

    private Context mContext;
    private CustomPopWindow mPopwindow;
    private int width;
    private int height;

    private int directionJud = 1;

    private HuffmanTree<Character> huffmanTree;

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
        getPermissions();
        initComponent();
        initListener();
    }

    /**
     * 动态获取读写权限
     * 由于Android 6.0 的权限是一套新的授权机制，所有不能只在AndroidManifest.xml添加权限
     */
    public void getPermissions(){
        int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    /**
     * 初始化组件
     */
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

        mContext = this;
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
                //设置类型，这里设置的是任意类型
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
        s = s.replaceAll(" +", "");
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
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                if (!TextUtils.isEmpty(uri.getAuthority())) {
                    Cursor cursor = getContentResolver().query(uri,
                            new String[] { MediaStore.Images.Media.DATA },null, null, null);
                    if (null == cursor) {
                        Toast.makeText(this, "文件没找到", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    cursor.moveToFirst();
                    commonFilePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    cursor.close();
                } else {
                    commonFilePath = uri.getPath();
                    }
            }else{
                Toast.makeText(this, "文件没找到", Toast.LENGTH_SHORT).show();
                return;
            }
            readFile(commonFilePath);
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
                Map<Character, Integer> map = stringCountForFile(content.toString());
                // 获取entry对象
                Set<Map.Entry<Character, Integer>> entrySet = map.entrySet();
                // 方便遍历
                List<Map.Entry<Character, Integer>> entryArray = new ArrayList<>(entrySet);

                Node1[] nodes = new Node1[entrySet.size()];

                for (int i = 0; i < entrySet.size(); i++) {
                    Map.Entry<Character, Integer> oneEntry = entryArray.get(i);
                    nodes[i] = new Node1(oneEntry.getKey());
                    nodes[i].weight = oneEntry.getValue();
                }
                huffmanTree = new HuffmanTree<Character>(nodes);
                String fileName = file.getName();
                saveFile(huffmanTree, file.getName());
//                Log.d("dailiwen", huffmanTree.toString());
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

    public Map<Character, Integer> stringCountForFile(String letter) {
        Map<Character, Integer> weightingMap = new HashMap<>();

        char[] letters = letter.toCharArray();

        for (int i = 0; i < letters.length; i++) {
            int Amont = 0;

            if (weightingMap.get(letters[i]) != null) {
                Amont = weightingMap.get(letters[i]);
            }
            weightingMap.put(letters[i], Amont + 1);
        }

        return weightingMap;
    }

    public static void saveFile(HuffmanTree<Character> bfile, String fileName) {
        String filePath = "/storage/emulated/0/courseDesign";

        File file = null;
        try {
            //通过创建对应路径的下是否有相应的文件夹。
            File dir = new File(filePath);
            if (!dir.exists()) {// 判断文件目录是否存在
                //如果文件存在则删除已存在的文件夹。
                dir.mkdirs();
            }

            //如果文件存在则删除文件
            file = new File(filePath, fileName);
            if(file.exists()){
                file.delete();
            }
            File tempFile = new File("/storage/emulated/0/courseDesign" + fileName);
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(tempFile)));
            dataOutputStream.writeObject(bfile);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                saveHuffmanTree.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 利用产生的哈夫曼树压缩文件
     * @param path
     * @param fileName
     */
    public void saveCompressedFile(String path, String fileName, String text){
        //判断手机是否存在SD卡
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //获取SD卡的当前的工作
            sdCardPath  = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        File tempFile = new File(sdCardPath + "/" +path + "/" + fileName);
        try {
            //创建二进制文件流
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(tempFile)));
            //根据传进来的
            int[] allCode = analysisByte(text);
            for(int i = 0; i < allCode.length; i++){
                //以二进制的方式将编码存入文件
                dataOutputStream.write(allCode[i]);
            }
            dataOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将每八个字符（二进制编码）装换为十进制
     * @param text
     * @return
     */
    public int[] analysisByte(String text){
        //确定该编码一共能转换多少个编码
        int size = text.length()/8;
        int[] allCode = new int[size + 1];
        String tempString = "";
        for(int i = 0 ; i < size; i++){
            tempString = text.substring(i * 8, (i + 1) * 8);
            //进制转换
            for(int j = 0; j < tempString.length(); j++){
                allCode[i] += (Integer.parseInt(tempString.charAt(tempString.length() - j - 1) + "")) * (int) Math.pow(2, j);
            }
        }
        //将几个不能被八整除的编码单独进行二进制到十进制的转换
        tempString = text.substring(size * 8, text.length());
        for(int j = 0; j < tempString.length(); j++){
            allCode[size] += (Integer.parseInt(tempString.charAt(tempString.length() - j - 1) + "")) * (int) Math.pow(2, j);
        }
        return allCode;
    }

    /**
     * 筛选字符，去除不安全的字符
     * @param text
     * @return
     */
    public String screenString(String text){
        StringBuilder stringBuilder = new StringBuilder();
        char temp = ' ';
        for(int i = 0; i < text.length(); i++){
            temp = text.charAt(i);
            if(temp != ' '&&Character.isLetter(temp)){
                stringBuilder.append(temp);
            }
        }
        String current = stringBuilder.toString().toLowerCase();
        return current;
    }

}
