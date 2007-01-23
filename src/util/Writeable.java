package util;

import java.io.IOException;

public interface Writeable
  {
    public void writeTo (IndentedWriter writer) throws IOException;
  }
