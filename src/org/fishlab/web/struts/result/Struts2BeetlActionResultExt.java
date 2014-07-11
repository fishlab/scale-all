package org.fishlab.web.struts.result;

import org.bee.tl.core.GroupTemplate;
import org.bee.tl.ext.struts2.Struts2BeetlActionResult;

public class Struts2BeetlActionResultExt extends Struts2BeetlActionResult {
	private static final long serialVersionUID = 3001038514400600017L;

	public void initGroupTemplate(GroupTemplate group) {
		 group.setCharset("UTF-8");
	 }
}
