package com.ft.common;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import com.ft.utils.GetterUtil;
import com.ft.utils.ReflectionUtils;
import com.ft.utils.Validator;


/**
 * 閫氱敤鏁版嵁鎸佷箙灞�
 * 
 * @author zhangQ 
 * Date: 2013-6-3 20:29
 * 
 */
public abstract class BaseDao<T> {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	protected SessionFactory sessionFactory;
	protected Class<T> clazz;

	/**
	 * 鐢ㄤ簬Dao灞傚瓙绫讳娇鐢ㄧ殑鏋勯�鍑芥暟. 閫氳繃瀛愮被鐨勬硾鍨嬪畾涔夊彇寰楀璞＄被鍨婥lass. 
	 * eg. public class UserDao extends BaseDao<User, Long>
	 */
	public BaseDao() {
		this.clazz = ReflectionUtils.getSuperClassGenricType(getClass());
	}

	/**
	 * 鐢ㄤ簬鐢ㄤ簬鐪佺暐Dao灞� 鍦⊿ervice灞傜洿鎺ヤ娇鐢ㄩ�鐢˙aseDao鐨勬瀯閫犲嚱鏁� 鍦ㄦ瀯閫犲嚱鏁颁腑瀹氫箟瀵硅薄绫诲瀷Class. 
	 * eg. HibernateDao<User, Long> userDao = new BaseDao<User,
	 * Long>(sessionFactory, User.class);
	 */
	public BaseDao(final SessionFactory sessionFactory, final Class<T> entityClass) {
		this.sessionFactory = sessionFactory;
		this.clazz = entityClass;
	}

	/**
	 * 鍙栧緱sessionFactory.
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * 閲囩敤@Autowired鎸夌被鍨嬫敞鍏essionFactory, 褰撴湁澶氫釜SesionFactory鐨勬椂鍊橭verride鏈嚱鏁�
	 */
	@Autowired
	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 鍙栧緱褰撳墠Session.
	 */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 鏂板瀵硅薄.
	 */
	public void save(final T entity) {
		Assert.notNull(entity, "entity涓嶈兘涓虹┖");
		getSession().save(entity);
		logger.debug("save entity: {}", entity);
	}
	
	/**
	 * 淇濆瓨鏂板鎴栦慨鏀圭殑瀵硅薄.
	 * @param entity
	 */
	public void saveOrUpdate(final T entity){
		Assert.notNull(entity,"entity涓嶈兘涓虹┖");
		getSession().saveOrUpdate(entity);
		logger.debug("saveOrUpdate entity: {}", entity);
	}
	
	/**
	 * 鏇存柊瀵硅薄
	 */
	public void update(final T entity){
		Assert.notNull(entity,"entity涓嶈兘涓虹┖");
		getSession().update(entity);
		logger.debug("update entity: {}", entity);
	}
	
	/**
	 * 鏇存柊瀵硅薄
	 */
	@SuppressWarnings("unchecked")
	public T merge(final T entity){
		Assert.notNull(entity,"entity涓嶈兘涓虹┖");
		logger.debug("merge entity: {}", entity);
		return (T) getSession().merge(entity);
	}
	
	/**
	 * 鍒犻櫎瀵硅薄.
	 * 
	 * @param entity 瀵硅薄蹇呴』鏄痵ession涓殑瀵硅薄鎴栧惈id灞炴�鐨則ransient瀵硅薄.
	 */
	public void delete(final T entity) {
		Assert.notNull(entity, "entity涓嶈兘涓虹┖");
		getSession().delete(entity);
		logger.debug("delete entity: {}", entity);
	}

	/**
	 * 鎸塱d鍒犻櫎瀵硅薄.
	 */
	public void delete(final Serializable id) {
		Assert.notNull(id, "id涓嶈兘涓虹┖");
		delete(get(id));
		logger.debug("delete entity {},id is {}", clazz.getSimpleName(), id);
	}

