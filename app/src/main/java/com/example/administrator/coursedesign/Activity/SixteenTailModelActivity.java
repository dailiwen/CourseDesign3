package com.example.administrator.coursedesign.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.coursedesign.R;
import com.example.administrator.coursedesign.Entity.SixteenTailModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author dailiwen
 */
public class SixteenTailModelActivity extends AppCompatActivity {
    @BindView(R.id.parent)
    LinearLayout parent;
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.NBHSolve)
    Button NBHsolve;
    @BindView(R.id.DIGSolve)
    Button DIGSolve;
    @BindView(R.id.reset)
    Button reset;
    @BindView(R.id.one)
    TextView one;
    @BindView(R.id.two)
    TextView two;
    @BindView(R.id.three)
    TextView three;
    @BindView(R.id.four)
    TextView four;
    @BindView(R.id.five)
    TextView five;
    @BindView(R.id.six)
    TextView six;
    @BindView(R.id.seven)
    TextView seven;
    @BindView(R.id.eight)
    TextView eight;
    @BindView(R.id.nine)
    TextView nine;
    @BindView(R.id.ten)
    TextView ten;
    @BindView(R.id.eleven)
    TextView eleven;
    @BindView(R.id.twelve)
    TextView twelve;
    @BindView(R.id.thirteen)
    TextView thirteen;
    @BindView(R.id.fourteen)
    TextView fourteen;
    @BindView(R.id.fifteen)
    TextView fifteen;
    @BindView(R.id.sixteen)
    TextView sixteen;

    private ArrayList<TextView> list = new ArrayList<TextView>();
    private Context mContext;
    /**
     * 邻边规则解决
     */
    private SixteenTailModel NBHmodel = new SixteenTailModel(false);
    /**
     * 对角线规则解决
     */
    private SixteenTailModel DIAGmodel = new SixteenTailModel(true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixteen_tail_model);
        mContext = this;
        ButterKnife.bind(this);
        initComponent();
        initListener();
    }

    /**
     * 初始化相关组件
     */
    private void initComponent() {
        list.add(one);
        list.add(two);
        list.add(three);
        list.add(four);
        list.add(five);
        list.add(six);
        list.add(seven);
        list.add(eight);
        list.add(nine);
        list.add(ten);
        list.add(eleven);
        list.add(twelve);
        list.add(thirteen);
        list.add(fourteen);
        list.add(fifteen);
        list.add(sixteen);
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        //回上界面按钮
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(MainActivity.class);
                finish();
            }
        });

        //邻边解决按钮
        DIGSolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.removeAllViews();
                java.util.List<Integer> path = NBHmodel.getShortestPath(SixteenTailModel.getIndex(getMatrix()));
                if (path.size() == 1) {
                    showToast("抱歉，此布局无解");
                } else {
                    for (int i = 0; i < 16; i++) {
                        list.get(i).setClickable(false);
                    }
                    for (int i = 1; i < path.size(); i++) {
                        parent.addView(addView(SixteenTailModel.getNode(path.get(i - 1).intValue()), SixteenTailModel.getNode(path.get(i).intValue())));
                    }
                }
            }
        });

        //对角线解决按钮
        NBHsolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.removeAllViews();
                java.util.List<Integer> path = DIAGmodel.getShortestPath(SixteenTailModel.getIndex(getMatrix()));
                if (path.size() == 1) {
                    showToast("抱歉，此布局无解");
                } else {
                    for (int i = 0; i < 16; i++) {
                        list.get(i).setClickable(false);
                    }
                    for (int i = 1; i < path.size(); i++) {
                        parent.addView(addView(SixteenTailModel.getNode(path.get(i - 1).intValue()), SixteenTailModel.getNode(path.get(i).intValue())));
                    }
                }
            }
        });

        //重置按钮
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 16; i++ ) {
                    list.get(i).setText("H");
                    list.get(i).setTextColor(getResources().getColor(R.color.textView));
                    list.get(i).setClickable(true);
                }
                delView();
            }
        });

        //（4,4）格响应事件
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("T".equals(one.getText())) {
                    one.setText("H");
                    one.setTextColor(getResources().getColor(R.color.textView));
                } else {
                    one.setText("T");
                    one.setTextColor(getResources().getColor(R.color.blue));
                }
            }
        });

        //（4,3）格响应事件
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("T".equals(two.getText())) {
                    two.setText("H");
                    two.setTextColor(getResources().getColor(R.color.textView));
                } else {
                    two.setText("T");
                    two.setTextColor(getResources().getColor(R.color.blue));
                }
            }
        });

        //（4,2）格响应事件
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("T".equals(three.getText())) {
                    three.setText("H");
                    three.setTextColor(getResources().getColor(R.color.textView));
                } else {
                    three.setText("T");
                    three.setTextColor(getResources().getColor(R.color.blue));
                }
            }
        });

        //（4,1）格响应事件
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("T".equals(four.getText())) {
                    four.setText("H");
                    four.setTextColor(getResources().getColor(R.color.textView));
                } else {
                    four.setText("T");
                    four.setTextColor(getResources().getColor(R.color.blue));
                }
            }
        });

        //（3,4）格响应事件
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("T".equals(five.getText())) {
                    five.setText("H");
                    five.setTextColor(getResources().getColor(R.color.textView));
                } else {
                    five.setText("T");
                    five.setTextColor(getResources().getColor(R.color.blue));
                }
            }
        });

        //（3,3）格响应事件
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("T".equals(six.getText())) {
                    six.setText("H");
                    six.setTextColor(getResources().getColor(R.color.textView));
                } else {
                    six.setText("T");
                    six.setTextColor(getResources().getColor(R.color.blue));
                }
            }
        });

        //（3,2）格响应事件
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("T".equals(seven.getText())) {
                    seven.setText("H");
                    seven.setTextColor(getResources().getColor(R.color.textView));
                } else {
                    seven.setText("T");
                    seven.setTextColor(getResources().getColor(R.color.blue));
                }
            }
        });

        //（3,1）格响应事件
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("T".equals(eight.getText())) {
                    eight.setText("H");
                    eight.setTextColor(getResources().getColor(R.color.textView));
                } else {
                    eight.setText("T");
                    eight.setTextColor(getResources().getColor(R.color.blue));
                }
            }
        });

        //（2,4）格响应事件
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("T".equals(nine.getText())) {
                    nine.setText("H");
                    nine.setTextColor(getResources().getColor(R.color.textView));
                } else {
                    nine.setText("T");
                    nine.setTextColor(getResources().getColor(R.color.blue));

                }
            }
        });

        //（2,3）格响应事件
        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("T".equals(ten.getText())) {
                    ten.setText("H");
                    ten.setTextColor(getResources().getColor(R.color.textView));
                } else {
                    ten.setText("T");
                    ten.setTextColor(getResources().getColor(R.color.blue));
                }
            }
        });

        //（2,2）格响应事件
        eleven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("T".equals(eleven.getText())) {
                    eleven.setText("H");
                    eleven.setTextColor(getResources().getColor(R.color.textView));
                } else {
                    eleven.setText("T");
                    eleven.setTextColor(getResources().getColor(R.color.blue));
                }
            }
        });

        //（2,1）格响应事件
        twelve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("T".equals(twelve.getText())) {
                    twelve.setText("H");
                    twelve.setTextColor(getResources().getColor(R.color.textView));
                } else {
                    twelve.setText("T");
                    twelve.setTextColor(getResources().getColor(R.color.blue));
                }
            }
        });

        //（1,4）格响应事件
        thirteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("T".equals(thirteen.getText())) {
                    thirteen.setText("H");
                    thirteen.setTextColor(getResources().getColor(R.color.textView));
                } else {
                    thirteen.setText("T");
                    thirteen.setTextColor(getResources().getColor(R.color.blue));
                }
            }
        });

        //（1,3）格响应事件
        fourteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("T".equals(fourteen.getText())) {
                    fourteen.setText("H");
                    fourteen.setTextColor(getResources().getColor(R.color.textView));
                } else {
                    fourteen.setText("T");
                    fourteen.setTextColor(getResources().getColor(R.color.blue));
                }
            }
        });

        //（1,2）格响应事件
        fifteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("T".equals(fifteen.getText())) {
                    fifteen.setText("H");
                    fifteen.setTextColor(getResources().getColor(R.color.textView));
                } else {
                    fifteen.setText("T");
                    fifteen.setTextColor(getResources().getColor(R.color.blue));
                }
            }
        });

        //（1,1）格响应事件
        sixteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("T".equals(sixteen.getText())) {
                    sixteen.setText("H");
                    sixteen.setTextColor(getResources().getColor(R.color.textView));
                } else {
                    sixteen.setText("T");
                    sixteen.setTextColor(getResources().getColor(R.color.blue));
                }
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
     * 以char[]的形式存储16个格子的值
     */
    public char[] getMatrix() {
        char[] initialNode = new char[16];
        for (int i = 0;i < 16; i++) {
            initialNode[i] = list.get(i).getText().charAt(0);
        }
        return initialNode;
    }

    /**
     * 动态添加布局,显示执行的过程
     */
    private View addView(char[] ch1, char[] ch) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.sixteen_tail_terminal, null);
        view.setLayoutParams(lp);

        ArrayList<TextView> textlist = new ArrayList<TextView>();
        TextView textView1 = view.findViewById(R.id.one);
        textlist.add(textView1);
        TextView textView2 = view.findViewById(R.id.two);
        textlist.add(textView2);
        TextView textView3 = view.findViewById(R.id.three);
        textlist.add(textView3);
        TextView textView4 = view.findViewById(R.id.four);
        textlist.add(textView4);
        TextView textView5 = view.findViewById(R.id.five);
        textlist.add(textView5);
        TextView textView6 = view.findViewById(R.id.six);
        textlist.add(textView6);
        TextView textView7 = view.findViewById(R.id.seven);
        textlist.add(textView7);
        TextView textView8 = view.findViewById(R.id.eight);
        textlist.add(textView8);
        TextView textView9 = view.findViewById(R.id.nine);
        textlist.add(textView9);
        TextView textView10 = view.findViewById(R.id.ten);
        textlist.add(textView10);
        TextView textView11 = view.findViewById(R.id.eleven);
        textlist.add(textView11);
        TextView textView12 = view.findViewById(R.id.twelve);
        textlist.add(textView12);
        TextView textView13 = view.findViewById(R.id.thirteen);
        textlist.add(textView13);
        TextView textView14 = view.findViewById(R.id.fourteen);
        textlist.add(textView14);
        TextView textView15 = view.findViewById(R.id.fifteen);
        textlist.add(textView15);
        TextView textView16 = view.findViewById(R.id.sixteen);
        textlist.add(textView16);

        for (int i = 0; i < 16; i++) {
            if(ch[i] != ch1[i]) {
                textlist.get(i).setText(ch[i] + "");
                textlist.get(i).setTextColor(this.getResources().getColor(R.color.red));
            }
            else {
                textlist.get(i).setText(ch[i] + "");
                textlist.get(i).setTextColor(this.getResources().getColor(R.color.black));
            }
        }
        return view;
    }

    /**
     * 删除添加的布局
     */
    private void delView() {
        parent.removeAllViews();
    }
}
