# SpringMVC学习笔记
## 1. SpringMVC简介：
### 1.1 MVC：
<p>MVC是一种软件架构的思想，将软件按照模型、视图、控制器来划分</p>
<p>M：Model，模型层，指工程中的JavaBean，作用是处理数据</p>
<p>JavaBean分为两类：</p>

  - 一类称为实体类Bean：专门存储业务数据的，如 Student、User 等
  - 一类称为业务处理 Bean：指 Service 或 Dao 对象，专门用于处理业务逻辑和数据访问。
<p>V：View，视图层，指工程中的html或jsp等页面，作用是与用户进行交互，展示数据</p>
<p>C：Controller，控制层，指工程中的servlet，作用是接收请求和响应浏览器</p>
<p>MVC的工作流程： 用户通过视图层发送请求到服务器，在服务器中请求被Controller接收，Controller
调用相应的Model层处理请求，处理完毕将结果返回到Controller，Controller再根据请求处理的结果
找到相应的View视图，渲染数据后最终响应给浏览器</p>

### 1.2 SpringMVC:

<p>SpringMVC是Spring的一个后续产品，是Spring的一个子项目</p>

<p>SpringMVC 是 Spring 为表述层开发提供的一整套完备的解决方案。在表述层框架历经 Strust、
WebWork、Strust2 等诸多产品的历代更迭之后，目前业界普遍选择了 SpringMVC 作为 Java EE 项目
表述层开发的首选方案。</p>
注：三层架构分为表述层（或表示层）、业务逻辑层、数据访问层，表述层表示前台页面和后台
servlet

### 1.3 SpringMVCt特点
  - Spring家族原生产品，与IOC容器等基础设施无缝对接
  - 基于原生的Servlet，通过了功能强大的前端控制器**DispatcherServlet**，对请求和响应进行统一处理
  - 表述层各细分领域需要解决的问题全方位覆盖，提供全面解决方案 
  - 代码清新简洁，大幅度提升开发效率 
  - 内部组件化程度高，可插拔式组件即插即用，想要什么功能配置相应组件即可 
  - 性能卓著，尤其适合现代大型、超大型互联网项目要求
## 2 配置SpringMVC
### 2.1 配置pom.xml 导入依赖项
```xml
<packaging>war</packaging>
<dependencies>
    <!--    SpringMVC    -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.3.1</version>
    </dependency>
    <!--    日志    -->
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>1.2.3</version>
    </dependency>
    <!--     servletAPI      -->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>3.1.0</version>
        <scope>provided</scope>
    </dependency>
    <!--   spring与Thymeleaf整合包   -->
    <dependency>
        <groupId>org.thymeleaf</groupId>
        <artifactId>thymeleaf-spring5</artifactId>
        <version>3.0.12.RELEASE</version>
    </dependency>
</dependencies>
```
### 2.2 配置web.xml
```xml
    <!--
        配置SpringMVC的前端控制器DispatcherServlet

        url-pattern中/与/*的区别：
        /：匹配浏览器向服务器发送的所有请求(不包括jsp)
        /*：匹配浏览器向服务器发送的所有请求(包括jsp)
    -->
    <servlet>
        <servlet-name>SpringMVC</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!-- 设置servlet配置文件的路径，更改了之后，就可以不用在默认WEB-INF路径下了 -->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:SpringMVC.xml</param-value>
        </init-param>
        <!-- 将DispatchServlet初始化的时间提前到服务器启动前 -->
        <load-on-startup>1</load-on-startup>
    </servlet>

    <servlet-mapping>
        <servlet-name>SpringMVC</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
```

### 2.3 创建请求控制器
<p>由于前端控制器对浏览器发送的请求进行了统一的处理，但是具体的请求有不同的处理过程，因此需要创建处理具体请求的类，即请求控制器
<p>请求控制器中每一个处理请求的方法成为控制器方法
<p>因为SpringMVC的控制器由一个POJO（普通的Java类）担任，因此需要通过@Controller注解将其标识为一个控制层组件，交给Spring的IoC容器管理，此时SpringMVC才能够识别控制器的存在

```java
@Controller
public class HelloController {

}
```

### 2.4 创建SpringMVC的配置文件
<p>SpringMVC配置文件默认的位置为WEB-INF下，默认名称为:[servlet-name]-servlet.xml，当前配置下的配置文件名为SpringMVC-servlet.xml</p>

### 2.5 利用控制器对请求进行处理
#### 2.5.1 创建处理请求方法:
  - 在controller的组件中，添加方法，在方法前使用@RequestMapping("")注解标注，()中传入要处理的请求
    ```java
      @RequestMapping("/")
      public String index(){
           return "index";  
      }
    ```
  - @RequestMapping注解：处理请求和控制器方法之间的映射关系
  - @RequestMapping注解的value属性可以通过请求地址匹配请求，“/”表示的当前工程的上下文路径 localhost:8080/springMVC/

### 2.6 总结
<p>浏览器发送请求，若请求地址符合前端控制器的url-pattern，该请求就会被前端控制器
DispatcherServlet处理。前端控制器会读取SpringMVC的核心配置文件，通过扫描组件找到控制器，
将请求地址和控制器中@RequestMapping注解的value属性值进行匹配，若匹配成功，该注解所标识的
控制器方法就是处理请求的方法。处理请求的方法需要返回一个字符串类型的视图名称，该视图名称会
被视图解析器解析，加上前缀和后缀组成视图的路径，通过Thymeleaf对视图进行渲染，最终转发到视
图所对应页面

## 3 @RequestMapping注解
