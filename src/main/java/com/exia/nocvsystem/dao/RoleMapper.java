package com.exia.nocvsystem.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exia.nocvsystem.entity.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.ognl.DynamicSubscript;
import org.springframework.stereotype.Service;

import java.util.List;

import static net.sf.jsqlparser.parser.feature.Feature.select;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/3/16 10:24
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    @Select("select mid from role_menu where rid=#{roleId}")
    List<Integer> queryMidByRid(Integer roleId);
    //1.分配菜单栏之前删除所有的数据
    @Delete("delete from role_menu where rid = #{rid}")
    void deleteRoleByRid(Integer rid);
    //2.保存分配角色与菜单的关系
    @Insert("insert into role_menu(rid,mid) values (#{rid},#{mid})")
    void saveRoleMenu(Integer rid, Integer mid);
    @Select("select rid from user_role where uid = #{id}")
    List<Integer> queryUserRoleById(Integer id);
    @Insert("insert into user_role(uid,rid) values (#{uid},#{rid})")
    void saveUserRole(Integer uid, Integer rid);

    @Delete("delete from user_role where uid=#{uid}")
    void deleteRoleUserByUid(Integer uid);
    @Select("select mid from role_menu where rid = #{roleId}")
    List<Integer> queryAllPermissionByRid(Integer roleId);

    @Select("select a.data_authority from role a,user_role b where a.id = b.rid and b.uid=#{uid}")
    Integer queryDataAuthority(Integer uid);


}
