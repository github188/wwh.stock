package com.zjhcsoft.struc.util.datasource.formatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Formatter {
	
	public final Logger LOG = LoggerFactory.getLogger(Formatter.class);
	
	public abstract String format(String formattingString);

}
