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
      - FileSystemXmlApplicationContext
      - ConfigurableApplicationContext
      - WebApplicationContext
    - 