package c7;
import java.util.Date;
import java.util.Locale;

/**
 * How to inspect a Cache-control header
 */
public class CacheControl
{
    private Date maxAge = null;
    private Date sMaxAge = null;
    private boolean mustRevalidate = false;
    private boolean noCache = false;
    private boolean noStore = false;
    private boolean proxyRevalidate = false;
    private boolean publicCache = false;
    private boolean privateCache = false;
    public CacheControl(String s)
    {
        if (s == null || !s.contains(":"))
        {
            return; // default policy
        }
        String value = s.split(":")[1].trim();
        String[] components = value.split(",");
        Date now = new Date();
        for (String component : components)
        {
            try
            {
                component = component.trim().toLowerCase(Locale.US);
                if (component.startsWith("max-age="))
                {
                    int secondsInTheFuture = Integer.parseInt(component.substring(8));
                    maxAge = new Date(now.getTime() + 1000 * secondsInTheFuture);
                }
                else if (component.startsWith("s-maxage="))
                {
                    int secondsInTheFuture = Integer.parseInt(component.substring(8));
                    sMaxAge = new Date(now.getTime() + 1000 * secondsInTheFuture);
                }
                else if (component.equals("must-revalidate"))
                {
                    mustRevalidate = true;
                }
                else if (component.equals("proxy-revalidate"))
                {
                    proxyRevalidate = true;
                }
                else if (component.equals("no-cache"))
                {
                    noCache = true;
                }
                else if (component.equals("public"))
                {
                    publicCache = true;
                }
                else if (component.equals("private"))
                {
                    privateCache = true;
                }
            }
            catch (RuntimeException ex)
            {
                continue;
            }
        }
    }
    public Date getMaxAge()
    {
        return maxAge;
    }
    public Date getSharedMaxAge()
    {
        return sMaxAge;
    }
    public boolean mustRevalidate()
    {
        return mustRevalidate;
    }
    public boolean proxyRevalidate()
    {
        return proxyRevalidate;
    }
    public boolean noStore()
    {
        return noStore;
    }
    public boolean noCache()
    {
        return noCache;
    }
    public boolean publicCache()
    {
        return publicCache;
    }
    public boolean privateCache()
    {
        return privateCache;
    }

    /**
     * A client can take advantage of this information:
     * If a representation of the resource is available in the local cache, and its expiry date has not arrived, just use it.
     * Don't even bother talking to the server.
     * If a representation of the resource is available in the local cache, but the expiry date has arrived, check the
     * server with HEAD to see if the resource has changed before performing a full GET.
     */

}