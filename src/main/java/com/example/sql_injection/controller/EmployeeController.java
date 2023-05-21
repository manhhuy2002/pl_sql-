package com.example.sql_injection.controller;

import com.example.sql_injection.DTO.LoginDTO;
import com.example.sql_injection.JDBC.ConnectJDBC;
import com.example.sql_injection.model.Employee;
import com.example.sql_injection.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @GetMapping("/login")
    public String login(Model model){

        model.addAttribute("logindto",new LoginDTO());
        return "login";
    }

    @Autowired
    private ConnectJDBC connectJDBC;

    @GetMapping("/")
    public String login(){
        return "redirect:/login";
    }

    @PostMapping("/checkLogin")
    public String checkLogin(@ModelAttribute("model") LoginDTO logindto, BindingResult result, Model model) throws SQLException {
        final String regex = "order by";
        String sql = MessageFormat.format("SELECT * FROM employees WHERE username =''{0}'' and password=''{1}'' ",logindto.getUsername(),logindto.getPassword());
        final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(sql);
        if(matcher.find()){
            model.addAttribute("logindto",new LoginDTO());
            model.addAttribute("error","Bạn định hack tôi à?");
            return "login";
        }
        if (result.hasErrors()) {
                model.addAttribute("logindto", new LoginDTO());
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu sai rồi!");
            return "login";
        }
        Connection connection = connectJDBC.getConnection();
        PreparedStatement statement = null;
        statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            return "redirect:/dashboard";
        }
        model.addAttribute("logindto", new LoginDTO());
        model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu sai rồi!");
        return "login";
    }


    @GetMapping("logout")
    public String logout(){

        return  "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String viewHomePage(Model model) {
        model.addAttribute("listEmployees", employeeService.getAllEmployees());
        return "index";
    }
        @GetMapping("/showNewEmployeeForm")
        public String showNewEmployeeForm(Model model){
            Employee employee = new Employee();
            model.addAttribute("employee",employee);
            return "new_employee";
        }
    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        // save employee to database
        employeeService.saveEmployee(employee);
        return "redirect:/dashboard";
    }
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable( value = "id") long id, Model model) {

        // get employee from the service
        Employee employee = employeeService.getEmployeeById(id);

        // set employee as a model attribute to pre-populate the form
        model.addAttribute("employee", employee);
        return "update_employee";
    }
    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable (value = "id") long id) {

        // call delete employee method
        this.employeeService.deleteEmployeeById(id);
        return "redirect:/dashboard";
    }
    @GetMapping("/dashboardEmployee")
        public String employeeDashboard() {
            return "employeeDashboard";
        }
}



/*package com.example.sql_injection.controller;

import ch.qos.logback.core.model.Model;
import com.example.sql_injection.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeController {
    // display list of employees
    @Autowired
    private EmployeeService employeeService;
    @GetMapping("/")
    public  String viewHomePage(Model model){
        model.addAttribute("listEmployees",employeeService.getAllEmployees());
        return "index";
    }
}
*/