	/**
	 * 鎸塱d鑾峰彇瀵硅薄.
	 */
	@SuppressWarnings("unchecked")
	public T get(final Serializable id) {
		Assert.notNull(id, "id涓嶈兘涓虹┖");
		return (T) getSession().get(clazz, id);
	}

	/**
	 * 鑾峰彇鍏ㄩ儴瀵硅薄.
	 */
	public List<T> findAll() {
		return find();
	}
	
	/**
	 * 缁熻鎵�湁
	 * @return
	 */
	public int countAll(){
		return GetterUtil.getInteger(getSession().createQuery("select count("+getIdName()+") from "+clazz.getSimpleName()).setCacheable(true).uniqueResult());
	}
	
	
	/**
	 * 鍒犻櫎鎵�湁
	 */
	public void removeAll(){
		int num= getSession().createQuery("delete from "+clazz.getSimpleName()).executeUpdate();
		logger.debug("delete entity {},total {}", clazz.getSimpleName(), num);
	}
	
	/**
	 * 鎸夌収hql鎵归噺鏇存柊
	 * @param hql
	 * @param objects
	 */
	public void batchHandleByHQL(String hql,Object... objects){
		Query q = getSession().createQuery(hql);
		if(Validator.isNotNull(objects)){
			for(int i=0; i<objects.length; i++){
				q.setParameter(i, objects[i]);
			}
		}
		q.executeUpdate();
	}
	
	/**
	 * 鍒嗛〉鏌ヨ鎵�湁锛屽苟涓旀寜鐓rderBy瀛楁鎺掑簭
	 * @param orderBy
	 * @param isAsc
	 * @param firstIndex
	 * @param maxResult
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getScrollData(String orderBy, boolean isAsc,int firstIndex,int maxResult) {
		Criteria c = createCriteria();
		if (isAsc) {
			c.addOrder(Order.asc(orderBy));
		} else {
			c.addOrder(Order.desc(orderBy));
		}
		
		if(firstIndex!=-1 && maxResult!=-1){
			c.setFirstResult(firstIndex).setMaxResults(maxResult);
		}
		return c.list();
	}
	
	/**
	 * 鍒嗛〉鎺掑簭鏌ヨ
	 * @param orderby
	 * @param firstIndex
	 * @param maxResult
	 * @return
	 */
	public List<T> getScrollData(LinkedHashMap<String, String> orderby,int firstIndex, int maxResult) {
		return getScrollData(null, null, orderby,firstIndex, maxResult);
	}

	/**
	 * 鏉′欢鍒嗛〉鏌ヨ
	 * @param whereHql 涓嶅惈"where"鍏抽敭瀛楃殑鏌ヨ鏉′欢锛屽"name=? and age=?"
	 * @param queryParams 鍙傛暟鍊煎璞�
	 * @param firstIndex
	 * @param maxResult
	 * @return
	 */
	public List<T> getScrollData(String whereHql, Object[] queryParams,int firstIndex, int maxResult) {
		return getScrollData(whereHql, queryParams, null,firstIndex, maxResult);
	}

	/**
	 * 鍒嗛〉鏌ヨ鎵�湁
	 * @param firstIndex
	 * @param maxResult
	 * @return
	 */
	public List<T> getScrollData(int firstIndex, int maxResult) {
		return getScrollData(null, null, null,firstIndex, maxResult);
	}
	
