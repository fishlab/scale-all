package org.fishlab.web.view.velocity;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Hashtable;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;

public class CacheDirective extends Directive {

	final static Hashtable<String, String> body_tpls = new Hashtable<String, String>();

	@Override
	public String getName() {
		return "cache";
	} // 指定指令的名称

	@Override
	public int getType() {
		return BLOCK;
	} // 指定指令类型为块指令

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.velocity.runtime.directive.Directive#render()
	 */
	@Override
	public boolean render(InternalContextAdapter context, Writer writer,
			Node node) throws IOException, ResourceNotFoundException,
			ParseErrorException, MethodInvocationException {
		// 获得缓存信息
		SimpleNode sn_region = (SimpleNode) node.jjtGetChild(0);
		String region = (String) sn_region.value(context);
		SimpleNode sn_key = (SimpleNode) node.jjtGetChild(1);
		Serializable key = (Serializable) sn_key.value(context);

		Node body = node.jjtGetChild(2);
		// 检查内容是否有变化
		String tpl_key = key + "@" + region;
		String body_tpl = body.literal();
		// String old_body_tpl = body_tpls.get(tpl_key);
		String cache_html = "CACHED HTML SZD";
		StringWriter sw = new StringWriter();
		body.render(context, sw);
		cache_html = sw.toString();
		// CacheHelper.set(region, key, cache_html);
		body_tpls.put(tpl_key, body_tpl);
		writer.write(cache_html);
		return true;
	}
}