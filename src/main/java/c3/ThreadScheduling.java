package c3;

public class ThreadScheduling {

    private String filename;
    public ThreadScheduling(String filename)
    {
        this.filename = filename;
    }

    public void calculateDigest()
    {
        ListCallbackDigest cb = new ListCallbackDigest(filename);
        cb.addDigestListener(this);
        Thread t = new Thread(cb);
        t.setPriority(8);
        t.start();
    }
}
