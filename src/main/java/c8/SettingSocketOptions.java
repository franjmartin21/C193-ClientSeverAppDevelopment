package c8;

import java.io.IOException;
import java.net.Socket;

public class SettingSocketOptions {
    /**
     * Socket options specify how the native sockets on which the Java Socket class relies send and receive data. Java
     * supports nine options for client-side sockets:
     * TCP_NODELAY
     * SO_BINDADDR
     * SO_TIMEOUT
     * SO_LINGER
     * SO_SNDBUF
     * SO_RCVBUF
     * SO_KEEPALIVE
     * OOBINLINE
     * IP_TOS
     */

    /**
     * TCP_NODELAY
     *
     * public void setTcpNoDelay(boolean on) throws SocketException
     * public boolean getTcpNoDelay() throws SocketException
     *
     * Setting TCP_NODELAY to true ensures that packets are sent as quickly as possible regardless of their size. Normally,
     * small (one-byte) packets are combined into larger packets before being sent. Before sending another packet, the local
     * host waits to receive acknowledgment of the previous packet from the remote system. This is known as Nagle's algorithm.
     * The problem with Nagle's algorithm is that if the remote system doesn't send acknowledgments back to the local system
     * fast enough, applications that depend on the steady transfer of small parcels of information may slow down.
     *
     * setTcpNoDelay(true) turns off buffering for the socket. setTcpNoDelay(false) turns it back on. getTcpNoDelay()
     * returns true if buffering is off and false if buffering is on. For example, the following fragment turns off buffering
     * (that is, it turns on TCP_NODELAY) for the socket s if it isn't already off:
     *
     * if (!s.getTcpNoDelay()) s.setTcpNoDelay(true);
     *
     * These two methods are each declared to throw a SocketException, which will happen if the underlying socket implementation
     * doesn't support the TCP_ NODELAY option.
     */

    /**
     * SO_LINGER
     *
     * public void setSoLinger(boolean on, int seconds) throws SocketException
     * public int getSoLinger() throws SocketException
     *
     * The SO_LINGER option specifies what to do with datagrams that have not yet been sent when a socket is closed. By
     * default, the close() method returns immediately; but the system still tries to send any remaining data. If the linger
     * time is set to zero, any unsent packets are thrown away when the socket is closed. If SO_LINGER is turned on and
     * the linger time is any positive value, the close() method blocks while waiting the specified number of seconds
     * for the data to be sent and the acknowledgments to be received. When that number of seconds has passed, the socket
     * is closed and any remaining data is not sent, acknowledgment or no.
     *
     *  These two methods each throw a SocketException if the underlying socket implementation does not support the SO_LINGER
     *  option. The setSoLinger() method can also throw an IllegalArgumentException if you try to set the linger time to a
     *  negative value. However, the getSoLinger() method may return –1 to indicate that this option is disabled, and as
     *  much time as is needed is taken to deliver the remaining data; for example, to set the linger timeout for the Socket
     *  s to four minutes, if it's not already set to some other value:
     *
     *  if (s.getTcpSoLinger() == -1) s.setSoLinger(true, 240);
     *
     *  The maximum linger time is 65,535 seconds, and may be smaller on some platforms. Times larger than that will be
     *  reduced to the maximum linger time. Frankly, 65,535 seconds (more than 18 hours) is much longer than you actually
     *  want to wait. Generally, the platform default value is more appropriate.
     */

    /**
     * SO_TIMEOUT
     *
     * public void setSoTimeout(int milliseconds) throws SocketException
     * public int getSoTimeout() throws SocketException
     *
     * Normally when you try to read data from a socket, the read() call blocks as long as necessary to get enough bytes.
     * By setting SO_TIMEOUT, you ensure that the call will not block for more than a fixed number of milliseconds. When
     * the timeout expires, an InterruptedIOException is thrown, and you should be prepared to catch it. However, the socket
     * is still connected. Although this read() call failed, you can try to read from the socket again. The next call may succeed.
     *
     * Timeouts are given in milliseconds. Zero is interpreted as an infinite timeout; it is the default value. For example,
     * to set the timeout value of the Socket object s to 3 minutes if it isn't already set, specify 180,000 milliseconds:
     *
     * if (s.getSoTimeout() == 0) s.setSoTimeout(180000);
     *
     * These two methods each throw a SocketException if the underlying socket implementation does not support the SO_TIMEOUT
     * option. The setSoTimeout() method also throws an IllegalArgumentException if the specified timeout value is negative.
     */


