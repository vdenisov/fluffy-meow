package org.plukh.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class StreamGobbler extends Thread {
    private static final Logger log = LogManager.getLogger(StreamGobbler.class);

    protected InputStream in;
    protected PrintStream out;
    protected boolean closeOutputStream;

    // reads everything from input stream until empty, printing to provided output
    StreamGobbler(InputStream in, OutputStream out) {
        this(in, new PrintStream(out));
    }

    public StreamGobbler(InputStream in, PrintStream out) {
        this(in, out, false);
    }

    public StreamGobbler(InputStream in, OutputStream out, boolean closeOutputStream) {
        this.in = in;
        this.out = new PrintStream(out);
        this.closeOutputStream = closeOutputStream;
    }

    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null)
                out.println(line);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (closeOutputStream) out.close();
        }
    }
}
