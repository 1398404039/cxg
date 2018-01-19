package com.cxg.service;

import java.util.List;

/**
 * @ClassName: IGeneralDaoMybatis
 * @Description: 閫氱敤鎺ュ彛
 * @author haipeng
 * @date 2017骞�鏈�鏃�
 */
public interface IGeneralService<T> {

	/**
	 * @Title: selectAll
	 * @Description: 鏌ヨ鍏ㄩ儴缁撴灉
	 * @param @return 杩斿洖瀹炰綋缁撳悎
	 * @return List<T>
	 * @throws
	 * @author haipeng
	 * @date 2017骞�鏈�鏃�
	 */
	List<T> selectAll();

	/**
	 * @Title: select
	 * @Description: 鏍规嵁瀹炰綋涓殑灞炴�鍊艰繘琛屾煡璇紝鏌ヨ鏉′欢浣跨敤绛夊彿
	 * @param @param record 瀹炰綋瀵硅薄
	 * @param @return
	 * @return List<T>
	 * @throws
	 * @author haipeng
	 * @date 2017骞�鏈�鏃�
	 */
	List<T> select(T record);

	/**
	 * @Title: selectOne
	 * @Description: 鏍规嵁瀹炰綋涓殑灞炴�杩涜鏌ヨ锛屽彧鑳芥湁涓�釜杩斿洖鍊硷紝鏈夊涓粨鏋滄槸鎶涘嚭寮傚父锛屾煡璇㈡潯浠朵娇鐢ㄧ瓑鍙�
	 * @param @param record
	 * @param @return
	 * @return T
	 * @throws
	 * @author haipeng
	 * @date 2017骞�鏈�鏃�
	 */
	T selectOne(T record);

	/**
	 * @Title: selectByPrimaryKey
	 * @Description: 鏍规嵁涓婚敭瀛楁杩涜鏌ヨ锛屾柟娉曞弬鏁板繀椤诲寘鍚畬鏁寸殑涓婚敭灞炴�锛屾煡璇㈡潯浠朵娇鐢ㄧ瓑鍙�
	 * @param @param key 涓婚敭鍊�
	 * @param @return 鍗曚釜瀹炰綋瀵硅薄
	 * @return T
	 * @throws
	 * @author haipeng
	 * @date 2017骞�鏈�鏃�
	 */
	T selectByPrimaryKey(Object key);

	/**
	 * @Title: selectCount
	 * @Description: 鏍规嵁瀹炰綋涓殑灞炴�鏌ヨ鎬绘暟锛屾煡璇㈡潯浠朵娇鐢ㄧ瓑鍙�
	 * @param @param record 瀹炰綋瀵硅薄
	 * @param @return
	 * @return int
	 * @throws
	 * @author haipeng
	 * @date 2017骞�鏈�鏃�
	 */
	int selectCount(T record);

	/**
	 * @Title: selectByExample
	 * @Description: 鏍规嵁Example鏉′欢杩涜鏌ヨ
	 * @param @param example
	 * @param @return
	 * @return List<T>
	 * @throws
	 * @author haipeng
	 * @date 2017骞�鏈�鏃�
	 */
	List<T> selectByExample(Object example);

	/**
	 * @Title: selectCountByExample
	 * @Description: 鏍规嵁Example鏉′欢杩涜鏌ヨ鎬绘暟
	 * @param @param example
	 * @param @return
	 * @return int
	 * @throws
	 * @author haipeng
	 * @date 2017骞�鏈�鏃�
	 */
	int selectCountByExample(Object example);

	/**
	 * @Title: insert
	 * @Description: 淇濆瓨涓�釜瀹炰綋锛宯ull鐨勫睘鎬т篃浼氫繚瀛橈紝涓嶄細浣跨敤鏁版嵁搴撻粯璁ゅ�
	 * @param @param record 瀹炰綋瀵硅薄
	 * @param @return
	 * @return int
	 * @throws
	 * @author haipeng
	 * @date 2017骞�鏈�鏃�
	 */
	int insert(T record);

