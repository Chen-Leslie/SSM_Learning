package com.mybatis.mapper;

import com.mybatis.pojo.Dept;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Property;

/**
 * @author:Chen
 * @Date:2022/9/5
 * @Description:
 */
public interface DeptMapper {
    Dept getDeptByDeptId(@Param("deptId") Integer deptId);

    //查询部门以及部门中的员工(一对多)
    Dept getDeptAndEmpByDeptId(@Param("deptId") Integer deptId);

    //分步查询部门以及部门中的员工(一对多)
    Dept getDeptAndEmpByStepByDeptId(@Param("deptId") Integer deptId);
}
