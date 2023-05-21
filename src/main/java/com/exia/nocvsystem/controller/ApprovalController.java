package com.exia.nocvsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exia.nocvsystem.dao.UserMapper;
import com.exia.nocvsystem.entity.ApprovalProcess;
import com.exia.nocvsystem.entity.HeSuan;
import com.exia.nocvsystem.entity.Role;
import com.exia.nocvsystem.entity.User;
import com.exia.nocvsystem.enums.ApprovalNodeStatusEnum;
import com.exia.nocvsystem.service.ApprovalService;
import com.exia.nocvsystem.service.RoleService;
import com.exia.nocvsystem.service.UserService;
import com.exia.nocvsystem.vo.ApprovalVo;
import com.exia.nocvsystem.vo.DataView;
import com.exia.nocvsystem.vo.HeSuanVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/4/27 20:28
 */
@Controller
@RequestMapping("/approval")
public class ApprovalController extends BaseController {
    @Autowired
    ApprovalService approvalService;
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;
    @Resource
    private UserMapper userMapper;
    @RequestMapping("/toApproval")
    public String toApproval(){
        return "approval/approval";
    }
    @RequestMapping("/loadAllApproval")
    @ResponseBody
    public DataView loadAllApproval(ApprovalVo approvalVo, HttpSession httpSession){
        User user=(User) httpSession.getAttribute("user");
        if(StringUtils.isNotEmpty(user.getUsername())) {
            Integer uid=user.getId();
            IPage<ApprovalProcess> page = new Page<>(approvalVo.getPage(), approvalVo.getLimit());
            QueryWrapper<ApprovalProcess> queryWrapper = new QueryWrapper();
            addUids(queryWrapper);
            approvalService.page(page, queryWrapper);
            //处理用户名
            List<ApprovalProcess> records=page.getRecords();
            for(ApprovalProcess a:records){
                List<String> usernames= userMapper.queryUidName(a.getUid());
                String username=usernames.get(0);
                a.setUsername(username);
            }
            return new DataView(page.getTotal(), page.getRecords());
        }
        return new DataView();
    }
    @RequestMapping("/addApproval")
    @ResponseBody
    public DataView addApproval(ApprovalProcess approvalProcess,HttpSession httpSession){
        DataView dataView=new DataView();
        try{
            if((userService.getById(approvalProcess.getUid()).equals(null))){
                approvalProcess.setCreateTime(new Date());
                User user = (User) httpSession.getAttribute("user");
                Integer id = user.getId();
                approvalProcess.setUid(id);
                List<Integer> roleList = roleService.queryUserRoleById(id);
                Integer integer = roleList.get(0);
                Role byId = roleService.getById(integer);
                String name = byId.getName();
                if (StringUtils.equals(name, "student")) {
                    approvalProcess.setNodeStatus(ApprovalNodeStatusEnum.APPROVAL_TEACHER_ING.getCode());
                } else if (StringUtils.equals(name, "teacher")) {
                    approvalProcess.setNodeStatus(ApprovalNodeStatusEnum.APPROVAL_COLLEGE_ING.getCode());
                } else if (StringUtils.equals(name, "dean")) {
                    approvalProcess.setNodeStatus(ApprovalNodeStatusEnum.APPROVAL_COLLEGE_PASSED.getCode());
                } else if (StringUtils.equals(name, "admin")) {
                    approvalProcess.setNodeStatus(ApprovalNodeStatusEnum.APPROVAL_COLLEGE_PASSED.getCode());
                } else {
                    approvalProcess.setNodeStatus(ApprovalNodeStatusEnum.APPROVAL_TEACHER_ING.getCode());
                }
                approvalService.save(approvalProcess);
                dataView.setCode(200);
                dataView.setMsg("申请请假成功");
                return dataView;
            }else{
                dataView.setCode(100);
                dataView.setMsg("申请请假失败");
                return dataView;
            }
        }
        catch (Exception e){
                dataView.setCode(100);
                dataView.setMsg("申请请假失败");
                return dataView;
        }
    }
    @RequestMapping("/rejectApproval")
    @ResponseBody
    public DataView rejectApproval(ApprovalProcess approvalProcess,HttpSession session ){
        DataView dataView=new DataView();
        approvalProcess.setUpdateTime(new Date());
        //1.判断是不是审核中的状态
        User user=(User)session.getAttribute("user");
        Integer id= user.getId();
        String username=user.getUsername();
        //2.根据老师和院系进行节点状态改变
        List<Integer> roleList = roleService.queryUserRoleById(id);
        Integer integer = roleList.get(0);
        Role byId = roleService.getById(integer);
        String rolename = byId.getName();
        if(StringUtils.equals(rolename,"student")){
            dataView.setCode(100);
            dataView.setMsg("学生不能进行审批！");
            return dataView;
        }
        //查询节点状态
        ApprovalProcess serviceById=approvalService.getById(approvalProcess.getId());
        String nodeStatus=serviceById.getNodeStatus();
        //判断审批状态
        if((StringUtils.equals(ApprovalNodeStatusEnum.APPROVAL_TEACHER_ING.getCode(),nodeStatus))
                ||(StringUtils.equals(ApprovalNodeStatusEnum.APPROVAL_COLLEGE_ING.getCode(),nodeStatus))){
            if(StringUtils.equals(rolename,"admin")){
                approvalProcess.setNodeStatus(ApprovalNodeStatusEnum.COLLEGE_REJECTED.getCode());
            }
            else if(StringUtils.equals(rolename,"teacher")){
                approvalProcess.setNodeStatus(ApprovalNodeStatusEnum.TEACHER_REJECTED.getCode());
            }else if(StringUtils.equals(rolename,"dean")){
                approvalProcess.setNodeStatus(ApprovalNodeStatusEnum.COLLEGE_REJECTED.getCode());
            }else{
                approvalProcess.setNodeStatus(ApprovalNodeStatusEnum.TEACHER_REJECTED.getCode());
            }

            //修改库
            approvalService.updateById(approvalProcess);
            dataView.setCode(200);
            dataView.setMsg(username+":角色:"+rolename+"驳回成功！");
            return dataView;
        }
        dataView.setCode(100);
        dataView.setMsg(username+":角色:"+rolename+"该角色不能去驳回请假申请！");
        return dataView;
    }
    @RequestMapping("/successApproval")
    @ResponseBody
    public DataView successApproval(ApprovalProcess approvalProcess,HttpSession session){
        DataView dataView=new DataView();
        approvalProcess.setUpdateTime(new Date());
        //1.判断是不是审核中的状态
        User user=(User)session.getAttribute("user");
        Integer id= user.getId();
        String username=user.getUsername();
        //2.根据老师和院系进行节点状态改变
        List<Integer> roleList = roleService.queryUserRoleById(id);
        Integer integer = roleList.get(0);
        Role byId = roleService.getById(integer);
        String rolename = byId.getName();
        if(StringUtils.equals(rolename,"student")){
            dataView.setCode(100);
            dataView.setMsg("学生不能进行审批！");
            return dataView;
        }
        //查询节点状态
        ApprovalProcess serviceById=approvalService.getById(approvalProcess.getId());
        String nodeStatus=serviceById.getNodeStatus();
        //判断审批状态
        if((StringUtils.equals(ApprovalNodeStatusEnum.APPROVAL_TEACHER_ING.getCode(),nodeStatus))
                ||(StringUtils.equals(ApprovalNodeStatusEnum.APPROVAL_COLLEGE_ING.getCode(),nodeStatus))){
            if(StringUtils.equals(rolename,"admin")){
                approvalProcess.setNodeStatus(ApprovalNodeStatusEnum.APPROVAL_COLLEGE_PASSED.getCode());
            }
            else if(StringUtils.equals(rolename,"teacher")){
                approvalProcess.setNodeStatus(ApprovalNodeStatusEnum.APPROVAL_TEACHER_PASSED.getCode());
            }else if(StringUtils.equals(rolename,"dean")){
                approvalProcess.setNodeStatus(ApprovalNodeStatusEnum.APPROVAL_COLLEGE_PASSED.getCode());
            }else{
                approvalProcess.setNodeStatus(ApprovalNodeStatusEnum.APPROVAL_TEACHER_PASSED.getCode());
            }

            //更新库
            approvalService.updateById(approvalProcess);
            dataView.setCode(200);
            dataView.setMsg(username+":角色:"+rolename+"审批成功！");
            return dataView;
        }else if(StringUtils.equals(ApprovalNodeStatusEnum.APPROVAL_TEACHER_PASSED.getCode(),nodeStatus)){
            if(StringUtils.equals(rolename,"admin")||StringUtils.equals(rolename,"dean")){
                approvalProcess.setNodeStatus(ApprovalNodeStatusEnum.APPROVAL_COLLEGE_PASSED.getCode());
            }
            //更新库
            approvalService.updateById(approvalProcess);
            dataView.setCode(200);
            dataView.setMsg(username+":角色:"+rolename+"审批成功！");
            return dataView;
        }
        dataView.setCode(100);
        dataView.setMsg(username+":角色:"+rolename+"该角色不能去审批！");
        return dataView;
    }

}
