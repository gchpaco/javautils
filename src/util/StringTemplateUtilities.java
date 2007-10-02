package util;

import java.util.ArrayList;
import java.util.List;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

public abstract class StringTemplateUtilities
{
  public static List<StringTemplate> listToTemplates (
                                                      List<? extends StringTemplateClient> list,
                                                      StringTemplateGroup templates)
    {
      List<StringTemplate> result = new ArrayList<StringTemplate> ();
      for (StringTemplateClient stc : list)
        result.add (stc.writeUsing (templates));
      return result;
    }

}