	/**
	 * 鏍规嵁瀹屾暣鐨凥QL璇彞鍒嗛〉鏌ヨ
	 * @param fullHQL
	 * @param queryParams
	 * @param firstIndex
	 * @param maxResult
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getScrollDataByFulHQL(String fullHQL,Object[] queryParams,int firstIndex,int maxResult){
		Query query = this.createQuery(fullHQL, queryParams);
		if(firstIndex!=-1 && maxResult!=-1){
			query.setFirstResult(firstIndex).setMaxResults(maxResult);
		}
		return query.list();
	}

	/**
	 * 鏉′欢鏌ヨ锛屽苟涓斿垎椤佃繑鍥炵粨鏋�
	 * @param whereHql 涓嶅惈"where"鍏抽敭瀛楃殑鏌ヨ鏉′欢锛屽"name=? and age=?"
	 * @param queryParams 鍙傛暟鍊煎璞�
	 * @param orderBy 鎺掑簭map锛屽 new LinkedHashMap<String,String>().put("name","desc")
	 * @param firstIndex
	 * @param maxResult
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getScrollData(String whereHql,Object[] queryParams,LinkedHashMap<String,String> orderBy ,int firstIndex,int maxResult){
		StringBuilder where = new StringBuilder();
		where.append("from "+clazz.getSimpleName()+" o ");
		where.append(whereHql==null || "".equals(whereHql.trim()) ? " ":"where " + whereHql);
		where.append(" "+ buildOrderBy(orderBy));
		
		Query query = this.createQuery(where.toString(), queryParams);
		if(firstIndex!=-1 && maxResult!=-1){
			query.setFirstResult(firstIndex).setMaxResults(maxResult);
		}
		return query.list();
	}
	
	/**
	 * 鏋勯�order by璇彞
	 * @param orderBy
	 * @return
	 */
	protected String buildOrderBy(LinkedHashMap<String,String> orderBy){
		StringBuilder orderbysql = new StringBuilder();
		if(orderBy!=null && !orderBy.isEmpty()){
			orderbysql.append(" order by ");
			for(String key : orderBy.keySet()){
				orderbysql.append("o.").append(key).append(" ").append(orderBy.get(key)).append(",");
			}
			orderbysql.deleteCharAt(orderbysql.length()-1);
		}
		return orderbysql.toString();
	}
	
	/**
	 * 鎸夌収鏉′欢缁熻
	 * @param whereHql
	 * @param queryParams
	 * @return
	 */
	public int countBy(String whereHql,Object[] queryParams){
		StringBuilder where = new StringBuilder();
		where.append("select count("+getIdName()+") from "+clazz.getSimpleName()+" o ");
		where.append(whereHql==null || "".equals(whereHql.trim()) ? " ":"where " + whereHql);
		
		Query query = this.createQuery(where.toString(), queryParams);
		return GetterUtil.getInteger(query.uniqueResult());
	}
	
	/**
	 * 鏇村姞瀹屾暣hql鏉′欢鏌ヨ
	 * @param fullHQL
	 * @param queryParams
	 * @return
	 */
	public int countByFullHQL(String fullHQL,Object... queryParams){
		Query query = this.createQuery(fullHQL, queryParams);
		return GetterUtil.getInteger(query.uniqueResult());
	}
	
	/**
	 * 鎸夊睘鎬ф煡鎵惧璞″垪琛�鍖归厤鏂瑰紡涓虹浉绛�
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List<T> findBy(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName涓嶈兘涓虹┖");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return find(criterion);
	}

	/**
	 * 鎸夊睘鎬ф煡鎵惧敮涓�璞�鍖归厤鏂瑰紡涓虹浉绛�
	 * @param propertyName
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T findUniqueBy(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName涓嶈兘涓虹┖");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(criterion).setCacheable(true).setFirstResult(0).setMaxResults(1).uniqueResult();
	}

	/**
	 * 鎸塱d鍒楄〃鑾峰彇瀵硅薄.
	 * @param ids
	 * @return
	 */
	public List<T> findByIds(List<? extends Serializable> ids) {
		if(ids==null || ids.isEmpty()){
			return null;
		}
		return find(Restrictions.in(getIdName(), ids));
	}

