package com.coco.controller;

import com.coco.mapper.ContactMapper;
import com.coco.pojo.Contact;
import com.coco.utils.ApiResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 联系人管理控制器
 */
@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactMapper contactMapper;

    /**
     * 保存或更新联系人信息
     *
     * @param contact 联系人对象
     * @return API响应
     */
    @PostMapping("/saveOrUpdate")
    public ApiResponse<Void> saveOrUpdate(@RequestBody Contact contact) {
        if (contact.getId() == null) {
            contactMapper.insert(contact);
        } else {
            contactMapper.updateById(contact);
        }
        return ApiResponse.success("操作成功", null);
    }

    /**
     * 删除指定ID的联系人
     *
     * @param id 联系人ID
     * @return API响应
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        contactMapper.deleteById(id);
        return ApiResponse.success("删除成功", null);
    }

    /**
     * 批量删除联系人
     *
     * @param ids 联系人ID列表
     * @return API响应
     */
    @DeleteMapping("/batch")
    public ApiResponse<Void> deleteBatch(@RequestBody List<Integer> ids) {
        for (Integer id : ids) {
            contactMapper.deleteById(id);
        }
        return ApiResponse.success("批量删除成功", null);
    }

    /**
     * 查询所有联系人信息
     *
     * @return API响应
     */
    @GetMapping
    public ApiResponse<List<Contact>> findAll() {
        List<Contact> contacts = contactMapper.selectAll();
        return ApiResponse.success(contacts);
    }
    /**
     * 查询个人的所有联系人或收藏的联系人
     *
     * @param userId 用户ID
     * @param collectStatus 收藏状态（可选，1-收藏，0-未收藏）
     * @return API响应
     */
    @GetMapping("/user/{userId}")
    public ApiResponse<List<Contact>> findUserContacts(@PathVariable Integer userId,
                                                       @RequestParam(required = false) Integer collectStatus) {
        List<Contact> contacts = contactMapper.selectByUserId(userId, collectStatus);
        return ApiResponse.success(contacts);
    }
    /**
     * 查询指定ID的联系人信息
     *
     * @param id 联系人ID
     * @return API响应
     */
    @GetMapping("/{id}")
    public ApiResponse<Contact> findOne(@PathVariable Integer id) {
        Contact contact = contactMapper.selectById(id);
        return ApiResponse.success(contact);
    }

    /**
     * 分页查询联系人信息
     *
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @param contact  查询条件
     * @return API响应
     */
    @PostMapping("/page")
    public ApiResponse<PageInfo<Contact>> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                                   @RequestBody(required = false) Contact contact) {
        PageHelper.startPage(pageNum, pageSize);
        List<Contact> contacts = contactMapper.selectByEntity(contact);
        PageInfo<Contact> pageInfo = new PageInfo<>(contacts);
        return ApiResponse.success(pageInfo);
    }

    /**
     * 更新联系人收藏状态
     *
     * @param id            联系人ID
     * @param collectStatus 新状态
     * @return API响应
     */
    @PutMapping("/{id}/collectStatus")
    public ApiResponse<Void> updateCollectStatus(@PathVariable Integer id, @RequestParam Integer collectStatus) {
        contactMapper.updateCollectStatus(id, collectStatus);
        return ApiResponse.success("状态更新成功", null);
    }


    /**
     * 根据用户ID统计总联系人数量以及收藏量
     * @param userId 用户ID
     * @return API响应
     */
    @GetMapping("/count/{userId}")
    public ApiResponse<Map<String, Integer>> countContactsByUserId(@PathVariable Integer userId) {
        Map<String, Integer> result = contactMapper.countContactsByUserId(userId);
        return ApiResponse.success(result);
    }
}