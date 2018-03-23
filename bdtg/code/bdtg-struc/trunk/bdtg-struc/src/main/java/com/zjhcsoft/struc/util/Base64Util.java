package com.zjhcsoft.struc.util;

import java.io.UnsupportedEncodingException;

public class Base64Util {
	// 加密  
	public static String getBase64(String str) {  
        byte[] b = null;  
        String s = null;  
        try {  
            b = str.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        if (b != null) {  
            s =Base64.encode(b);  
        }  
        return s;  
    }  
  
    // 解密  
	public static String getFromBase64(String s) {  
        byte[] b = null;  
        String result = null;  
        if (s != null) {  
            try {  
                b = Base64.decode(s);  
                result = new String(b, "utf-8");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    }  
    public static String decode(String content){
    	content = content.replaceAll("\"", "");
    	content = content.substring(0, 1)+content.substring(2, content.length());
		content = content.substring(0, 4)+content.substring(5, content.length());
		content = getFromBase64(content);
		content = content.substring(0, 1)+content.substring(2, content.length());
		content = content.substring(0, 6)+content.substring(7, content.length());
		content = getFromBase64(content);
		return content;
    }
    public static String encode(String content){
    	content = getBase64(content);
    	content = content.substring(0, 1)+"p"+content.substring(1, content.length());    	
		content = content.substring(0, 7)+"p"+content.substring(7, content.length());
		content = getBase64(content);
		content = content.substring(0, 1)+"x"+content.substring(1, content.length());
		content = content.substring(0, 5)+"x"+content.substring(5, content.length());
		return content;
    }
	public static String combine_query(String pageindex, String startdate,
			String enddate, String ent_code, String sitetype, String site_code,
			String item_code) {
		String query = "{\"ticket\":\"451a9846-058b-4944-86c6-fccafdb7d8d0\",\"pageParams\":{\"pageSize\":20,\"pageIndex\":"
				+ pageindex
				+ "},\"queryParams\":{\"dStart\":\""
				+ startdate
				+ "\",\"dEnd\":\""
				+ enddate
				+ "\",\"enterpriseCode\":\""
				+ ent_code
				+ "\",\"monitorSiteType\":\""
				+ sitetype
				+ "\",\"monitorSiteCode\":\""
				+ site_code
				+ "\",\"igCode\":\""
				+ item_code + "\",\"isTwoHour\":-1,\"isAudit\":1}}";
		return query;
	}
	
	public static String jump_index(String pageindex,String base64) {
		String param = decode(base64.replaceAll("%3D", "="));
		param=param.substring(0,param.indexOf("\"pageIndex\":")+12)+pageindex+param.substring(param.indexOf("},\"queryParams\""));
		return encode(param).replaceAll("=", "%3D");
	}
	
	public static void main(String[] args) {
		String query = "ZxXB5xSnpkR3BGMGRYTWlPakVzSW1SaGRHRWlPbHQ3SW0xdmJtbDBiM0pUYVhSbFEyOWtaU0k2SWpNeU1EVTRNakF3TkRrNE1URTVNU0lzSW0xdmJtbDBiM0pUYVhSbFRtRnRaU0k2SXVXaG1PYWhwZW1WaCtheG9lYXd0T1draE9lUWh1YWNpZW1aa09XRnJPV1B1T2FPa3VhVXZ1V1BveUlzSW0xdmJtbDBiM0pUYVhSbFZIbHdaU0k2SWpBeElpd2liVzl1YVhSdmNsTnBkR1ZVZVhCbFRtRnRaU0k2SXVXNm4rYXd0T21iaHVTNHJlYU9rdWFVdmlJc0ltbG5VRXREYjJSbElqb2lOemxCTWpaRk5EUXRPRFl5UWkwME1UUXpMVUl4UTBRdFJrRkdNa0kwUmpJMFFrVkRJaXdpY0c5c2JIVjBhVzl1Um1GamRHOXlRMjlrWlNJNklqQXhNU0lzSW5CdmJHeDFkR2x2YmtaaFkzUnZjazVoYldVaU9pSkRUMFFpTENKdGIyNXBkRzl5Vlc1cGRDSTZJbTFuTDJ3aUxDSnRiMjVwZEc5eWFXNW5WSGx3WlNJNklqSWlMQ0p0YjI1cGRHOXlhVzVuVkhsd1pVNWhiV1VpT2lMb2g2cmxpcWpubTVIbXRZc2lMQ0p6WVcxd2JHbHVaMk41WTJ4bElqb2lNRElpTENKellXMXdiR2x1WjJONVkyeGxUbUZ0WlNJNkl1aS9udWU3cmVlYmtlYTFpeUlzSW5OMFlXNWtZWEprVlhCd1pYSk1hVzFwZENJNk5qQXVNQ3dpYzNSaGJtUmhjbVJNYjNkbGNreHBiV2wwSWpwdWRXeHNMQ0pOYjI1cGRHOXlSR0YwWlNJNklqQXdNREV0TURFdE1ERWdNREE2TURBNk1EQWlmU3g3SW0xdmJtbDBiM0pUYVhSbFEyOWtaU0k2SWpNeU1EVTRNakF3TkRrNE1URTVNU0lzSW0xdmJtbDBiM0pUYVhSbFRtRnRaU0k2SXVXaG1PYWhwZW1WaCtheG9lYXd0T1draE9lUWh1YWNpZW1aa09XRnJPV1B1T2FPa3VhVXZ1V1BveUlzSW0xdmJtbDBiM0pUYVhSbFZIbHdaU0k2SWpBeElpd2liVzl1YVhSdmNsTnBkR1ZVZVhCbFRtRnRaU0k2SXVXNm4rYXd0T21iaHVTNHJlYU9rdWFVdmlJc0ltbG5VRXREYjJSbElqb2lNemhrTVdVM00yUXRPV0pqTmkwME0yWXhMVGd6TW1NdE56a3lNalppTmpNNE1ESmpJaXdpY0c5c2JIVjBhVzl1Um1GamRHOXlRMjlrWlNJNklqQTJNQ0lzSW5CdmJHeDFkR2x2YmtaaFkzUnZjazVoYldVaU9pTG1zS2ptc0s0aUxDSnRiMjVwZEc5eVZXNXBkQ0k2SW0xbkwyd2lMQ0p0YjI1cGRHOXlhVzVuVkhsd1pTSTZJaklpTENKdGIyNXBkRzl5YVc1blZIbHdaVTVoYldVaU9pTG9oNnJsaXFqbm01SG10WXNpTENKellXMXdiR2x1WjJONVkyeGxJam9pTURJaUxDSnpZVzF3YkdsdVoyTjVZMnhsVG1GdFpTSTZJdWkvbnVlN3JlZWJrZWExaXlJc0luTjBZVzVrWVhKa1ZYQndaWEpNYVcxcGRDSTZOUzR3TENKemRHRnVaR0Z5WkV4dmQyVnlUR2x0YVhRaU9tNTFiR3dzSWsxdmJtbDBiM0pFWVhSbElqb2lNREF3TVMwd01TMHdNU0F3TURvd01Eb3dNQ0o5TEhzaWJXOXVhWFJ2Y2xOcGRHVkRiMlJsSWpvaU16SXdOVGd5TURBME9UZ3hNVGt4SWl3aWJXOXVhWFJ2Y2xOcGRHVk9ZVzFsSWpvaTVhR1k1cUdsNlpXSDVyR2g1ckMwNWFTRTU1Q0c1cHlKNlptUTVZV3M1WSs0NW82UzVwUys1WStqSWl3aWJXOXVhWFJ2Y2xOcGRHVlVlWEJsSWpvaU1ERWlMQ0p0YjI1cGRHOXlVMmwwWlZSNWNHVk9ZVzFsSWpvaTVicWY1ckMwNlp1RzVMaXQ1bzZTNXBTK0lpd2lhV2RRUzBOdlpHVWlPaUkxTXpJNVJEWXpRUzB4T1VGR0xUUkZNREV0T1RFeE5TMUVOekkwT0VRME5Ea3dSVElpTENKd2IyeHNkWFJwYjI1R1lXTjBiM0pEYjJSbElqb2lNVEF4SWl3aWNHOXNiSFYwYVc5dVJtRmpkRzl5VG1GdFpTSTZJdWFBdStlanR5SXNJbTF2Ym1sMGIzSlZibWwwSWpvaWJXY3ZiQ0lzSW0xdmJtbDBiM0pwYm1kVWVYQmxJam9pTWlJc0ltMXZibWwwYjNKcGJtZFVlWEJsVG1GdFpTSTZJdWlIcXVXS3FPZWJrZWExaXlJc0luTmhiWEJzYVc1blkzbGpiR1VpT2lJd01pSXNJbk5oYlhCc2FXNW5ZM2xqYkdWT1lXMWxJam9pNkwrZTU3dXQ1NXVSNXJXTElpd2ljM1JoYm1SaGNtUlZjSEJsY2t4cGJXbDBJam93TGpVc0luTjBZVzVrWVhKa1RHOTNaWEpNYVcxcGRDSTZiblZzYkN3aVRXOXVhWFJ2Y2tSaGRHVWlPaUl3TURBeExUQXhMVEF4SURBd09qQXdPakF3SW4xZExDSnBibVp2VEdsemRDSTZXMTBzSW5OeGJFbHVabTlNYVhOMElqcGJYU3dpZFhObFZHbHRaU0k2TUM0d2ZRPT0=";
		System.out.print(decode(query));
//		jump_index("2", "ZxXB5xSjBhV3BOclpYUWlPaUkwTlRGaE9UZzBOaTB3TlRoaUxUUTVORFF0T0Raak5pMW1ZMk5oWm1SaU4yUTRaREFpTENKd1lXZGxVR0Z5WVcxeklqcDdJbkJoWjJWVGFYcGxJam94TUN3aWNHRm5aVWx1WkdWNElqb3hmU3dpY1hWbGNubFFZWEpoYlhNaU9uc2laRk4wWVhKMElqb2lNakF4TlMwd09TMHdObFF3TURvd01Eb3dNQ0lzSW1SRmJtUWlPaUl5TURFMUxUQTVMVEEzVkRJek9qVTVPalU1SWl3aVpXNTBaWEp3Y21selpVTnZaR1VpT2lJek1qQXhNREF3TURBeE1qVWlMQ0p0YjI1cGRHOXlVMmwwWlZSNWNHVWlPaUl3TVNJc0ltMXZibWwwYjNKVGFYUmxRMjlrWlNJNklqTXlNREV3TURBd01ERXlOVEV3TVNJc0ltbG5RMjlrWlNJNklqQXdNU0lzSW1selZIZHZTRzkxY2lJNkxURXNJbWx6UVhWa2FYUWlPakY5ZlE9PQ==");
//		String str = "{\"ticket\":\"451a9846-058b-4944-86c6-fccafdb7d8d0\",\"pageParams\":{\"pageSize\":10,\"pageIndex\":1},\"queryParams\":{\"dStart\":\"2015-09-06T00:00:00\",\"dEnd\":\"2015-09-07T23:59:59\",\"enterpriseCode\":\"320100000125\",\"monitorSiteType\":\"01\",\"monitorSiteCode\":\"320100000125101\",\"igCode\":\"001\",\"isTwoHour\":-1,\"isAudit\":1}}";
//		System.out.print(encode(str));
	}

}
