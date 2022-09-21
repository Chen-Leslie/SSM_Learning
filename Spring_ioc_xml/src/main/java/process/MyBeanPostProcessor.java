package process;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-09-21
 */
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    // 在Bean生命周期初始化之前执行
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("MyBeanPostProcessor---->后置处理器postProcessBeforeInitialization");
        return bean;
    }

    @Override
    // 在Bean生命周期初始化之后执行
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("MyBeanPostProcessor---->后置处理器postProcessAfterInitialization");
        return bean;
    }
}
