package d2rq.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;

import de.fuberlin.wiwiss.d2rq.jena.ModelD2RQ;

public class BaseDao {
	private static Logger log = LogManager.getLogger(BaseDao.class.getName());

	@SuppressWarnings("unused")
	public String exeSparql(String sparql) throws Exception {
		log.debug("--------------------select start-------------");
		ModelD2RQ d2rqModel = new ModelD2RQ("file:doc/ttl/ontology.ttl");

		log.debug("-------------------" + sparql);
		Query q = QueryFactory.create(sparql);
		ResultSet rs = QueryExecutionFactory.create(q, d2rqModel).execSelect();
		//ResultSetFormatter.out(System.out, rs, q);// 格式化输出到控制台
		String result = ResultSetFormatter.asXMLString(rs);
		log.debug("rest="+result);
		return result;
	}

}
