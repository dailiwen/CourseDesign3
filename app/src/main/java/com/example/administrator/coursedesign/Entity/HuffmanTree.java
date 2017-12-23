package com.example.administrator.coursedesign.Entity;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;


/**
 * @author dailiwen
 * @date 2017/12/13 0013 下午 2:30
 */

public class HuffmanTree<E> implements Serializable {
    private LinkedList<Node1<E>> list = new LinkedList<Node1<E>>();
    private Node1<E>[] nodes;
    private Node1<E> head;
    private int size;

    public HuffmanTree(Node1<E>[] nodes){
        this.nodes = nodes;
        for(int i = 0; i < nodes.length;i++){
            if(nodes[i].weight != 0){
                list.add(nodes[i]);
                size++;
            }
        }
        toTree();
    }


    public void toTree(){
        Node1<E> minNode1;
        Node1<E> minNode2;
        while( list.size() > 1){
            minNode1 = list.get(0);
            for(int i = 0;i <list.size();i++){
                int weight = list.get(i).weight;
                if(minNode1.weight >= weight){
                    minNode1 =  list.get(i);
                }
            }
            list.remove(minNode1);

            minNode2 = list.get(0);
            for(int i = 0;i < list.size();i++){
                int weight = list.get(i).weight;
                if(minNode2.weight >= weight){
                    minNode2 = list.get(i);
                }
            }
            list.remove(minNode2);
            list.add(buildTree(minNode1,minNode2));
        }
        head = list.get(0);
    }


    public Node1<E> getHead(){
        return this.head;
    }

    public Map<E, String> getEncodeMap(){
        Map<E, String>  encodeMap = new HashMap<E,String>();
        for(int i = 0;i < nodes.length;i++){
            encodeMap.put(nodes[i].data, count(nodes[i]));
        }
        return encodeMap;
    }

    @Override
    public String toString(){
        String s = "";
        for(int i = 0;i < nodes.length;i++){
            s += nodes[i].data + " : " + count(nodes[i]) + "\n";
        }

        return s;
    }

    public String count(Node1<E> currentNode){
        Node1<E> currentChild = currentNode;
        Node1<E> currentParent = currentChild.parent;
        String s ="";
        while(currentParent != null){
            if(currentParent.Lchild.equals(currentChild)){
                s = "1" + s;
            }else{
                s = "0" + s;
            }
            currentChild = currentParent;
            currentParent = currentParent.parent;
        }
        return s;
    }

    public Node1<E> buildTree(Node1<E> LTree,Node1<E> RTree){
        Node1<E> node = new Node1<E>();
        node.weight = LTree.weight + RTree.weight;
        node.Lchild = LTree;
        node.Rchild = RTree;
        LTree.parent = node;
        RTree.parent = node;
        return node;
    }

    public static class Node1<E> implements Serializable{
        E data;
        public int weight;
        Node1<E> parent;
        Node1<E> Rchild;
        Node1<E> Lchild;

        public Node1(){

        }
        public Node1(E e) {
            data = e;
        }
    }

    public static class Node<E> {
        E data;
        int weight;
        Node leftChild;
        Node rightChild;

        public Node(E data, int weight) {
            super();
            this.data = data;
            this.weight = weight;
        }

        public E getData() {
            return data;
        }

        public int getWeight() {
            return weight;
        }

        public Node getLeftChild() {
            return leftChild;
        }

        public Node getRightChild() {
            return rightChild;
        }

        @Override
        public String toString() {
            return "Node[data=" + data + ", weight=" + weight + "]";
        }
    }

    /**
     * 构造哈夫曼树
     */
    public static Node createTree(List<Node> nodes) {
        // 只要nodes数组中还有2个以上的节点
        while (nodes.size() > 1) {
            quickSort(nodes);
            //获取权值最小的两个节点
            Node left = nodes.get(nodes.size()-1);
            Node right = nodes.get(nodes.size()-2);

            //生成新节点，新节点的权值为两个子节点的权值之和
            Node parent = new Node(null, left.weight + right.weight);

            //让新节点作为两个权值最小节点的父节点
            parent.leftChild = left;
            parent.rightChild = right;

            //删除权值最小的两个节点
            nodes.remove(nodes.size()-1);
            nodes.remove(nodes.size()-1);

            //将新节点加入到集合中
            nodes.add(parent);
        }

        return nodes.get(0);
    }

    /**
     * 将指定集合中的i和j索引处的元素交换
     */
    private static void swap(List<Node> nodes, int i, int j) {
        Node tmp;
        tmp = nodes.get(i);
        nodes.set(i, nodes.get(j));
        nodes.set(j, tmp);
    }

    /**
     * 实现快速排序算法，用于对节点进行排序
     */
    private static void subSort(List<Node> nodes, int start, int end) {
        if (start < end) {
            // 以第一个元素作为分界值
            Node base = nodes.get(start);
            // i从左边搜索，搜索大于分界值的元素的索引
            int i = start;
            // j从右边开始搜索，搜索小于分界值的元素的索引
            int j = end + 1;
            while (true) {
                // 找到大于分界值的元素的索引，或者i已经到了end处
                while (i < end && nodes.get(++i).weight >= base.weight)
                    ;
                // 找到小于分界值的元素的索引，或者j已经到了start处
                while (j > start && nodes.get(--j).weight <= base.weight)
                    ;

                if (i < j) {
                    swap(nodes, i, j);
                } else {
                    break;
                }
            }

            swap(nodes, start, j);

            //递归左边子序列
            subSort(nodes, start, j - 1);
            //递归右边子序列
            subSort(nodes, j + 1, end);
        }
    }

    public static void quickSort(List<Node> nodes){
        subSort(nodes, 0, nodes.size()-1);
    }


    /**
     * 广度优先遍历
     */
    public static List<Node> breadthFirst(Node root){
        Queue<Node> queue = new ArrayDeque<Node>();
        List<Node> list = new ArrayList<Node>();

        if(root!=null){
            //将根元素加入“队列”
            queue.offer(root);
        }

        while(!queue.isEmpty()){
            //将该队列的“队尾”元素加入到list中
            list.add(queue.peek());
            Node p = queue.poll();

            //如果左子节点不为null，将它加入到队列
            if(p.leftChild != null){
                queue.offer(p.leftChild);
            }

            //如果右子节点不为null，将它加入到队列
            if(p.rightChild != null){
                queue.offer(p.rightChild);
            }
        }
        return list;
    }
}
