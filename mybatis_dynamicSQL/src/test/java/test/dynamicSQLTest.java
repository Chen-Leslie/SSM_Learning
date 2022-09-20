package test;

import com.mybatis.mapper.DynamicSQLMapper;
import com.mybatis.pojo.Emp;
import com.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.sql.Array;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;

/**
 * @author:Chen
 * @Date:2022/9/5
 * @Description:
 */
public class dynamicSQLTest {
    @Test
    public void testGetEmpByCondition(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        DynamicSQLMapper mapper = sqlSession.getMapper(DynamicSQLMapper.class);
        //Emp emp = new Emp(null, "张三", 18, "男");
        Emp emp = new Emp(null, "", null, "");
        List<Emp> emps = mapper.getEmpByCondition(emp);
        emps.forEach(System.out::println);
        sqlSession.close();
    }

    @Test
    public void testGetEmpByChoose(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        DynamicSQLMapper mapper = sqlSession.getMapper(DynamicSQLMapper.class);
        Emp emp = new Emp(null, "张三", 18, "男");
        List<Emp> emps = mapper.getEmpByChoose(emp);
        emps.forEach(System.out::println);
        sqlSession.close();
    }

    @Test
    public void testInsertMoreEmp(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        DynamicSQLMapper mapper = sqlSession.getMapper(DynamicSQLMapper.class);
        Emp emp1 = new Emp(null, "小明1", 18, "男");
        Emp emp2 = new Emp(null, "小明2", 18, "男");
        Emp emp3 = new Emp(null, "小明3", 18, "男");
        List<Emp> emps = Arrays.asList(emp1, emp2, emp3);
        mapper.insertMoreEmp(emps);
        sqlSession.close();
    }

    @Test
    public void testDeleteMoreEmp(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        DynamicSQLMapper mapper = sqlSession.getMapper(DynamicSQLMapper.class);
        Integer[] empIds = new Integer[]{5, 6, 7};
        mapper.deleteMoreEmp(empIds);
        sqlSession.close();
    }
}
