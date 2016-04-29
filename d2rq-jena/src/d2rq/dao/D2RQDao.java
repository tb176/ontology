package d2rq.dao;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class D2RQDao extends BaseDao{
	private static Logger log = LogManager.getLogger(D2RQDao.class.getName());
	private static final String PREFIX ="PREFIX db: <>"+
								"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
								"PREFIX owl: <http://www.w3.org/2002/07/owl#>"+
								"PREFIX map: <>"+
								"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"+
								"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
								"PREFIX vocab: <vocab/>"+
								"PREFIX jdbc: <http://d2rq.org/terms/jdbc/>";
	//第一个方法，查询所有的类
	public String getAllCls() throws Exception {
		String sparql = PREFIX + " SELECT DISTINCT ?class WHERE { [] a ?class } ORDER BY ?class ";
		
		return super.exeSparql(sparql);
	}
	//第二个方法，根据客户号查询关联信息
	public String getMsgByCustno(String custno)throws Exception{
		String sparql = PREFIX + "SELECT DISTINCT ?property ?hasValue ?isValueOf" + "WHERE {"
				+ "  { <doc/ttl/ontology.ttl#busi_entity_type/"+custno+"> ?property ?hasValue }" + " UNION"
				+ "{ ?isValueOf ?property <doc/ttl/ontology.ttl#busi_entity_type/"+custno+"> }" + "}"
				+ "ORDER BY (!BOUND(?hasValue)) ?property ?hasValue ?isValueOf";
		return super.exeSparql(sparql);
	}
	
}
