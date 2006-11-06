package util;

import java.io.*;

public class IndentedWriter implements Closeable, Appendable, Flushable
  {
    private PrintStream output;

    private String indent, step;

    public IndentedWriter (PrintStream output)
      {
        this.output = output;
        this.indent = "";
        this.step = "    ";
      }

    public IndentedWriter (PrintStream output, String indentStep)
      {
        this.output = output;
        this.indent = "";
        this.step = indentStep;
      }

    public void indent ()
      {
        this.indent = this.indent + this.step;
      }

    public void dedent ()
      {
        this.indent = this.indent.substring (4);
      }

    public void ln (String line)
      {
        output.print (indent);
        output.println (line);
      }

    public void close ()
      {
        output.close ();
      }

    public Appendable append (CharSequence csq)
      {
        output.append (csq);
        return this;
      }

    public Appendable append (char c)
      {
        output.append (c);
        return this;
      }

    public Appendable append (CharSequence csq, int start, int end)
      {
        output.append (csq, start, end);
        return this;
      }

    public void flush ()
      {
        output.flush ();
      }
  }