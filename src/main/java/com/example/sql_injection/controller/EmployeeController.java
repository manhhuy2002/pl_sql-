package com.example.sql_injection.controller;

import com.example.sql_injection.DTO.LoginDTO;
import com.example.sql_injection.JDBC.ConnectJDBC;
import com.example.sql_injection.model.Employee;
import com.example.sql_injection.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;


@Controller
@Slf4j
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


//    @PostMapping("/login")
//    public String checkLogin(@ModelAttribute("model") LoginDTO logindto, BindingResult result, Model model) throws SQLException {
//        final String procedureCall = "{ call check_login(?, ?, ?) }";
//
//        if (result.hasErrors()) {
//            model.addAttribute("logindto", new LoginDTO());
//            model.addAttribute("error", "Wrong username or password!");
//            return "login";
//        }
//
//        try (Connection connection = connectJDBC.getConnection();
//             CallableStatement statement = connection.prepareCall(procedureCall)) {
//            statement.setString(1, logindto.getUsername());
//            statement.setString(2, logindto.getPassword());
//            statement.registerOutParameter(3, OracleTypes.CURSOR);
//
//            statement.execute();
//
//            try (ResultSet resultSet = ((OracleCallableStatement) statement).getCursor(3)) {
//                List<ResponseSQL> responseSQLS = new ArrayList<>();
//
//                while (resultSet.next()) {
//                    String username = resultSet.getString("username");
//                    String password = resultSet.getString("password");
//                    ResponseSQL responseSQL = new ResponseSQL(username, password);
//                    responseSQLS.add(responseSQL);
//                }
//
//                if (responseSQLS.size() != 0) {
//                    String username = responseSQLS.get(0).getUsername();
//                    String password = responseSQLS.get(0).getPassword();
//
//                    if (username != null && password != null && username.equals("administrator123@z") && password.equals("jlijsfnsf99wrsnlf")) {
//                        return "redirect:/dashboard";
//                    } else if (checkCredentialsInDatabase(username, password)) {
//                        return "redirect:/dashboardEmployee";
//                    } else {
//                        model.addAttribute("results", responseSQLS);
//                        model.addAttribute("query", logindto.getUsername());
//                        return "data";
//                    }
//                }
//            }
//        }
//    }
@PostMapping("/login")
public String checkLogin(@ModelAttribute("model") LoginDTO logindto, BindingResult result, Model model) throws SQLException {
    if (result.hasErrors()) {
        model.addAttribute("logindto", new LoginDTO());
        model.addAttribute("error", "Wrong username or password!");
        return "login";
    }
    Connection connection = null;
    CallableStatement cstmt = null;
    String loginResult = null;


        // Kết nối tới cơ sở dữ liệu
        connection = connectJDBC.getConnection();

        // Tạo đối tượng CallableStatement và gọi function
        cstmt = connection.prepareCall("{? = call chelogin(?, ?)}");
        cstmt.registerOutParameter(1, Types.VARCHAR); // Đăng ký tham số đầu ra kiểu VARCHAR
        cstmt.setString(2, logindto.getUsername());
        cstmt.setString(3, logindto.getPassword());
        cstmt.execute();

        // Lấy giá trị trả về từ function
        loginResult = cstmt.getString(1);


// Xử lý kết quả
    if (loginResult != null && loginResult.equals("Login Succeeded")) {
        return "redirect:/dashboard";
    } else {
        model.addAttribute("logindto", new LoginDTO());
        model.addAttribute("error", "Wrong username or password1111!");
        return "login";
    }


}

//    @PostMapping("/login")
//    public String checkLogin(@ModelAttribute("model") LoginDTO logindto, BindingResult result, Model model) throws SQLException {
//        final String regex = "jqk";
//        String sql = MessageFormat.format("SELECT username,password FROM employees WHERE username =''{0}'' and password=''{1}'' ",logindto.getUsername(),logindto.getPassword());
//        log.info(sql);
//        final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
//        final Matcher matcher = pattern.matcher(sql);
//        if(matcher.find()){
//            model.addAttribute("logindto",new LoginDTO());
//            model.addAttribute("error","Do you want to hack me?");
//            return "login";
//        }
//        if (result.hasErrors()) {
//            model.addAttribute("logindto", new LoginDTO());
//            model.addAttribute("error", "Wrong username or password!");
//            return "login";
//        }
//        Connection connection = connectJDBC.getConnection();
//        CallableStatement cst = connection.prepareCall("{CALL loginTo(?,?)}");
//        cst.registerOutParameter(2, Types.INTEGER);
//        cst.setInt(1,20);
//        cst.execute();
//        int res = cst.getInt(2);
//        System.out.println("result is: "+res);
//        cst.close();
//        PreparedStatement statement = null;
//        statement = connection.prepareStatement(sql);
//        ResultSet resultSet = statement.executeQuery();
//        List<ResponseSQL> responseSQLS = new ArrayList<>();
//        while(resultSet.next()) {
//            String username = resultSet.getString("username");
//            String password = resultSet.getString("password");
//            ResponseSQL responseSQL = new ResponseSQL(username, password);
//            responseSQLS.add(responseSQL);
//
//        }
//        if (responseSQLS.size() != 0) {
//            String username = responseSQLS.get(0).getUsername();
//            String password = responseSQLS.get(0).getPassword();
//            if (username != null && password != null && username.equals("administrator123@z") && password.equals("jlijsfnsf99wrsnlf")) {
//                return "redirect:/dashboard";
//            } else if (checkCredentialsInDatabase(username, password)) {
//                return "redirect:/dashboardEmployee";
//            } else {
//                model.addAttribute("results",responseSQLS);
//                model.addAttribute("query", logindto.getUsername());
//                return "data";
//            }
//        }
//
//        model.addAttribute("logindto", new LoginDTO());
//        model.addAttribute("error", "Wrong username or password!");
//        return "login";
//    }
//
//    private boolean checkCredentialsInDatabase(String username, String password) {
//        try {
//            Connection connection = connectJDBC.getConnection();
//            String query1 = "SELECT COUNT(*) FROM employees WHERE username = ? AND password = ?";
//            PreparedStatement statement = connection.prepareStatement(query1);
//            statement.setString(1, username);
//            statement.setString(2, password);
//            ResultSet resultSet = statement.executeQuery();
//
//            // Kiểm tra số lượng bản ghi trả về
//            resultSet.next();
//            int count = resultSet.getInt(1);
//
//            // Trả về true nếu tồn tại bản ghi, ngược lại trả về false
//            return count > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }



// bind param
//    @PostMapping("/checkLogin")
//    public String checkLogin(@ModelAttribute("model") LoginDTO logindto, BindingResult result, Model model) throws SQLException {
//
//    String username = logindto.getUsername();
//        String password = logindto.getPassword();
//        String sql = "SELECT * FROM employees WHERE username = ? and password = ?";
//
//        if (result.hasErrors()) {
//            model.addAttribute("logindto", new LoginDTO());
//            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu sai rồi!");
//            return "login";
//        }
//
//        Connection connection = connectJDBC.getConnection();
//        PreparedStatement statement = connection.prepareStatement(sql);
//        statement.setString(1, username);
//        statement.setString(2, password);
//        ResultSet resultSet = statement.executeQuery();
//
//        if (resultSet.next()) {
//            return "redirect:/dashboard";
//        } else {
//            model.addAttribute("logindto", new LoginDTO());
//            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu sai rồi!");
//            return "login";
//        }
//    }







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