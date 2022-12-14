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

## 4 SpringMVC获取请求参数
### 4.1 通过servletAPI获取
<p>将HttpServletRequest作为控制器方法的形参，此时HttpServletRequest类型的参数表示封装了当前请
求的请求报文的对象

```java
    @RequestMapping("/param/servletAPI")
    public String getParamByServletAPI(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("username:" + username + "," + "password:" + password);
        return "success";
    }
```

### 4.2 通过控制器方法的形参获取请求参数
<p>只需要在控制器方法的形参位置设置一个形参，形参名字与请求参数一致即可

```java
    @RequestMapping("/param")
    public String getParam(String username, String password){
        System.out.println("username:" + username + "," + "password:" + password);
        return "success";
    }
```

#### 4.2.1 @RequestParam
<p>当请求参数与形参名字不一致时，将请求参数和控制器方法的形参绑定
<p>@RequestParam的三个属性
  
  - value：设置和形参绑定的请求参数的名字
  - required：设置是否必须传输value所对应的请求参数，默认为true，表示必须传输，否则报错400：Required String parameter 'xxx' is not present
  - defaultValue：设置当没有传输value所对应的请求参数，为形参所设置的默认值，此时和required属性无关

#### 4.2.2 @RequestHeader
<p>将请求头信息和控制器方法的形参绑定</p>
<p>注解一共有三个属性：value、required、defaultValue，用法同@RequestParam</p>

#### 4.2.3 @CookieValue
<p>将cookie数据和控制器方法的形参绑定</p>
<p>注解一共有三个属性：value、required、defaultValue，用法同@RequestParam</p>

#### 4.2.4 通过POJO获取请求参数
<p>可以在控制器方法的形参位置设置一个实体类类型的形参，此时若浏览器传输的请求参数的参数名和实
体类中的属性名一致，那么请求参数就会为此属性赋值

```java
    @RequestMapping("/param/pojo")
    public String getParamByPojo(User user){
        System.out.println(user);
        return "success";
    }
```

```html
    <form th:action="@{/param/pojo}">
        用户名:<input type="text" name="username"><br>
        密码:<input type="password" name="password"><br>
        <input type="submit" value="登录"><br>
    </form>
```

### 4.3 解决获取请求参数的乱码问题
<p>解决获取请求参数的乱码问题，可以使用SpringMVC提供的编码过滤器CharacterEncodingFilter，但是
必须在web.xml中进行注册</p>

```xml
<filter>
    <filter-name>CharacterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
        <param-name>encoding</param-name>
        <param-value>UTF-8</param-value>
    </init-param>
    <init-param>
        <param-name>forceEncoding</param-name>
        <param-value>true</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>CharacterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```
``Remark:SpringMVC中处理编码的过滤器一定要配置到其他过滤器之前，否则无效``

## 5 域对象共享数据
### 5.1 使用servletAPI向request域对象共享数据

```java
    @RequestMapping("/testServletAPI")
    public String testServletAPI(HttpServletRequest request){
        request.setAttribute("testScope", "hello,servletAPI");
        return "success";
    }
```

### 5.2 使用ModelAndView向request域对象共享数据
<p>使用ModelAndView时，可以使用Model功能向请求域中共享数据，使用View功能设置逻辑视图，但是控制器方法一定要返回ModelAndView</p>

```java
    @RequestMapping("/test/mav")
    public ModelAndView testMAV(){
        /**
         * ModelAndView包含Model和View的功能
         * Model：向请求域中共享数据
         * View：设置逻辑视图实现页面跳转
         */
        ModelAndView modelAndView = new ModelAndView();
        // 向请求域中共享数据
        modelAndView.addObject("testRequestScope","hello, ModelAndView");
        // 设置逻辑视图
        modelAndView.setViewName("success");
        return modelAndView;
    }
```

### 5.3 使用Model向request域对象共享数据

```java
    @RequestMapping("/test/model")
    public String testModel(Model model){
        model.addAttribute("testRequestScope", "hello, Model");
        return "success";
    }
```

