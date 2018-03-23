package com.zjhcsoft.struc.update;

import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Updater {
	
	protected static final Logger LOG = LoggerFactory.getLogger(Updater.class);
	
	public abstract void update(Queue<String> queue);
}
