package com.search.method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.search.test.ListNode;

public class DFS {
	
	private HashMap<String, ListNode> Lomania_Table = new HashMap<String, ListNode>();
	
	// 待扩展栈
	private Stack<ListNode> explored = new Stack<ListNode>();
	private Queue<String> hasExplored = new LinkedList<String>();
	private boolean flag = true;
	
	private String start;
	private String end;
	
	public DFS(HashMap<String, ListNode> table, String s, String e) {
		this.Lomania_Table = table;
		this.start = s;
		this.end = e;
		explored.add(Lomania_Table.get(start));
	}
	
	public boolean in_Explored(Stack<ListNode> stack, String name) {
		
		Iterator<ListNode> iter = explored.iterator();
		while(iter.hasNext()) {
			ListNode temp = iter.next();	
			if(temp.location.equals(name))
			  return true;
		}
			
		return false;
	}
	
	public void DFS_Search() {
		while(!explored.isEmpty()) {
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
				if(temp.location.equals(end))
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
				ListNode node = explored.pop();
				hasExplored.add(node.location);
				System.out.print(node.location + "扩展出:");
				while(node.next != null) {
					node = node.next;
					if(!hasExplored.contains(node.location) && !in_Explored(explored, node.location)) {
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
