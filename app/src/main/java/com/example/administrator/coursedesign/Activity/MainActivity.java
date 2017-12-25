package com.example.administrator.coursedesign.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.coursedesign.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author dailiwen
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.point)
    Button pointgame;
    @BindView(R.id.manualpoint)
    Button manualpointgame;
    @BindView(R.id.sixteen)
    Button sixteen;
    @BindView(R.id.maze)
    Button maze;
    @BindView(R.id.huffman)
    Button huffman;
    @BindView(R.id.cross)
    Button cross;
    @BindView(R.id.jdc)
    Button jdc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        pointgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TwentyfourActivity.class);
                startActivity(intent);
            }
        });

        manualpointgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ManualTwentyfoutActivity.class);
                startActivity(intent);
            }
        });

        sixteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SixteenTailModelActivity.class);
                startActivity(intent);
            }
        });

        maze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MazeActivity.class);
                startActivity(intent);
            }
        });

        huffman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HaffmanActivity.class);
                startActivity(intent);
            }
        });

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CrossRiverActivity.class);
                startActivity(intent);
            }
        });

        jdc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JDCShowActivity.class);
                startActivity(intent);
            }
        });
    }
}
