package com.search.method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import com.search.test.ListNode;

public class BFS {

	private HashMap<String, ListNode> Lomania_Table = new HashMap<String, ListNode>();
	// 待扩展队列
	private Queue<ListNode> explored = new LinkedList<ListNode>();
	private Queue<String> hasExplored = new LinkedList<String>();
	private boolean flag = true;
	
	public BFS(HashMap<String, ListNode> table) {
		this.Lomania_Table = table;
		explored.add(Lomania_Table.get("Arad"));
	}
	
	public void BFS_Search() {
		while( !explored.isEmpty()) {
			// 遍历待扩展集中是否包含目标结点
			// 除去重复结点
			Iterator<ListNode> iter = explored.iterator();
			ArrayList<String> unique = new ArrayList<String>();
			while(iter.hasNext()) {
				ListNode temp = iter.next();
				if(unique.contains(temp.location))
					iter.remove();
				else {
					unique.add(temp.location);
				}
				if(temp.location.equals("Bucharest"))
					flag = false;
			}
			// 待扩展结点是否在队列中
			if(flag == false) {
			    System.out.println("当前待扩展集结点数:" + explored.size());
				break;	
			}
			else {
				int len = explored.size();
				System.out.println("当前待扩展集结点数:" + len);
				for(int i = 0; i < len; i++) {
					ListNode node = explored.poll();
					hasExplored.add(node.location);
					System.out.print(node.location + "扩展出:");
					while(node.next != null) {
						node = node.next;
						if(!hasExplored.contains(node.location)) {
						    explored.add(Lomania_Table.get(node.location));
						    System.out.print(node.location + " ");
						}
					}
					System.out.println();
				}
				System.out.println();
			}
		}
	}
	
}
