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
### 3.1 @RequestMapping注解的功能
<p>从注解名称上我们可以看到，@RequestMapping注解的作用就是将请求和处理请求的控制器方法关联
起来，建立映射关系。</p>
<p>SpringMVC 接收到指定的请求，就会来找到在映射关系中对应的控制器方法来处理这个请求。</p>

### 3.2 @RequestMapping注解的位置
  - @RequestMapping标识一个类：设置映射请求的请求路径的初始信息
  - @RequestMapping标识一个方法：设置映射请求请求路径的具体信息
```java
@Controller
@RequestMapping("/test")
public class TestRequestMappingController {

    //此时请求映射所映射的请求的请求路径为：/test/testRequestMapping
    @RequestMapping("/testRequestMapping")
    public String testRequestMapping(){
        return "success";
    }
    
}
```

### 3.3 @RequestMapping注解的value属性
<p>@RequestMapping注解的value属性通过请求的请求地址匹配请求映射
<p>@RequestMapping注解的value属性是一个字符串类型的数组，表示该请求映射能够匹配多个请求地址(路径)
所对应的请求
<p>@RequestMapping注解的value属性必须设置，至少通过请求地址匹配请求映射

```java
    @RequestMapping({"/testRequestMapping", "/hello"})
    public String testRequestMapping(){
        return "success";
    }
```

### 3.4 @RequestMapping注解的method属性
<p>@RequestMapping注解的method属性通过请求的请求方式（get或post）匹配请求映射
<p>@RequestMapping注解的method属性是一个RequestMethod类型的数组，表示该请求映射能够匹配
多种请求方式的请求
<p>若当前请求的请求地址满足请求映射的value属性，但是请求方式不满足method属性，则浏览器报错
405：Request method 'POST' not supported

```html
    <form th:action="@{/test/hello}" method="post">
        <input type="submit">
    </form>
```
```java
    @Controller
    @RequestMapping("/test")
    public class TestRequestMappingController {
        @RequestMapping(
                value = {"/testRequestMapping", "/hello"},
                method = {RequestMethod.GET})
        public String testRequestMapping(){
            return "success";
        }
    }
```
注：
  - 1、对于处理指定请求方式的控制器方法，SpringMVC中提供了@RequestMapping的派生注解
    - 处理get请求的映射-->@GetMapping
    - 处理post请求的映射-->@PostMapping
    - 处理put请求的映射-->@PutMapping
    - 处理delete请求的映射-->@DeleteMapping
  - 2、常用的请求方式有get，post，put，delete
    - 但是目前浏览器只支持get和post，若在form表单提交时，为method设置了其他请求方式的字符 串（put或delete），则按照默认的请求方式get处理
    - 若要发送put和delete请求，则需要通过spring提供的过滤器HiddenHttpMethodFilter，在 RESTful部分会讲到


### 3.5 @RequestMapping注解的params属性（了解）
  - @RequestMapping注解的params属性通过请求的请求参数匹配请求映射
  - @RequestMapping注解的params属性是一个字符串类型的数组，可以通过四种表达式设置请求参数和请求映射的匹配关系
    - "param"：要求请求映射所匹配的请求必须携带param请求参数
    - "!param"：要求请求映射所匹配的请求必须不能携带param请求参数
    - "param=value"：要求请求映射所匹配的请求必须携带param请求参数且param=value
    - "param!=value"：要求请求映射所匹配的请求必须携带param请求参数但是param!=value

```html
<a th:href="@{/test(username='admin',password=123456)">测试@RequestMapping的params属性-->/test</a><br>
```
```java
@RequestMapping(
    value = {"/testRequestMapping", "/test"}, 
    method = {RequestMethod.GET, RequestMethod.POST},
    params = {"username","password!=123456"}
)
public String testRequestMapping(){
    return "success";
}
```

注：
<p>若当前请求满足@RequestMapping注解的value和method属性，但是不满足params属性，此时
页面回报错400：Parameter conditions "username, password!=123456" not met for actual
request parameters: username={admin}, password={123456}

### 3.6 @RequestMapping注解的headers属性（了解）
  - @RequestMapping注解的headers属性通过请求的请求头信息匹配请求映射
  - @RequestMapping注解的headers属性是一个字符串类型的数组，可以通过四种表达式设置请求头信 息和请求映射的匹配关系
    - "header"：要求请求映射所匹配的请求必须携带header请求头信息
    - "!header"：要求请求映射所匹配的请求必须不能携带header请求头信息
    - "header=value"：要求请求映射所匹配的请求必须携带header请求头信息且header=value
    - "header!=value"：要求请求映射所匹配的请求必须携带header请求头信息且header!=value
<p>若当前请求满足@RequestMapping注解的value和method属性，但是不满足headers属性，此时页面
显示404错误，即资源未找到

### 3.7 SpringMVC支持ant风格的路径
  - ？：表示任意的单个字符
  - *：表示任意的0个或多个字符
  - \*\*：表示任意层数的任意目录
<p>注意：在使用\*\*时，只能使用/**/xxx的方式

### 3.8 SpringMVC支持路径中的占位符（重点）
  - 原始方式：/deleteUser?id=1
  - rest方式：/user/delete/1
  - SpringMVC路径中的占位符常用于RESTful风格中，当请求路径中将某些数据通过路径的方式传输到服
    务器中，就可以在相应的@RequestMapping注解的value属性中通过占位符{xxx}表示传输的数据，在
    通过@PathVariable注解，将占位符所表示的数据赋值给控制器方法的形参
```html
<a th:href="@{/testRest/1/admin}">测试路径中的占位符-->/testRest</a><br>
```
```java
@RequestMapping("/testRest/{id}/{username}")
public String testRest(@PathVariable("id") String id, @PathVariable("username")
String username){
System.out.println("id:"+id+",username:"+username);
return "success";
}
//最终输出的内容为-->id:1,username:admin
```