### 5.4 使用map向request域对象共享数据

```java
    @RequestMapping("/test/map")
    public String testModelMap(Map<String, Object> map){
        map.put("testRequestScope", "hello, map");
        return "success";
    }
```

### 5.5 使用ModelMap向request域对象共享数据

```java
    @RequestMapping("/test/modelMap")
    public String testModelMap(ModelMap modelMap){
        modelMap.addAttribute("testRequestScope", "hello, ModelMap");
        return "success";
    }
```

### 5.6 Model、ModelMap、Map的关系
<p>Model、ModelMap、Map类型的参数其实本质上都是 BindingAwareModelMap 类型的</p>

```java
    public interface Model{}
    public class ModelMap extends LinkedHashMap<String, Object> {}
    public class ExtendedModelMap extends ModelMap implements Model {}
    public class BindingAwareModelMap extends ExtendedModelMap
```

### 5.7 向session域共享数据

```java
    @RequestMapping("/testSession")
    public String testSession(HttpSession session){
        session.setAttribute("testSessionScope", "hello,session");
        return "success";
    }
```

### 5.8 向application域共享数据

```java
    @RequestMapping("/testApplication")
    public String testApplication(HttpSession session){
        ServletContext application = session.getServletContext();
        application.setAttribute("testApplicationScope", "hello,application");
        return "success";
    }
```

## 6 SpringMVC的视图

<p>SpringMVC中的视图是View接口，视图的作用渲染数据，将模型Model中的数据展示给用户</p>
<p>SpringMVC视图的种类很多，默认有转发视图和重定向视图</p>
<p>当工程引入jstl的依赖，转发视图会自动转换为JstlView</p>
<p>若使用的视图技术为Thymeleaf，在SpringMVC的配置文件中配置了Thymeleaf的视图解析器，由此视
图解析器解析之后所得到的是ThymeleafView</p>

### 6.1 ThymeleafView
<p>当控制器方法中所设置的视图名称没有任何前缀时，此时的视图名称会被SpringMVC配置文件中所配置
的视图解析器解析，视图名称拼接视图前缀和视图后缀所得到的最终路径，会通过转发的方式实现跳转</p>


### 6.2 转发视图
<p>SpringMVC中默认的转发视图是InternalResourceView</p>
<p>SpringMVC中创建转发视图的情况：</p>
<p>当控制器方法中所设置的视图名称以"forward:"为前缀时，创建InternalResourceView视图，此时的视图名称不会被SpringMVC配置文件中所配置的视图解析器解析，而是会将前缀"forward:"去掉，剩余部
分作为最终路径通过转发的方式实现跳转</p>
<p>例如"forward:/"，"forward:/employee"</p>

```java
    @RequestMapping("/test/view/forward")
    public String testInternalResourceView(){
        return "forward:/test/model";
    }
```

### 6.3 重定向视图
<p>SpringMVC中默认的重定向视图是RedirectView</p>
<p>当控制器方法中所设置的视图名称以"redirect:"为前缀时，创建RedirectView视图，此时的视图名称不
会被SpringMVC配置文件中所配置的视图解析器解析，而是会将前缀"redirect:"去掉，剩余部分作为最
终路径通过重定向的方式实现跳转</p>

```java
    @RequestMapping("/test/view/redirect")
    public String testRedirectView(){
        return "redirect:/test/model";
    }
```

### 6.4 视图控制器view-controller
<p>当控制器方法中，仅仅用来实现页面跳转，即只需要设置视图名称时，可以将处理器方法使用view-controller标签进行表示</p>

```xml
<!--开启mvc注解驱动-->
<mvc:annotation-driven/>
<!--视图控制器，为当前请求直接设置视图名称，实现页面跳转-->
<mvc:view-controller path="/" view-name="index"></mvc:view-controller>
```

<p>当SpringMVC中设置任何一个view-controller时，其他控制器中的请求映射将全部失效，此时需
要在SpringMVC的核心配置文件中设置开启mvc注解驱动的标签：</p>

