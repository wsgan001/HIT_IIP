package com.search.method;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import com.search.test.ListNode;

public class AStar {

	private HashMap<String, ListNode> Lomania_Table = new HashMap<String, ListNode>();
	private HashMap<String, Integer> Lomania_Distance = new HashMap<String, Integer>();
	private HashMap<String, Integer> HIT_Distance = new HashMap<String, Integer>();
	
	private boolean flag = true;
	
	private Queue<String> hasExplored = new LinkedList<String>();
	
	private PriorityQueue<ListNode> explored;
	
	public AStar(HashMap<String, ListNode> table) {
		
		this.Lomania_Table = table;
		
		Input_Distance();
		Input_HITDistance();
	}
	
    public void AStar_HITSearch() {
		
		explored = new PriorityQueue<ListNode>(11, new Comparator<ListNode>() {
					@Override  
			        public int compare(ListNode o1, ListNode o2)  
			        {  
			           return (HIT_Distance.get(o1.location) + o1.source) - (HIT_Distance.get(o2.location) + o2.source);
			        }
		});
		
		explored.add(Lomania_Table.get("正心楼"));
		// 当可扩展优先队列不空时
		while( !explored.isEmpty() ) {
		
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
			}			
			// 弹出最小堆堆顶元素
			ListNode node = explored.poll();
			
			int foreward = node.distance;
			
			hasExplored.add(node.location);
			System.out.print(node.location + "扩展出:");
			if(node.location.equals("诚意楼")) {
				flag = false;
				break;
			}
			while(node.next != null) {
				node = node.next;
				if(!hasExplored.contains(node.location)) {
					// 将加入节点与初始结点的距离累加
					Lomania_Table.get(node.location).source = foreward + node.distance;
				    explored.add(Lomania_Table.get(node.location));
				    System.out.print(node.location + " ");
				}
			}
			System.out.println();
			
			if(flag == false)
				break;
			
		}
	}
	
	public void AStar_Search() {
		
		explored = new PriorityQueue<ListNode>(11, new Comparator<ListNode>() {
					@Override  
			        public int compare(ListNode o1, ListNode o2)  
			        {  
			           return (Lomania_Distance.get(o1.location) + o1.source) - (Lomania_Distance.get(o2.location) + o2.source);
			        }
		});
		
		explored.add(Lomania_Table.get("Arad"));
		// 当可扩展优先队列不空时
		while( !explored.isEmpty() ) {
		
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
			}			
			// 弹出最小堆堆顶元素
			ListNode node = explored.poll();
			
			int foreward = node.distance;
			
			hasExplored.add(node.location);
			System.out.print(node.location + "扩展出:");
			if(node.location.equals("Bucharest")) {
				flag = false;
				break;
			}
			while(node.next != null) {
				node = node.next;
				if(!hasExplored.contains(node.location)) {
					// 将加入节点与初始结点的距离累加
					Lomania_Table.get(node.location).source = foreward + node.distance;
				    explored.add(Lomania_Table.get(node.location));
				    System.out.print(node.location + " ");
				}
			}
			System.out.println();
			
			if(flag == false)
				break;
			
		}
	}
	
	public void Input_HITDistance() {
		HIT_Distance.put("3G体验店", 178);
		HIT_Distance.put("致知楼", 203);
		HIT_Distance.put("图书馆", 383);
		HIT_Distance.put("超市", 187);
		HIT_Distance.put("正心楼", 275);
		HIT_Distance.put("美食长廊", 144);
		HIT_Distance.put("篮球场", 245);
		HIT_Distance.put("圆通快递", 231);
		HIT_Distance.put("网球场", 259);
		HIT_Distance.put("韵达快递", 340);
		HIT_Distance.put("校医院", 247);
		HIT_Distance.put("二公寓", 130);
		HIT_Distance.put("工商银行", 143);
		HIT_Distance.put("活动中心", 510);
		HIT_Distance.put("诚意楼", 0);
	}
	
	public void Input_Distance() {
		Lomania_Distance.put("Arad", 366);
		Lomania_Distance.put("Bucharest", 0);
		Lomania_Distance.put("Craiova", 160);
		Lomania_Distance.put("Dobreta", 242);
		Lomania_Distance.put("Eforie", 161);
		Lomania_Distance.put("Faragas", 178);
		Lomania_Distance.put("Giurgiu", 77);
		Lomania_Distance.put("Hirsova", 151);
		Lomania_Distance.put("Iasi", 226);
		Lomania_Distance.put("Lugoj", 244);
		Lomania_Distance.put("Mehadai", 241);
		Lomania_Distance.put("Neamt", 234);
		Lomania_Distance.put("Oradea", 380);
		Lomania_Distance.put("Pitesti", 98);
		Lomania_Distance.put("Rimnicu", 193);
		Lomania_Distance.put("Sibiu", 253);
		Lomania_Distance.put("Timisoara", 329);
		Lomania_Distance.put("Urziceni", 80);
		Lomania_Distance.put("Vaslui", 199);
		Lomania_Distance.put("Zerind", 374);
	}
	
}
