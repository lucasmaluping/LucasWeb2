package com.lucas.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.startup.UserConfig;

import com.lucas.contents.UserContent;
import com.lucas.dao.CustomerDAO;
import com.lucas.dao.UserDao;
import com.lucas.dao.factory.CustomerDAOFactory;
import com.lucas.dao.impl.CustomerDAOJdbcImpl;
import com.lucas.dao.impl.CustomerDAOXMLImpl;
import com.lucas.dao.impl.UserDaoJdbcImpl;
import com.lucas.domain.CriteriaCustomer;
import com.lucas.domain.Customer;
import com.lucas.domain.User;
import com.lucas.utils.JSONUtils;
import com.mysql.jdbc.Connection;

/**
 * Servlet implementation class CustomerServlet
 */
@WebServlet(urlPatterns="*.do",name="CustomerServlet")
public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private CustomerDAO customerDAO = new CustomerDAOJdbcImpl();
//	private CustomerDAO customerDAO = new CustomerDAOXMLImpl();
	private CustomerDAO customerDAO = CustomerDAOFactory.getInstance().getCustomerDAO();
	private UserDao userDao = new UserDaoJdbcImpl();
    public CustomerServlet() {
        super();
    }
	
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req, resp);
    }
    
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//    	String method = req.getParameter("method");
//		System.out.println("para:" + method);
//		switch(method) {
//		case "add":
//			addCustomer(req, resp);
//			break;
//		case "query":
//			query(req, resp);
//			break;
//		case "delete":
//			delete(req, resp);
//			break;
//		}
//    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String servletPath = request.getServletPath();
    	System.out.println("servletPath:" + servletPath);
    	String methodName = servletPath.substring(1);
    	methodName = methodName.substring(0, methodName.length()-3);
    	System.out.println("methodName:" + methodName);
    	
    	Method method;
		try {
			method = getClass().getDeclaredMethod(methodName,   HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		} 
    	
    }

	@SuppressWarnings("unused")
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.out.println("delete");
		String idStr = req.getParameter("id");
		int id = 0;
		try {
			id = Integer.valueOf(idStr);
			customerDAO.delete(id);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect("query.do");
		
	}
	
	@SuppressWarnings("unused")
	private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String id = req.getParameter("id");
		String name = req.getParameter("name");
		String oldName = req.getParameter("oldName");
		String phone = req.getParameter("phone");
		String address = req.getParameter("address");
		
		if(!oldName.equalsIgnoreCase(name)) {
			long count = customerDAO.getCountWithName(name);
			if(count > 0) {
				req.setAttribute("message", "用戶名" + name + "已经被占用，请重新选择");
				req.getRequestDispatcher("/updatecustomer.jsp").forward(req, resp);
				return;
			}
		}
		
		Customer customer = new Customer(name, address, phone);
		customer.setId(Integer.valueOf(id));
		customerDAO.update(customer);
		
		resp.sendRedirect("query.do");
	}
	
	
	@SuppressWarnings("unused")
	private void edit(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		System.out.println("edit");
		String forwordPath = "/error.jsp";
		String idStr = req.getParameter("id");
		int id = -1;
		try {
			Customer customer = customerDAO.get(Integer.valueOf(idStr));
			if(customer != null) {
				forwordPath = "/updatecustomer.jsp";
				req.setAttribute("customer", customer);
			}
		} catch (Exception e) {}
		
		req.getRequestDispatcher(forwordPath).forward(req, resp);
		
	}


	@SuppressWarnings("unused")
	private void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("query");
		String name = req.getParameter("name");
		String address = req.getParameter("address");
		String phone = req.getParameter("phone");
		CriteriaCustomer cc = new CriteriaCustomer(name, address, phone);
		List<Customer> customers = customerDAO.getForListWithCriteriaCustomer(cc);
//		req.setAttribute("customers", customers);
//		req.getRequestDispatcher("/index.jsp").forward(req, resp);
		String result = new JSONUtils().parseToJson(customers);
		PrintWriter writer = resp.getWriter();
		writer.write(result);
	}
	
	@SuppressWarnings("unused")
	private void queryUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("queryUser");
//		Enumeration<String> parameterNames = req.getParameterNames();
//		while(parameterNames.hasMoreElements()) {
//			System.out.println("name：" + parameterNames.nextElement());
//		}
		String result = "error";
		String name = req.getParameter("name");
		String password = req.getParameter("password");
		
		List<User> users = userDao.getAll();
		
		
		
		long count = userDao.getCountWithName(name);
		if(count > 0) {
			System.out.println("...ok..");
			for(User user : users) {
				if(user.getName().equals(name) && user.getPassword().equals(password)) {
					result = "ok";
				}
			}
		}
		
//		String result = new JSONUtils().parseToJson(users);
		PrintWriter writer = resp.getWriter();
		writer.write(result);
	}


	@SuppressWarnings("unused")
	private void addCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("addCustomer");
		String name = req.getParameter("name");
		String address = req.getParameter("address");
		String phone = req.getParameter("phone");
		System.out.println("name:" + name + "...address:" + address + "...phone:" + phone);
		
		long count = customerDAO.getCountWithName(name);
		if(count > 0) {
			req.setAttribute("message", "用户名" + name + "已被占用，请重新选择");
			req.getRequestDispatcher("/newcustomer.jsp").forward(req, resp);
			
			PrintWriter writer = resp.getWriter();
			
			return;
		}
		
		Customer customer = new Customer(name, address, phone);
		customerDAO.save(customer);
		resp.sendRedirect("success.jsp");
		
	}
	
	@SuppressWarnings("unused")
	private void addUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String msg = UserContent.error;
		System.out.println("addUser");
		String name = req.getParameter("name");
		String password = req.getParameter("password");
		String age = req.getParameter("age");
		String gender = req.getParameter("gender");
		long countWithName = userDao.getCountWithName(name);
		if(countWithName > 0) {
			msg = UserContent.error_user_exit;
		} else {
			User user = new User(name, password, "", age, gender);
			System.out.println("...user:" + user.toString());
			int result = userDao.save(user);
			if(result > 0) {
				msg = UserContent.ok;
			}
		}
		PrintWriter writer = resp.getWriter();
		writer.write(msg);
		
	}
	
	
	
}
