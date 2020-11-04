

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

/**
 * @author xuxingjun
 * @data 2020/10/19  -  22:12
 */
public class MyDownload {
    public static int threadCount = 3;


    public static void main(String[] args) {
        /**
         * 从键盘获取要处理的文件，如果文件不存在退出程序
         * 存在则创建一个与要操作文件相同大小的空文件以备使用
         */
        Scanner getFileName = new Scanner(System.in);
        System.out.println("请输入要处理的文件路径：");
        String fileName = getFileName.nextLine();
        File file = new File(fileName);
        boolean exists = file.exists();
        if (!exists){
            System.out.println("文件不存在！！");
            return;
        }
        System.out.println("请输入处理之后的文件路径：");
        String newFileName = getFileName.nextLine();


        long fileSize = file.length();
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(newFileName, "rw");
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

        //计算每一个模块的大小
        long eachSize = fileSize / threadCount;

        for (int i = 0; i < threadCount; i++) {
            long startIndex = i * eachSize;
            long endIndex = (i + 1) * eachSize - 1;
            /**
             * 当时最后一个线程的时候,endIndex的值就由文件大小决定
             */
            if (i == threadCount - 1) {
                endIndex = fileSize;
            }
            DownThread downThread = new DownThread(fileName,newFileName, startIndex, endIndex);
            new Thread(downThread).start();

        }
    }
}





