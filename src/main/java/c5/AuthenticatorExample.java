package c5;

public class AuthenticatorExample {
    /**
     * The java.net package includes an Authenticator class that you can use to provide a username and password for sites
     * that protect themselves using HTTP authentication:
     *
     * public abstract class Authenticator extends Object
     *
     * Since Authenticator is an abstract class, you must subclass it. Different subclasses may retrieve the information
     * in different ways. For example, a character mode program might just ask the user to type the username and password
     * on System.in. A GUI program would likely put up a dialog box like the one shown in Figure 5-2.
     * An automated robot might read the username out of an encrypted file.
     *
     * To make the URL class use the subclass, install it as the default authenticator by passing it to the static
     * Authenticator.setDefault() method:
     *
     * public static void setDefault(Authenticator a)
     *
     * Authenticator.setDefault(new DialogAuthenticator());
     *
     * You only need to do this once. From this point forward, when the URL class needs a username and password,
     * it will ask the DialogAuthenticator using the static Authenticator.requestPasswordAuthentication() method:
     *
     * public static PasswordAuthentication requestPasswordAuthentication(InetAddress address, int port, String protocol, String prompt, String scheme) throws SecurityException
     *
     * The Authenticator subclass must override the getPasswordAuthentication() method. Inside this method, you collect
     * the username and password from the user or some other source and return it as an instance of the java.net.PasswordAuthentication class
     *
     * protected PasswordAuthentication getPasswordAuthentication()
     *
     * You can get more details about the request by invoking any of these methods inherited from the Authenticator superclass:
     *
     * protected final InetAddress getRequestingSite()
     * protected final int         getRequestingPort()
     * protected final String      getRequestingProtocol()
     * protected final String      getRequestingPrompt()
     * protected final String      getRequestingScheme()
     * protected final String      getRequestingHost()
     * protected final String      getRequestingURL()
     * protected Authenticator.RequestorType getRequestorType()
     *
     * The getRequestingURL() method returns the complete URL for which authentication has been requested—an important detail
     * if a site uses different names and passwords for different files. The getRequestorType() method returns one of the
     * two named constants (i.e., Authenticator.RequestorType.PROXY or Authenticator.RequestorType.SERVER) to indicate
     * whether the server or the proxy server is requesting the authentication.
     *
     * The PasswordAuthentication Class
     *
     * PasswordAuthentication is a very simple final class that supports two read-only properties: username and password.
     * The username is a String. The password is a char array so that the password can be erased when it's no longer needed.
     * A String would have to wait to be garbage collected before it could be erased, and even then it might still exist
     * somewhere in memory on the local system, possibly even on disk if the block of memory that contained it had been
     * swapped out to virtual memory at one point. Both username and password are set in the constructor:
     *
     * public PasswordAuthentication(String userName, char[] password)
     *
     * Each is accessed via a getter method:
     * public String getUserName()
     * public char[] getPassword()
     *
     * The JPasswordField Class
     *
     * One useful tool for asking users for their passwords in a more or less secure fashion is the JPasswordField component from Swing:
     *
     * public class JPasswordField extends JTextField
     *
     * This lightweight component behaves almost exactly like a text field. However, anything the user types into it is
     * echoed as an asterisk. This way, the password is safe from anyone looking over the user's shoulder at what's being
     * typed on the screen.
     *
     * JPasswordField also stores the passwords as a char array so that when you're done with the password you can
     * overwrite it with zeros. It provides the getPassword() method to return this:
     *
     * public char[] getPassword()
     *
     * Otherwise, you mostly use the methods it inherits from the JTextField superclass. Example 5-11 demonstrates a
     * Swing-based Authenticator subclass that brings up a dialog to ask the user for his username and password.
     * Most of this code handles the GUI. A JPasswordField collects the password and a simple JTextField retrieves the username.
     * Flip back to Figure 5-2 to see the rather simple dialog box this produces
     */




}
