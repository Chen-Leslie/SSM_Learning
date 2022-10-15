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
    <bean id="studentTwo" class="Student">
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
      <bean id="studentFour" class="Student">
        <property name="cls" ref="classOne"></property>
      </bean>
      <bean id="classOne" class="Class">
        <property name="cId" value="1"></property>
        <property name="cName" value="1班"></property>
      </bean>
    ```
    - 级联方式，但是需要保证提前为class属性类赋值或者实例化
    ```xml
    <bean id="studentFive" class="Student">
        <property name="cls" ref="classOne"></property>
        <property name="cls.cId" value="2"></property>
        <property name="cls.cName" value="2班"></property>
    </bean>
    ```
    - 内部bean
    ```xml
    <bean id="studentSix" class="Student">
        <property name="cls">
            <bean id="classInner" class="Class">
                <property name="cId" value="3"></property>
                <property name="cName" value="3班"></property>
            </bean>
        </property>
    </bean>
    ```
  - 为数组属性赋值
    - 利用property标签下的array子标签，array内容如果是字面量则直接用value，如果是类则可以用ref
    ```xml
    <bean id="studentSix" class="Student">
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
    <bean id="classOne" class="Class">
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
    <bean id="classOne" class="Class">
        <property name="students" ref="studentList"></property>
    </bean>
    ```
  - 为Map集合属性赋值
    - 利用property标签下面的Map子标签，使用entry子标签表示一个键值对
    ```xml
    <bean id="teacherOne" class="Teacher">
        <property name="tId" value="1"></property>
        <property name="tName" value="t1"></property>
    </bean>
    <bean id="teacherTwo" class="Teacher">
        <property name="tId" value="2"></property>
        <property name="tName" value="t2"></property>
    </bean>
    <bean id="studentFour" class="Student">
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
    <bean id="studentFour" class="Student">
        <property name="teacherMap" ref="TeacherMap"></property>
    </bean>
    <util:map id="TeacherMap">
        <entry key="1" value-ref="teacherOne"></entry>
        <entry key="2" value-ref="teacherTwo"></entry>
    </util:map>
    ```
  - p命名空间
      ```xml
      <bean id="studentSeven" class="Student"
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

### 3.2 AOP概述与相关术语
#### 3.2.1 AOP概述
  <p>AOP是一种设计思想，面向切面编程</p>

#### 3.2.2 相关术语
  - 横切关注点
  - 通知
    - 前置通知 在被代理的目标方法前执行
    - 返回通知 在被代理的目标方法成功执行后执行
    - 异常通知 在被代理的目标方法异常结束后执行
    - 后置通知 在被代理的目标方法最终结束后执行
    - 环绕通知 使用try catch finally结构围绕整个被代理的目标方法，包括上面四种
  - 切面：封装通知方法的类
  - 目标：被代理的目标对象
  - 代理：向目标对象应用通知之后创建的代理对象
  - 连接点
  - 切入点：定位连接点的方式，本质是一个表达式

#### 3.2.3 作用：
  - 简化代码
  - 代码增强

### 3.3 基于注解的AOP
#### 3.3.1 AOP的注意事项
  - 切面类和目标类都需要交给IOC容器来管理
  - 利用org.aspectj.lang.annotation.Aspect下的@Aspect将切面类标识为切面
  - 在spring的配置文件中利用<aop:aspectj-autoproxy/>开启基于注解的AOP
  - 在切面中，需要通过指定的注解将方法标识为通知方法
    - @Before()：前置通知，在目标对象方法执行之前执行
    - @After()：后置通知，在目标对象方法的finally子句中执行
    - @AfterReturning(): 返回通知，在目标对象返回值之后执行
    - @AfterThrowing(): 异常通知，在在目标对象方法的catch子句中执行
    - @Around(): 环绕通知，环绕通知方法的返回值一定要和目标方法的返回值一致
  - 切入点表达式：添加在标识通知的注解的value属性中，"execution(...)"，
    - 在execution里可以用*表示通配符，在参数列表里可以用..代替
    - execution(public int com.SpringLearning.annotation.CalculatorImpl.add(int, int))
    - execution(* com.SpringLearning.annotation.CalculatorImpl.*(..))
      - 第一个*表示任意的访问修饰符与任意的返回值类型
      - 第二个*表示类中任意的方法
      - ..表示任意的参数
      - 类的地方也可以使用*代替，表示包下所有的类
    - 切入点的重用：
    ```java
        // 声明一个公共的切入点
        @Pointcut("execution(* com.SpringLearning.annotation.CalculatorImpl.*(..))")
    
        public void PointCut(){}
        
        @Before("PointCut()")
    ```
  - 连接点信息：在通知方法的参数位置设置JoinPoint类型的参数，就可以获取连接点所对应方法的信息
  - 切面的优先级：可以通过@Order()注解的value属性设置优先级，默认为Integer的最大值，value的属性值越小，优先级越高
