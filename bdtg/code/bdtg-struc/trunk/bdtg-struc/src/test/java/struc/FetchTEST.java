package struc;

import com.zjhcsoft.struc.fetch.wrapper.HttpCommonFetcherWrapper;

public class FetchTEST {

	public static void main(String[] args) {
		System.out.print(HttpCommonFetcherWrapper.httpExecute("http://www.hgzbw.com/l_huagong-xiangmu_1.html", "UTF-8", "GET"));
	}

}
	