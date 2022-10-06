# Spring Framework学习笔记
## 1. Spring Framework简介：
### 1.1 Spring Framework特性
  - 非侵入式
  - 控制反转 IOC
  - 面向切面编程 AOP
  - 容器
  - 组件化
  - 声明式
  - 一站式
### 1.2 SpringFramework五大功能模块
  

## 2.IOC
### 2.1 IOC容器
#### 2.1.1 IOC思想
  - 传统获取资源方式
  - 反转控制方式获取资源
  - DI(Dependency Injection 依赖注入)
    - DI可以理解成IOC的一种具体实现，IOC是一种反转控制的思想
#### 2.1.2 IOC容器在Spring中的实现
  - Spring提供了IOC容器的两种实现方式：
    - BeanFactory：基本实现，不提供给开发人员使用
    - ApplicationContext：BeanFactory的子接口，它的主要实现类有
      - ClassPathXmlApplicationContext：通过读取类路径下的Xml格式的配置文件来创建IOC容器对象
      - FileSystemXmlApplicationContext：通过文件系统路径读取Xml格式的配置文件创建IOC容器对象
      - ConfigurableApplicationContext：ApplicationContext的子接口，包括一些扩展方法refresh与close，让ApplicationContext具有启动、关闭、刷新上下文的能力
      - WebApplicationContext：专门为Web应用准备，基于Web环境创建IOC容器对象，并将对象引入存入ServletContext域中
### 2.2 基于XML管理Bean
#### 2.2.1 利用Spring配置文件(xml文件)为IOC容器添加组件bean
  - 利用bean标签
    ```xml
    <!--
        bean:配置一个bean对象，将对象交给IOC容器管理
        属性：
            id:bean的唯一标识，不能重复
            class:bean对象所属的类型(全类名)
    -->
    <bean id="helloworld" class="com.springLearning.HelloWorld"></bean>
    ```
  - Spring底层默认使用反射技术调用组件类的无参构造器来创建组件对象，需要事先定义无参构造器。
#### 2.2.2 获取Bean的三种方式
  - 根据id获取
    ```java
    Student student = (Student) ioc.getBean("studentOne");
    ```
  - 根据类型获取
    ```java
    Student student = ioc.getBean(Student.class);
    ```
    注意： 
    - 根据类型获取bean时，要求IOC容器中有且仅有一个此类的bean。
    - 若没有任何一个类型匹配的对象，则抛出异常NoSuchBeanDefinitionException
    - 若有多个类型匹配的对象，则抛出异常NoUniqueBeanDefinitionException
  - 根据id与类型获取
    ```java
    Student student = ioc.getBean("studentOne", Student.class);
    ```
    
  - 问题
    - 如果组件类实现了接口，根据接口类型可以获取 bean 吗?
      <p>可以，前提是bean唯一
    - 如果一个接口有多个实现类，这些实现类都配置了bean，根据接口类型可以获取 bean 吗?
      <p>不可以，bean不唯一
  - 结论
    - 根据类型来获取bean时，在满足bean唯一性的前提下，其实只是看：『对象 instanceof 指定的类
      型』的返回结果，只要返回的是true就可以认定为和类型匹配，能够获取到。
    - 即，bean的类型、bean的父类、bean实现的接口类都可以获取bean
