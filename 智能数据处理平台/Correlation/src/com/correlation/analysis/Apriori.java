package com.correlation.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JTextArea;

public class Apriori {

	private ArrayList<String> testSet = new ArrayList<String>();
	// 具体解析后的数据集，可以根据具体文件格式扩展解析方式
	private List<List<String>> dataset = new ArrayList<List<String>>();

	// 第一个数据项集及频繁集
	private List<List<String>> cItemset = new ArrayList<List<String>>();
	private Map<List<String>, Integer> lItemset = new HashMap<List<String>, Integer>();
	
	// 后面的数据项集及频繁集
	private List<List<String>> ckItemset = new ArrayList<List<String>>();
	private Map<List<String>, Integer> lkItemset = new HashMap<List<String>, Integer>();

	// 假设min_sup_count = 2
	final static double MIN_SUPPORT = 2;
	private boolean endTag = false;
	private JTextArea textArea;

	public Apriori(ArrayList<String> testSet, JTextArea textArea) {
		this.testSet = testSet;
		praseSet();
		
		this.textArea = textArea;
	}

	// Apriori函数主函数
	public void aprioriAnalysis() {
		// 获取第一个项集
		getFirstCandidate();
		// 显示所有元素
		displayItem();
		// 获取第一个频繁项集
		lItemset = getSupportedItemset(cItemset);
		printfLKitemset(lItemset);

		while (!endTag) {
			ckItemset = getNextCandidate(lItemset) ;
            lkItemset = getSupportedItemset (ckItemset);
            if(lkItemset.size() != 0 )
                printfLKitemset ( lkItemset) ;
        
            cItemset = ckItemset ;
            lItemset = lkItemset ;
		}

	}

	// 获取第一个项集
	public void getFirstCandidate() {

		List<String> tempLine = new ArrayList<String>();
		// 获取所有单个元素
		for (int i = 0; i < dataset.size(); i++) {
			for (int j = 0; j < dataset.get(i).size(); j++) {
				if (tempLine.contains(dataset.get(i).get(j)))
					;
				else
					tempLine.add(dataset.get(i).get(j));
			}
		}

		for (int i = 0; i < tempLine.size(); i++) {
			List<String> str = new ArrayList<String>();
			str.add(tempLine.get(i));
			cItemset.add(str);
		}
	}

	public Map<List<String>, Integer> getSupportedItemset(
			List<List<String>> cItemset) {
		Map<List<String>, Integer> supportedItemset = new HashMap<List<String>, Integer>();
		// 查看当前是否还存在大于MIN_SUPPORT的项集
		boolean end = true;
		for (int i = 0; i < cItemset.size(); i++) {
			int count = countFrequent(cItemset.get(i));
			if (count >= MIN_SUPPORT) {
				supportedItemset.put(cItemset.get(i), count);
				end = false;
			}
		}

		endTag = end;
		return supportedItemset;
	}

	// 计算单项集是否包含某个元素
	public int countFrequent(List<String> list) {
		int count = 0;
		for (int i = 0; i < dataset.size(); i++) {
			boolean curRecordLineNotHave = false;

			for (int k = 0; k < list.size(); k++) {
				if (!dataset.get(i).contains(list.get(k))) {
					curRecordLineNotHave = true;
					break;
				}
			}
			if (curRecordLineNotHave == false) {
				count++;
			}
		}
		return count;
	}

	/**
	 * method following is the getNextCandidata usually can be known as get Ck
	 * from Lk-1
	 * */
	public List<List<String>> getNextCandidate(
			Map<List<String>, Integer> lItemset) {
		List<List<String>> nextItemset = new ArrayList<List<String>>();
		List<List<String>> preItemset = getLItemset(lItemset);

		for (int i = 0; i < preItemset.size(); i++) {
			List<String> tempLine = new ArrayList<String>();
			tempLine.addAll(preItemset.get(i));

			for (int j = i + 1; j < preItemset.size(); j++) {
				// 两组长度相同的集合
				if (preItemset.get(i).size() == preItemset.get(j).size()) {

					// 不同元素为1个
					if (1 == differElemNum(preItemset.get(i), preItemset.get(j))) {
						int index = getDifferIndex(preItemset.get(i),
								preItemset.get(j));

						tempLine.add(preItemset.get(j).get(index));
						// 判断加入新元素后是否为子集
						if (isSubSets(tempLine, preItemset)) {
							List<String> aLine = new ArrayList<String>();
							for (int m = 0; m < tempLine.size(); m++) {
								aLine.add(tempLine.get(m));
							}
							// 去除重复项
							if (nextItemSetNotHave(aLine, nextItemset))
								nextItemset.add(aLine);
						}
					}
				}// outer if
				tempLine.remove(tempLine.size() - 1);
			}// for j
		}

		return nextItemset;

	}

