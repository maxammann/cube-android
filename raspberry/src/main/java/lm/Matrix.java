package lm;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static lm.Lm.CreateFont;
import static lm.Lm.Position;
import static lm.Lm.PrintString;
import static lm.Lm.RGB;
import static lm.Lm.Request;
import static lm.Lm.Request.Type.*;
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

    public Matrix(InetAddress host, int port) throws IOException {
        this(new Socket(host, port).getOutputStream());
    }

    public void switchScreen(String name) throws IOException {
        send(request(SETSCREEN)
                .setSetscreen(SetScreen.newBuilder().setName(name)));
    }

    public void createFont(String font, int size) throws IOException {
        Request request = request(CREATEFONT)
                .setCreatefont(CreateFont.newBuilder()
                                .setId(0)
                                .setFont(font)
                                .setSize(size)
                ).build();

        send(request);
    }


    public void printString(String text, int x, int y) throws IOException {
        Request request = request(PRINTSTRING)
                .setPrintstring(PrintString.newBuilder()
                                .setRgb(RGB.newBuilder().setR(255).setG(0).setB(255))
                                .setFont(0)
                                .setPos(Position.newBuilder().setX(x).setY(y))
                                .setText(text)
                ).build();

        send(request);
    }

    public void clear() throws IOException {
        send(request(CLEAR));
    }

    public void swap() throws IOException {
        send(request(SWAPBUFFERS));
    }

    public void unpause() throws IOException {
        send(request(UNPAUSE));
    }

    public void pause() throws IOException {
        send(request(PAUSE));
    }

    public void screen(String name) throws IOException {
        send(request(SETSCREEN).setSetscreen(Lm.SetScreen.newBuilder().setName(name)));
    }

    public void alarms(Iterable<Lm.Alarm> alarms) throws IOException {
        Lm.Alarms.Builder alarmsContainer = Lm.Alarms.newBuilder();

        alarmsContainer.addAllAlarms(alarms);

        Request.Builder setAlarms = request(ALARM_REQUST)
                .setAlarmRequest(Lm.AlarmRequest.newBuilder().setType(Lm.AlarmRequest.Type.SET_ALARMS).setAlarms(alarmsContainer));
        send(setAlarms);
    }

    public void next() throws IOException {
        send(request(MENU_NEXT));
    }

    public Request.Builder request(Request.Type type) {
        return Request.newBuilder().setType(type);
    }


    public void send(Request.Builder request) throws IOException {
        send(request.build());
    }

    public void send(Request request) throws IOException {
        byte[] bytes = request.toByteArray();

        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(ByteOrder.BIG_ENDIAN);

        buffer.putInt(bytes.length);

        os.write(buffer.array());

        os.write(bytes);
        os.flush();
    }

    public void close() throws IOException {
        os.close();
    }
}
