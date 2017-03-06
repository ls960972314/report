package com.sypay.omp.report.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * job
 * @author lishun
 *
 */
public class HourQuartzJob {
	
	private final Log log = LogFactory.getLog(HourQuartzJob.class);
	/* job */
	public void work() {
		log.info("job begin");
		
		log.info("job end");
	}
	
}
