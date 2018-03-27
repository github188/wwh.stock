package com.bm.wanma.entity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.bm.wanma.utils.TimeUtil;

/**
 * @author cm
 * 把初始Arraylist里面的数据,转化成带有分组并且标识悬浮类别(SECTION)和内容(ITEM)的list
 */
public class InvoiceRecordSectionBean {
	//类型--内容
	public static final int ITEM = 0;
	//类型--顶部悬浮的标题
	public static final int SECTION = 1;
	public final int type; //所属于的类型
	public final InvoiceRecordBean billBean; //listview显示的item的数据实体类,可根据自己的项目来设置


	public InvoiceRecordSectionBean(int type, InvoiceRecordBean messages) {
		super();
		this.type = type;
		this.billBean = messages;
	}

	public int getType() {
		return type;
	}
	public InvoiceRecordBean getBillBean() {
		return billBean;
	}

	/**
	 * 通过HashMap键值对的特性，将ArrayList的数据进行分组，返回带有分组Header的ArrayList。
	 * @param details 从后台接受到的ArrayList的数据，其中日期格式为：yyyy-MM-dd HH:mm:ss
	 * @return list  返回的list是分类后的包含header（yyyy-MM-dd）和item（HH:mm:ss）的ArrayList
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList<InvoiceRecordSectionBean> getData(
			List<InvoiceRecordBean> details) {
		//最后我们要返回带有分组的list,初始化
		ArrayList<InvoiceRecordSectionBean> list = new ArrayList<InvoiceRecordSectionBean>();
		//时间转换的util类
		//Map<BillBean, List<BillBean>> map = new HashMap<BillBean, List<BillBean>>();//key无序
		Map<InvoiceRecordBean, List<InvoiceRecordBean>> map = new LinkedHashMap<InvoiceRecordBean, List<InvoiceRecordBean>>();
		// 按照detail里面的时间进行分类
		InvoiceRecordBean detail = new InvoiceRecordBean();
		for (int i = 0; i < details.size(); i++) {
			try {
				String key = TimeUtil.getYYMM(details.get(i)
						.getTime());
				if (detail.getTime() != null && !"".equals(detail.getTime())) {
					//判断这个Key对象有没有生成,保证是唯一对象.如果第一次没有生成,那么new一个对象,之后同组的其他item都指向这个key
					boolean b = !key.equals(detail.getTime());
					if (b) {
						detail = new InvoiceRecordBean();
					}
				}
				detail.setTime(key);
				//把属于当月yyyy-MM的时间 全部指向这个key
				List<InvoiceRecordBean> billDetails = map.get(detail);
				//判断这个key对应的值有没有初始化,若第一次进来,这new一个arryalist对象,之后属于这一天的item都加到这个集合里面
				if (billDetails == null) {
					billDetails = new ArrayList<InvoiceRecordBean>();
				}
				billDetails.add(details.get(i));
				map.put(detail, billDetails);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		// 用迭代器遍历map添加到list里面
		Iterator iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Entry) iterator.next();
			InvoiceRecordBean key = (InvoiceRecordBean) entry.getKey();
			//我们的key(yyyy-MM)作为标题.类别属于SECTION
			list.add(new InvoiceRecordSectionBean(SECTION, key));
			List<InvoiceRecordBean> li = (List<InvoiceRecordBean>) entry.getValue();
			for (InvoiceRecordBean bean : li) {
				//对应的值(HH:mm:ss)作为标题下的item,类别属于ITEM
				list.add(new InvoiceRecordSectionBean(ITEM, bean));
			}
		}
		// 把分好类的hashmap添加到list里面便于显示
		return list;
	}
	
	public static ArrayList<InvoiceRecordSectionBean> addList(ArrayList<InvoiceRecordSectionBean> list1,ArrayList<InvoiceRecordSectionBean> list2){
		
		for(InvoiceRecordSectionBean bean : list1){
			if(bean.getBillBean().getTime().equals(list2.get(0).getBillBean().getTime())){
				list2.remove(0);
				break;
			}
		}
		list1.addAll(list2);
		return list1;
	}


	
}