#### 2.2.3 依赖注入(Dependency Injection)——为类中的属性赋值
  - setter注入
    - 利用bean标签下的property子标签
    ```xml
    <!--
        property:通过成员变量的set方法进行赋值
        name:设置需要赋值的属性(和set方法有关)
        value:设置为属性所赋的值
    -->
    <bean id="studentTwo" class="pojo.Student">
        <property name="sId" value="1001"></property>
        <property name="age" value="23"></property>
        <property name="sName" value="zhangsan"></property>
        <property name="gender" value="男"></property>
    </bean>
    ```
  - 构造器注入
    - 利用bean标签下的 constructor-arg 子标签
    ```xml
        <constructor-arg value="1002"></constructor-arg>
        <constructor-arg value="lisi"></constructor-arg>
        <constructor-arg value="24"></constructor-arg>
        <constructor-arg value="女"></constructor-arg>
    ```
  - 特殊值处理
    - 字面量赋值
    ```xml
    <property name="" value=""></property>
    ```
    - null值
    ```xml
    <!--注意：<property name="" value="null"></property>表示赋值为字符串"null"-->
    <property name="">
        <null/>
    </property>
    ```
    - xml实体
    ```xml
    <!--注意：小于号在xml文档中用来定义标签的开始，不能随便用-->
    <!--解决方案一：使用xml实体代替-->
    <property name="" value="a &lt; b"/>
    ```
    - CDATA节
    ```xml
    <!--解决方案二：使用CDATA节代替-->
    <!--CDATA中的C代表Character，表示文本，xml会原样解析-->
    <property name="">
        <value><![CDATA[a<b]]></value>
    </property>
    ```
  - 为类属性赋值
    - 利用 property 标签中的 ref 属性，ref属性的作用是引用IOC容器中的bean的id
    ```xml
      <bean id="studentFour" class="pojo.Student">
        <property name="cls" ref="classOne"></property>
      </bean>
      <bean id="classOne" class="pojo.Class">
        <property name="cId" value="1"></property>
        <property name="cName" value="1班"></property>
      </bean>
    ```
    - 级联方式，但是需要保证提前为class属性类赋值或者实例化
    ```xml
    <bean id="studentFive" class="pojo.Student">
        <property name="cls" ref="classOne"></property>
        <property name="cls.cId" value="2"></property>
        <property name="cls.cName" value="2班"></property>
    </bean>
    ```
    - 内部bean
    ```xml
    <bean id="studentSix" class="pojo.Student">
        <property name="cls">
            <bean id="classInner" class="pojo.Class">
                <property name="cId" value="3"></property>
                <property name="cName" value="3班"></property>
            </bean>
        </property>
    </bean>
    ```
  - 为数组属性赋值
    - 利用property标签下的array子标签，array内容如果是字面量则直接用value，如果是类则可以用ref
    ```xml
    <bean id="studentSix" class="pojo.Student">
        <property name="hobbies">
            <array>
                <value>抽烟</value>
                <value>喝酒</value>
                <value>烫头</value>
            </array>
        </property>
    </bean>
    ```
  - 为列表属性赋值
    - 利用property标签下的list子标签，list内容如果是字面量则直接用value，如果是类则可以用ref
    ```xml
    <bean id="classOne" class="pojo.Class">
        <property name="students">
            <list>
                <ref bean="studentOne"></ref>
                <ref bean="studentTwo"></ref>
                <ref bean="studentThree"></ref>
            </list>
        </property>
    </bean>
    ```
    - util:list：配置一个集合类型的bean，但是需要使用util的约束
    ```xml
    <util:list id="studentList">
        <ref bean="studentOne"></ref>
        <ref bean="studentTwo"></ref>
        <ref bean="studentThree"></ref>
    </util:list>
    <bean id="classOne" class="pojo.Class">
        <property name="students" ref="studentList"></property>
    </bean>
    ```
  - 为Map集合属性赋值
    - 利用property标签下面的Map子标签，使用entry子标签表示一个键值对
    ```xml
    <bean id="teacherOne" class="pojo.Teacher">
        <property name="tId" value="1"></property>
        <property name="tName" value="t1"></property>
    </bean>
    <bean id="teacherTwo" class="pojo.Teacher">
        <property name="tId" value="2"></property>
        <property name="tName" value="t2"></property>
    </bean>
    <bean id="studentFour" class="pojo.Student">
        <property name="teacherMap">
            <map>
                <entry key="1" value-ref="teacherOne"></entry>
                <entry key="2" value-ref="teacherTwo"></entry>
            </map>
        </property>
    </bean>
    ```
    - 利用util:map
    ```xml
    <bean id="studentFour" class="pojo.Student">
        <property name="teacherMap" ref="TeacherMap"></property>
    </bean>
    <util:map id="TeacherMap">
        <entry key="1" value-ref="teacherOne"></entry>
        <entry key="2" value-ref="teacherTwo"></entry>
    </util:map>
    ```
  - p命名空间
      ```xml
      <bean id="studentSeven" class="pojo.Student"
            p:sId="1006"
            p:age="18">
      </bean>
      ```
