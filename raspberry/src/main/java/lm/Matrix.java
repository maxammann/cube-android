package lm;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static lm.Lm.CreateFont;
import static lm.Lm.Position;
import static lm.Lm.PrintString;
import static lm.Lm.RGB;
import static lm.Lm.Request;
import static lm.Lm.SetScreen;

/**
 * Represents a Matrix
 */
public class Matrix {

    private final DataOutputStream os;

    public Matrix(OutputStream os) {this.os = new DataOutputStream(os);}

//    public Matrix(String socket) throws IOException {
//        this(new UnixDomainSocketClient(socket, JUDS.SOCK_STREAM).getOutputStream());
//    }

    public Matrix(String host, int port) throws IOException {
        this(new Socket(host, port).getOutputStream());
    }

    public void switchScreen(String name) throws IOException {
        Request request = Request.newBuilder().setType(Request.Type.SETSCREEN)
                .setSetscreen(SetScreen.newBuilder().setName(name)).build();

        sendRequest(request);
    }

    public void createFont(String font, int size) throws IOException {
        Request request = Request.newBuilder().setType(Request.Type.CREATEFONT)
                .setCreatefont(CreateFont.newBuilder()
                                .setId(0)
                                .setFont(font)
                                .setSize(size)
                ).build();

        sendRequest(request);
    }


    public void printString(String text, int x, int y) throws IOException {
        Request request = Request.newBuilder().setType(Request.Type.PRINTSTRING)
                .setPrintstring(PrintString.newBuilder()
                                .setRgb(RGB.newBuilder().setR(255).setG(0).setB(255))
                                .setFont(0)
                                .setPos(Position.newBuilder().setX(x).setY(y))
                                .setText(text)
                ).build();

        sendRequest(request);
    }

    public void clear() throws IOException {
        sendRequest(Request.newBuilder().setType(Request.Type.CLEAR).build());
    }

    public void swap() throws IOException {
        sendRequest(Request.newBuilder().setType(Request.Type.SWAPBUFFERS).build());
    }

    public void unpause() throws IOException {
        sendRequest(Request.newBuilder().setType(Request.Type.UNPAUSE).build());
    }

    public void pause() throws IOException {
        sendRequest(Request.newBuilder().setType(Request.Type.PAUSE).build());
    }

    private void sendRequest(Request request) throws IOException {
        byte[] bytes = request.toByteArray();

//        os.writeInt(bytes.length);

        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(ByteOrder.BIG_ENDIAN);

        buffer.putInt(bytes.length);

        System.out.println(bytes.length);


        os.write(buffer.array());

        os.write(bytes);
        os.flush();
    }

    public void close() throws IOException {
        os.close();
    }
}
