package com.search.test;

public class ListNode {
	// 结点地名
    public String location;
    // 结点与头结点的距离
    public int distance;
    // 链接
    public ListNode next;
    // 迭代深度
    public int depth;
    // 与初始结点的实际距离
    public int source;
    // 构造函数
    public ListNode(String l, int dis) {
        location = l;
        distance = dis;
        depth = 0;
        source = 0;
        next = null;
    }
}

