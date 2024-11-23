package com.coco.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.coco.mapper.ContactMapper;
import com.coco.pojo.Contact;
import com.coco.utils.ApiResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    /**
     * 导出个人的联系人列表
     *
     * @param userId   用户ID
     * @param response HTTP响应
     */
    @GetMapping("/export/{userId}")
    public void exportContacts(@PathVariable Integer userId, HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<Contact> list = contactMapper.selectByUserId(userId, null);

        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        // 自定义标题别名
        writer.addHeaderAlias("id", "ID");
        writer.addHeaderAlias("userId", "用户ID");
        writer.addHeaderAlias("name", "姓名");
        writer.addHeaderAlias("avatar", "头像");
        writer.addHeaderAlias("mobile", "手机号码");
        writer.addHeaderAlias("email", "电子邮箱");
        writer.addHeaderAlias("address", "联系地址");
        writer.addHeaderAlias("birthday", "生日");
        writer.addHeaderAlias("company", "公司名称");
        writer.addHeaderAlias("position", "职位");
        writer.addHeaderAlias("remark", "备注信息");
        writer.addHeaderAlias("collectStatus", "收藏状态");
        writer.addHeaderAlias("created", "创建时间");
        writer.addHeaderAlias("updated", "更新时间");

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("联系人信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }

    /**
     * excel 导入
     *
     * @param file 导入的文件
     * @return API响应
     */
    @PostMapping("/import")
    public ApiResponse<Void> importContacts(@RequestParam("file") MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 方式2：忽略表头的中文，直接读取表的内容
        List<List<Object>> list = reader.read(1);
        List<Contact> contacts = CollUtil.newArrayList();
        for (List<Object> row : list) {
            Contact contact = new Contact();
            contact.setId(Integer.parseInt(row.get(0).toString()));
            contact.setUserId(Integer.parseInt(row.get(1).toString()));
            contact.setName(row.get(2).toString());
            contact.setAvatar(row.get(3).toString());
            contact.setMobile(row.get(4).toString());
            contact.setEmail(row.get(5).toString());
            contact.setAddress(row.get(6).toString());
            contact.setBirthday(LocalDateTime.parse(row.get(7).toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            contact.setCompany(row.get(8).toString());
            contact.setPosition(row.get(9).toString());
            contact.setRemark(row.get(10).toString());
            contact.setCollectStatus(Integer.parseInt(row.get(11).toString()));
            contact.setCreated(LocalDateTime.parse(row.get(12).toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            contact.setUpdated(LocalDateTime.parse(row.get(13).toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            contacts.add(contact);
        }

        for (Contact contact : contacts) {
            contactMapper.insert(contact);
        }
        return ApiResponse.success("导入成功", null);
    }
}