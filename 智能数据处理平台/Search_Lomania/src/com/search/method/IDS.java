package com.search.method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
//import java.util.Map.Entry;


import com.search.test.ListNode;

public class IDS {

    private HashMap<String, ListNode> Lomania_Table = new HashMap<String, ListNode>();
	
	// 待扩展栈
	private Stack<ListNode> explored = new Stack<ListNode>();
	private Queue<String> hasExplored = new LinkedList<String>();
	private HashMap<String, Integer> depthMap = new HashMap<String, Integer>();
	private boolean flag = true;
	
	private String start;
	private String end;
	
	public IDS(HashMap<String, ListNode> table, String s, String e) {
		this.Lomania_Table = table;
		this.start = s;
		this.end = e;
		explored.add(Lomania_Table.get(start));
		depthMap.put(start, 0);
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
    
    /*public boolean all_Larger(HashMap<String, Integer> depthMap, int depth) {
		
    	Iterator<Entry<String, Integer>> iter = depthMap.entrySet().iterator(); 
		while (iter.hasNext()) { 
		    Entry<String, Integer> entry = (Entry<String, Integer>) iter.next(); 
		    int val = (int) entry.getValue();
		    if(val > depth)
		        return false;
		} 
		return true;
	}*/
	
	public void IDS_Search() {
		for(int depth = 0; depth < 4; depth++) {
			int loop = 10;
			System.out.println("**********************************迭代加深搜索当前深度("+depth+")**********************************");
			explored = new Stack<ListNode>();
			hasExplored = new LinkedList<String>();
			depthMap = new HashMap<String, Integer>();
			flag = true;
			explored.add(Lomania_Table.get(start));
			depthMap.put(start, 0);
			
			while(!explored.isEmpty() && loop > 0) {
				// 遍历待扩展集中是否包含目标结点
				// 除去重复结点
				loop--;
				Iterator<ListNode> iter = explored.iterator();
				ArrayList<String> unique = new ArrayList<String>();
				while(iter.hasNext()) {
					ListNode temp = iter.next();
					if(unique.contains(temp.location))
						iter.remove();
					else {
						unique.add(temp.location);
			            System.out.print(temp.location + " " );
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
					ListNode node = new ListNode("", 0);
					node.next = explored.pop();
					node = node.next;
									
					hasExplored.add(node.location);
					int head_depth = depthMap.get(node.location);
					System.out.print(node.location + "扩展出:");
					
					while(node.next != null) {
						node = node.next;
						if(!hasExplored.contains(node.location)  && !in_Explored(explored, node.location)) {
							node.depth = head_depth + 1;
							depthMap.put(node.location, node.depth);
							if(depthMap.get(node.location) <= depth) {
								explored.add(Lomania_Table.get(node.location));
								System.out.print(node.location + "(" + node.depth + ")" + " ");
							}
						}
					}
					System.out.println();
				}
				System.out.println();		
			}
		}
	}
}
