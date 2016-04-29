package d2rq.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import d2rq.dao.D2RQDao;
import d2rq.interfaces.D2RQInterface;

public class D2RQService extends BaseService implements D2RQInterface{
	private static Logger log = LogManager.getLogger(D2RQService.class.getName());
	D2RQDao dao = new D2RQDao();
	@Override
	public String getAllCls() throws Exception {
		long beg = System.currentTimeMillis();
		String result = dao.getAllCls();
		long end = System.currentTimeMillis();
		log.debug("====该次服务[getAllCls]耗时["+(end-beg)+"]毫秒");
		return result;
	}
	@Override
	public String getMsgByCustno(String custno) throws Exception {
		long beg = System.currentTimeMillis();
		String result = dao.getMsgByCustno(custno);
		long end = System.currentTimeMillis();
		log.debug("====该次服务[getAllCls]耗时["+(end-beg)+"]毫秒");
		return result;
	}

}