```xml
<mvc:annotation-driven/>
```

## 7 RESTful

### 7.1 RESTful简介
<p>REST：Representational State Transfer，表现层资源状态转移。</p>

  - 资源：
    - 资源是一种看待服务器的方式，即，将服务器看作是由很多离散的资源组成。每个资源是服务器上一个
      可命名的抽象概念。因为资源是一个抽象的概念，所以它不仅仅能代表服务器文件系统中的一个文件、
      数据库中的一张表等等具体的东西，可以将资源设计的要多抽象有多抽象，只要想象力允许而且客户端
      应用开发者能够理解。与面向对象设计类似，资源是以名词为核心来组织的，首先关注的是名词。一个
      资源可以由一个或多个URI来标识。URI既是资源的名称，也是资源在Web上的地址。对某个资源感兴
      趣的客户端应用，可以通过资源的URI与其进行交互。
  - 资源的表述：
    - 资源的表述是一段对于资源在某个特定时刻的状态的描述。可以在客户端-服务器端之间转移（交
      换）。资源的表述可以有多种格式，例如HTML/XML/JSON/纯文本/图片/视频/音频等等。资源的表述格
      式可以通过协商机制来确定。请求-响应方向的表述通常使用不同的格式。
  - 状态转移
    - 状态转移说的是：在客户端和服务器端之间转移（transfer）代表资源状态的表述。通过转移和操作资
      源的表述，来间接实现操作资源的目的。

### 7.2 RESTful实现
<p>具体说，就是 HTTP 协议里面，四个表示操作方式的动词：GET、POST、PUT、DELETE。</p>
<p>它们分别对应四种基本操作：GET 用来获取资源，POST 用来新建资源，PUT 用来更新资源，DELETE
用来删除资源。</p>
<p>REST 风格提倡 URL 地址使用统一的风格设计，从前到后各个单词使用斜杠分开，不使用问号键值对方
式携带请求参数，而是将要发送给服务器的数据作为 URL 地址的一部分，以保证整体风格的一致性。</p>

|操作|传统方式|REST风格|
:----:|:----:|:----:
|查询操作|getUserById?id=1|user/1-->get请求方式|
|保存操作|saveUser|user-->post请求方式|
|删除操作|deleteUser?id=1|user/1-->delete请求方式|
|更新操作|updateUser|user-->put请求方式|

## 7.3 HiddenHttpMethodFilter
<p>由于浏览器只支持发送get和post方式的请求，那么该如何发送put和delete请求呢？
<p>SpringMVC 提供了 HiddenHttpMethodFilter 帮助我们将 POST 请求转换为 DELETE 或 PUT 请求
HiddenHttpMethodFilter 处理put和delete请求的条件：
  
  - 当前请求的请求方式必须为post
  - 当前请求必须传输请求参数_method

<p>满足以上条件，HiddenHttpMethodFilter 过滤器就会将当前请求的请求方式转换为请求参数
_method的值，因此请求参数_method的值才是最终的请求方式
<p>在web.xml中注册HiddenHttpMethodFilter

```xml
<filter>
    <filter-name>HiddenHttpMethodFilter</filter-name>
    <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>HiddenHttpMethodFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

```html
    <form th:action="@{/user}" method="post">
        <input type="hidden" name="_method" value="put">
        <input type="submit" value="修改用户信息">
    </form>
```

<p>目前为止，SpringMVC中提供了两个过滤器：

  - CharacterEncodingFilter和
  - HiddenHttpMethodFilter
<p>在web.xml中注册时，必须先注册CharacterEncodingFilter，再注册HiddenHttpMethodFilter
原因：

  - 在CharacterEncodingFilter中通过 request.setCharacterEncoding(encoding) 方法设置字
符集的 
  - request.setCharacterEncoding(encoding) 方法要求前面不能有任何获取请求参数的操作
  - 而 HiddenHttpMethodFilter 恰恰有一个获取请求方式的操作：String paramValue = request.getParameter(this.methodParam);


