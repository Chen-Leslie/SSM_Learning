package com.mybatis.mapper;

import com.mybatis.pojo.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author:Chen
 * @Date:2022/9/5
 * @Description:
 */
public interface EmpMapper {
    Emp getEmpByEmpId(@Param("empId") Integer empId);

    Emp getEmpAndDeptByEmpId(@Param("empId") Integer empId);

    Emp getEmpAndDeptByStepByEmpId(@Param("empId") Integer empId);

    List<Emp> getDeptAndEmpByStepByDeptId(@Param("deptId") Integer deptId);
}
