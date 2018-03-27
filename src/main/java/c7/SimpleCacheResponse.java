package c7;
import java.io.*;
import java.net.*;
import java.util.*;

public class SimpleCacheResponse extends CacheResponse
{
    private final Map<String, List<String>> headers;
    private final SimpleCacheRequest request;
    private final Date expires;
    private final CacheControl control;
    public SimpleCacheResponse(SimpleCacheRequest request, URLConnection uc, CacheControl control)throws IOException
    {
        this.request = request;
        this.control = control;
        this.expires = new Date(uc.getExpiration());
        this.headers = Collections.unmodifiableMap(uc.getHeaderFields());
    }
    @Override
    public InputStream getBody()
    {
        return new ByteArrayInputStream(request.getData());
    }
    @Override
    public Map<String, List<String>> getHeaders()throws IOException
    {
        return headers;
    }
    public CacheControl getControl()
    {
        return control;
    }
    public boolean isExpired()
    {
        Date now = new Date();
        if (control.getMaxAge().before(now)) return true;
        else if (expires != null && control.getMaxAge() != null)
        {
            return expires.before(now);
        }
        else
        {
            return false;
        }
    }
    /**
     * Finally, you need a simple ResponseCache subclass that stores and retrieves the cached values as requested while
     * paying attention to the original Cache-control header. Example 7-11 demonstrates such a simple class that stores a
     * finite number of responses in memory in one big thread-safe HashMap. This class is suitable for a single-user,
     * private cache (because it ignores the private and public attributes of Cache-control).
     */

}