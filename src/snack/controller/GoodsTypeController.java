package snack.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.server.ServerCloneException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import snack.dao.GoodsTypeDao;
import snack.util.StringUtil;

@WebServlet("/type")
public class GoodsTypeController extends HttpServlet{
	/* 每当客户请求当前的servlet时，先调用这个方法判断请求类型，然后转发
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void service(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
		req.setCharacterEncoding("utf-8"); // 将请求的编码转换
		resp.setCharacterEncoding("utf-8");
		super.service(req, resp);
	}
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		String op = request.getParameter("op");
		if ( "add".equals(op) ){
			add(request,response);
		}else if("finds".equals(op)){
			finds(request,response);
		}else if("find".equals(op)){
			find(request,response);
		}else if("update".equals(op)){
			update(request,response);
		}
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String tid = request.getParameter("tid");
		String status = request.getParameter("status");
		
		int result = 0;
		if(StringUtil.checkNull(tid,status)){
			result = -1;
			
		}else{
			GoodsTypeDao goodsTypeDao = new GoodsTypeDao();
			result = goodsTypeDao.update(tid, Integer.parseInt(status));
		}
		
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		
	}
	

	private void find(HttpServletRequest request, HttpServletResponse response) throws IOException {
		GoodsTypeDao goodsTypeDao = new GoodsTypeDao();
		
		
		List<Map<String , String>> list = goodsTypeDao.find();
		
		PrintWriter out = response.getWriter();
		
		
		//以json格式发送数据
		Gson gson = new GsonBuilder().serializeNulls().create();
		out.print(gson.toJson(list));
		out.flush();
		
		
	}

	private void finds(HttpServletRequest request, HttpServletResponse response) throws IOException {
GoodsTypeDao goodsTypeDao = new GoodsTypeDao();
		
		
		List<Map<String , String>> list = goodsTypeDao.finds();
		
		PrintWriter out = response.getWriter();
		
		
		//以json格式发送数据
		Gson gson = new GsonBuilder().serializeNulls().create();
		out.print(gson.toJson(list));
		out.flush();
		
		
	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String tname = request.getParameter("tname");
		
		
		int result = 0;
		if(StringUtil.checkNull(tname)){
			result = -1;
			
		}else{
			GoodsTypeDao goodsTypeDao = new GoodsTypeDao();
			result = goodsTypeDao.add(tname);
		}
		
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		
		
	}
}
