import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @description 编写线程类所需要的属性，实现Runnable接口，重写run方法
 * @author xuxingjun
 * @data 2020/10/19  -  22:33
 */
class DownThread implements Runnable {
    private String readFileName;
    private String writeFileName;
    private long startIndex;
    private long endIndex;

    public DownThread(String readFileName,String writeFileName, long startIndex, long endIndex) {
        this.readFileName = readFileName;
        this.writeFileName = writeFileName;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void run() {
        System.out.println("线程" + Thread.currentThread().getName() + "  开始执行...");
        RandomAccessFile readerResource = null;
        RandomAccessFile writeResource = null;
        try {
            readerResource = new RandomAccessFile(readFileName, "rw");
            writeResource = new RandomAccessFile(writeFileName, "rw");
            readerResource.seek(startIndex);
            writeResource.seek(startIndex);;
            byte[] buffer = new byte[1024];
            int len;

            while ((len = readerResource.read(buffer)) != -1 && writeResource.getFilePointer() <= endIndex) {
                writeResource.write(buffer,0,len);
            }
            System.out.println("线程" + Thread.currentThread().getName() + "  执行完成！");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {


            if (readerResource != null){
                try {
                    readerResource.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (writeResource != null){
                try {
                    writeResource.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
