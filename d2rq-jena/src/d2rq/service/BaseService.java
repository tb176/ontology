package d2rq.service;

import d2rq.dao.BaseDao;

public class BaseService {
	private BaseDao dao;

	public BaseDao getDao() {
		return dao;
	}

	public void setDao(BaseDao dao) {
		this.dao = dao;
	}
}