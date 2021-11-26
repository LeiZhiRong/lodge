package com.shags.lodge.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author yglei
 * @classname UdpGetClientMacAdder
 * @description 客户端MAC地址获取工具类
 * @date 2021-07-29 13:07
 */
public class UdpGetClientMacAdder {
    private final String sRemoteAdder;
    private final byte[] buffer = new byte[1024];
    private final DatagramSocket ds;

    public UdpGetClientMacAdder(String strAdder) throws Exception {
        sRemoteAdder = strAdder;
        ds = new DatagramSocket();
    }

    public final void send(final byte[] bytes) throws IOException {
        int iRemotePort = 137;
        InetAddress address=InetAddress.getByName(sRemoteAdder);
        DatagramPacket dp = new DatagramPacket(bytes, bytes.length, address, iRemotePort);
        ds.send(dp);
    }

    public final DatagramPacket receive() throws Exception {
        DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
        ds.receive(dp);
        return dp;
    }

    public byte[] GetQueryCmd() {
        byte[] t_ns = new byte[50];
        t_ns[0] = 0x00;
        t_ns[1] = 0x00;
        t_ns[2] = 0x00;
        t_ns[3] = 0x10;
        t_ns[4] = 0x00;
        t_ns[5] = 0x01;
        t_ns[6] = 0x00;
        t_ns[7] = 0x00;
        t_ns[8] = 0x00;
        t_ns[9] = 0x00;
        t_ns[10] = 0x00;
        t_ns[11] = 0x00;
        t_ns[12] = 0x20;
        t_ns[13] = 0x43;
        t_ns[14] = 0x4B;

        for (int i = 15; i < 45; i++) {
            t_ns[i] = 0x41;
        }
        t_ns[45] = 0x00;
        t_ns[46] = 0x00;
        t_ns[47] = 0x21;
        t_ns[48] = 0x00;
        t_ns[49] = 0x01;
        return t_ns;
    }

    public final String GetMacAdder(byte[] breadth) {
        int i = breadth[56] * 18 + 56;
        String sAdder;
        StringBuilder sb = new StringBuilder(17);
        for (int j = 1; j < 7; j++) {
            sAdder = Integer.toHexString(0xFF & breadth[i + j]);
            if (sAdder.length() < 2) {
                sb.append(0);
            }
            sb.append(sAdder.toUpperCase());
            if (j < 6) sb.append(':');
        }
        return sb.toString();
    }

    public final void close() throws Exception {
        ds.close();
    }

    public final String GetRemoteMacAdder() throws Exception {
        byte[] backed = GetQueryCmd();
        send(backed);
        DatagramPacket dp = receive();
        String smack = GetMacAdder(dp.getData());
        close();
        return smack;
    }

    public static String getLocalMac(String strAdder) throws Exception {
        InetAddress adder = InetAddress.getLocalHost();
        try {
            if (!(adder.getHostAddress()).equals(strAdder)) {
                UdpGetClientMacAdder sumac = new UdpGetClientMacAdder(strAdder);

                return sumac.GetRemoteMacAdder();
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }
}
