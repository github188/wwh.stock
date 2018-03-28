package com.ijustyce.fastandroiddev3.http;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by yangchun on 2016/11/10.
 */

public class DownloadResponseBody extends ResponseBody {

    private ResponseBody responseBody;
    private ProgressListener progressListener;
    private BufferedSource bufferedSource;

    public DownloadResponseBody(ResponseBody responseBody,
                                ProgressListener progressListener) {
        this.responseBody = responseBody;
        this.progressListener = progressListener;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L, total = -1;
            int percent = -1;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                if (bytesRead == -1) {
                    percent = 100;
                    if (progressListener != null) progressListener.onProgress(percent, true);
                    return  bytesRead;
                }
                totalBytesRead += bytesRead;
                if (total == -1) {
                    total = responseBody.contentLength();
                    if (total < 1) total = Long.MAX_VALUE;
                }
                int newPercent = (int)(totalBytesRead * 100 / total);
                if (newPercent == percent) return bytesRead;
                percent = newPercent;
                if (percent < 0) percent = 0;
                if (percent > 100) percent = 100;
                if (null != progressListener) {
                    progressListener.onProgress(percent, false);
                }
                return bytesRead;
            }
        };

    }
}
