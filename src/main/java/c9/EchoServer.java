package c9;
import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.util.*;
import java.io.IOException;

/**
 * In the examples so far, the server has only written to client sockets. It hasn't read from them. Most protocols, however,
 * require the server to do both. This isn't hard. You'll accept a connection as before, but this time ask for both an
 * InputStream and an OutputStream. Read from the client using the InputStream and write to it using the OutputStream.
 * The main trick is understanding the protocol: when to write and when to read.
 *
 * The echo protocol, defined in RFC 862, is one of the simplest interactive TCP services. The client opens a socket to
 * port 7 on the echo server and sends data. The server sends the data back. This continues until the client closes the
 * connection. The echo protocol is useful for testing the network to make sure that data is not mangled by a misbehaving
 * router or firewall. You can test echo with Telnet like this:
 */
public class EchoServer
{
    public static int DEFAULT_PORT = 7;

    /**
     * This sample is line oriented because that's how Telnet works. It reads a line of input from the console, sends it to the
     * server, then waits to read a line of output it gets back. However, the echo protocol doesn't require this. It echoes
     * each byte as it receives it. It doesn't really care whether those bytes represent characters in some encoding or are
     * divided into lines. Unlike many protocols, echo does not specify lockstep behavior where the client sends a request
     * but then waits for the full server response before sending any more data.
     *
     * Unlike daytime and time, in the echo protocol the client is responsible for closing the connection. This makes it
     * even more important to support asynchronous operation with many threads because a single client can remain connected
     * indefinitely. In Example 9-5, the server spawns up to 500 threads.
     * @param args
     */
    public static void main(String[] args)
    {
        int port;
        try
        {
            port = Integer.parseInt(args[0]);
        }
        catch (RuntimeException ex)
        {
            port = DEFAULT_PORT;
        }
        System.out.println("Listening for connections on port " + port);
        ServerSocketChannel serverChannel;
        Selector selector;
        try
        {
            serverChannel = ServerSocketChannel.open();
            ServerSocket ss = serverChannel.socket();
            InetSocketAddress address = new InetSocketAddress(port);
            ss.bind(address);
            serverChannel.configureBlocking(false);
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return;
        }
        while (true)
        {
            try
            {
                selector.select();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
                break;
            }
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while (iterator.hasNext())
            {
                SelectionKey key = iterator.next();
                iterator.remove();
                try
                {
                    if (key.isAcceptable())
                    {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        System.out.println("Accepted connection from " + client);
                        client.configureBlocking(false);
                        SelectionKey clientKey = client.register(
                                selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ);
                        ByteBuffer buffer = ByteBuffer.allocate(100);
                        clientKey.attach(buffer);
                    }
                    if (key.isReadable())
                    {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer output = (ByteBuffer) key.attachment();
                        client.read(output);
                    }
                    if (key.isWritable())
                    {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer output = (ByteBuffer) key.attachment();
                        output.flip();
                        client.write(output);
                        output.compact();
                    }
                }
                catch (IOException ex)
                {
                    key.cancel();
                    try
                    {
                        key.channel().close();
                    }
                    catch (IOException cex) {}
                }
            }
        }
    }
}