    /**
     * SO_RCVBUF and SO_SNDBUF
     *
     * TCP uses buffers to improve network performance. Larger buffers tend to improve performance for reasonably fast
     * (say, 10Mbps and up) connections whereas slower, dial-up connections do better with smaller buffers. Generally,
     * transfers of large, continuous blocks of data, which are common in file transfer protocols such as FTP and HTTP,
     * benefit from large buffers, whereas the smaller transfers of interactive sessions, such as Telnet and many games,
     * do not. Relatively old operating systems designed in the age of small files and slow networks, such as BSD 4.2,
     * use two-kilobyte buffers. Windows XP used 17,520 byte buffers. These days, 128 kilobytes is a common default.
     *
     * Maximum achievable bandwidth equals buffer size divided by latency. For example, on Windows XP suppose the latency
     * between two hosts is half a second (500 ms). Then the bandwidth is 17520 bytes / 0.5 seconds = 35040 bytes / second = 273.75
     * kilobits / second. That's the maximum speed of any socket, regardless of how fast the network is.
     *
     * You can use ping to check the latency to a particular host manually, or you can time a call to InetAddress.isReachable()
     * from inside your program.
     *
     * The SO_RCVBUF option controls the suggested send buffer size used for network input. The SO_SNDBUF option controls
     * the suggested send buffer size used for network output:
     *
     * public void setReceiveBufferSize(int size) throws SocketException, IllegalArgumentException
     * public int getReceiveBufferSize() throws SocketException
     * public void setSendBufferSize(int size) throws SocketException, IllegalArgumentException
     * public int getSendBufferSize() throws SocketException
     *
     * Although it looks like you should be able to set the send and receive buffers independently, the buffer is usually
     * set to the smaller of these two. For instance, if you set the send buffer to 64K and the receive buffer to 128K,
     * you'll have 64K as both the send and receive buffer size. Java will report that the receive buffer is 128K, but
     * the underlying TCP stack will really be using 64K.
     *
     * The setReceiveBufferSize()/setSendBufferSize methods suggest a number of bytes to use for buffering output on this socket.
     * However, the underlying implementation is free to ignore or adjust this suggestion. In particular, Unix and Linux
     * systems often specify a maximum buffer size, typically 64K or 256K, and do not allow any socket to have a larger one.
     * If you attempt to set a larger value, Java will just pin it to the maximum possible buffer size. On Linux, it's not
     * unheard of for the underlying implementation to double the requested size. For example, if you ask for a 64K buffer,
     * you may get a 128K buffer instead.
     *
     * These methods throw an IllegalArgumentException if the argument is less than or equal to zero. Although they're
     * also declared to throw SocketException, they probably won't in practice, because a SocketException is thrown for
     * the same reason as IllegalArgumentException and the check for the IllegalArgumentException is made first.
     */

    /**
     * SO_KEEPALIVE
     *
     * If SO_KEEPALIVE is turned on, the client occasionally sends a data packet over an idle connection (most commonly
     * once every two hours), just to make sure the server hasn't crashed. If the server fails to respond to this packet,
     * the client keeps trying for a little more than 11 minutes until it receives a response. If it doesn't receive a
     * response within 12 minutes, the client closes the socket. Without SO_KEEPALIVE, an inactive client could live more
     * or less forever without noticing that the server had crashed. These methods turn SO_KEEPALIVE on and off and determine
     * its current state:
     *
     * public void setKeepAlive(boolean on) throws SocketException
     * public boolean getKeepAlive() throws SocketException
     *
     * The default for SO_KEEPALIVE is false. This code fragment turns SO_KEEPALIVE off, if it's turned on:
     * if (s.getKeepAlive()) s.setKeepAlive(false);
     */

    /**
     * OOBINLINE
     *
     * TCP includes a feature that sends a single byte of "urgent" data out of band. This data is sent immediately. Furthermore,
     * the receiver is notified when the urgent data is received and may elect to process the urgent data before it processes
     * any other data that has already been received. Java supports both sending and receiving such urgent data. The sending
     * method is named, obviously enough, sendUrgentData():
     *
     * public void sendUrgentData(int data) throws IOException
     *
     * This method sends the lowest-order byte of its argument almost immediately. If necessary, any currently cached data
     * is flushed first.
     *
     * How the receiving end responds to urgent data is a little confused, and varies from one platform and API to the next.
     * Some systems receive the urgent data separately from the regular data. However, the more common, more modern approach
     * is to place the urgent data in the regular received data queue in its proper order, tell the application that urgent
     * data is available, and let it hunt through the queue to find it.
     * By default, Java ignores urgent data received from a socket. However, if you want to receive urgent data inline with
     * regular data, you need to set the OOBINLINE option to true using these methods:
     *
     * public void setOOBInline(boolean on) throws SocketException
     * public boolean getOOBInline() throws SocketException
     *
     * The default for OOBINLINE is false. This code fragment turns OOBINLINE on, if it's turned off:
     *
     * if (!s.getOOBInline()) s.setOOBInline(true);
     *
     * Once OOBINLINE is turned on, any urgent data that arrives will be placed on the socket's input stream to be read
     * in the usual way. Java does not distinguish it from nonurgent data. That makes it less than ideally useful, but
     * if you have a particular byte (e.g., a Ctrl-C) that has special meaning to your program and never shows up in the
     * regular data stream, then this would enable you to send it more quickly.
     */