### 3.4 基于xml的AOP

```xml
    <aop:config>
        <!--   设置一个公共的切入点表达式  -->
        <aop:pointcut id="pointCut" expression="execution(* com.SpringLearning.xml.CalculatorImpl.*(..))"/>
        <!--   将IOC容器中的某一个bean设置为切面  -->
        <aop:aspect ref="loggerAspect">
            <aop:before method="beforeAdviceMethod" pointcut-ref="pointCut"></aop:before>
            <aop:after method="afterAdviceMethod" pointcut-ref="pointCut"></aop:after>
            <aop:after-returning method="afterReturningAdviceMethod" pointcut-ref="pointCut" returning="result"></aop:after-returning>
            <aop:after-throwing method="afterThrowingAdviceMethod" pointcut-ref="pointCut" throwing="exception"></aop:after-throwing>
            <aop:around method="aroundAdviceMethod" pointcut-ref="pointCut"></aop:around>
        </aop:aspect>
        <aop:aspect ref="validateAspect" order="1">
            <aop:before method="beforeAdviceMethod" pointcut-ref="pointCut"></aop:before>
        </aop:aspect>
    </aop:config>
   ```

## 4. 声明式事务
### 4.1 JdbcTemplate
### 4.2 声明式事务概念
#### 4.2.1 编程式事务
  - <p>事务功能的相关操作全部通过自己编写代码来实现</p>
  - 缺陷：
    - 细节未被屏蔽
    - 难以复用
#### 4.2.2 声明式事务
  - 由框架进行将固定模式的代码抽取出来并进行封装，封装以后只需要在配置文件中进行简单配置即可
### 4.3 基于注解的声明式事务
#### 4.3.1 声明式事务的配置步骤
  - 在spring配置文件中配置事务管理器
  - 开启事务的注解驱动
  - 只需要在需要被事务管理的方法上添加@Transactional注解就会被事务管理
    - @Transactional可以标注在方法上
    - @Transactional可以标注在类上，则类中所有方法都会被事务管理
#### 4.3.2 配置事务管理器
```xml
    <bean class="org.springframework.jdbc.datasource.DataSourceTransactionManager" id="transactionManager">
        <property name="dataSource" ref="druidDataSource"></property>
    </bean>
```
#### 4.3.3 开启事务的注解驱动
<p>将使用@Transactional标注的方法或类中所有的方法使用事务进行管理</p>
<p>transaction-manager属性设置事务管理器的id，若事务管理的bean的id为transactionManager，则可以不写</p>
```xml
    <tx:annotation-driven transaction-manager="transactionManager"/>
```

#### 4.3.4 事务属性：只读
<p>告诉数据库，这个操作不涉及写操作</p>

```java
@Transactional(readOnly = true)
```

#### 4.3.5 事务属性：超时
<p>超时回滚，释放资源</p>

```java
@Transactional(timeout = 3)
```

#### 4.3.6 事务属性：回滚策略
<p>声明式事务默认只针对运行时异常回滚，编译时异常不回滚</p>
<p>可以通过@Transactional中的属性设置回滚策略</p>
  
  - rollbackFor：需要设置一个class类型的对象，表示出现什么而回滚
  - rollbackForClassName：需要设置一个字符串类型的全类名，表示出现什么而回滚
  - norollbackFor：需要设置一个class类型的对象，表示出现什么不回滚
  - rollbackForClassName：需要设置一个字符串类型的全类名，表示出现什么不回滚

#### 4.3.7 事务属性：事务隔离级别
<p>声明式事务默认只针对运行时异常回滚，编译时异常不回滚</p>

