package factory;

import org.springframework.beans.factory.FactoryBean;
import pojo.User;
import java.lang.Class;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-09-21
 */
public class UserFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return new User();
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}
