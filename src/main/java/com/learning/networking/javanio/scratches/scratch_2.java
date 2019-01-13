import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

class Scratch {
    public static void main(String[] args) {
        ByteBuffer buf = ByteBuffer.allocate(40);

        try (RandomAccessFile file = new RandomAccessFile("/Users/kkr0007/Downloads/test.txt", "rw");
             FileChannel fChannel = file.getChannel()) {

            int bytesRead = fChannel.read(buf);

            while (bytesRead != -1) {
                buf.flip();

                while (buf.hasRemaining()) {
                    System.out.print((char)buf.get());
                }

                buf.clear();
                bytesRead = fChannel.read(buf);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}