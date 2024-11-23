package com.coco.mapper;

import com.coco.pojo.Contact;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.Map;

/**
 * 联系人Mapper接口
 */
@Mapper
public interface ContactMapper {

    /**
     * 插入新的联系人记录
     * @param contact 联系人对象
     * @return 受影响的行数
     */
    @Insert("INSERT INTO tb_contact(user_id, name, avatar, mobile, email, address, birthday, company, position, remark, collect_status) " +
            "VALUES(#{userId}, #{name}, #{avatar}, #{mobile}, #{email}, #{address}, #{birthday}, #{company}, #{position}, #{remark}, #{collectStatus})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Contact contact);

    /**
     * 根据ID更新联系人信息
     * @param contact 联系人对象
     * @return 受影响的行数
     */
    @Update("UPDATE tb_contact SET user_id=#{userId}, name=#{name}, avatar=#{avatar}, mobile=#{mobile}, email=#{email}, " +
            "address=#{address}, birthday=#{birthday}, company=#{company}, position=#{position}, remark=#{remark}, " +
            "collect_status=#{collectStatus} WHERE id=#{id}")
    int updateById(Contact contact);

    /**
     * 根据ID删除联系人记录
     * @param id 联系人ID
     * @return 受影响的行数
     */
    @Delete("DELETE FROM tb_contact WHERE id=#{id}")
    int deleteById(Integer id);

    /**
     * 根据ID查询联系人信息
     * @param id 联系人ID
     * @return 联系人对象
     */
    @Select("SELECT * FROM tb_contact WHERE id=#{id}")
    @Results(id = "contactMap", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "avatar", property = "avatar", jdbcType = JdbcType.VARCHAR),
            @Result(column = "mobile", property = "mobile", jdbcType = JdbcType.VARCHAR),
            @Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),
            @Result(column = "address", property = "address", jdbcType = JdbcType.VARCHAR),
            @Result(column = "birthday", property = "birthday", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "company", property = "company", jdbcType = JdbcType.VARCHAR),
            @Result(column = "position", property = "position", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "collect_status", property = "collectStatus", jdbcType = JdbcType.INTEGER),
            @Result(column = "created", property = "created", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "updated", property = "updated", jdbcType = JdbcType.TIMESTAMP)
    })
    Contact selectById(Integer id);

    /**
     * 查询所有联系人信息
     * @return 联系人对象列表
     */
    @Select("SELECT * FROM tb_contact")
    @ResultMap("contactMap")
    List<Contact> selectAll();

    /**
     * 根据条件查询联系人信息
     * @param contact 查询条件
     * @return 联系人对象列表
     */
    @Select("<script>" +
            "SELECT * FROM tb_contact" +
            "<where>" +
            "    <if test='userId != null'>" +
            "        AND user_id = #{userId}" +
            "    </if>" +
            "    <if test='name != null and name != \"\"'>" +
            "        AND name LIKE CONCAT('%', REPLACE(#{name}, ' ', ''), '%')" +
            "    </if>" +
            "    <if test='mobile != null and mobile != \"\"'>" +
            "        OR mobile LIKE CONCAT('%', REPLACE(#{mobile}, ' ', ''), '%')" +
            "    </if>" +
            "    <if test='email != null and email != \"\"'>" +
            "        OR email LIKE CONCAT('%', REPLACE(#{email}, ' ', ''), '%')" +
            "    </if>" +
            "</where>" +
            "</script>")
    @ResultMap("contactMap")
    List<Contact> selectByEntity(Contact contact);

    /**
     * 更新联系人收藏状态
     * @param id 联系人ID
     * @param collectStatus 新状态
     * @return 受影响的行数
     */
    @Update("UPDATE tb_contact SET collect_status = #{collectStatus} WHERE id = #{id}")
    int updateCollectStatus(@Param("id") Integer id, @Param("collectStatus") Integer collectStatus);
    /**
     * 根据用户ID查询联系人信息
     * @param userId 用户ID
     * @param collectStatus 收藏状态（可选）
     * @return 联系人对象列表
     */
    @Select("<script>" +
            "SELECT * FROM tb_contact" +
            " WHERE user_id = #{userId}" +
            "<if test='collectStatus != null'>" +
            " AND collect_status = #{collectStatus}" +
            "</if>" +
            "</script>")
    @ResultMap("contactMap")
    List<Contact> selectByUserId(@Param("userId") Integer userId, @Param("collectStatus") Integer collectStatus);

    /**
     * 根据用户ID统计总联系人数量以及收藏量
     * @param userId 用户ID
     * @return 包含总联系人数量和收藏量的Map
     */
    @Select("SELECT COUNT(*) AS totalContacts, SUM(CASE WHEN collect_status = 1 THEN 1 ELSE 0 END) AS favoriteContacts " +
            "FROM tb_contact WHERE user_id = #{userId}")
    Map<String, Integer> countContactsByUserId(@Param("userId") Integer userId);

}