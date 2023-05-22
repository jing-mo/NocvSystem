package com.exia.nocvsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exia.nocvsystem.entity.Institude;
import com.exia.nocvsystem.entity.Class;
import com.exia.nocvsystem.entity.User;
import com.exia.nocvsystem.realm.PasswordHelper;
import com.exia.nocvsystem.service.*;
import com.exia.nocvsystem.vo.DataView;
import com.exia.nocvsystem.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/4/6 14:09
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
    @Autowired
    private UserService userService;
    @Autowired
    private ClassService classService;
    @Autowired
    private InstitudeService institudeService;
    @Autowired
    private RoleService roleService;

    @RequestMapping("/toUser")
    public String toUser() {
        return "user/user";
    }

    @RequestMapping("/toChangePassword")
    public String toChangePassword() {
        return "user/changepassword";
    }

    @RequestMapping("/toUserInfo")
    public String toUserInfo(Model model) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        model.addAttribute("user",userService.getById(user.getId()));
        return "user/userinfo";
    }

    /**
     * 分页查询所有用户数据带有查询条件
     *
     * @param userVo
     * @return
     */
    @RequestMapping("/loadAllUser")
    @ResponseBody
    public DataView getAllUser(UserVo userVo) {
        //1.第一种方法
//        if(StringUtils.isNotBlank(userVo.getUsername())) {
//            userService.loadUserByLeftJoin(userVo.getUsername(),userVo.getPage(),userVo.getLimit());
//        }
        //2.mapper
        //@Select("select a.username,b.name form user as a where a.username=#{} Left join class as b on a.class_id=b.id limit #{},#{};")

        //2.第二种方法

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        IPage<User> page = new Page<>(userVo.getPage(), userVo.getLimit());
        queryWrapper.like(StringUtils.isNotBlank(userVo.getUsername()), "username", userVo.getUsername());
        queryWrapper.eq(StringUtils.isNotBlank(userVo.getPhone()), "phone", userVo.getPage());
        IPage<User> iPage = userService.page(page, queryWrapper);
        for (User user : iPage.getRecords()) {
            if (user.getClassId() != null) {
                Class theClass = classService.getById(user.getClassId());
                user.setClassName(theClass.getName());
            }
            //为学院名字进行赋值
            if (user.getInstitudeId() != null) {

                Institude institude = institudeService.getById(user.getInstitudeId());

                user.setInstitudeName(institude.getName());
            }
            //为老师名字进行赋值
            if (user.getInstitudeId() != null) {
                String teacher = userService.findTeacher(user.getTeacherId());
                user.setTeacherName(teacher);
            }
        }

        return new DataView(iPage.getTotal(), iPage.getRecords());
    }

    /**
     * 新增用户
     */
    @RequestMapping("/addUser")
    @ResponseBody
    public DataView addUser(User user) {
        userService.autoIncrement();
        DataView dataView = new DataView();
            if(!userService.isExistsUser(user.getCardId())){
                String password=user.getPassword();
                String salt= PasswordHelper.createSalt();
                user.setSalt(salt);
                user.setImg("/images/default.jpg");
                String newPassword=PasswordHelper.createCredentials(password,salt);
                if(PasswordHelper.checkCredentials(password,salt,newPassword)){
                    user.setPassword(newPassword);
                }else{
                    dataView.setMsg("用户添加失败");
                    dataView.setCode(100);
                    return dataView;
                }
                if(!userService.isExistsCardId(user.getTeacherId())){
                    dataView.setMsg("用户添加失败");
                    dataView.setCode(100);
                    return dataView;
                }
                if(!userService.isTrueInstitude(user.getClassId(),user.getInstitudeId())){
                    dataView.setMsg("班级与院级不匹配");
                    dataView.setCode(100);
                    return dataView;
                }
                userService.save(user);
                dataView.setMsg("用户添加成功!");
                dataView.setCode(200);
                return dataView;
            }else {
                dataView.setMsg("用户添加失败!");
                dataView.setCode(100);
                return dataView;
            }

    }

    /**
     * 初始化下拉列表数据（班级）
     */
    @RequestMapping("/listAllClass")
    @ResponseBody
    public List<Class> listAllClass() {
        List<Class> list = classService.list();
        return list;
    }

    /**
     * 初始化下拉列表数据（学院）
     */
    @RequestMapping("/listAllInstitude")
    @ResponseBody
    public List<Institude> listAllInstitude() {
        List<Institude> list = institudeService.list();
        return list;
    }

    @RequestMapping("/updateUser")
    @ResponseBody
    public DataView updateUser(User user) {
        DataView dataView = new DataView();
        try {
            String password = user.getPassword();
            String salt = PasswordHelper.createSalt();
            user.setSalt(salt);
            String newPassword = PasswordHelper.createCredentials(password, salt);
            if (PasswordHelper.checkCredentials(password, salt, newPassword)) {
                user.setPassword(newPassword);
            } else {
                dataView.setMsg("用户修改失败");
                dataView.setCode(100);
                return dataView;
            }
            if (!userService.isTrueInstitude(user.getClassId(), user.getInstitudeId())) {
                dataView.setMsg("班级与院级不匹配");
                dataView.setCode(100);
                return dataView;
            }
            if(!userService.isExistsCardId(user.getTeacherId())){
                dataView.setMsg("该上级不存在！");
                dataView.setCode(100);
                return dataView;
            }
            userService.updateById(user);
            dataView.setMsg("用户修改成功");
            dataView.setCode(200);
            return dataView;
        }catch (Exception e){
            dataView.setMsg("用户修改失败1");
            dataView.setCode(200);
            return dataView;
        }
    }

    @RequestMapping("/deleteUser/{id}")
    @ResponseBody
    public DataView deleteUser(@PathVariable("id") Integer id) {
        userService.autoIncrement();
        userService.removeById(id);
        DataView dataView = new DataView();
        dataView.setMsg("用户删除成功");
        dataView.setCode(200);
        return dataView;
    }

    /**
     * 重置密码
     */
    @RequestMapping("/resetPwd/{id}")
    @ResponseBody
    public DataView resetPwd(@PathVariable("id") Integer id) {
        User user = new User();
        user.setId(id);
        String salt= PasswordHelper.createSalt();
        user.setSalt(salt);
        String newPassword=PasswordHelper.createCredentials("123456",salt);
        if(PasswordHelper.checkCredentials("123456",salt,newPassword)){
            user.setPassword(newPassword);
        }else{
            DataView dataView = new DataView();
            dataView.setMsg("用户添加失败");
            dataView.setCode(300);
            return dataView;
        }
        userService.updateById(user);
        DataView dataView = new DataView();
        dataView.setMsg("用户重置密码成功");
        dataView.setCode(200);
        return dataView;
    }

    /**
     * 点击分配用户角色，初始化用户的角色
     * 打开分配角色的弹出层
     */
    @RequestMapping("/initRoleByUserId")
    @ResponseBody
    public DataView initRoleByUserId(Integer id) {
        //1.查询角色
        List<Map<String, Object>> listMaps = roleService.listMaps();
        //2.查询当前用户
        List<Integer> currentUserRoleIds = roleService.queryUserRoleById(id);
        //前端变为选中状态
        for (Map<String, Object> map : listMaps) {
            Boolean LAY_CHECKED = false;
            Integer roleId = (Integer) map.get("id");
            for (Integer rid : currentUserRoleIds) {
                if (rid.equals(roleId)) {
                    LAY_CHECKED = true;
                    break;
                }
            }
            map.put("LAY_CHECKED", LAY_CHECKED);

        }
        return new DataView(Long.valueOf(listMaps.size()), listMaps);
    }

    @RequestMapping("/saveUserRole")
    @ResponseBody
    public DataView saveUserRole(Integer uid, Integer[] ids) {
        DataView dataView = new DataView();
        try {
            boolean hasDean=false;
            for(int i=0;i<ids.length;i++)
                if(ids[i]==4){
                    hasDean=true;
                    break;
                }
            if(userService.isExistsDean(uid)&&hasDean==true) {
                dataView.setMsg("该院系已经有一名院长！");
                dataView.setCode(100);
                return dataView;
            }
            userService.saveUserRole(uid, ids);
            dataView.setMsg("用户分配角色成功");
            dataView.setCode(200);
            return dataView;
        }catch (Exception e){
            dataView.setMsg("用户分配角色失败！");
            dataView.setCode(100);
            return dataView;
        }
    }

    /**
     * 修改密码
     */
    @RequestMapping("/changePassword")
    @ResponseBody
    public DataView changePassword(User user, String newPwdOne, String newPwdTwo) {
        User byId = userService.getById(user.getId());
        if (StringUtils.equals(byId.getPassword(), user.getPassword())
                && StringUtils.equals(newPwdOne, newPwdTwo)
        ) {
            user.setPassword(newPwdOne);
            userService.updateById(user);
            DataView dataView = new DataView();
            dataView.setMsg("用户重置密码成功");
            dataView.setCode(200);
            return dataView;
        }
        DataView dataView = new DataView();
        dataView.setMsg("用户重置密码失败，可能旧密码输入错误或两次密码输入不一致");
        dataView.setCode(100);
        return dataView;
    }

    @RequestMapping(value="/uploadFile")
    @ResponseBody
    public  DataView uploadFile(@RequestParam("imgPath") MultipartFile photo,HttpSession session) {

        String date = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        // 图片存放位置
        String imagePath = "E:/java/NocvSystem/src/main/resources/static/images/userfaces/"+"time"+date+"name";
        DataView dataView = new DataView();
        System.out.println(photo.getSize());
        if (photo.getSize() > 1048576) {
            dataView.setMsg("请上传小于1M的头像");
            dataView.setCode(100);
            return dataView;
        } else {
            try (InputStream input = photo.getInputStream()) {
                File path = new File("E:/java/NocvSystem/src/main/resources/static/images/userfaces/");
                if (!path.exists()) {
                    path.mkdir();
                }
                //获取源照片名
                String originalFilename = photo.getOriginalFilename();
                //获取照片后缀名
                String suffixName = originalFilename.substring(originalFilename.lastIndexOf('.'));
                //使用UUID
                String fileName = UUID.randomUUID().toString() + suffixName;
                //保存照片到磁盘
                photo.transferTo(new File("E:/java/NocvSystem/src/main/resources/static/images/userfaces/" + fileName));

                // 数据库存放图片信息 path
                String url = "/images/userfaces/"+fileName;
                User user = (User) session.getAttribute("user");
                user.setImg(url);
                userService.updateById(user);
                dataView.setMsg("上传成功!");
                dataView.setCode(200);
                return dataView;
            } catch (Exception e) {
                e.printStackTrace();
                dataView.setMsg("重复上传头像!");
                dataView.setCode(100);
                return dataView;
            }
        }
    }
}

