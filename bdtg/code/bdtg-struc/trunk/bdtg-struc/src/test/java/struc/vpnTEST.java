package struc;

import com.zjhcsoft.struc.fetch.wrapper.HttpCommonFetcherWrapper;

public class vpnTEST {

	public static void main(String[] args) {
		System.out.print(HttpCommonFetcherWrapper.httpExecute("https://www.baidu.com/", "UTF-8", "GET","218.90.174.167",3128));
	}

}
	