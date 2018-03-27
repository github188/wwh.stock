package cn.hzstk.securities.util;

public class GB2Alpha {
    //字母Z使用了两个标签，这里有２７个值
    //i, u, v都不做声母, 跟随前面的字母
    private char[] chartable =
        {
            '啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈', '哈',
            '击', '喀', '垃', '妈', '拿', '哦', '啪', '期', '然',
            '撒', '塌', '塌', '塌', '挖', '昔', '压', '匝', '座'
        };

    private char[] alphatable =
        {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
            'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        };

    //private static int[] table = {45217, 45253, 45761, 46318, 46826, 47010, 47297, 47614, 47614, 48119, 49062, 49324, 49896, 50371, 50614, 50622, 50906, 51387, 51446, 52218, 52218, 52218, 52698, 52980, 53689, 54481, 55289};

    private int[] table = new int[27];
    //初始化
    {
        for (int i = 0; i < 27; ++i) {
            table[i] = gbValue(chartable[i]);
        }
    }

    public GB2Alpha() {
    }

    //主函数,输入字符,得到他的声母,
    //英文字母返回对应的大写字母
    //其他非简体汉字返回 '0'

    public char Char2Alpha(char ch) {
        String tmp = ch + "";
        switch (tmp) {
            case "鳌":
                return 'A';
            case "钛":
            case "蜓":
            case "螳":
            case "钽":
            case "沱":
            case "韬":
                return 'T';
            case "行":
            case "锆":
            case "昊":
            case "晖":
            case "瀚":
            case "癀":
            case "骅":
            case "灏":
                return 'H';
            case "晟":
            case "宸":
            case "萃":
                return 'C';
            case "孚":
                return 'F';
            case "莞":
            case "钴":
                return 'G';
            case "濮":
                return 'P';
            case "锝":
            case "黛":
                return 'D';
            case "稞":
            case "恺":
                return 'K';
            case "岷":
            case "钼":
            case "懋":
                return 'M';
            case "玑":
            case "迦":
            case "珈":
            case "琚":
                return 'J';
            case "珑":
            case "麟":
            case "锂":
            case "榈":
            case "鹭":
            case "泸":
            case "翎":
            case "徕":
                return 'L';
            case "蜻":
            case "荃":
            case "琪":
                return 'Q';
            case "榕":
            case "螂":
            case "睿":
                return 'R';
            case "昇":
                return 'S';
            case "圳":
            case "獐":
            case "祯":
            case "柘":
                return 'Z';
            case "鑫":
            case "浔":
            case "曦":
            case "馨":
            case "禧":
                return 'X';
            case "钰":
            case "怡":
            case "楹":
            case "兖":
                return 'Y';
        }

        if (ch >= 'a' && ch <= 'z')
            return (char) (ch - 'a' + 'A');
        if (ch >= 'A' && ch <= 'Z')
            return ch;
        int gb = gbValue(ch);
        if (gb < table[0])
            return '0';
        int i;
        for (i = 0; i < 26; ++i) {
            if (match(i, gb)) break;
        }
        if (i >= 26)
            return '0';
        else
            return alphatable[i];
    }

    //根据一个包含汉字的字符串返回一个汉字拼音首字母的字符串
    public String String2Alpha(String SourceStr){

        String Result = "";
        int StrLength = SourceStr.length();
        int i;
        try {
            for (i = 0; i < StrLength; i++) {
                Result += Char2Alpha(SourceStr.charAt(i));
            }
        } catch (Exception e) {
            Result = "";
        }
        return Result.replace("0","");
    }

    private boolean match(int i, int gb) {

        if (gb < table[i])
            return false;
        int j = i + 1;

        //字母Z使用了两个标签
        while (j < 26 && (table[j] == table[i])) ++j;
        if (j == 26)
            return gb <= table[j];
        else
            return gb < table[j];
    }

    //取出汉字的编码
    private int gbValue(char ch) {

        String str = new String();
        str += ch;
        try {
            byte[] bytes = str.getBytes("GBK");
            if (bytes.length < 2)
                return 0;
            return (bytes[0] << 8 & 0xff00) + (bytes[1] &
                    0xff);
        } catch (Exception e) {
            return 0;
        }
    }

    public static void main(String[] args) {

        GB2Alpha obj1 = new GB2Alpha();
        System.out.println(obj1.String2Alpha("指数50"));
        System.out.println(obj1.String2Alpha("红蜻蜓"));
        /*char ch = "Z".charAt(0);
        byte b = (byte)ch;
        System.out.println((int)b);
        int i = 0x41;
        b = (byte)i;
        System.out.println(b);
        System.out.println((int)ch);*/
        return;
    }
}