	/**
	 * 鎸塇QL鏌ヨ瀵硅薄鍒楄〃.
	 * 
	 * @param values 鏁伴噺鍙彉鐨勫弬鏁�鎸夐『搴忕粦瀹�
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> find(final String hql, final Object... values) {
		return createQuery(hql, values).list();
	}

	/**
	 * 鎸塇QL鏌ヨ瀵硅薄鍒楄〃.
	 * 
	 * @param values 鍛藉悕鍙傛暟,鎸夊悕绉扮粦瀹�
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> find(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).list();
	}
	
	/**
	 * 鎸夌収FullHQL鍒嗛〉鏌ヨ瀵硅薄鍒楄〃
	 * @param firstIndex 璧峰琛岀殑涓嬫爣锛岀涓�釜浠�寮�銆傛敞鎰忥細鍖哄埆pageIndex 
	 * @param pageSize
	 * @param hql 瀹屾暣鐨刪ql璇彞
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> find(int firstIndex,int pageSize,final String hql,final Object... values) {
		return createQuery(hql, values).setFirstResult(firstIndex).setMaxResults(pageSize).list();
	}

	/**
	 * 鎸塇QL鏌ヨ鍞竴瀵硅薄.
	 * 
	 * @param values 鏁伴噺鍙彉鐨勫弬鏁�鎸夐『搴忕粦瀹�
	 */
	@SuppressWarnings("unchecked")
	public <X> X findUnique(final String hql, final Object... values) {
		return (X) createQuery(hql, values).setFirstResult(0).setMaxResults(1).uniqueResult();
	}

	/**
	 * 鎸塇QL鏌ヨ鍞竴瀵硅薄.
	 * 
	 * @param values 鍛藉悕鍙傛暟,鎸夊悕绉扮粦瀹�
	 */
	@SuppressWarnings("unchecked")
	public <X> X findUnique(final String hql, final Map<String, ?> values) {
		return (X) createQuery(hql, values).setFirstResult(0).setMaxResults(1).uniqueResult();
	}

	/**
	 * 鎵цHQL杩涜鎵归噺淇敼/鍒犻櫎鎿嶄綔.
	 * @param hql
	 * @param values
	 * @return
	 */
	public int batchExecute(final String hql, final Object... values) {
		return createQuery(hql, values).executeUpdate();
	}
	