	// 得到对应的超过MIN_SUPPORT的项集
	public List<List<String>> getLItemset(Map<List<String>, Integer> lItemset) {
		List<List<String>> itemset = new ArrayList<List<String>>();
		Iterator<Map.Entry<List<String>, Integer>> iterator = lItemset
				.entrySet().iterator();
		Entry<List<String>, Integer> entry;
		while (iterator.hasNext()) {
			entry = iterator.next();
			List<String> key = entry.getKey();
			itemset.add(key);
		}
		return itemset;
	}

	// 计算长度相同的两个集合中不同元素的个数
	public int differElemNum(List<String> list1, List<String> list2) {
		int count = 0;
		boolean flag;
		for (int i = 0; i < list1.size(); i++) {
			flag = true;
			for (int j = 0; j < list1.size(); j++) {
				if (list1.get(i).equals(list2.get(j))) {
					flag = false;
					break;
				}
			}
			if (flag == true) {
				count++;
			}
		}
		return count;
	}

	// 计算不同元素的下标
	public int getDifferIndex(List<String> list1, List<String> list2) {
		int index = -1;
		for (int i = 0; i < list1.size(); i++) {
			for (int j = 0; j < list1.size(); j++) {
				if (!list1.get(i).equals(list2.get(j))) {
					index = j;
				}
			}
			if (index != -1)
				break;
		}

		return index;
	}

	// 判断一个集合是否为其子集
	// 讲 l2 中唯一一个不同于 l1 的元素 归并到 l1 中之后 得到的新的集合 l3) 任意l3 的子集 都必须属于 Lk-1 对应的集合中
	public boolean isSubSets(List<String> tempList, List<List<String>> lItemset) {

		boolean flag = false;

		for (int i = 0; i < tempList.size(); i++) {
			List<String> testLine = new ArrayList<String>();

			for (int j = 0; j < tempList.size(); j++) {
				if (i != j) {
					testLine.add(tempList.get(j));
				}
			}

			for (int k = 0; k < lItemset.size(); k++) {
				if (testLine.equals(lItemset.get(k))) {
					flag = true;
					break;
				}
			}

			if (flag == false) {
				return false;
			}
		}

		return flag; // return true;

	}

	public boolean nextItemSetNotHave(List<String> aLine,
			List<List<String>> nextItemset) {
		boolean notHave = true;

		for (int i = 0; i < nextItemset.size(); i++) {
			if (aLine.equals(nextItemset.get(i))) {
				notHave = false;
			}
		}
		return notHave;
	}

	// 解析文件数据格式
	public void praseSet() {
		for (int i = 1; i < testSet.size(); i++) {
			List<String> tmpLine = new ArrayList<String>();
			String[] tempHead = testSet.get(i).split("	");
			String[] tempContent = tempHead[1].split(", ");
			for (int j = 0; j < tempContent.length; j++) {
				tmpLine.add(tempContent[j]);
			}
			dataset.add(tmpLine);
		}
	}

	public void printfLKitemset(Map<List<String>, Integer> lkItemset) {
		Iterator<Entry<List<String>, Integer>> iterator = lkItemset.entrySet()
				.iterator();
		Entry<List<String>, Integer> entry;
		while (iterator.hasNext()) {
			entry = iterator.next();
			List<String> key = entry.getKey();
			Integer value = entry.getValue();
			for (int i = 0; i < key.size(); i++) {
				// System.out.print(key.get(i));
				textArea.append(key.get(i));
				// System.out.print("  ");
				textArea.append(" ");
			}
			// System.out.println("the value : " + value.intValue());
			textArea.append("the value : " + value.intValue() + "\n");
		}
	}

	public void display() {
		for (int i = 0; i < dataset.size(); i++) {
			for (int j = 0; j < dataset.get(i).size(); j++) {
				System.out.print(dataset.get(i).get(j) + " ");
			}
			System.out.println();
		}
	}

	public void displayItem() {
		for (int i = 0; i < cItemset.size(); i++) {
			for (int j = 0; j < cItemset.get(i).size(); j++) {
				System.out.print(cItemset.get(i).get(j) + " ");
			}
			System.out.println();
		}
	}

}
