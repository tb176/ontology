package com.ontology.common.dao;

import org.apache.jena.rdf.model.Model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class BaseDao {
	private static Logger log = LogManager.getLogger(BaseDao.class.getName());
	private Model model ;//数据模型

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
	
	/**
	 * 封装一些公用的方法
	 */
	
	
	
	
	
	
	
	/**
	 * 输出sparql操作日志
	 * @param sql
	 * @param params
	 */
	private void writesparqlLog(String sql,long time,String para ){
		StringBuffer sb = new StringBuffer();
		log.debug("====sparql:["+sql+"]====params:["+para+"]====time:["+time+"]");
	}
	
}
