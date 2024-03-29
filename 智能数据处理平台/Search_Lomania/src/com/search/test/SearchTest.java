package com.search.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.search.method.AStar;
import com.search.method.BFS;
import com.search.method.DFS;
import com.search.method.IDS;

public class SearchTest {
	
	private HashMap<String, ListNode> Lomania_Table = new HashMap<String, ListNode>();
	private HashMap<String, ListNode> HIT_Table = new HashMap<String, ListNode>();
	
	public HashMap<String, ListNode> getLomania_Table() {
		return Lomania_Table;
	}

	public HashMap<String, ListNode> getHIT_Table() {
		return HIT_Table;
	}
	
	public SearchTest() {
		generateLomania();
		generateHIT();
	}
	
	// 构造罗马尼亚问题邻接表
	public void generateLomania() {
		// Oradea
		ListNode Oradea = new ListNode("Oradea", 0);
		ListNode temp1 = new ListNode("Zerind", 71);
		Oradea.next = temp1;
		ListNode temp2 = new ListNode("Sibiu", 151);
		temp1.next = temp2;
        Lomania_Table.put("Oradea", Oradea);
        
		// Zerind
		ListNode Zerind = new ListNode("Zerind", 0);
		ListNode temp3 = new ListNode("Oradea", 71);
		Zerind.next = temp3;
		ListNode temp4 = new ListNode("Arad", 75);
		temp3.next = temp4;
		Lomania_Table.put("Zerind", Zerind);
		
		// Arad
		ListNode Arad = new ListNode("Arad", 0);
		ListNode temp5 = new ListNode("Zerind", 75);
		Arad.next = temp5;
		ListNode temp6 = new ListNode("Timisoara", 118);
		temp5.next = temp6;
		ListNode temp7 = new ListNode("Sibiu", 140);
		temp6.next = temp7;
		Lomania_Table.put("Arad", Arad);
		
		// Timisoara
		ListNode Timisoara = new ListNode("Timisoara", 0);
		ListNode temp8 = new ListNode("Lugoj", 111);
		Timisoara.next = temp8;
		ListNode temp9 = new ListNode("Arad", 118);
		temp8.next = temp9;
		Lomania_Table.put("Timisoara", Timisoara);
		
		// Lugoj
		ListNode Lugoj = new ListNode("Lugoj", 0);
		ListNode temp10 = new ListNode("Mehadia", 70);
		Lugoj.next = temp10;
		ListNode temp11 = new ListNode("Timisoara", 111);
		temp10.next = temp11;
		Lomania_Table.put("Lugoj", Lugoj);
		
		// Mehadia
		ListNode Mehadia = new ListNode("Mehadia", 0);
		ListNode temp12 = new ListNode("Lugoj", 70);
		Mehadia.next = temp12;
		ListNode temp13 = new ListNode("Dobreta", 75);
		temp12.next = temp13;
		Lomania_Table.put("Mehadia", Mehadia);
		
		// Dobreta
		ListNode Dobreta = new ListNode("Dobreta", 0);
		ListNode temp14 = new ListNode("Mehadia", 75);
		Dobreta.next = temp14;
		ListNode temp15 = new ListNode("Craiova", 120);
		temp14.next = temp15;
		Lomania_Table.put("Dobreta", Dobreta);
		
		// Sibiu
		ListNode Sibiu = new ListNode("Sibiu", 0);
		ListNode temp16 = new ListNode("Rimnicu", 80);
		Sibiu.next = temp16;
		ListNode temp17 = new ListNode("Faragas", 99);
		temp16.next = temp17;
		ListNode temp18 = new ListNode("Arad", 140);
		temp17.next = temp18;
		ListNode temp19 = new ListNode("Oradea", 151);
		temp18.next = temp19;
		Lomania_Table.put("Sibiu", Sibiu);
		
		// Rimnicu
		ListNode Rimnicu = new ListNode("Rimnicu", 0);
		ListNode temp20 = new ListNode("Sibiu", 80);
		Rimnicu.next = temp20;
		ListNode temp21 = new ListNode("Pitesti", 97);
		temp20.next = temp21;
		ListNode temp22 = new ListNode("Craiova", 146);
		temp21.next = temp22;
		Lomania_Table.put("Rimnicu", Rimnicu);
		
		// Craiova
		ListNode Craiova = new ListNode("Craiova", 0);
		ListNode temp23 = new ListNode("Dobreta", 120);
		Craiova.next = temp23;
		ListNode temp24 = new ListNode("Pitesti", 138);
		temp23.next = temp24;
		ListNode temp25 = new ListNode("Rimnicu", 146);
		temp24.next = temp25;
		Lomania_Table.put("Craiova", Craiova);
		
		// Faragas
		ListNode Faragas = new ListNode("Faragas", 0);
		ListNode temp26 = new ListNode("Sibiu", 99);
		Faragas.next = temp26;
		ListNode temp27 = new ListNode("Bucharest", 211);
		temp26.next = temp27;
		Lomania_Table.put("Faragas", Faragas);
		
		// Pitesti
		ListNode Pitesti = new ListNode("Pitesti", 0);
		ListNode temp28 = new ListNode("Rimnicu", 97);
		Pitesti.next = temp28;
		ListNode temp29 = new ListNode("Bucharest", 101);
		temp28.next = temp29;
		ListNode temp30 = new ListNode("Craiova", 138);
		temp29.next = temp30;
		Lomania_Table.put("Pitesti", Pitesti);
		
		// Bucharest
		ListNode Bucharest = new ListNode("Bucharest", 0);
		ListNode temp31 = new ListNode("Urziceni", 86);
		Bucharest.next = temp31;
		ListNode temp32 = new ListNode("Giurgui", 90);
		temp31.next = temp32;
		ListNode temp33 = new ListNode("Pitesti", 101);
		temp32.next = temp33;
		ListNode temp34 = new ListNode("Faragas", 211);
		temp33.next = temp34;
		Lomania_Table.put("Bucharest", Bucharest);
		
		// Giurgui
		ListNode Giurgui = new ListNode("Giurgui", 0);
		ListNode temp35 = new ListNode("Bucharest", 90);
		Giurgui.next = temp35;
		Lomania_Table.put("Giurgui", Giurgui);
		
		// Urziceni
		ListNode Urziceni = new ListNode("Urziceni", 0);
		ListNode temp36 = new ListNode("Bucharest", 86);
		Urziceni.next = temp36;
		ListNode temp37 = new ListNode("Hirsova", 98);
		temp36.next = temp37;
		Lomania_Table.put("Urziceni", Urziceni);
		
		// Neamt
		ListNode Neamt = new ListNode("Neamt", 0);
		ListNode temp38 = new ListNode("Iasi", 87);
		Neamt.next = temp38;
		Lomania_Table.put("Neamt", Neamt);
		
		// Iasi
		ListNode Iasi = new ListNode("Iasi", 0);
		ListNode temp39 = new ListNode("Neamt", 87);
		Iasi.next = temp39;
		ListNode temp40 = new ListNode("Vaslui", 92);
		temp39.next = temp40;
		Lomania_Table.put("Iasi", Iasi);
		
		// Vaslui
		ListNode Vaslui = new ListNode("Vaslui", 0);
		ListNode temp41 = new ListNode("Iasi", 92);
		Vaslui.next = temp41;
		ListNode temp42 = new ListNode("Hirsova", 142);
		temp41.next = temp42;
		Lomania_Table.put("Vaslui", Vaslui);
		
		// Hirsova
		ListNode Hirsova = new ListNode("Hirsova", 0);
		ListNode temp43 = new ListNode("Eforie", 86);
		Hirsova.next = temp43;
		ListNode temp44 = new ListNode("Urziceni", 98);
		temp43.next = temp44;
		ListNode temp45 = new ListNode("Vaslui", 142);
		temp44.next = temp45;
		Lomania_Table.put("Hirsova", Hirsova);
		
		// Eforie
		ListNode Eforie = new ListNode("Eforie", 0);
		ListNode temp46 = new ListNode("Hirsova", 86);
		Eforie.next = temp46;
		Lomania_Table.put("Eforie", Eforie);
		
	}
	
