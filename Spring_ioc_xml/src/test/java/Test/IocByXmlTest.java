package Test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.SprintLearning.transaction.pojo.Student;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-09-20
 */
public class IocByXmlTest {

    @Test
    public void testDI(){
        ApplicationContext ioc = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        Student studentTwo = ioc.getBean("studentThree", Student.class);
        System.out.println(studentTwo);
    }

    @Test
    public void testIOC(){
        ApplicationContext ioc = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        Student studentOne = ioc.getBean(Student.class);
        System.out.println(studentOne);
    }
}
