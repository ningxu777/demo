package com.neil.demo.io_nio;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Neil on 2018/3/30.
 */
public class Nio {

    /**
     * FileChannel 例子
     */
    public static void fileChannel(){
        try {

            RandomAccessFile randomAccessFile = new RandomAccessFile("data/example.txt","rw");

            FileChannel channel = randomAccessFile.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(48);

            int bytesRead = channel.read(buf);

            while(bytesRead != -1){
                System.out.println("Read "+bytesRead);
                buf.flip();

                while(buf.hasRemaining()){
                    System.out.println((char)buf.get());
                }

                buf.clear();
                bytesRead = channel.read(buf);
            }
            randomAccessFile.close();
            channel.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