	// 构造HIT邻接表
	public void generateHIT() {
		// 正心楼
		ListNode zhengxin = new ListNode("正心楼", 0);
		ListNode temp1 = new ListNode("3G体验店", 108);
		zhengxin.next = temp1;
		ListNode temp2 = new ListNode("图书馆", 210);
		temp1.next = temp2;
		HIT_Table.put("正心楼", zhengxin);
		
		// 活动中心
		ListNode huodong = new ListNode("活动中心", 0);
		ListNode temp3 = new ListNode("篮球场", 256);
		huodong.next = temp3;
		ListNode temp4 = new ListNode("韵达快递", 320);
		temp3.next = temp4;
		HIT_Table.put("活动中心", huodong);
		
		// 韵达快递
		ListNode yunda = new ListNode("韵达快递", 0);
		ListNode temp5 = new ListNode("网球场", 135);
		yunda.next = temp5;
		ListNode temp6 = new ListNode("3G体验店", 185);
		temp5.next = temp6;
		ListNode temp7 = new ListNode("活动中心", 320);
		temp6.next = temp7;
		HIT_Table.put("韵达快递", yunda);
		
		// 网球场
		ListNode wangqiu = new ListNode("网球场", 0);
		ListNode temp8 = new ListNode("篮球场", 94);
		wangqiu.next = temp8;
		ListNode temp9 = new ListNode("韵达快递", 135);
		temp8.next = temp9;
		ListNode temp10 = new ListNode("工商银行", 137);
		temp9.next = temp10;
		HIT_Table.put("网球场", wangqiu);
		
		// 篮球场
		ListNode lanqiu = new ListNode("篮球场", 0);
		ListNode temp11 = new ListNode("圆通快递", 64);
		lanqiu.next = temp11;
		ListNode temp12 = new ListNode("网球场", 94);
		temp11.next = temp12;
		ListNode temp13 = new ListNode("诚意楼", 245);
		temp12.next = temp13;
		ListNode temp14 = new ListNode("活动中心", 256);
		temp13.next = temp14;
		HIT_Table.put("篮球场", lanqiu);
		
		// 圆通快递
		ListNode yuantong = new ListNode("圆通快递", 0);
		ListNode temp15 = new ListNode("篮球场", 64);
		yuantong.next = temp15;
		HIT_Table.put("圆通快递", yuantong);
		
		// 3G体验店
		ListNode g = new ListNode("3G体验店", 0);
		ListNode temp16 = new ListNode("工商银行", 59);
		g.next = temp16;
		ListNode temp17 = new ListNode("超市", 75);
		temp16.next = temp17;
		ListNode temp18 = new ListNode("正心楼", 108);
		temp17.next = temp18;
		ListNode temp19 = new ListNode("韵达快递", 185);
		temp18.next = temp19;
		HIT_Table.put("3G体验店", g);
		
		// 工商银行
		ListNode gongshang = new ListNode("工商银行", 0);
		ListNode temp20 = new ListNode("3G体验店", 59);
		gongshang.next = temp20;
		ListNode temp21 = new ListNode("网球场", 137);
		temp20.next = temp21;
		ListNode temp22 = new ListNode("诚意楼", 143);
	    temp21.next = temp22;
	    HIT_Table.put("工商银行", gongshang);
	    
	    // 超市
	    ListNode chaoshi = new ListNode("超市", 0);
	    ListNode temp23 = new ListNode("致知楼", 72);
	    chaoshi.next = temp23;
	    ListNode temp24 = new ListNode("3G体验店", 75);
	    temp23.next = temp24;
	    HIT_Table.put("超市", chaoshi);
	    
	    // 诚意楼
	    ListNode chengyi = new ListNode("诚意楼", 0);
	    ListNode temp25 = new ListNode("美食长廊", 114);
	    chengyi.next = temp25;
	    ListNode temp26 = new ListNode("二公寓", 130);
	    temp25.next = temp26;
	    ListNode temp27 = new ListNode("工商银行", 143);
	    temp26.next = temp27;
	    ListNode temp28 = new ListNode("篮球场", 245);
	    temp27.next = temp28;
	    HIT_Table.put("诚意楼", chengyi);
	    
	    // 图书馆
	    ListNode tushu = new ListNode("图书馆", 0);
	    ListNode temp29 = new ListNode("致知楼", 179);
	    tushu.next = temp29;
	    ListNode temp30 = new ListNode("正心楼", 210);
	    temp29.next = temp30;
	    HIT_Table.put("图书馆", tushu);
	    
	    // 致知楼
	    ListNode zhizhi = new ListNode("致知楼", 0);
	    ListNode temp31 = new ListNode("超市", 72);
	    zhizhi.next = temp31;
	    ListNode temp32 = new ListNode("美食长廊", 118);
	    temp31.next = temp32;
	    ListNode temp33 = new ListNode("图书馆", 179);
	    temp32.next = temp33;
	    HIT_Table.put("致知楼", zhizhi);
	    
	    // 美食长廊
	    ListNode meishi = new ListNode("美食长廊", 0);
	    ListNode temp34 = new ListNode("诚意楼", 114);
	    meishi.next = temp34;
	    ListNode temp35 = new ListNode("致知楼", 118);
	    temp34.next = temp35;
	    HIT_Table.put("美食长廊", meishi);
	    
	    // 二公寓
	    ListNode ergongyu = new ListNode("二公寓", 0);
	    ListNode temp36 = new ListNode("校医院", 112);
	    ergongyu.next = temp36;
	    ListNode temp37 = new ListNode("诚意楼", 130);
	    temp36.next = temp37;
	    HIT_Table.put("二公寓", ergongyu);
	    
	    // 校医院
	    ListNode xiaoyiyuan = new ListNode("校医院", 0);
	    ListNode temp38 = new ListNode("二公寓", 112);
	    xiaoyiyuan.next = temp38;
	    HIT_Table.put("校医院", xiaoyiyuan);
		
	}
	
