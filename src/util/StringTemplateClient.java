package util;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

public interface StringTemplateClient
{
  public abstract StringTemplate writeUsing (StringTemplateGroup templates);
}