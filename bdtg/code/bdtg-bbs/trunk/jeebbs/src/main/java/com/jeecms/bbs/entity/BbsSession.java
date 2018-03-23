package com.jeecms.bbs.entity;

import com.jeecms.bbs.entity.base.BaseBbsSession;



public class BbsSession extends BaseBbsSession {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public BbsSession () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public BbsSession (java.lang.Long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public BbsSession (
		java.lang.Long id,
		java.lang.String sessionId,
		java.lang.String ip,
		java.util.Date firstActivetime,
		java.util.Date lastActivetime) {

		super (
			id,
			sessionId,
			ip,
			firstActivetime,
			lastActivetime);
	}

/*[CONSTRUCTOR MARKER END]*/


}