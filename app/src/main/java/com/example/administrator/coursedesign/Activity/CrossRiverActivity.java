package com.example.administrator.coursedesign.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.coursedesign.Entity.CrossRiver;
import com.example.administrator.coursedesign.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CrossRiverActivity extends AppCompatActivity {

    @BindView(R.id.boatmove)
    LinearLayout boat;
    @BindView(R.id.leftsheep)
    ImageView leftSheep;
    @BindView(R.id.leftgrass)
    ImageView leftGrass;
    @BindView(R.id.leftwolf)
    ImageView leftWolf;
    @BindView(R.id.rightsheep)
    ImageView rightSheep;
    @BindView(R.id.rightgrass)
    ImageView rightGrass;
    @BindView(R.id.rightwolf)
    ImageView rightWolf;
    @BindView(R.id.onboat)
    ImageView onBoat;
    @BindView(R.id.start)
    Button start;
    @BindView(R.id.back)
    Button back;

    List<Integer> path;
    private int[] boatImageViews;
    private ImageView[] leftImageViews;
    private ImageView[] rightImageViews;
    char[] thingsStatus = {'0', '0', '0', '0'};
    private int statusTemp;

    private int animationCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cross_river);
        ButterKnife.bind(this);
        initComponent();
        initListener();
    }

    /**
     * 初始化相关组件
     */
    private void initComponent() {
        rightGrass.setVisibility(View.INVISIBLE);
        rightSheep.setVisibility(View.INVISIBLE);
        rightWolf.setVisibility(View.INVISIBLE);

        boatImageViews = new int[]{R.drawable.ic_wolf, R.drawable.ic_sheep, R.drawable.ic_grass};
        leftImageViews = new ImageView[]{leftWolf, leftSheep, leftGrass};
        rightImageViews = new ImageView[]{rightWolf, rightSheep, rightGrass};

        //初始化将所有对象都放在南岸
        char[] initialNode= {0,0,0,0};

        CrossRiver mode = new CrossRiver();
        int index = CrossRiver.getIndex(initialNode);
        path = mode.getShortestPath(index);
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        //开始按钮
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animationCount < 8) {
                    start.setClickable(false);
                    start.setText("动画中");
                    startCross();
                }
                if (animationCount == 8) {
                    restart();
                    start.setText("开始");
                }
            }
        });

        //回上界面按钮
        back.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                openActivity(MainActivity.class);
                finish();
            }
        });
    }

    /**
     * 开始过河的第一步
     */
    public void startCross() {
        boat.setVisibility(View.VISIBLE);

        if (animationCount == 8) {
            return;
        }

        char[] state = CrossRiver.getNode(path.get(animationCount).intValue());
        animationCount++;
        pictureChanges(state);
    }

    /**
     * 依次和之前比较4个角色的状态，确定绘制船上的角色
     */
    public void pictureChanges(char[] status) {
        for (int i = 1; i < 4; i++) {
            if (status[i] == '1' && thingsStatus[i] == '0') {
                leftImageViews[i - 1].setVisibility(View.INVISIBLE);
                onBoat.setBackgroundDrawable(getResources().getDrawable(boatImageViews[i - 1]));
                onBoat.setVisibility(View.VISIBLE);
                thingsStatus[i] = '1';
                leftAnimation(i - 1);
                return;
            } else if (status[i] == '0' && thingsStatus[i] == '1') {
                rightImageViews[i - 1].setVisibility(View.INVISIBLE);
                onBoat.setBackgroundDrawable(getResources().getDrawable(boatImageViews[i - 1]));
                onBoat.setVisibility(View.VISIBLE);
                thingsStatus[i] = '0';
                rightAnimation(i - 1);
                return;
            }
        }
        if (status[0] == '0') {
            animation(true);
        } else {
            animation(false);
        }
    }

    /**
     * 船载物向左开的动画
     */
    public void leftAnimation(int status) {
        statusTemp = status;
        AnimationSet animationSet = new AnimationSet(true);

        TranslateAnimation translateAnimation = new TranslateAnimation(
                    //X轴初始位置
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    //X轴移动的结束位置
                    Animation.RELATIVE_TO_SELF, 2.2f,
                    //y轴开始位置
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    //y轴移动后的结束位置
                    Animation.RELATIVE_TO_SELF, 0.0f);

        //3秒完成动画
        translateAnimation.setDuration(2000);
        //如果fillAfter的值为真的话，动画结束后，控件停留在执行后的状态
        animationSet.setFillAfter(true);
        //将AlphaAnimation这个已经设置好的动画添加到 AnimationSet中
        animationSet.addAnimation(translateAnimation);
        //启动动画
        CrossRiverActivity.this.boat.startAnimation(animationSet);

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                onBoat.setVisibility(View.INVISIBLE);
                rightImageViews[statusTemp].setVisibility(View.VISIBLE);

                if (animationCount == 8) {
                    start.setText("重新开始");
                    start.setClickable(true);
                    showToast("展示已经结束，请点击重新开始按钮，重新观看");
                    return;
                }
                char[] state = CrossRiver.getNode(path.get(animationCount).intValue());
                animationCount++;
                pictureChanges(state);
            }
        });
    }

    /**
     * 船载物向右开的动画
     */
    public void rightAnimation(int status) {
        statusTemp = status;
        AnimationSet animationSet = new AnimationSet(true);

        TranslateAnimation translateAnimation = new TranslateAnimation(
                    //X轴初始位置
                    Animation.RELATIVE_TO_SELF, 2.2f,
                    //X轴移动的结束位置
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    //y轴开始位置
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    //y轴移动后的结束位置
                    Animation.RELATIVE_TO_SELF, 0.0f);


        //3秒完成动画
        translateAnimation.setDuration(2000);
        //如果fillAfter的值为真的话，动画结束后，控件停留在执行后的状态
        animationSet.setFillAfter(true);
        //将AlphaAnimation这个已经设置好的动画添加到 AnimationSet中
        animationSet.addAnimation(translateAnimation);
        //启动动画
        CrossRiverActivity.this.boat.startAnimation(animationSet);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                onBoat.setVisibility(View.INVISIBLE);
                leftImageViews[statusTemp].setVisibility(View.VISIBLE);

                if (animationCount == 8) {
                    start.setText("重新开始");
                    start.setClickable(true);
                    showToast("展示已经结束，请点击重新开始按钮，重新观看");
                    return;
                }
                char[] state = CrossRiver.getNode(path.get(animationCount).intValue());
                animationCount++;
                pictureChanges(state);
            }
        });
    }

    /**
     * 船没有载物向左或右开的动画
     */
    public void animation(boolean famerStatus) {
        AnimationSet animationSet = new AnimationSet(true);

        TranslateAnimation translateAnimation;
        if (famerStatus) {
            translateAnimation = new TranslateAnimation(
                    //X轴初始位置
                    Animation.RELATIVE_TO_SELF, 2.2f,
                    //X轴移动的结束位置
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    //y轴开始位置
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    //y轴移动后的结束位置
                    Animation.RELATIVE_TO_SELF, 0.0f);
        } else {
            translateAnimation = new TranslateAnimation(
                    //X轴初始位置
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    //X轴移动的结束位置
                    Animation.RELATIVE_TO_SELF, 2.2f,
                    //y轴开始位置
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    //y轴移动后的结束位置
                    Animation.RELATIVE_TO_SELF, 0.0f);
        }

        //3秒完成动画
        translateAnimation.setDuration(2000);
        //如果fillAfter的值为真的话，动画结束后，控件停留在执行后的状态
        animationSet.setFillAfter(true);
        //将AlphaAnimation这个已经设置好的动画添加到 AnimationSet中
        animationSet.addAnimation(translateAnimation);
        //启动动画
        CrossRiverActivity.this.boat.startAnimation(animationSet);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                if (animationCount == 8) {
                    start.setText("重新开始");
                    start.setClickable(true);
                    showToast("展示已经结束，请点击重新开始按钮，重新观看");
                    return;
                }
                char[] state = CrossRiver.getNode(path.get(animationCount).intValue());
                animationCount++;
                pictureChanges(state);
            }
        });
    }

    /**
     * 重新开始游戏，重新初始化，对应的组件
     */
    public void restart() {
        AnimationSet animationSet = new AnimationSet(true);

        TranslateAnimation translateAnimation = new TranslateAnimation(
                //X轴初始位置
                Animation.RELATIVE_TO_SELF, 0.0f,
                //X轴移动的结束位置
                Animation.RELATIVE_TO_SELF, 0.0f,
                //y轴开始位置
                Animation.RELATIVE_TO_SELF, 0.0f,
                //y轴移动后的结束位置
                Animation.RELATIVE_TO_SELF, 0.0f);

        //如果fillAfter的值为真的话，动画结束后，控件停留在执行后的状态
        animationSet.setFillAfter(true);
        //将AlphaAnimation这个已经设置好的动画添加到 AnimationSet中
        animationSet.addAnimation(translateAnimation);
        //启动动画
        CrossRiverActivity.this.boat.startAnimation(animationSet);
        rightGrass.setVisibility(View.INVISIBLE);
        rightSheep.setVisibility(View.INVISIBLE);
        rightWolf.setVisibility(View.INVISIBLE);

        leftGrass.setVisibility(View.VISIBLE);
        leftSheep.setVisibility(View.VISIBLE);
        leftWolf.setVisibility(View.VISIBLE);
        thingsStatus = new char[]{'0', '0', '0', '0'};
        animationCount = 1;
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
}