	// 遍历邻接表
	public void display(HashMap<String, ListNode> table) {
		Iterator<Entry<String, ListNode>> iter = table.entrySet().iterator(); 
		while (iter.hasNext()) { 
		    Entry<String, ListNode> entry = (Entry<String, ListNode>) iter.next(); 
		    ListNode val = (ListNode) entry.getValue();
		    while(val != null) {
		    	System.out.print(val.location + "(" + val.distance + ")" + "->");
		    	val = val.next;
		    }
		    System.out.println();
		} 
	}
	

	public static void main(String[] args) {
		SearchTest searchTest = new SearchTest();
		searchTest.display(searchTest.Lomania_Table);
		
		System.out.println();
		
		searchTest.display(searchTest.HIT_Table);
		
		
		System.out.println("**********************************宽度优先搜索**********************************");
		BFS bfs = new BFS(searchTest.Lomania_Table, "Arad", "Bucharest");
		bfs.BFS_Search();
		
		System.out.println("**********************************深度优先搜索**********************************");
		DFS dfs = new DFS(searchTest.Lomania_Table, "Arad", "Bucharest");
		dfs.DFS_Search();
		
		System.out.println("**********************************迭代加深搜索**********************************");
		IDS ids = new IDS(searchTest.Lomania_Table, "Arad", "Bucharest");
		ids.IDS_Search();
		
		System.out.println("**********************************宽度优先搜索**********************************");
		BFS bfs_hit = new BFS(searchTest.HIT_Table, "正心楼", "诚意楼");
		bfs_hit.BFS_Search();
		
		System.out.println("**********************************深度优先搜索**********************************");
		DFS dfs_hit = new DFS(searchTest.HIT_Table, "正心楼", "诚意楼");
		dfs_hit.DFS_Search();
		
		System.out.println("**********************************迭代加深搜索**********************************");
		IDS ids_hit = new IDS(searchTest.HIT_Table, "正心楼", "诚意楼");
		ids_hit.IDS_Search();
		
		System.out.println("**********************************A*算法罗马尼亚问题**********************************");
		AStar aStar = new AStar(searchTest.Lomania_Table);
		aStar.AStar_LomaniaSearch();
		System.out.println();
		System.out.println("**********************************A*算法哈工大导航问题**********************************");
		AStar hitStar = new AStar(searchTest.HIT_Table);
		hitStar.AStar_HITSearch();
	}
		
}
