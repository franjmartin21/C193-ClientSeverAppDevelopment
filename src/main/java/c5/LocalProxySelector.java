package c5;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Each running virtual machine has a single java.net.ProxySelector object it uses to locate the proxy server for different
 * connections. The default ProxySelector merely inspects the various system properties and the URL's protocol to decide how
 * to connect to different hosts. However, you can install your own subclass of ProxySelector in place of the default selector
 * and use it to choose different proxies based on protocol, host, path, time of day, or other criteria.
 */
public class LocalProxySelector extends ProxySelector
{
    private List<URI> failed = new ArrayList<URI>();
    public List<Proxy> select(URI uri)
    {
        List<Proxy> result = new ArrayList<Proxy>();
        if (failed.contains(uri)|| !"http".equalsIgnoreCase(uri.getScheme()))
        {
            result.add(Proxy.NO_PROXY);
        }
        else
        {
            SocketAddress proxyAddress
                    = new InetSocketAddress( "proxy.example.com", 8000);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyAddress);
            result.add(proxy);
        }
        return result;
    }
    public void connectFailed(URI uri, SocketAddress address, IOException ex)
    {
        failed.add(uri);
    }

    /**
     * Each virtual machine has exactly one ProxySelector. To change the ProxySelector, pass the new selector to the
     * static ProxySelector.setDefault() method, like so:
     *
     * ProxySelector selector = new LocalProxySelector():
     * ProxySelector.setDefault(selector);
     *
     * From this point forward, all connections opened by that virtual machine will ask the ProxySelector for the right
     * proxy to use. You normally shouldn't use this in code running in a shared environment. For instance, you wouldn't
     * change the ProxySelector in a servlet because that would change the ProxySelector for all servlets running in the
     * same container.
     */
}