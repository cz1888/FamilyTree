package com.ft.common;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.ft.common.BaseDao;
import com.ft.common.InjectBaseDependencyHelper;
import com.ft.common.Page;

/**
 * 閫氱敤Service瀹炵幇
 * 
 * @author zhangQ
 * create date:2013-6-19
 */
@Transactional
public abstract class BaseService<T> {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private BaseDao<T> baseDao;

	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}


	public BaseService(){
	}

	@PostConstruct
	public void init(){
		InjectBaseDependencyHelper.findAndInjectBaseDaoDependency(this);
	}

	/**
	 * 鏂板瀵硅薄.
	 */
	@Transactional
	public void save(final T entity) {
		/*
		Object uuidFile = ReflectionUtils.getDeclaredField(entity, "uuid");
		if(Validator.isNotNull(uuidFile)){
			Object fieldValue = ReflectionUtils.getFieldValue(entity, "uuid");
			if(Validator.isNull(fieldValue)){
				ReflectionUtils.invokeSetterMethod(entity, "uuid", Identities.uuid2());		//鑷姩浜х敓uuid
			}
		}
		*/
		baseDao.save(entity);
	}

	/**
	 * 淇濆瓨鏂板鎴栦慨鏀圭殑瀵硅薄.
	 * @param entity
	 */
	@Transactional
	public void saveOrUpdate(final T entity){
		baseDao.saveOrUpdate(entity);
	}

	/**
	 * 鏇存柊瀵硅薄
	 */
	@Transactional
	public void update(final T entity){
		/*
		Object uuidFile = ReflectionUtils.getDeclaredField(entity, "uuid");
		if(Validator.isNotNull(uuidFile)){
			Object fieldValue = ReflectionUtils.getFieldValue(entity, "uuid");
			if(Validator.isNull(fieldValue)){
				ReflectionUtils.invokeSetterMethod(entity, "uuid", Identities.uuid2());		//鑷姩浜х敓uuid
			}
		}
		*/
		baseDao.update(entity);
	}

	/**
	 * 鏇存柊瀵硅薄
	 * @Transactional
	 */
	public T merge(final T entity){
		return baseDao.merge(entity);
	}
	
	/**
	 * 鍒犻櫎瀵硅薄.
	 * 
	 * @param entity 瀵硅薄蹇呴』鏄痵ession涓殑瀵硅薄鎴栧惈id灞炴�鐨則ransient瀵硅薄.
	 */
	@Transactional
	public void delete(final T entity) {
		baseDao.delete(entity);
	}

	/**
	 * 鎸塱d鍒犻櫎瀵硅薄.
	 */
	@Transactional
	public void delete(final Serializable id) {
		baseDao.delete(id);
	}

	/**
	 * 鎸塱d鑾峰彇瀵硅薄.
	 */
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public T get(final Serializable id) {
		return baseDao.get(id);
	}

	/**
	 * 鑾峰彇鍏ㄩ儴瀵硅薄.
	 */
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public List<T> findAll() {
		return baseDao.findAll();
	}
	
	/**
	 * 缁熻鎵�湁
	 * @return
	 */
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public int countAll(){
		return baseDao.countAll();
	}
	
	/**
	 * 鍒犻櫎鎵�湁
	 */
	@Transactional
	public void removeAll(){
		baseDao.removeAll();
	}
	
	/**
	 * 鎸夌収hql鎵归噺鏇存柊
	 * @param hql
	 * @param objects
	 */
	@Transactional
	public void batchHandleByHQL(String hql,Object... objects){
		baseDao.batchHandleByHQL(hql, objects);
	}
	
	/**
	 * 鍒嗛〉鏌ヨ鎵�湁锛屽苟涓旀寜鐓rderBy瀛楁鎺掑簭
	 * @param orderBy
	 * @param isAsc
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public Page<T> getScrollData(String orderBy, boolean isAsc,int pageIndex,int pageSize) {
		Page<T> page = new Page<T>(pageIndex,pageSize);
		page.setRecords(baseDao.getScrollData(orderBy, isAsc, page.getStart(), pageSize));
		page.setTotalRow(baseDao.countAll());
		return page;
	}
	
	/**
	 * 鍒嗛〉鏌ヨ鎵�湁锛屽苟涓旀寜鐓rderBy灏佽鐨凪ap鎺掑簭
	 * @param orderby
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public Page<T> getScrollData(LinkedHashMap<String, String> orderby,int pageIndex, int pageSize) {
		Page<T> page = new Page<T>(pageIndex,pageSize);
		page.setRecords(baseDao.getScrollData(orderby, page.getStart(), pageSize));
		page.setTotalRow(baseDao.countAll());
		return page;
	}

	/**
	 * 鏉′欢鍒嗛〉鏌ヨ
	 * @param whereHql 涓嶅惈"where"鍏抽敭瀛楃殑鏌ヨ鏉′欢锛屽"name=? and age=?"
	 * @param queryParams 鍙傛暟鍊煎璞�
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public Page<T> getScrollData(String whereHql, Object[] queryParams,int pageIndex, int pageSize) {
		Page<T> page = new Page<T>(pageIndex,pageSize);
		page.setRecords(baseDao.getScrollData(whereHql, queryParams, page.getStart(), pageSize));
		page.setTotalRow(baseDao.countBy(whereHql, queryParams));
		return page;
	}
	
	/**
	 * 鏉′欢鍒嗛〉鏌ヨ
	 * @param whereHql 涓嶅惈"where"鍏抽敭瀛楃殑鏌ヨ鏉′欢锛屽"name=? and age=?"
	 * @param pageIndex
	 * @param pageSize
	 * @param queryParams 鍙傛暟鍊煎璞�
	 * @return
	 */
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public Page<T> getScrollData(String whereHql,int pageIndex, int pageSize, Object... queryParams) {
		Page<T> page = new Page<T>(pageIndex,pageSize);
		page.setRecords(baseDao.getScrollData(whereHql, queryParams, page.getStart(), pageSize));
		page.setTotalRow(baseDao.countBy(whereHql, queryParams));
		return page;
	}

	/**
	 * 鍒嗛〉鏌ヨ鎵�湁
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public Page<T> getScrollData(int pageIndex, int pageSize) {
		Page<T> page = new Page<T>(pageIndex,pageSize);
		page.setRecords(baseDao.getScrollData(page.getStart(), pageSize));
		page.setTotalRow(baseDao.countAll());
		return page;
	}

	/**
	 * 鏉′欢鏌ヨ锛屽苟涓斿垎椤佃繑鍥炵粨鏋�
	 * @param whereHql 涓嶅惈"where"鍏抽敭瀛楃殑鏌ヨ鏉′欢锛屽"name=? and age=?"
	 * @param queryParams 鍙傛暟鍊煎璞�
	 * @param orderBy 鎺掑簭map锛屽 new LinkedHashMap<String,String>().put("name","desc")
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public Page<T> getScrollData(String whereHql,Object[] queryParams,LinkedHashMap<String,String> orderBy ,int pageIndex,int pageSize){
		Page<T> page = new Page<T>(pageIndex,pageSize);
		page.setRecords(baseDao.getScrollData(whereHql, queryParams, orderBy, page.getStart(), pageSize));
		page.setTotalRow(baseDao.countBy(whereHql, queryParams));
		return page;
	}
	
	/**
	 * 鎸夌収鏉′欢缁熻
	 * @param whereHql
	 * @param queryParams
	 * @return
	 */
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public int countBy(String whereHql,Object[] queryParams){
		return baseDao.countBy(whereHql, queryParams);
	}
	
	/**
	 * 鏇村姞瀹屾暣hql鏉′欢鏌ヨ
	 * @param fullHQL
	 * @param queryParams
	 * @return
	 */
	public int countByFullHQL(String fullHQL,Object... queryParams){
		return baseDao.countByFullHQL(fullHQL, queryParams);
	}
	
	/**
	 * 鎸夊睘鎬ф煡鎵惧璞″垪琛�鍖归厤鏂瑰紡涓虹浉绛�
	 */
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public List<T> findBy(final String propertyName, final Object value) {
		return baseDao.findBy(propertyName, value);
	}

	/**
	 * 鎸夊睘鎬ф煡鎵惧敮涓�璞�鍖归厤鏂瑰紡涓虹浉绛�
	 */
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public T findUniqueBy(final String propertyName, final Object value) {
		return baseDao.findUniqueBy(propertyName, value);
	}

	/**
	 * 鎸塱d鍒楄〃鑾峰彇瀵硅薄.
	 */
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public List<T> findByIds(List<? extends Serializable> ids) {
		return baseDao.findByIds(ids);
	}
	
	/**
	 * 鎸塱d鍒楄〃鑾峰彇瀵硅薄.
	 */
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public List<T> findByIds(Serializable[] ids) {
		List<Serializable> idList = Lists.newArrayList();
		if(ids!=null && ids.length>0){
			for (Serializable id : ids) {
				idList.add(id);
			}
		}
		return baseDao.findByIds(idList);
	}

	/**
	 * 鎸塇QL鏌ヨ瀵硅薄鍒楄〃.
	 * 
	 * @param values 鏁伴噺鍙彉鐨勫弬鏁�鎸夐『搴忕粦瀹�
	 */
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public <X> List<X> find(final String hql, final Object... values) {
		return baseDao.find(hql, values);
	}

	/**
	 * 鎸塇QL鏌ヨ瀵硅薄鍒楄〃.
	 * 
	 * @param values 鍛藉悕鍙傛暟,鎸夊悕绉扮粦瀹�
	 */
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public <X> List<X> find(final String hql, final Map<String, ?> values) {
		return baseDao.find(hql, values);
	}
	
	/**
	 * 鎸夌収FullHQL鍒嗛〉鏌ヨ瀵硅薄鍒楄〃
	 * @param firstIndex 璧峰琛岀殑涓嬫爣锛岀涓�釜浠�寮�銆傛敞鎰忥細鍖哄埆pageIndex 
	 * @param pageSize
	 * @param hql 瀹屾暣鐨刪ql璇彞
	 * @param values
	 * @return
	 */
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public <X> List<X> find(int firstIndex,int pageSize,final String hql, final Object... values) {
		return baseDao.find(firstIndex, pageSize,hql,values);
	}

	/**
	 * 鍒嗛〉鏌ヨ鎵�湁鍒楄〃
	 * @param firstIndex 璧峰琛岀殑涓嬫爣锛岀涓�釜浠�寮�銆傛敞鎰忥細鍖哄埆pageIndex 
	 * @param pageSize
	 * @return
	 */
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public List<T> findScrollData(int firstIndex,int pageSize) {
		return baseDao.getScrollData(firstIndex, pageSize);
	}

	/**
	 * 鎸塇QL鏌ヨ鍞竴瀵硅薄.
	 * 
	 * @param values 鏁伴噺鍙彉鐨勫弬鏁�鎸夐『搴忕粦瀹�
	 */
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public <X> X findUnique(final String hql, final Object... values) {
		return baseDao.findUnique(hql, values);
	}

	/**
	 * 鎸塇QL鏌ヨ鍞竴瀵硅薄.
	 * 
	 * @param values 鍛藉悕鍙傛暟,鎸夊悕绉扮粦瀹�
	 */
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public <X> X findUnique(final String hql, final Map<String, ?> values) {
		return baseDao.findUnique(hql, values);
	}

	/**
	 * 鎵цHQL杩涜鎵归噺淇敼/鍒犻櫎鎿嶄綔.
	 */
	@Transactional
	public int batchExecute(final String hql, final Object... values) {
		return baseDao.batchExecute(hql, values);
	}
	
	/**
	 * 鎵цHQL杩涜鎵归噺淇敼/鍒犻櫎鎿嶄綔.
	 */
	@Transactional
	public int batchExecute(final Object[] values,final String hql) {
		return baseDao.batchExecute(hql, values);
	}
	
	/**
	 * 鎵цHQL杩涜鎵归噺淇敼/鍒犻櫎鎿嶄綔.
	 * 
	 * @return 鏇存柊璁板綍鏁�
	 */
	@Transactional
	public int batchExecute(final String hql, final Map<String, ?> values) {
		return baseDao.batchExecute(hql, values);
	}

	/**
	 * 鏍规嵁鏌ヨHQL涓庡弬鏁板垪琛ㄥ垱寤篞uery瀵硅薄.
	 * <p/>
	 * 鏈被灏佽鐨刦ind()鍑芥暟鍏ㄩ儴榛樿杩斿洖瀵硅薄绫诲瀷涓篢,褰撲笉涓篢鏃朵娇鐢ㄦ湰鍑芥暟.
	 * 
	 * @param values 鏁伴噺鍙彉鐨勫弬鏁�鎸夐『搴忕粦瀹�
	 */
	public Query createQuery(final String queryString, final Object... values) {
		return baseDao.createQuery(queryString, values);
	}

	/**
	 * 鎸塁riteria鏌ヨ瀵硅薄鍒楄〃.
	 * 
	 * @param criterions 鏁伴噺鍙彉鐨凜riterion.
	 */
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public List<T> find(final Criterion... criterions) {
		return baseDao.find(criterions);
	}

	/**
	 * 鎸塁riteria鏌ヨ鍞竴瀵硅薄.
	 * 
	 * @param criterions 鏁伴噺鍙彉鐨凜riterion.
	 */
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public T findUnique(final Criterion... criterions) {
		return baseDao.findUnique(criterions);
	}

	/**
	 * 鍒濆鍖栧璞� 浣跨敤load()鏂规硶寰楀埌鐨勪粎鏄璞roxy, 鍦ㄤ紶鍒癡iew灞傚墠闇�杩涜鍒濆鍖� 
	 * 鍙垵濮嬪寲entity鐨勭洿鎺ュ睘鎬�浣嗕笉浼氬垵濮嬪寲寤惰繜鍔犺浇鐨勫叧鑱旈泦鍚堝拰灞炴�. 濡傞渶鍒濆鍖栧叧鑱斿睘鎬�鍙疄鐜版柊鐨勫嚱鏁�鎵ц:
	 * Hibernate.initialize(user.getRoles())锛屽垵濮嬪寲User鐨勭洿鎺ュ睘鎬у拰鍏宠仈闆嗗悎. Hibernate.initialize
	 * (user.getDescription())锛屽垵濮嬪寲User鐨勭洿鎺ュ睘鎬у拰寤惰繜鍔犺浇鐨凞escription灞炴�.
	 */
	public void initEntity(T entity) {
		baseDao.initEntity(entity);
	}

	/**
	 * @see #initEntity(Object)
	 */
	public void initEntity(List<T> entityList) {
		baseDao.initEntity(entityList);
	}
}
