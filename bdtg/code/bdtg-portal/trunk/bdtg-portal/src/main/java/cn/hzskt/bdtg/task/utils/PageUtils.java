package cn.hzskt.bdtg.task.utils;

import java.util.List;

import com.github.pagehelper.PageInfo;

public class PageUtils {

	/**
	 * @param items 分页数据集合
	 * @param total 总记录数
	 * @param pageIndex	当前页数
	 * @param pageSize 每页显示的记录数大小
	 * @param pagnationNum 底部显示的页数个数
	 * @return
	 */
	public static PageInfo buildPageInfo(List items, int total, int pageIndex, int pageSize, int pagnationNum) {

		int pageNums = (total + pageSize - 1) / pageSize;
		if(pageNums < 1) pageNums = 1;
		PageInfo p = new PageInfo();
		p.setList(items);
		p.setTotal(total);
		p.setPageNum(pageIndex);
		p.setPageSize(pageSize);
		p.setPages(pageNums);
		
		p.setHasPreviousPage(pageIndex > 1 && pageIndex <= pageNums);
		p.setHasNextPage(pageIndex < pageNums);
		
		int nums = pageIndex > pageNums ? pageNums : pageIndex;
		int len = pageNums > pagnationNum ? pagnationNum : pageNums;	//显示的个数
		
		int start = (nums - pagnationNum < 1) ? 1 : (nums - pagnationNum + 1);
		
		int[] array = new int[len];
		for(int i = 0; i < len; i++){
			array[i] = start + i;
		}
		p.setNavigatepageNums(array);
		p.setStartRow((pageIndex-1) * pageSize + 1);
		p.setEndRow((pageIndex * pageSize > total) ? total:pageIndex * pageSize);
		return p;
	}
	
}
