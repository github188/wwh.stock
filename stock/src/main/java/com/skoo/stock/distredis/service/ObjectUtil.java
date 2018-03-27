package com.skoo.stock.distredis.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * <p>对象与字节数组之间的转换工具类,要求传入的对象必须实现序列号接口.</p>
 * @author youfa yu
 * Jun 21, 2009
 */
public class ObjectUtil {

    /**
     * 对象转换成字节数组,要求传入的对象必须实现序列号接口.
     * @param obj
     * @return byte[]
     */
    public static byte[] ObjectToByte(Object obj)
    {
        byte[] bytes = null;
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);

            bytes = bo.toByteArray();

            bo.close();
            oo.close();
        }
        catch(Exception e) {
            System.out.println("请检查你传入的对象是否继承了Serializable接口");
            System.out.println("translation"+e.getMessage());
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * 字节数组转换成对象
     * @param bytes
     * @return Object 取得结果后强制转换成你存入的对象类型
     */
    public static Object ByteToObject(byte[] bytes){
        Object obj = null;
        try {
            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream oi = new ObjectInputStream(bi);

            obj = oi.readObject();

            bi.close();
            oi.close();
        }
        catch(Exception e) {
            System.out.println("translation"+e.getMessage());
            e.printStackTrace();
        }
        return obj;
    }

}