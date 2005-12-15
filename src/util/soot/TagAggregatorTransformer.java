/*
 * Created on Oct 28, 2005
 */
package util.soot;

import soot.Unit;
import soot.tagkit.Tag;
import soot.tagkit.TagAggregator;

public abstract class TagAggregatorTransformer extends TagAggregator {
	@SuppressWarnings ("unchecked")
	@Override
	public void considerTag (Tag t, Unit u) {
		units.add (u);
		tags.add (t);
	}
}