	/**
	 * 鎵цHQL杩涜鎵归噺淇敼/鍒犻櫎鎿嶄綔.
	 * 
	 * @param hql
	 * @param values
	 * @return 鏇存柊璁板綍鏁�
	 */
	public int batchExecute(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 鏍规嵁鏌ヨHQL涓庡弬鏁板垪琛ㄥ垱寤篞uery瀵硅薄.
	 * <p/>
	 * 鏈被灏佽鐨刦ind()鍑芥暟鍏ㄩ儴榛樿杩斿洖瀵硅薄绫诲瀷涓篢,褰撲笉涓篢鏃朵娇鐢ㄦ湰鍑芥暟.
	 * 
	 * @param values 鏁伴噺鍙彉鐨勫弬鏁�鎸夐『搴忕粦瀹�
	 */
	public Query createQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString涓嶈兘涓虹┖");
		Query query = getSession().createQuery(queryString).setCacheable(true);
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 鏍规嵁鏌ヨHQL涓庡弬鏁板垪琛ㄥ垱寤篞uery瀵硅薄.
	 * 
	 * @param values 鍛藉悕鍙傛暟,鎸夊悕绉扮粦瀹�
	 */
	public Query createQuery(final String queryString, final Map<String, ?> values) {
		Assert.hasText(queryString, "queryString涓嶈兘涓虹┖");
		Query query = getSession().createQuery(queryString).setCacheable(true);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	/**
	 * 鎸塁riteria鏌ヨ瀵硅薄鍒楄〃.
	 * 
	 * @param criterions 鏁伴噺鍙彉鐨凜riterion.
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}

	/**
	 * 鎸塁riteria鏌ヨ鍞竴瀵硅薄.
	 * 
	 * @param criterions 鏁伴噺鍙彉鐨凜riterion.
	 */
	@SuppressWarnings("unchecked")
	public T findUnique(final Criterion... criterions) {
		return (T) createCriteria(criterions).setFirstResult(0).setMaxResults(1).uniqueResult();
	}

	/**
	 * 鏍规嵁Criterion鏉′欢鍒涘缓Criteria.
	 * <p/>
	 * 鏈被灏佽鐨刦ind()鍑芥暟鍏ㄩ儴榛樿杩斿洖瀵硅薄绫诲瀷涓篢,褰撲笉涓篢鏃朵娇鐢ㄦ湰鍑芥暟.
	 * 
	 * @param criterions 鏁伴噺鍙彉鐨凜riterion.
	 */
	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(clazz).setCacheable(true);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}
	
	/**
	 * 鎵цsql鏌ヨ
	 * @param sql
	 * @return
	 */
	public SQLQuery queryBySQL(String sql) {
		SQLQuery query=getSession().createSQLQuery(sql);
		return query;
	}
	
	/**
	 * 鎸夌収sql鍒嗛〉鏌ヨ瀵硅薄鍒楄〃
	 * @param firstIndex 璧峰琛岀殑涓嬫爣锛岀涓�釜浠�寮�銆傛敞鎰忥細鍖哄埆pageIndex 
	 * @param pageSize
	 * @param sql 瀹屾暣鐨剆ql璇彞
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> queryBySQL(int firstIndex,int pageSize,final String sql,final Object... values) {
		SQLQuery query = this.queryBySQL(sql);
		
		if(firstIndex!=-1 && pageSize!=-1){
			query.setFirstResult(firstIndex).setMaxResults(pageSize);
		}
		
		if (values != null && values.length > 0) {
			for (int i=0;i<values.length;i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query.list();
	}
	
	/**
	 * 鏍规嵁sql缁熻涓暟
	 * 
	 * @param sql
	 * @param values
	 * @return
	 */
	public Integer countBySQL(final String sql,final Object... values) {
		SQLQuery query = this.queryBySQL(sql);
		if (values != null && values.length > 0) {
			for (int i=0;i<values.length;i++) {
				query.setParameter(i, values[i]);
			}
		}
		return GetterUtil.getInteger(query.uniqueResult());
	}

	/**
	 * 鎵цsql鏇存柊璇彞
	 * @param sql
	 * @return
	 */
	public int executeUpdateBySQL(String sql) {
		return getSession().createSQLQuery(sql).executeUpdate();
	}

	/**
	 * 鍒濆鍖栧璞� 浣跨敤load()鏂规硶寰楀埌鐨勪粎鏄璞roxy, 鍦ㄤ紶鍒癡iew灞傚墠闇�杩涜鍒濆鍖� 
	 * 鍙垵濮嬪寲entity鐨勭洿鎺ュ睘鎬�浣嗕笉浼氬垵濮嬪寲寤惰繜鍔犺浇鐨勫叧鑱旈泦鍚堝拰灞炴�. 濡傞渶鍒濆鍖栧叧鑱斿睘鎬�鍙疄鐜版柊鐨勫嚱鏁�鎵ц:
	 * Hibernate.initialize(user.getRoles())锛屽垵濮嬪寲User鐨勭洿鎺ュ睘鎬у拰鍏宠仈闆嗗悎. Hibernate.initialize
	 * (user.getDescription())锛屽垵濮嬪寲User鐨勭洿鎺ュ睘鎬у拰寤惰繜鍔犺浇鐨凞escription灞炴�.
	 */
	public void initEntity(T entity) {
		Hibernate.initialize(entity);
	}

	/**
	 * @see #initEntity(Object)
	 */
	public void initEntity(List<T> entityList) {
		for (T entity : entityList) {
			Hibernate.initialize(entity);
		}
	}

	/**
	 * Flush褰撳墠Session.
	 */
	public void flush() {
		getSession().flush();
	}

	/**
	 * 鍙栧緱瀵硅薄鐨勪富閿悕.
	 */
	public String getIdName() {
		ClassMetadata meta = getSessionFactory().getClassMetadata(clazz);
		return meta.getIdentifierPropertyName();
	}
}
