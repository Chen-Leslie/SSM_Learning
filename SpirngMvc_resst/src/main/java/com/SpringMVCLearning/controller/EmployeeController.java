package com.SpringMVCLearning.controller;

import com.SpringMVCLearning.dao.EmployeeDao;
import com.SpringMVCLearning.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-10-25
 * 查询所有的员工信息 ---> /employee ---> GET
 * 根据id查询员工信息 ---> /employee/1 ---> GET
 * 跳转到添加页面 ---> /to/add ---> GET
 * 新增员工信息 ---> /employee ---> POST
 * 跳转到修改页面 ---> /employee/1 ---> GET
 * 修改员工信息 ---> /employee ---> PUT
 * 删除员工信息 ---> /employee/1 ---> DELETE
 */
@Controller
public class EmployeeController {

    @Autowired
    private EmployeeDao employeeDao;

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public String getAllEmployee(Model model){
        Collection<Employee> allEmployee = employeeDao.getAll();
        // 将所有的员工信息在请求域中共享
        model.addAttribute("allEmployee", allEmployee);
        // 跳转到页面
        return "employee_list";
    }

    @RequestMapping(value = "/employee", method=RequestMethod.POST)
    public String addEmployee(Employee employee){
        // 保存员工信息
        employeeDao.save(employee);
        // 访问列表功能(重定向)
        return "redirect:/employee";
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
    public String toUpdate(@PathVariable("id") Integer id, Model model){
        Employee employee = employeeDao.get(id);
        model.addAttribute("employee", employee);
        return "employee_update";
    }

    @RequestMapping(value = "/employee", method=RequestMethod.PUT)
    public String updateEmployee(Employee employee){
        // 保存员工信息
        employeeDao.save(employee);
        // 访问列表功能(重定向)
        return "redirect:/employee";
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE)
    public String deleteEmployee(@PathVariable("id") Integer id) {
        employeeDao.delete(id);
        return "redirect:/employee";
    }

}
