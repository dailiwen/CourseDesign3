package com.example.administrator.coursedesign.Entity;

import org.litepal.crud.DataSupport;

/**
 * @author dailiwen
 * @date 2017/12/7 0007 下午 7:12
 */

public class Maze extends DataSupport {
    private int id;
    private String maze;
    private String mazeId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaze() {
        return maze;
    }

    public void setMaze(String maze) {
        this.maze = maze;
    }

    public String getMazeId() {
        return mazeId;
    }

    public void setMazeId(String mazeId) {
        this.mazeId = mazeId;
    }
}
