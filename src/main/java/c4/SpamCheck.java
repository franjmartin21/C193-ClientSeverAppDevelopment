package c4;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SpamCheck {

    /**
     * To find out if a certain IP address is a known spammer, reverse the bytes of the address, add the domain of the blackhole
     * service, and look it up. If the address is found, it's a spammer. If it isn't, it's not. For instance, if you want to ask
     * sbl.spamhaus.org if 207.87.34.17 is a spammer, you would look up the hostname 17.34.87.207.sbl.spamhaus.org. (Note that
     * despite the numeric component, this is a hostname ASCII string, not a dotted quad IP address.)
     * @param args
     */
    public static final String BLACKHOLE = "sbl.spamhaus.org";

    public static void main(String[] args) throws UnknownHostException
    {
        for (String arg: args)
        {
            if (isSpammer(arg))
            {
                System.out.println(arg + " is a known spammer.");
            }
            else
            {
                System.out.println(arg + " appears legitimate.");
            }
        }
    }

    private static boolean isSpammer(String arg)
    {
        try
        {
            InetAddress address = InetAddress.getByName(arg);
            byte[] quad = address.getAddress();
            String query = BLACKHOLE;
            for (byte octet : quad)
            {
                int unsignedByte = octet < 0 ? octet + 256 : octet;
                query = unsignedByte + "." + query;
            }
            InetAddress.getByName(query);
            return true;
        }
        catch (UnknownHostException e)
        {
            return false;
        }
    }

}

