package com.ontology.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ontology.dao.DemoDao;
import com.ontology.rmiservice.DemoInterface;

public class DemoService implements DemoInterface{
	private static Logger log = LogManager.getLogger(DemoService.class.getName());

	public void sparqlRDF(String rdfPath, String sparqlPath) throws Exception {
		long beg = System.currentTimeMillis();
		new DemoDao().sparqlRDF(rdfPath, sparqlPath);
		long end = System.currentTimeMillis();
		log.debug("====该次服务[findAuthroizeTreeList]耗时["+(end-beg)+"]毫秒");
		
	}

}
