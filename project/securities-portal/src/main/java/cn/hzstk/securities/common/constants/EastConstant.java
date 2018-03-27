package cn.hzstk.securities.common.constants;

public class EastConstant {
    public static final String hqdigi2 = "http://hqdigi2.eastmoney.com/EM_Quote2010NumericApplication/Index.aspx";
    public static final String hq2hk = "http://hq2hk.eastmoney.com/EM_Quote2010NumericApplication/Index.aspx";
    public static final String hq2nhk = "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx";
    public static final String hq2gjgp = "http://hq2gjgp.eastmoney.com/EM_Quote2010NumericApplication/Index.aspx";
    public static final String hq2gjqh = "http://hq2gjqh.eastmoney.com/EM_Futures2010NumericApplication/index.aspx";
    public static final String bkQuery = "http://hq2data.eastmoney.com/bk/BKQuery.aspx";
    public static final String AHQuoteList = "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx";
    public static final String hq2gnqh = "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx";
    public static final String zy = "?type=z&sortType=C&sortRule=-1&jsSort=1&jsName=quote_zy&ids={0}";
    public static final String sh = "?type=s&style=15&sortType=C&sortRule=-1&pageSize=5&page=1&jsName=quote_sh&Reference=rtj";
    public static final String zd = "?type=z&sortType=Default&jsName=quote_zd&Reference=rtj&ids=0000101,0000161,0000011,0000121,0000111,0000131";
    public static final String sz = "?type=s&style=25&sortType=C&sortRule=-1&pageSize=5&page=1&jsName=quote_sz&Reference=rtj";
    public static final String zdp = "?type=c&ids=10,11,20,21&&jsName=allzdp&reference=rtj";

    public static final String[] HQ_LIST = {"_A"};
    public static final String[] HQ_PARAM = {hq2nhk+"?type=CT&cmd=C.{0}&sty=FCOIATA&sortType=A&sortRule=1&page=1&pageSize=5000&lvl=&cb=&token=7bc05d0d4c3c22ef9fca8c2a912d779c&jsName=quote_123","default"};
    public static final String[] LIST_PARAM = {hq2nhk+"?type=CT&cmd=C.{0}&sty=FCOIATA&sortType=C&sortRule=-1&page=1&pageSize=5000&lvl=&cb=&token=7bc05d0d4c3c22ef9fca8c2a912d779c&jsName=quote_123","default"};
    public static final String[] SELF_PARAM = {hqdigi2+"?type=z&jsName=quote_mystocks&reference=rtj&ids={0}&v=0.5916964176361457","quotation"};
    public static final String[] BKHY_PARAM = {hq2nhk+"?type=CT&cmd=C._BKHY&sty=FCCS&st=c&p=1&ps=1000&sr=-1&cb=&token=d0075ac6916d4aa6ec8495db9efe7ede","Trade"};
    public static final String[] BKGN_PARAM = {hq2nhk+"?type=CT&cmd=C._BKGN&sty=FCCS&st=c&p=1&ps=1000&sr=-1&cb=&token=d0075ac6916d4aa6ec8495db9efe7ede","Notion"};
    public static final String[] INDEX_PARAM = {"http://nuff.eastmoney.com/EM_Finance2015TradeInterface/JS.ashx?id={0}&token=44c9d251add88e27b65ed86506f6e5da&cb=callback07597635712985804&callback=callback07597635712985804&_=1483968817498","Value"};
    public static final String[] INDEX_COUNT_PARAM = {"http://nufm3.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=SZ.CYB&sty=UDFN&st=z&sr=&p=&ps=&token=44c9d251add88e27b65ed86506f6e5da&cb=callback0055620886071356956&callback=callback0055620886071356956&_=1483971778782","default"};
    public static final String[] BK_LIST = {"BKHY","BKGN","BKDY"};
    public static final String[] BK_PARAM = {hq2nhk+"?type=CT&cmd=C._{0}&sty=FPGBKI&st=c&sr=-1&p=1&ps=5000&cb=&token=7bc05d0d4c3c22ef9fca8c2a912d779c&v=0.3134632949186802","default"};
    public static final String[] YJFP_PARAM = {"http://data.eastmoney.com/DataCenter_V3/yjfp/getlist.ashx?pagesize=5000&page=1&sr=-1&sortType=YAGGR&mtk=%C8%AB%B2%BF%B9%C9%C6%B1&filter=(ReportingPeriod='{0}')&rt=49491607", "data"};

    public static final String[] TIGER_PARAM = {"http://data.eastmoney.com/DataCenter_V3/stock2016/gghq.ashx?code={0}","data"};
    public static final String[] INVEST_PARAM = {"http://datainterface.eastmoney.com/EM_DataCenter/JS.aspx?type=GSRL&sty=GSRL&stat=10&fd={0}&sr=2&p=1&ps=5000","default"};
    public static final String[] ZJLX_PARAM = {hq2nhk+"?type=ct&st=(BalFlowMain)&sr=-1&p=1&ps=5000&token=894050c76af8597a853f5b408b759f5d&cmd=C._AB&sty=DCFFITA&rt=49572133","default"};
    public static final String[] YJBB_LIST = {"YJYG","YJKB","YJBB","YYSJ"};
    public static final String[] YJBB_PARAM = {"http://datainterface.eastmoney.com/EM_DataCenter/JS.aspx?type=SR&sty={0}&fd={1}&st=13&sr=-1&p=1&ps=5000","default"};
    public static final String[] YJYG_PARAM = {"http://datainterface.eastmoney.com/EM_DataCenter/JS.aspx?type=SR&sty={0}&fd={1}&st=4&sr=-1&p=1&ps=5000","default"};
    public static final String[] YYSJ_PARAM = {"http://datainterface.eastmoney.com/EM_DataCenter/JS.aspx?type=SR&sty={0}&fd={1}&st=2&sr=1&p=1&ps=5000","default"};
    public static final String[] HXSJ_PARAM = {"http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd={0}{1}&sty=GBFDB&st=z&sr=&p=&ps=&js=&token=5c46f660fab8722944521b8807de07c0","default"};
}
