package com.example.mylibrary.net;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.File;

/**
 * 用于解决listview嵌套的问题
 * 
 * @author lei
 * 
 */
public class Utility {

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}

	/**
	 * 获取文件后缀名
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFilePrefix(String filePath) {
		File f = new File(filePath);
		String fileName = f.getName();
		String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
		return prefix;
	}
}
