package com.coco.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 联系人实体类
 * 对应数据库表 tb_contact
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 用户ID（某个用户联系人）
     */
    private Integer userId;

    /**
     * 联系人姓名
     */
    private String name;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 生日
     */

    private LocalDateTime birthday;

    /**
     * 公司名称
     */
    private String company;

    /**
     * 职位
     */
    private String position;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 状态：1-收藏联系人  0未收藏
     */
    private Integer collectStatus;

    /**
     * 创建时间
     */
    private LocalDateTime created;

    /**
     * 更新时间
     */
    private LocalDateTime updated;
}