隔离级别一共有四种
  - 读未提交 READ UNCOMMITTED：允许Transaction01读取Transaction02未提交的修改
  - 读已提交 READ COMMITTED：要求Transaction01只能读取Transaction02已提交的修改。
  - 可重复读 REPEATABLE READ：确保Transaction01可以多次从一个字段中读取到相同的值，即Transaction01执行期间禁止其它
    事务对这个字段进行更新。
  - 串行化 SERIALIZABLE：确保Transaction01可以多次从一个表中读取到相同的行，在Transaction01执行期间，禁止其它
    事务对这个表进行添加、更新、删除操作。可以避免任何并发问题，但性能十分低下。

各个隔离级别解决并发问题的能力：

|隔离级别|脏读|不可重复读|幻读|
:----------:|:--------:|:-------:|:-------:
|READ UNCOMMITTED|y|y|y|
|READ COMMITTED|n|y|y|
|REPEATABLE READ|n|n|y|
|SERIALIZABLE|n|n|n|

各类数据库对事务隔离级别的支持

|隔离级别|Oracle|MySQL|
:----:|:---:|:---:
|READ UNCOMMITTED|n|y|
|READ COMMITTED|y(default)|y|
|REPEATABLE READ|n|y(default)|
|SERIALIZABLE|y|y|

使用方式：
```java
@Transactional(isolation=xxx)
```

#### 4.3.8 事务属性：事务传播行为
<p>当事务方法被另一个事务方法调用时，必须指定事务应该如何传播。例如：方法可能继续在现有事务中
运行，也可能开启一个新事务，并在自己的事务中运行。</p>
<p>比如在结账系统中，checkout功能具有自己的事务，结账两本书时，需要调用buyBook两次，但是buyBook具有自己的事务，如果 第一本结算成功，第二本结算未成功，则回滚时是全部回滚，还是第二本买书事务回滚</p>

  - 在上述例子中，bugBook的事务默认情况是采用checkout的事务，即默认采用调用者的事务
  - 若需要使用自己的事务，则在自己事务的@Transactional中添加属性
  ```java
    @Transactional(propagation = Propagation.REQUIRES_NEW)
  ```
  - @Transactional(propagation = Propagation.REQUIRED)，默认情况，表示如果当前线程上有已经开
    启的事务可用，那么就在这个事务中运行。经过观察，购买图书的方法buyBook()在checkout()中被调
    用，checkout()上有事务注解，因此在此事务中执行。所购买的两本图书的价格为80和50，而用户的余
    额为100，因此在购买第二本图书时余额不足失败，导致整个checkout()回滚，即只要有一本书买不
    了，就都买不了
  - @Transactional(propagation = Propagation.REQUIRES_NEW)，表示不管当前线程上是否有已经开启
    的事务，都要开启新事务。同样的场景，每次购买图书都是在buyBook()的事务中执行，因此第一本图
    书购买成功，事务结束，第二本图书购买失败，只在第二次的buyBook()中回滚，购买第一本图书不受
    影响，即能买几本就买几本

### 4.4 基于XML的声明式事务
```xml
    <!--  配置事务管理器  -->
    <bean class="org.springframework.jdbc.datasource.DataSourceTransactionManager" id="transactionManager">
        <property name="dataSource" ref="druidDataSource"></property>
    </bean>

    <!--  配置事务通知  -->
    <tx:advice id="tx" transaction-manager="transactionManager">
        <!--  针对不同连接点方法配置不同的管理参数  -->
        <tx:attributes>
            <!--  若不在此配置需要事务管理的方法，则不会对连接点方法进行事务管理  -->
            <tx:method name="buyBook" read-only="false"/>
            <!--  可以在方法命名中使用通配符*，表示get开头的方法都是只读  -->
            <tx:method name="get*" read-only="true"/>
            <tx:method name="*" read-only="false"/>
        </tx:attributes>
    </tx:advice>
    
    <!--  将service.Impl下的所有类以及所有方法纳入事务管理中  -->
    <aop:config>
        <aop:advisor advice-ref="tx" pointcut="execution(* com.SprintLearning.transaction.service.Impl.*.*(..))"></aop:advisor>
    </aop:config>
```

 
