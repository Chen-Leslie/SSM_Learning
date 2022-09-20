package test;

import com.mybatis.mapper.DeptMapper;
import com.mybatis.mapper.EmpMapper;
import com.mybatis.pojo.Dept;
import com.mybatis.pojo.Emp;
import com.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

/**
 * @author:Chen
 * @Date:2022/9/5
 * @Description:
 */
public class ResultMapTest {
    @Test
    public void testGetEmpByEmpId(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        Emp emp = mapper.getEmpByEmpId(1);
        System.out.println(emp);
        sqlSession.close();
    }

    @Test
    public void testGetEmpAndDeptByEmpId(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        Emp emp = mapper.getEmpAndDeptByEmpId(1);
        System.out.println(emp);
        sqlSession.close();
    }

    @Test
    public void testGetEmpAndDeptByStepByEmpId(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        Emp emp = mapper.getEmpAndDeptByStepByEmpId(1);
        System.out.println(emp);
        sqlSession.close();
    }

    @Test
    public void testGetDeptAndEmpByEmpId(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        DeptMapper mapper = sqlSession.getMapper(DeptMapper.class);
        Dept dept = mapper.getDeptAndEmpByDeptId(1);
        System.out.println(dept);
        sqlSession.close();
    }

    @Test
    public void testGetDeptAndEmpByStepByEmpId(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        DeptMapper mapper = sqlSession.getMapper(DeptMapper.class);
        Dept dept = mapper.getDeptAndEmpByStepByDeptId(1);
        System.out.println(dept);
        sqlSession.close();
    }
}
