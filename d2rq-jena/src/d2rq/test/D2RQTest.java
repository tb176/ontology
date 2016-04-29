package d2rq.test;

import d2rq.service.D2RQService;

public class D2RQTest {
	public static void main(String[] args) throws Exception {
		D2RQService d2rq = new D2RQService();
		//d2rq.getAllCls("doc/ttl/ontology.ttl");
		String s = d2rq.getMsgByCustno("0000007057");
	}
}
