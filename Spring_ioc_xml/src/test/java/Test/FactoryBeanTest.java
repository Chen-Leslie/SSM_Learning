package Test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pojo.User;
/**
 * @author cbr
 * @version 1.0
 * @date 2022-09-21
 */
public class FactoryBeanTest {

    @Test
    public void testFactoryBean(){
        ApplicationContext ioc = new ClassPathXmlApplicationContext("Spring-factoryBean.xml");
        User user = ioc.getBean(User.class);
        System.out.println(user);
    }
}
