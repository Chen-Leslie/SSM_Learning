package Test;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pojo.User;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-09-21
 */
public class LifeCycleTest {
    @Test
    public void testLife(){
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("spring-lifecycle.xml");
        User bean = ac.getBean(User.class);
        System.out.println(bean);
        System.out.println("生命周期：4、通过IOC容器获取bean并使用");
        ac.close();
    }
}
