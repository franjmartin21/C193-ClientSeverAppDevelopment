package c4;

import java.net.*;

public class OReillyByName
{
    public static void main (String[] args)
    {
        try{
            /**
             * InetAddress has static factory methods that connect to a DNS server to resolve a hostname. The most common is
             * InetAddress.getByName()
             *
             * This method does not merely set a private String field in the InetAddress class. It actually makes a connection
             * to the local DNS server to look up the name and the numeric address. (If you've looked up this host previously,
             * the information may be cached locally, in which case a network connection is not required.) If the DNS server can't
             * find the address, this method throws an UnknownHostException, a subclass of IOException.
             */
            InetAddress address = InetAddress.getByName("www.oreilly.com");
            System.out.println(address);
            /**
             * You can also do a reverse lookup by IP address. For example, if you want the hostname for the address 208.201.239.100,
             * pass the dotted quad address to InetAddress.getByName():
             */
            address = InetAddress.getByName("23.197.72.77");
            System.out.println(address.getHostName());
        }
        catch (UnknownHostException ex){
            System.out.println("Could not find www.oreilly.com");
        }

        /**
         * www.oreilly.com actually has two addresses. Which one getHostName() returns is indeterminate. If, for some reason,
         * you need all the addresses of a host, call getAllByName() instead, which returns an array:
         */
        try{
            //InetAddress[] addresses = InetAddress.getAllByName("www.oreilly.com");
            InetAddress[] addresses = InetAddress.getAllByName("www.google.com");
            for (InetAddress address1 : addresses)
            {
                System.out.println(address1);
            }
        }
        catch (UnknownHostException ex) {
            System.out.println("Could not find www.oreilly.com");
        }

        /**
         * Finally, the getLocalHost() method returns an InetAddress object for the host on which your code is running
         */
        try
        {
            System.out.println("** InetAddress.getLocalHost()");
            InetAddress address = InetAddress.getLocalHost();
            System.out.println(address);
        }
        catch (UnknownHostException ex)
        {
            System.out.println("Could not find this computer's address.");
        }

        /**
         * If you know a numeric address, you can create an InetAddress object from that address without talking to DNS
         * using InetAddress.getByAddress(). This method can create addresses for hosts that do not exist or cannot be resolved:
         *
         * public static InetAddress getByAddress(byte[] addr) throws UnknownHostException
         * public static InetAddress getByAddress(String hostname, byte[] addr) throws UnknownHostException
         */
        try {
            System.out.println("** InetAddress create ip as array of bits");
            byte[] address = {107, 23, (byte) 216, (byte) 196};
            InetAddress lessWrong = InetAddress.getByAddress(address);
            InetAddress lessWrongWithname = InetAddress.getByAddress("lesswrong.com", address);
            System.out.println(lessWrongWithname);
        } catch (UnknownHostException ex){}
        /**
         * Unlike the other factory methods, these two methods make no guarantees that such a host exists or that the hostname
         * is correctly mapped to the IP address. They throw an UnknownHostException only if a byte array of an illegal size
         * (neither 4 nor 16 bytes long) is passed as the address argument
         */

        /**
         * Caching
         *
         * Because DNS lookups can be relatively expensive (on the order of several seconds for a request that has to go
         * through several intermediate servers, or one that's trying to resolve an unreachable host) the InetAddress class
         * caches the results of lookups
         *
         * Negative results (host not found errors) are slightly more problematic. It's not uncommon for an initial attempt
         * to resolve a host to fail, but the immediately following one to succeed. The first attempt timed out while the
         * information was still in transit from the remote DNS server. Then the address arrived at the local server and was
         * immediately available for the next request. For this reason, Java only caches unsuccessful DNS queries for 10 seconds.
         *
         * These times can be controlled by the system properties:
         * networkaddress.cache.ttl: specifies the number of seconds a successful DNS lookup will remain in Java's cache
         * networkaddress.cache.negative.ttl: is the number of seconds an unsuccessful lookup will be cached.
         */

        /**
         * Lookups by IP address
         *
         * When you call getByName() with an IP address string as an argument, it creates an InetAddress object for the requested
         * IP address without checking with DNS. This means it's possible to create InetAddress objects for hosts that don't really
         * exist and that you can't connect to. The hostname of an InetAddress object created from a string containing an IP address
         * is initially set to that string.
         * A DNS lookup for the actual hostname is only performed when the hostname is requested, either explicitly via a getHostName()
         * If, at the time the hostname is requested and a DNS lookup is finally performed, the host with the specified IP address
         * can't be found, the hostname remains the original dotted quad string. However, no UnknownHostException is thrown.
         *
         * Hostnames are much more stable than IP addresses. Some services have lived at the same hostname for years, but have
         * switched IP addresses several times. If you have a choice between using a hostname such as www.oreilly.com or an IP
         * address such as 208.201.239.37, always choose the hostname. Use an IP address only when a hostname is not available.
         */

        /**
         * Security issues
         *
         * Creating a new InetAddress object from a hostname is considered a potentially insecure operation because it requires
         * a DNS lookup
         * Untrusted code is not allowed to perform arbitrary DNS lookups for third-party hosts because of the prohibition against
         * making network connections to hosts other than the codebase. Arbitrary DNS lookups would open a covert channel by which
         * a program could talk to third-party hosts. For instance, suppose an applet downloaded from www.bigisp.com wants to send
         * the message "macfaq.dialup.cloud9.net is vulnerable" to crackersinc.com. All it has to do is request DNS information for
         * macfaq.dialup.cloud9.net.is.vulnerable.crackersinc.com. To resolve that hostname, the applet would contact the local
         * DNS server. The local DNS server would contact the DNS server at crackersinc.com. Even though these hosts don't exist,
         * the cracker can inspect the DNS error log for crackersinc.com to retrieve the message. This scheme could be considerably
         * more sophisticated with compression, error correction, encryption, custom DNS servers that email the messages to a fourth
         * site, and more, but this version is good enough for a proof of concept. Arbitrary DNS lookups are prohibited because
         * arbitrary DNS lookups leak information.
         *
         * Like all security checks, prohibitions against DNS resolutions can be relaxed for trusted code. The specific SecurityManager
         * method used to test whether a host can be resolved is checkConnect():
         *
         * public void checkConnect(String hostname, int port)
         */

        /**
         * Getter Methods
         *
         * public String getHostName()
         * public String getCanonicalHostName()
         * public byte[] getAddress()
         * public String getHostAddress()
         *
         * getHostName() method returns a String that contains the name of the host with the IP address represented by this InetAddress object.
         * If the machine in question doesn't have a hostname or if the security manager prevents the name from being determined,
         * a dotted quad format of the numeric IP address is returned. For example:
         */
        try{
            System.out.println("** Testing getHostName");
            InetAddress machine = InetAddress.getLocalHost();
            String localhost = machine.getHostName();
            System.out.println(localhost);
        }catch (UnknownHostException ex){}

        /**
         * getHostName() will only call DNS if it doesn't think it already knows the hostname. getCanonicalHostName() calls
         * DNS if it can, and may replace the existing cached hostname
         */
        try {
            System.out.println("** Testing getCanonicalHostName");
            InetAddress machine = InetAddress.getLocalHost();
            String localhost = machine.getCanonicalHostName();
            System.out.println(localhost);
        } catch(UnknownHostException ex){}
        /**
         * The getCanonicalHostName() method is particularly useful when you're starting with a dotted quad IP address rather than
         * the hostname. Example 4-3 converts the dotted quad address 208.201.239.37 into a hostname by using InetAddress.getByName()
         * and then applying getCanonicalHostName() on the resulting object.
         */
        try {
            System.out.println("** Testing getCanonicalHostName starting with IP");
            InetAddress ia = InetAddress.getByName("23.197.72.77");
            System.out.println(ia.getCanonicalHostName());
        } catch(UnknownHostException ex){}

        /**
         * The getHostAddress() method returns a string containing the dotted quad format of the IP address.
         * Example 4-4 uses this method to print the IP address of the local machine in the customary format.
         */
        try {
            System.out.println("** Testing getHostAddress");
            InetAddress me = InetAddress.getLocalHost();
            String dottedQuad = me.getHostAddress();
            System.out.println("My address is " + dottedQuad);
        }
        catch (UnknownHostException ex)
        {
            System.out.println("I'm sorry. I don't know my own address.");
        }

        /**
         * If you want to know the IP address of a machine (and you rarely do), then use the getAddress() method,
         * which returns an IP address as an array of bytes in network byte order. The most significant byte (i.e.,
         * the first byte in the address's dotted quad form) is the first byte in the array, or element zero. To be
         * ready for IPv6 addresses, try not to assume anything about the length of this array. If you need to know
         * the length of the array, use the array's length field:
         */
        try{
            System.out.println("** Testing getAddress");
            InetAddress me = InetAddress.getLocalHost();
            byte[] address = me.getAddress();
            for(byte a: address){
                System.out.println(a);
            }
        } catch(UnknownHostException ex){}


        try{
            System.out.println("** Testing getAddress signed");
            InetAddress other = InetAddress.getByName("23.197.72.77");
            byte[] address = other.getAddress();
            for(byte signedByte: address){
                System.out.println(signedByte);
            }
            System.out.println("** Testing getAddress convert to unsigned");
            for(byte signedByte: address){
                int unsignedByte = signedByte < 0 ? signedByte + 256 : signedByte;
                System.out.println(unsignedByte);
            }
        } catch(UnknownHostException ex){}


    }
}
