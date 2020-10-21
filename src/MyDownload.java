
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author xuxingjun
 * @data 2020/10/19  -  22:12
 */
public class MyDownload {
    public static int threadCount = 5;


    public static void main(String[] args) {
        File file = new File("");
        long fileSize = file.length();
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile("", "rw");
            randomAccessFile.setLength(fileSize);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        System.out.println(fileSize);
        long l =  (fileSize % (1024 * threadCount));
//        System.out.println("l = " + l);
        long newFileSize = fileSize - l;
//        System.out.println(newFileSize);
        long eachSize = newFileSize / threadCount;
//        System.out.println(eachSize);


        for (int i = 0; i < threadCount; i++) {
            long startIndex = i * eachSize;
            long endIndex = (i + 1) * eachSize - 1;
            /**
             * 当时最后一个线程的时候,endIndex的值就由文件大小决定
             */
            if (i == threadCount - 1) {
                endIndex = fileSize;

            }
            DownThread downThread = new DownThread("","", startIndex, endIndex);
            new Thread(downThread).start();
        }
    }
}





