package snack.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import snack.dao.AdminInfoDao;



@WebServlet("/admin")
// 说明Servlet通过<项目名>/admin 访问
public class AdminInfoController extends HttpServlet {
//	private static final long SerialVersionUID = -239648234435443532230L;

	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8"); // 将请求的编码转换
		resp.setCharacterEncoding("utf-8");
		super.service(req, resp);
	}

	/*
	 * 如果用户发送的是get请求，则会调用这个方法处理
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp); // 如果用户发送的是GET请求，那么也交给doPost()方法处理

	}
	/*
	 * 如果用户发送的是post请求，则会调用这个方法处理
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");

		if ("login".equals(op)) {
			login(request, response);

		}
	}

	/**
	 * 处理后台管理员登录的方法
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void login(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String account = request.getParameter("account");
		String pwd = request.getParameter("pwd");
		
		//调用DAO层校验用户输入的账号密码是否正确
		AdminInfoDao adminInfoDao =  new AdminInfoDao();
		Map<String,String> map = adminInfoDao.login(account, pwd);
		
		PrintWriter out = response.getWriter();
		if(map==null){  //没有找到对应的账号密码
			out.print("0");
		}else{
			//说明登录成功，将当前用户信息保存到session，以便后面页面用到
			request.getSession().setAttribute("currentLoginAdmin", map);
			out.print("1");
			
		}
		out.flush();
		
		}

}
