package c4;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class AddressTests {
    /**
     * Determining whether an IP address is v4 or v6
     */
    public static int getVersion(InetAddress ia)
    {
        byte[] address = ia.getAddress();
        if (address.length == 4) return 4;
        else if (address.length == 16) return 6;
        else return -1;
    }

    public static void main(String[] args) throws UnknownHostException {
        try{
            System.out.println(getVersion(InetAddress.getByName("www.google.com")));
        } catch (UnknownHostException e){}
    }

    /**
     * Address Types
     *
     * The isAnyLocalAddress() method returns true if the address is a wildcard address, false otherwise. A wildcard address
     * matches any address of the local system. This is important if the system has multiple network interfaces, as might be
     * the case on a system with multiple Ethernet cards or an Ethernet card and an 802.11 WiFi interface. In IPv4, the wildcard
     * address is 0.0.0.0. In IPv6, this address is 0:0:0:0:0:0:0:0 (a.k.a. ::).
     * public boolean isAnyLocalAddress()
     *
     * The isLoopbackAddress() method returns true if the address is the loopback address, false otherwise. The loopback address
     * connects to the same computer directly in the IP layer without using any physical hardware. Thus, connecting to the
     * loopback address enables tests to bypass potentially buggy or nonexistent Ethernet, PPP, and other drivers, helping
     * to isolate problems. Connecting to the loopback address is not the same as connecting to the system's normal IP address
     * from the same system. In IPv4, this address is 127.0.0.1. In IPv6, this address is 0:0:0:0:0:0:0:1 (a.k.a. ::1).
     * public boolean isLoopbackAddress()
     *
     * The isLinkLocalAddress() method returns true if the address is an IPv6 link-local address, false otherwise. This is
     * an address used to help IPv6 networks self-configure, much like DHCP on IPv4 networks but without necessarily using a
     * server. Routers do not forward packets addressed to a link-local address beyond the local subnet. All link-local addresses
     * begin with the eight bytes FE80:0000:0000:0000. The next eight bytes are filled with a local address, often copied from
     * the Ethernet MAC address assigned by the Ethernet card manufacturer.
     * public boolean isLinkLocalAddress()
     *
     * The isSiteLocalAddress() method returns true if the address is an IPv6 site-local address, false otherwise. Site-local
     * addresses are similar to link-local addresses except that they may be forwarded by routers within a site or campus but
     * should not be forwarded beyond that site. Site-local addresses begin with the eight bytes FEC0:0000:0000:0000.
     * The next eight bytes are filled with a local address, often copied from the Ethernet MAC address assigned by the Ethernet
     * card manufacturer.
     * public boolean isSiteLocalAddress()
     *
     * The isMulticastAddress() method returns true if the address is a multicast address, false otherwise. Multicasting
     * broadcasts content to all subscribed computers rather than to one particular computer. In IPv4, multicast addresses
     * all fall in the range 224.0.0.0 to 239.255.255.255. In IPv6, they all begin with byte FF. Multicasting will be discussed
     * in Chapter 13
     * public boolean isMulticastAddress()
     *
     * The isMCGlobal() method returns true if the address is a global multicast address, false otherwise. A global multicast
     * address may have subscribers around the world. All multicast addresses begin with FF. In IPv6, global multicast addresses
     * begin with FF0E or FF1E depending on whether the multicast address is a well known permanently assigned address or a
     * transient address. In IPv4, all multicast addresses have global scope, at least as far as this method is concerned.
     * As you'll see in Chapter 13, IPv4 uses time-to-live (TTL) values to control scope rather than addressing.
     * public boolean isMCGlobal()
     *
     * The isMCOrgLocal() method returns true if the address is an organization-wide multicast address, false otherwise.
     * An organization-wide multicast address may have subscribers within all the sites of a company or organization,
     * but not outside that organization. Organization multicast addresses begin with FF08 or FF18, depending on whether
     * the multicast address is a well known permanently assigned address or a transient address.
     * public boolean isMCOrgLocal()
     *
     * The isMCSiteLocal() method returns true if the address is a site-wide multicast address, false otherwise. Packets
     * addressed to a site-wide address will only be transmitted within their local site. Site-wide multicast addresses
     * begin with FF05 or FF15, depending on whether the multicast address is a well known permanently assigned address
     * or a transient address.
     * public boolean isMCSiteLocal()
     *
     * The isMCLinkLocal() method returns true if the address is a subnet-wide multicast address, false otherwise.
     * Packets addressed to a link-local address will only be transmitted within their own subnet. Link-local multicast
     * addresses begin with FF02 or FF12, depending on whether the multicast address is a well known permanently assigned
     * address or a transient address.
     * public boolean isMCLinkLocal()
     *
     * The isMCNodeLocal() method returns true if the address is an interface-local multicast address, false otherwise.
     * Packets addressed to an interface-local address are not sent beyond the network interface from which they originate,
     * not even to a different network interface on the same node. This is primarily useful for network debugging and testing.
     * Interface-local multicast addresses begin with the two bytes FF01 or FF11, depending on whether the multicast address
     * is a well known permanently assigned address or a transient address.
     * public boolean isMCNodeLocal()
     */

    /**
     * Testing Reachability
     *
     * The InetAddress class has two isReachable() methods that test whether a particular node is reachable from the current
     * host (i.e., whether a network connection can be made). Connections can be blocked for many reasons, including firewalls,
     * proxy servers, misbehaving routers, and broken cables, or simply because the remote host is not turned on when you try
     * to connect.
     *
     * public boolean isReachable(int timeout) throws IOException
     * public boolean isReachable(NetworkInterface interface, int ttl, int timeout) throws IOException
     */
}