#### 2.2.4 管理数据源与引入外部属性文件
  - 通过xml文件配置(类似配置bean)
  ```xml
  <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
    <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"></property>
    <property name="url" value="jdbc:mysql://localhost:3306/ssm?serverTimezone=UTC"></property>
    <property name="username" value="root"></property>
    <property name="password" value="123456"></property>
  </bean>
   ```
  - 通过jdbc.properties文件配置，最后在IOC容器中引入。 通过${key}方式访问value
  ```xml
  <context:property-placeholder location="jdbc.properties"></context:property-placeholder>
   <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
      <property name="driverClassName" value="${jdbc.driver}"></property>
      <property name="url" value="${jdbc.url}"></property>
      <property name="username" value="${jdbc.username}"></property>
      <property name="password" value="${jdbc.password}"></property>
   </bean>
   ```
  #### 2.2.5 bean的作用域
  - 概念
  <p>在Spring中可以通过配置bean标签的scope属性来指定bean的作用域范围，各取值含义参考下表
  
  | 取值  |  含义  | 创建对象的时机  |
  |:----:|:--------:|:---:|
  | singleton(default)   | 在IOC容器中，该对象始终为单例  |IOC容器初始化时|
  |prototype|在IOC容器中，该对象具有多个实例|获取bean时|
  <p>如果是在WebApplicationContext环境下还会有另外两个作用域（但不常用）：
  
  | 取值  |     含义     |
  :----------:|:--------:
  | request |在一个请求范围内有效  |
  | session | 在一个会话范围内有效 |
  #### 2.2.6 bean的生命周期
  - 实例化
  - 依赖注入
  - bean对象初始化之前操作(IOC的后置处理器postProcessBeforeInitialization负责)
  - 初始化，需要通过bean的InitMethod指定初始化方法
  - bean对象初始化之后操作(IOC的后置处理器postProcessAfterInitialization负责)
  - bean对象就绪可以使用
  - IOC容器关闭时销毁，需要通过bean的DestroyMethod方法指定销毁方法
  ```java
     /**
      * 注意：
      * 若bean的作用域为单例时，实例化、依赖注入以及初始化操作会在获取IOC容器时完成
      * 若bean的作用域为多例时，实例化、依赖注入以及初始化操作会在获取bean时完成
      */
  ```
  - bean的后置处理器：
    - 能够在生命周期初始化之前之后添加操作，需要实现BeanPostProcessor接口，且将实现类配置到IOC容器中。
    - 后置处理器对所有的Bean对象都起作用。
    ```java
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
    ```
#### 2.2.7 FactoryBean
  - 简介：
    <p>FactoryBean是Spring提供的一种整合第三方框架的常用机制。和普通的bean不同，配置一个
    FactoryBean类型的bean，在获取bean的时候得到的并不是class属性中配置的这个类的对象，而是
    getObject()方法的返回值。
    <p>FactoryBean是一个接口，需要创建一个类实现该接口
    <p>其中有三种方法：getObject()：设置一个对象交给IOC容器管理、getObjectType()：设置所提供对象的类型、isSingleton()：所提供的对象是否单例。
    <p>当把FactoryBean的实现类配置为bean时，会将当前类中的getObject()所返回的对象交给IOC容器管理。
    <p>相较于普通的Factory模式，不需要再获取Factory对象，直接就能获取Factory生成的对象。
  - 创建类UserFactoryBean
  ```java
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
  ```
  - 配置bean
  ```xml
   <bean class="factory.UserFactoryBean"></bean>
  ```
  - 测试
  ```java
    ApplicationContext ioc = new ClassPathXmlApplicationContext("Spring-factoryBean.xml");
    User user = ioc.getBean(User.class);
  ```
#### 2.2.8 基于XML的自动装配
  - 自动装配
    <p>根据指定的策略，在IOC容器中匹配某个bean，自动为bean中的类类型的属性或接口类型的属性赋值。</p>
    <p>可以通过bean标签中的autowire属性设置自动装配的策略</p>
    (1).no/default： 表示不装配，即bean中的属性不会自动匹配某个bean为属性赋值，此时属性使用默认值
    
    (2).byType：根据要赋值的属性的类型，在IOC容器中匹配某个bean，为属性赋值。若IOC没有匹配类型，此时属性不装配，使用默认值。若IOC有多个匹配类型，会抛出异常

    (3).byName：根据要赋值的属性的名字，在IOC容器中匹配某个bean，为属性赋值。属性名是哪个，对应的id就是哪个。

