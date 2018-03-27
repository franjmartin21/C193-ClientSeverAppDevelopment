package c7;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpURLConnectionExample {

    public static void main(String[] args) {
        /**
         * The java.net.HttpURLConnection class is an abstract subclass of URLConnection; it provides some additional
         * methods that are helpful when working specifically with http URLs. In particular, it contains methods to get
         * and set the request method, decide whether to follow redirects, get the response code and message, and figure
         * out whether a proxy server is being used. It also includes several dozen mnemonic constants matching the various
         * HTTP response codes. Finally, it overrides the getPermission() method from the URLConnection superclass,
         * although it doesn't change the semantics of this method at all.
         */

        /**
         * Because this class is abstract and its only constructor is protected, you can't directly create instances of
         * HttpURLConnection. However, if you construct a URL object using an http URL and invoke its openConnection()
         * method, the URLConnection object returned will be an instance of HttpURLConnection. Cast that URLConnection
         * to HttpURLConnection like this:
         */
        try {
            URL u = new URL("http://lesswrong.com/");
            URLConnection uc = u.openConnection();
            HttpURLConnection http = (HttpURLConnection) uc;
            System.out.println(http.getResponseCode());
        } catch (IOException e){
            e.printStackTrace();
        }

        /**
         * The Request Method
         *
         * When a web client contacts a web server, the first thing it sends is a request line. Typically, this line begins
         * with GET and is followed by the path of the resource that the client wants to retrieve and the version of the
         * HTTP protocol that the client understands. For example:
         *
         * GET /catalog/jfcnut/index.html HTTP/1.0
         */

        /**
         * By default, HttpURLConnection uses the GET method. However, you can change this with the setRequestMethod() method:
         *
         * public void setRequestMethod(String method) throws ProtocolException
         *
         * The method argument should be one of these seven case-sensitive strings:
         * GET
         * POST
         * HEAD
         * PUT
         * DELETE
         * OPTIONS
         * TRACE
         *
         * If it's some other method, then a java.net.ProtocolException, a subclass of IOException, is thrown.
         *
         * it's generally not enough to simply set the request method. Depending on what you're trying to do, you may
         * need to adjust the HTTP header and provide a message body as well. For instance, POSTing a form requires you
         * to provide a Content-length header.
         */

        /**
         * HEAD
         *
         * The HEAD function is possibly the simplest of all the request methods. It behaves much like GET. However, it
         * tells the server only to return the HTTP header, not to actually send the file. The most common use of this
         * method is to check whether a file has been modified since the last time it was cached. Example 7-15 is a simple
         * program that uses the HEAD request method and prints the last time a file on a server was modified.
         *
         * LastModified a usage of head HEAD
         */

        /**
         * DELETE
         *
         * The DELETE method removes a file at a specified URL from a web server. Because this request is an obvious security
         * risk, not all servers will be configured to support it, and those that are will generally demand some sort of
         * authentication. A typical DELETE request looks like this:
         *
         * DELETE /javafaq/2008march.html HTTP/1.1
         * Host: www.ibiblio.org
         * Accept: text/html, image/gif, image/jpeg, *; q=.2, *\/*; q=.2
         * Connection: close
         *
         * The server is free to refuse this request or ask for authorization. For example:

         HTTP/1.1 405 Method Not Allowed
         Date: Sat, 04 May 2013 13:22:12 GMT
         Server: Apache
         Allow: GET,HEAD,POST,OPTIONS,TRACE
         Content-Length: 334
         Connection: close
         Content-Type: text/html; charset=iso-8859-1

         <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
         <html><head>
         <title>405 Method Not Allowed</title>
         </head><body>
         <h1>Method Not Allowed</h1>
         <p>The requested method DELETE is not allowed for the URL
         /javafaq/2008march.html.</p>
         <hr>
         <address>Apache Server at www.ibiblio.org Port 80</address>
         </body></html>
         */

        /**
         * PUT
         *
         * Many HTML editors and other programs that want to store files on a web server use the PUT method. It allows
         * clients to place documents in the abstract hierarchy of the site without necessarily knowing how the site maps
         * to the actual local filesystem. This contrasts with FTP, where the user has to know the actual directory
         * structure as opposed to the server's virtual directory structure. Here's a how an editor might PUT a file on a
         * web server:

         PUT /blog/wp-app.php/service/pomdoros.html HTTP/1.1
         Host: www.elharo.com
         Authorization: Basic ZGFmZnk6c2VjZXJldA==
         Content-Type: application/atom+xml;type=entry
         Content-Length: 329
         If-Match: "e180ee84f0671b1"

         <?xml version="1.0" ?>
         <entry xmlns="http://www.w3.org/2005/Atom">
         <title>The Power of Pomodoros</title>
         <id>urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6a</id>
         <updated>2013-02-23T19:22:11Z</updated>
         <author><name>Elliotte Harold</name></author>
         <content>Until recently, I hadn't paid much attention to...</content>
         </entry>
         */

        /**
         * OPTIONS
         *
         * The OPTIONS request method asks what options are supported for a particular URL. If the request URL is an
         * asterisk (*), the request applies to the server as a whole rather than to one particular URL on the server.
         * For example:
         *
         *
        OPTIONS /xml/ HTTP/1.1
        Host: www.ibiblio.org
        Accept: text/html, image/gif, image/jpeg, *; q=.2, *\/*; q=.2
        Connection: close

         * The server responds to an OPTIONS request by sending an HTTP header with a list of the commands allowed on that
         * URL. For example, when the previous command was sent, here's what Apache responded with:

        HTTP/1.1 200 OK
        Date: Sat, 04 May 2013 13:52:53 GMT
        Server: Apache
        Allow: GET,HEAD,POST,OPTIONS,TRACE
        Content-Style-Type: text/css
        Content-Length: 0
        Connection: close
        Content-Type: text/html; charset=utf-8
        */

        /**
         * TRACE
         *
         * The TRACE request method sends the HTTP header that the server received from the client. The main reason for
         * this information is to see what any proxy servers between the server and client might be changing. For example,
         * suppose this TRACE request is sent:

        TRACE /xml/ HTTP/1.1
        Hello: Push me
        Host: www.ibiblio.org
        Accept: text/html, image/gif, image/jpeg, *; q=.2, *\/*; q=.2
        Connection: close


         The server should respond like this:

        HTTP/1.1 200 OK
        Date: Sat, 04 May 2013 14:41:40 GMT
        Server: Apache
        Connection: close
        Content-Type: message/http

        TRACE /xml/ HTTP/1.1
        Hello: Push me
        Host: www.ibiblio.org
        Accept: text/html, image/gif, image/jpeg, *; q=.2, *\/*; q=.2
        Connection: close
         */

        /**
         * Disconnecting from the Server
         *
         * HTTP 1.1 supports persistent connections that allow multiple requests and responses to be sent over a single
         * TCP socket. However, when Keep-Alive is used, the server won't immediately close a connection simply because
         * it has sent the last byte of data to the client. The client may, after all, send another request. Servers will
         * time out and close the connection in as little as 5 seconds of inactivity. However, it's still preferred for
         * the client to close the connection as soon as it knows it's done.
         *
         * The HttpURLConnection class transparently supports HTTP Keep-Alive unless you explicitly turn it off. That is,
         * it will reuse sockets if you connect to the same server again before the server has closed the connection.
         * Once you know you're done talking to a particular host, the disconnect() method enables a client to break the
         * connection:
         *
         * public abstract void disconnect()
         *
         * If any streams are still open on this connection, disconnect() closes them. However, the reverse is not true.
         * Closing a stream on a persistent connection does not close the socket and disconnect.
         */
    }
}
