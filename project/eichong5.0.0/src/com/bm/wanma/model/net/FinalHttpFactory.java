package com.bm.wanma.model.net;


import net.tsz.afinal.FinalHttp;


public class FinalHttpFactory {
    private static FinalHttp finalHttp;

    public static FinalHttp getFinalHttp() {
        if(finalHttp == null){
            finalHttp = new FinalHttp();
        }
        return finalHttp;
    }
    public static void setFinalHttp(FinalHttp finalHttp) {
        FinalHttpFactory.finalHttp = finalHttp;
    }

}