### 2.3 基于注解管理bean
#### 2.3.1 标记与扫描
  - 和 XML 配置文件一样，注解本身并不能执行，注解本身仅仅只是做一个标记，具体的功能是框架检测
    到注解标记的位置，然后针对这个位置按照注解标记的功能来执行具体操作。 
  - 本质上：所有一切的操作都是Java代码来完成的，XML和注解只是告诉框架中的Java代码如何执行。
#### 2.3.2 标识组件的常用注解
  - @Component：将类标识为普通组件
  - @Controller：将类标识为控制层组件
  - @Service：将类标识为业务层组件
  - @Repository：将类标识为持久层组件
#### 2.3.3 扫描组件
  - 通过context:component-scan标签扫描组件
  - 通过context:component-scan的子标签中的context:exclude-filter来排除不需要扫描的组件，其中type设置排除扫描的方式，type="annotation/assignable"，
    - annotation：根据注解的类型进行排除，expression需要设置排除的注解的全类名
    - assignable：根据类的类型进行排除，expression需要设置排除的类的全类名 
    ```xml
    <!--  扫描组件  -->
    <context:component-scan base-package="com.controller, com.dao.impl, com.service.impl">
      <!--  不扫描谁（不扫描controller类型的注解）  -->
      <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
      <!--  不扫描谁（不扫描UserController类）  -->
      <context:exclude-filter type="assignable" expression="com.controller.UserController"/>
    </context:component-scan>
    ```
  - 通过context:component-scan的子标签中的context:include-filter来排除不需要扫描的组件，其中type设置排除扫描的方式，type="annotation/assignable"，注意在此类情况下，需要在context:component-scan中设置use-default-filters="false"
    ```xml
    <!--  扫描组件  -->
    <context:component-scan base-package="com.controller, com.dao.impl, com.service.impl" use-default-filters="false>
      <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>
#### 2.3.4 自动注入
  - 使用@Autowired能够注解装配的位置
    - 标识在成员变量上，此时不需要设置set方法
    - 标识在set方法上
    - 为当前成员变量赋值的有参构造上
  - 自动装配实现过程
    - 通过byType的方式，在IOC容器中通过类型匹配某个bean为属性赋值
    - 若有多个类型匹配的bean，则会自动转换为byName的方式实现自动装配的效果
      - 即将要复制的属性的属性名作为bean的id匹配某个bean为属性赋值
    - 若byType与byName都不能实现自动装配，即此时IOC容器中，有多个类型匹配的bean，并且id与要赋值的变量名都不相同，此时会报错 
      - 可以通过@Qualifier("id")注解来指定某id的bean来赋值
  ```java
    @Autowired
  ```


## 3. AOP
### 3.1 代理模式
  - 通过提供一个代理类，让我们在调用目标方法时，不再是直接对目标方法进行调用，而是通过代理类间接调用。让不属于目标方法核心逻辑的代码从目标方法中剥离出来，调用目标方法时先调用代理对象的方法，减少对目标方法的调用和打扰，同时利于维护。
  - 在核心类里实现核心功能，在代理类中对核心类进行功能增强。
#### 3.1.1 静态代理
  <p>静态代理代码属于写死的，不具备灵活性，例如：写死了实现类，当有另一个类需要同样的日志功能时，不能很好的进行代理。
  <p>因此，提出进一步的需求，将日志功能集中到一个代理类中，将来有任何的日志需求，都通过这一个代理类来实现，即动态代理技术

#### 3.1.2 动态代理
  - 利用jdk的api实现动态代理——要求必须有接口，最终生成的代理类和目标类实现相同的接口，且在com.sun.proxy包下
    - Proxy.newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h)
      - ClassLoader loader:类加载器，指定加载动态生成的代理类的类加载器。
      - Class<?>[] interfaces：获取目标对象实现的所有接口的class对象的数组。
      - InvocationHandler h：设置代理类中的抽象方法如何重写
  - cglib动态代理——最终生成的代理类会继承目标类，并且和目标类在相同的包下。

