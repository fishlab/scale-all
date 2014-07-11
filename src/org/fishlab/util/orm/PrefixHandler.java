package org.fishlab.util.orm;

public interface PrefixHandler {
	public String getPrefix(String fieldName);
	
	
	final static PrefixHandler EMPTY_PREFIXHANDLER=new PrefixHandler() {
		static final String EMPTY_STRING="";
		@Override
		public String getPrefix(String fieldName) {
			return EMPTY_STRING;
		}
	};
	
	
}
