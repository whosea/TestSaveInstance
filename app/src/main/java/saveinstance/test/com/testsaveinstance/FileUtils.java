package saveinstance.test.com.testsaveinstance;

import android.content.Context;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class FileUtils {

    public static void writeToSDCard(String path, String data) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File(path));
            writer.write(data);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeSilently(writer);
        }
    }

    public static String readByAssets(Context context, String path) {
        return readByAssets(context,path, "utf-8");
    }

    public static String readByAssets(Context context, String path, String charset) {
        InputStream is = null;
        BufferedReader reader = null;
        try {
            StringBuffer sb = new StringBuffer();
            is = context.getAssets().open(path);
            reader = new BufferedReader(new InputStreamReader(is, charset));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();

            return "";
        } finally {
            closeSilently(reader);
            closeSilently(is);
        }
    }

    public static String readBySDCard(String path) {
        return readBySDCard(path, "utf-8");
    }

    public static String readBySDCard(String path, String charset) {
        FileInputStream is = null;
        BufferedReader reader = null;
        try {
            StringBuffer sb = new StringBuffer();

            is = new FileInputStream(new File(path));
            reader = new BufferedReader(new InputStreamReader(is, charset));

            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();

            return "";
        } finally {
            closeSilently(reader);
            closeSilently(is);
        }
    }

    // 删除该文件夹下的所有文件(不包括本身文件夹和子文件夹)
    public static void deleteFiles(File file) {
        if (!file.exists()) {
            return;
        } else {
            if (file.isFile()) {
                file.delete();
                return;
            } else if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    return;
                }

                for (File f : childFile) {
                    f.delete();
                }
            }
        }
    }

    /**
     * 清除缓存目录
     *
     * @param dir 目录
     * @param
     * @return
     */
    public static int clearCacheFolder(File dir, long curTime) {
        int deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, curTime);
                    }
                    if (child.lastModified() < curTime) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }

    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
//            return size + "Byte";
            return "0 K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + " KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + " MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + " GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + " TB";
    }


    private static void closeSilently(Closeable c) {
        if (c == null) {
            return;
        }
        try {
            c.close();
        } catch (Throwable t) {
            c = null;
        }
    }
}
