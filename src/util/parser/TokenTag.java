package util.parser;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class TokenTag<T>
  {
    protected T token;

    public TokenTag (T token)
      {
        this.token = token;
      }

    // This is quite deliberate; the goal here is to ensure that all tags are
    // equal to each other and hash to the same value iff their tokens are the
    // same.
    @Override
    public int hashCode ()
      {
        return new HashCodeBuilder (37, 11).append (token).toHashCode ();
      }

    @Override
    public boolean equals (Object obj)
      {
        if (obj instanceof TokenTag == false) return false;
        if (this == obj) return true;
        TokenTag<?> rhs = (TokenTag) obj;
        return new EqualsBuilder ().append (token, rhs.token).isEquals ();
      }
    
    @Override
    public String toString ()
      {
        return token.toString ();
      }

    public static <T> TokenTag<T> make (T object)
      {
        return new TokenTag<T> (object);
      }
  }