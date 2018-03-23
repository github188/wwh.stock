package cn.hzskt.bdtg.common.util.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.PutObjectRequest;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by allenwc on 16/2/24.
 */
public class OSSUtil {

    public static void upload(File localFile, String bucketName, String key) {
        OSSClient client = new OSSClient(OSSConfig.INSTANCE.getEndPoint(), OSSConfig.INSTANCE.getAccessKeyId(), OSSConfig.INSTANCE.getAccessKeySecret());
        client.putObject(new PutObjectRequest(bucketName, key, localFile));
        client.shutdown();
    }

    public static void upload(InputStream is,String bucketName,String key) {
        OSSClient client = new OSSClient(OSSConfig.INSTANCE.getEndPoint(), OSSConfig.INSTANCE.getAccessKeyId(), OSSConfig.INSTANCE.getAccessKeySecret());
        client.putObject(new PutObjectRequest(bucketName, key, is));
        client.shutdown();
    }

    public static void delete(String bucketName,String key) {
        OSSClient client = new OSSClient(OSSConfig.INSTANCE.getEndPoint(), OSSConfig.INSTANCE.getAccessKeyId(), OSSConfig.INSTANCE.getAccessKeySecret());
        client.deleteObject(new DeleteObjectsRequest(bucketName).withKey(key));
        client.shutdown();
    }

    public static void batchDelete(String bucketName,String prefix) {
        OSSClient client = new OSSClient(OSSConfig.INSTANCE.getEndPoint(), OSSConfig.INSTANCE.getAccessKeyId(), OSSConfig.INSTANCE.getAccessKeySecret());
        List<String> keys = new ArrayList<String>();
        int cnt = 1;
        while(cnt >0) {
            List<OSSObjectSummary> sums = client.listObjects(bucketName,prefix).getObjectSummaries();
            cnt = sums.size();
            if(cnt >0) {
                for(OSSObjectSummary summary : sums) {
                    keys.add(summary.getKey());
                }
                client.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys));
            }
        }
        client.shutdown();
    }

    public static void main(String[] args) {
        File f = new File("/Users/allenwc/Downloads/logo.png");
        OSSUtil.upload(f,OSSConfig.INSTANCE.getImgBucket(),"test/logo.png");
    }

}
