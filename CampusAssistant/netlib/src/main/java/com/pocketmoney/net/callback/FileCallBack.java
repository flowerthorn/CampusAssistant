package com.pocketmoney.net.callback;

import com.pocketmoney.net.frame.IResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zhy on 15/12/15.
 */
public abstract class FileCallBack extends NetCallback<File> {
    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir;
    /**
     * 目标文件存储的文件名
     */
    private String destFileName;

    public FileCallBack(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
    }

    public File saveFile(IResponse response) throws IOException {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {
            is = response.byteStream();
            final long total = response.contentLength();

            long sum = 0;

            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        inProgress(finalSum * 1.0f / total, total);
                    }
                });
            }
            fos.flush();

            return file;

        } finally {
            try {
                response.byteStream().close();
                if (is != null) is.close();
            } catch (IOException e) {
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
            }
        }
    }

    @Override
    public File handleResponseInBackground(IResponse response) {
        try {
            return saveFile(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected abstract void inProgress(float percent, long total);

    @Override
    public abstract void onResponse(String url, File response, int statusCode);

    @Override
    public abstract void onFailure(String url, int statusCode);

}
