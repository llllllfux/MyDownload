import java.io.IOException;
import java.io.RandomAccessFile;

/**
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
            writeResource.seek(startIndex);
//            System.out.println(readerResource.getFilePointer());
//            System.out.println(writeResource.getFilePointer());
//            System.out.println(startIndex+Thread.currentThread().getName());
//            System.out.println(endIndex + Thread.currentThread().getName());
            byte[] buffer = new byte[1024];
            int len;

            while ((len = readerResource.read(buffer)) != -1 && readerResource.getFilePointer() <= endIndex) {
//                System.out.println("线程" + Thread.currentThread().getName() + "  进入");

//                long l =writeResource.getFilePointer();
//                System.out.println(readerResource.getFilePointer());
                writeResource.write(buffer,0,len);

//                if (writeResource.getFilePointer() == endIndex ){
//
//                    System.out.println(Thread.currentThread().getName() + "进入判断");
////                    System.out.println(endIndex);
////                    System.out.println(writeResource.getFilePointer());
//                    readerResource.close();
//                    writeResource.close();
//                    break;
//                }
            }
//            System.out.println(writeResource.getFilePointer());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            System.out.println(startIndex+Thread.currentThread().getName());
//            try {
//                System.out.println(writeResource.getFilePointer()+Thread.currentThread().getName());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            System.out.println(endIndex + Thread.currentThread().getName());

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
            System.out.println("线程" + Thread.currentThread().getName() + "  执行完成！");
        }

    }
}
