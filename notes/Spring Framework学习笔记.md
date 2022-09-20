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
