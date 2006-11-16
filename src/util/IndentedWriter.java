package util;

import java.io.*;

public class IndentedWriter extends Writer
  {
    private Writer output;

    private String indent, step;

    public IndentedWriter (Writer output)
      {
        this.output = output;
        this.indent = "";
        this.step = "    ";
      }

    public IndentedWriter (Writer output, String indentStep)
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
        this.indent = this.indent.substring (this.step.length ());
      }

    public void println (String line) throws IOException
      {
        output.write (indent);
        output.write (line);
        output.write ('\n');
      }

    public void openWith (String line) throws IOException
      {
        output.write (indent);
        output.write (line);
      }
    
    public void closeWith (String line) throws IOException
      {
        output.write (line);
        output.write ('\n');
      }

    @Override
    public void close () throws IOException
      {
        output.close ();
      }

    @Override
    public void flush () throws IOException
      {
        output.flush ();
      }

    @Override
    public void write (char[] cbuf, int off, int len) throws IOException
      {
        output.write (cbuf, off, len);
      }
  }