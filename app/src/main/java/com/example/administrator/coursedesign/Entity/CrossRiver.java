package com.example.administrator.coursedesign.Entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.example.administrator.coursedesign.Tool.AbstractGraph;
import com.example.administrator.coursedesign.Tool.UnweightedGraph;

/**
 * @author dailiwen
 * 通过模仿翻硬币的方式
 * 构建农夫过河
 * 寻找出农夫所有过河方式
 * 通过广度遍历返回最优路径
 */

public class CrossRiver {
	public final static int NUMBER_OF_NODE = 16;
	protected AbstractGraph<Integer>.Tree tree;

	public CrossRiver() {
		List<AbstractGraph.Edge> edges = getEdges();
		UnweightedGraph<Integer> graph = new UnweightedGraph<Integer>(edges,
				NUMBER_OF_NODE);
		tree = graph.bfs(15);
	}

	private List<AbstractGraph.Edge> getEdges() {
		List<AbstractGraph.Edge> edges = new LinkedList<AbstractGraph.Edge>();
		for (int u = 0; u < NUMBER_OF_NODE; u++) {
			for (int k = 0; k < 4; k++) {
				char[] node = getNode(u);
				if(node[0] == node[k]){
					int v = getMoveNode(node, k);
					if(v != -1){
						edges.add(new AbstractGraph.Edge(v, u));
					}
				}
			}
		}
		return edges;
	}

	/**
	 * 传入当前所有对象的的情况，同时传来需要移动的对象
	 */
	public static int getMoveNode(char[] node, int posistion) {
		char[] tempNode = new char[node.length];

		System.arraycopy(node, 0, tempNode, 0, node.length);

		//如果移动的不是农夫，则需要农夫伴随当前对象一起移动
		if (posistion != 0) {
			MoveThing(tempNode, 0);
			MoveThing(tempNode, posistion);
		} else {
			MoveThing(tempNode, 0);
		}

		//判断狼羊的状态
		boolean sheepWolf = (tempNode[1] == tempNode[2]);
		//羊白菜的状态
		boolean sheepCabbage = (tempNode[2] == tempNode[3]);

		//狼羊不能够在一起，羊菜不能够在一起，除非人在场
		if((sheepWolf && (tempNode[1] != tempNode[0])) || (sheepCabbage && (tempNode[2] != tempNode[0]))){
			//这种情况应被设为不可达
			return -1;
		}else{
			System.arraycopy(
					tempNode, 0,node, 0, tempNode.length);
			return getIndex(node);
		}
	}

	/**
	 * 改变位置
	 */
	public static void MoveThing(char[] node, int posistion) {
		if (node[posistion] == '0') {
			node[posistion] = '1';
		}
		else {
			node[posistion] = '0';
		}

	}

	/**
	 * 通过下标获取到相应状态
	 */
	public static char[] getNode(int index) {
		char[] result = new char[4];

		for (int i = 0; i < 4; i++) {
			int digit = index % 2;
			if (digit == 0) {
				result[3 - i] = '0';
			}
			else {
				result[3 - i] = '1';
			}
			index = index / 2;
		}

		return result;
	}

	/**
	 * 通过状态获取相应下标
	 */
	public static int getIndex(char[] node) {
		int result = 0;
		for (int i = 0; i < 4; i++) {
			if (node[i] == '1') {
				result = result * 2 + 1;
			} else {
				result = result * 2 + 0;
			}
		}
		return result;
	}

	/**
	 * 以一个位置作为起点寻找将所有对象安全移动到北岸的方法
	 */
	public List<Integer> getShortestPath(int nodeIndex) {
		return tree.getPath(nodeIndex);
	}

}
