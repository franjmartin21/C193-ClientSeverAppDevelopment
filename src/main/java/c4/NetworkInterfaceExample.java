package c4;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class NetworkInterfaceExample {
    public static void main(String[] args) {
        /**
         * public static NetworkInterface getByName(String name) throws SocketException
         *
         * The getByName() method returns a NetworkInterface object representing the network interface with the particular name.
         * If there's no interface with that name, it returns null. If the underlying network stack encounters a problem while
         * locating the relevant network interface, a SocketException is thrown, but this isn't too likely to happen.
         */
        try{
            NetworkInterface ni = NetworkInterface.getByName("wlp3s0");
            if(ni == null){
                System.err.println("No such interface: eth0");
            }
        } catch (SocketException ex){
            System.err.println("Could not listen sockets");
        }

        /**
         * public static NetworkInterface getByName(String name) throws SocketException
         *
         * The getByInetAddress() method returns a NetworkInterface object representing the network interface bound to the
         * specified IP address. If no network interface is bound to that IP address on the local host, it returns null.
         * If anything goes wrong, it throws a SocketException. For example, this code fragment finds the network interface
         * for the local loopback address:
         */
        try
        {
            InetAddress local = InetAddress.getByName("127.0.0.1");
            NetworkInterface ni = NetworkInterface.getByInetAddress(local);
            if (ni == null)
            {
                System.err.println("That's weird. No local loopback address.");
            }
        }
        catch (SocketException ex)
        {
            System.err.println("Could not list network interfaces." );
        }
        catch (UnknownHostException ex)
        {
            System.err.println("That's weird. Could not lookup 127.0.0.1.");
        }

        /**
         * public static Enumeration getNetworkInterfaces() throws SocketException
         *
         * The getNetworkInterfaces() method returns a java.util.Enumeration listing all the network interfaces on the local
         * host. Example 4-8 is a simple program to list all network interfaces on the local host:
         */
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                System.out.println(ni);
            }
        }
        catch (SocketException ex)
        {
            System.err.println("Could not list network interfaces." );
        }

        /**
         * GETTER METHODS
         */
        /**
         * public Enumeration getInetAddresses()
         * A single network interface may be bound to more than one IP address. This situation isn't common these days, but
         * it does happen. The getInetAddresses() method returns a java.util.Enumeration containing an InetAddress object
         * for each IP address the interface is bound to. For example, this code fragment lists all the IP addresses for
         * the eth0 interface:
         *
         * public String getName()
         * The getName() method returns the name of a particular NetworkInterface object, such as eth0 or lo.
         *
         * public String getDisplayName()
         *
         * The getDisplayName() method allegedly returns a more human-friendly name for the particular NetworkInterface-something like
         * "Ethernet Card 0." However, in my tests on Unix, it always returned the same string as getName(). On Windows, you may
         * see slightly friendlier names such as "Local Area Connection" or "Local Area Connection 2."

         */
        try {
            NetworkInterface wifiCard = NetworkInterface.getByName("wlp3s0");
            Enumeration addresses = wifiCard.getInetAddresses();
            while (addresses.hasMoreElements()) {
                System.out.println(addresses.nextElement());
            }
            System.out.println(wifiCard.getName());
            System.out.println(wifiCard.getDisplayName());
        }
        catch (SocketException ex)
        {
            System.err.println("Could not list network interfaces." );
        }



    }
}
