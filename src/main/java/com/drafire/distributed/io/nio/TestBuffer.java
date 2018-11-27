package com.drafire.distributed.io.nio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class TestBuffer {
    //Bytebuffer a里的切片buffer b，会拥有自己的position、limit，mark会是null。有且只有a是只读的，b才是只读的；
    // 有且只有a是direct的，b才是direct
    //b改变，会影响a的内容（因为本来就是同一个buffer）

    public static void main(String[] args) {
        //put byte 是可以的，但是put int就不行了，为什么？
        //ps：这里是ByteBuffer，put int 的时候，当然要用IntBuffer啊，好傻  --20181127
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(i + "--" + buffer.capacity() + "--" + buffer.limit());
            String a = i + "";
            buffer.put(Byte.parseByte(a));
        }
        buffer.flip();
        buffer.position(0);
        buffer.limit(buffer.capacity());

        while (buffer.remaining() > 0) {
            System.out.println("[" + buffer.get() + "]");
        }
    }
}