	/**
	 * @Title: insertSelective
	 * @Description: 淇濆瓨涓�釜瀹炰綋锛宯ull鐨勫睘鎬т笉浼氫繚瀛橈紝浼氫娇鐢ㄦ暟鎹簱榛樿鍊�
	 * @param @param record 瀹炰綋瀵硅薄
	 * @param @return
	 * @return int
	 * @throws
	 * @author haipeng
	 * @date 2017骞�鏈�鏃�
	 */
	int insertSelective(T record);

	/**
	 * @Title: insertList
	 * @Description: 鎵归噺鎻掑叆锛屾敮鎸佹壒閲忔彃鍏ョ殑鏁版嵁搴撳彲浠ヤ娇鐢紝
	 * @param @param recordList 瀹炰綋闆嗗悎
	 * @param @return
	 * @return int
	 * @throws
	 * @author haipeng
	 * @date 2017骞�鏈�鏃�
	 */
	int insertList(List<T> recordList);

	/**
	 * @Title: insertUseGeneratedKeys
	 * @Description: 鎻掑叆鏁版嵁锛岄檺鍒朵负瀹炰綋鍖呭惈`id`灞炴�骞朵笖蹇呴』涓鸿嚜澧炲垪锛屽疄浣撻厤缃殑涓婚敭绛栫暐鏃犳晥
	 * @param @param record 瀹炰綋瀵硅薄
	 * @param @return
	 * @return int
	 * @throws
	 * @author haipeng
	 * @date 2017骞�鏈�鏃�
	 */
	int insertUseGeneratedKeys(T record);

	/**
	 * @Title: updateByPrimaryKey
	 * @Description: 鏍规嵁涓婚敭鏇存柊瀹炰綋鍏ㄩ儴瀛楁锛宯ull鍊间細琚洿鏂�
	 * @param @param record
	 * @param @return
	 * @return int
	 * @throws
	 * @author haipeng
	 * @date 2017骞�鏈�鏃�
	 */
	int updateByPrimaryKey(T record);

	/**
	 * @Title: updateByPrimaryKeySelective
	 * @Description: 鏍规嵁涓婚敭鏇存柊灞炴�涓嶄负null鐨勫�,鍗虫洿鏂伴儴鍒嗗瓧娈�
	 * @param @param record
	 * @param @return
	 * @return int
	 * @throws
	 * @author haipeng
	 * @date 2017骞�鏈�鏃�
	 */
	int updateByPrimaryKeySelective(T record);

	/**
	 * @Title: updateByCondition
	 * @Description: 鏍规嵁Condition鏉′欢鏇存柊瀹炰綋`record`鍖呭惈鐨勫叏閮ㄥ睘鎬э紝null鍊间細琚洿鏂�
	 * @param @param record 瀹炰綋瀵硅薄
	 * @param @param condition 鏉′欢
	 * @param @return
	 * @return int
	 * @throws
	 * @author haipeng
	 * @date 2017骞�鏈�鏃�
	 */
//	int updateByCondition(T record, Object condition);

	/**
	 * @Title: updateByConditionSelective
	 * @Description: 鏍规嵁Condition鏉′欢鏇存柊瀹炰綋`record`鍖呭惈鐨勪笉鏄痭ull鐨勫睘鎬у�
	 * @param @param record 瀹炰綋瀵硅薄
	 * @param @param condition 鏉′欢
	 * @param @return
	 * @return int
	 * @throws
	 * @author haipeng
	 * @date 2017骞�鏈�鏃�
	 */
//	int updateByConditionSelective(T record, Object condition);

	/**
	 * @Title: deleteByPrimaryKey
	 * @Description: 鏍规嵁涓婚敭瀛楁杩涜鍒犻櫎锛屾柟娉曞弬鏁板繀椤诲寘鍚畬鏁寸殑涓婚敭灞炴�
	 * @param @param key 涓婚敭鍊�
	 * @param @return
	 * @return int
	 * @throws
	 * @author haipeng
	 * @date 2017骞�鏈�鏃�
	 */
	int deleteByPrimaryKey(Object key);

	/**
	 * @Title: delete
	 * @Description:鏍规嵁瀹炰綋灞炴�浣滀负鏉′欢杩涜鍒犻櫎锛屾煡璇㈡潯浠朵娇鐢ㄧ瓑鍙�
	 * @param @param record
	 * @param @return
	 * @return int
	 * @throws
	 * @author haipeng
	 * @date 2017骞�鏈�鏃�
	 */
	int delete(T record);


}
