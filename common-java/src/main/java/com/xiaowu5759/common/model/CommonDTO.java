package com.xiaowu5759.common.model;

/**
 * 这里的维度数据：
 * 不能直接返回到前端，前端要处理的都是key-value，封装的一个json对象
 *
 * 三维度数据，数据库查询结果封装，可视化结果非常有用
 * Map<String, Object> 是封装批量二维数据
 * List<CommonDTO> 是封装批量三维数据
 * List<Map<String, Object>> 是可以封装任意维度数据，但是还是推荐封装对象，方便存取
 *
 * select date(create_time) date
 * group by date
 * count-sql的标准写法
 *  SELECT  #{carName} as keyDTO, count(*) as valueDTO, date_format(create_time, '%Y-%m-%d') as tag FROM `buried_point_record`
 *         <where>
 *             <if test="startTime != null and startTime != ''">
 *                 and date_format(create_time,'%Y-%m-%d') &gt;= date_format(#{startTime},'%Y-%m-%d')
 *             </if>
 *             <if test="endTime != null and endTime != ''">
 *                 and date_format(create_time, '%Y-%m-%d' ) &lt;= date_format(#{endTime}, '%Y-%m-%d' )
 *             </if>
 *             <if test="vmodelName != null and vmodelName != ''">
 *                 and vmodel like concat(#{vmodelName},'%')
 *             </if>
 *         </where>
 *         group by tag
 *
 * @author xiaowu
 * @date 2020/7/21 8:57
 */
public class CommonDTO {

    /** key值 */
    private String key;

    /** value值 */
    private Object value;

    /** 标签，词条管理 */
    private String tag;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
