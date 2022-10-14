package Test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.SprintLearning.transaction.pojo.Student;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-09-21
 */
public class ScopeTest {
    @Test
    public void testScope(){
        ApplicationContext ioc = new ClassPathXmlApplicationContext("Spring-scope.xml");
        Student student = ioc.getBean(Student.class);
        Student student1 = ioc.getBean(Student.class);
        System.out.println(student.equals(student1));
    }
}