    /**
     * SO_REUSEADDR
     *
     * When a socket is closed, it may not immediately release the local port, especially if a connection was open when
     * the socket was closed. It can sometimes wait for a small amount of time to make sure it receives any lingering
     * packets that were addressed to the port that were still crossing the network when the socket was closed. The system
     * won't do anything with any of the late packets it receives. It just wants to make sure they don't accidentally get
     * fed into a new process that has bound to the same port.
     *
     * This isn't a big problem on a random port, but it can be an issue if the socket has bound to a well-known port because
     * it prevents any other socket from using that port in the meantime. If the SO_REUSEADDR is turned on (it's turned off by default),
     * another socket is allowed to bind to the port even while data may be outstanding for the previous socket.
     *
     * In Java this option is controlled by these two methods:
     *
     * public void setReuseAddress(boolean on) throws SocketException
     * public boolean getReuseAddress() throws SocketException
     *
     * For this to work, setReuseAddress() must be called before the new socket binds to the port. This means the socket
     * must be created in an unconnected state using the noargs constructor; then setReuseAddress(true) is called, and
     * the socket is connected using the connect() method. Both the socket that was previously connected and the new socket
     * reusing the old address must set SO_REUSEADDR to true for it to take effect.
     */

    /**
     * IP_TOS Class of Service
     *
     * Different types of Internet service have different performance needs. For instance, video chat needs relatively high
     * bandwidth and low latency for good performance, whereas email can be passed over low-bandwidth connections and even
     * held up for several hours without major harm. VOIP needs less bandwidth than video but minimum jitter. It might be
     * wise to price the different classes of service differentially so that people won't ask for the highest class of
     * service automatically. After all, if sending an overnight letter cost the same as sending a package via media mail,
     * we'd all just use FedEx overnight, which would quickly become congested and overwhelmed. The Internet is no different.
     *
     * The class of service is stored in an eight-bit field called IP_TOS in the IP header. Java lets you inspect and
     * set the value a socket places in this field using these two methods:
     *
     * public int getTrafficClass() throws SocketException
     * public void setTrafficClass(int trafficClass) throws SocketException
     *
     * The traffic class is given as an int between 0 and 255. Because this value is copied to an eight-bit field in the
     * TCP header, only the low order byte of this int is used; and values outside this range cause IllegalArgumentExceptions.
     *
     * In 21st-century TCP stacks, the high-order six bits of this byte contain a Differentiated Services Code Point (DSCP)
     * value and the low-order two bits contain an Explicit Congestion Notification (ECN) value. The DSCP thus has room
     * for up to 26 different traffic classes. However, it's up to individual networks and routers to specify exactly what
     * the 64 different possible DSCP values mean. The four values shown in Table 8-1 are fairly common.
     *
     * Common DSCP values and interpretations

     PHB (Per Hop Behavior)	        Binary value	        Purpose
     Default	                    00000	                Best-effort traffic.
     Expedited Forwarding (EF)	    101110	                Low-loss, low-delay, low-jitter traffic. Often limited to 30% or less of network capacity.
     Assured Forwarding (AF)	    multiple	            Assured delivery up to a specified rate.
     Class Selector	                xxx000	                Backward compatibility with the IPv4 TOS header, as stored in the first three bits.

     For example, the Expedited Forwarding PHB is a good choice for for VOIP. EF traffic is often given strict priority
     queuing above all other traffic classes. This code fragment sets a socket to use Expedited Forwarding by setting the traffic class to 10111000:
     */
    public static void main(String[] args) {
        try {
            Socket s = new Socket("www.yahoo.com", 80);
            s.setTrafficClass(0xB8); // 10111000 in binary
        }catch (IOException e){}
    }
    /**
     * Remember the low-order two bits of this number are Explicit Congestion Notification, and should be set to zero.
     *
     * Assured Forwarding is actually 12 different DSCP values divided into four classes as shown in Table 8-2. The purpose
     * here is to allow a sender to express relative preferences for which packets to drop when the network is congested.
     * Within a class, packets with lower priority are dropped before packets with a higher priority. Between classes,
     * packest from a higher-priority class are given preference, though lower-priority classes are not starved completely.

     Assured forwarding priority classes

                        Class 1 (lowest priority)	    Class 2	        Class 3	        Class 4 (highest priority)
     Low Drop Rate	    AF11 (001010)	                AF21 (010010)	AF31 (011010)	AF41 (100010)
     Medium Drop Rate	AF12 (001100)	AF22 (010100)	AF32 (011100)	AF42 (100100)
     High Drop Rate	AF13 (001110)	AF23 (010110)	AF33 (011110)	AF43 (100110)

     */
}
