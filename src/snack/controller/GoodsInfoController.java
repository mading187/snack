package snack.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import snack.dao.GoodsInfoDao;
import snack.util.FileUploadUtil;
import snack.util.StringUtil;

import com.google.gson.Gson;



@WebServlet("/goods")
public class GoodsInfoController extends HttpServlet {
	/*
	 * 每当客户请求当前的servlet时，先调用这个方法判断请求类型，然后转发 (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
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

		if ("upload".equals(op)) { // 处理商品详情里面的图片上传
			upload(request, response);
		}else if("addGood".equals(op)){
			addGood(request, response);
		}else if("fingByGid".equals(op)){  //根据商品编号查询商品详情
			findByGid(request,response);
		}else if("finds".equals(op)){
			finds(request,response);
		}else if("findByPage".equals(op)){
			findByPage(request,response);
		}
	}
	
	/**根据编号查询
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void findByGid(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String gid = request.getParameter("gid");
		Map<String,String> map =null;
		
		if(StringUtil.checkNull(gid)){
			map = Collections.emptyMap();
		}
		else{
			GoodsInfoDao goodsInfoDao =new GoodsInfoDao();
			map = goodsInfoDao.findByGid(gid);
		}
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		out.print(gson.toJson(map));
		out.flush();
	}
	
	
	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void finds(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String tid = request.getParameter("tid");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		
		if(StringUtil.checkNull(page)){
			page= "1";
		}
		
		
		if(StringUtil.checkNull(rows)){
			rows = "20";
		}
		
		
		GoodsInfoDao goodsInfoDao = new GoodsInfoDao();
		int total = goodsInfoDao.total(tid);
		List<Map<String , String>> list = goodsInfoDao.findByPage(tid, Integer.parseInt(page), Integer.parseInt(rows));
		
		Map<String , Object> result = new HashMap<String, Object>();
		
		result.put("total", total);
		result.put("rows", list);
		
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		out.print(gson.toJson(result));
		out.flush();
		
		
	}
	
	
	private void findByPage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String tid = request.getParameter("tid");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		
		if(StringUtil.checkNull(page)){
			page= "1";
		}
		
		
		if(StringUtil.checkNull(rows)){
			rows = "20";
		}
		
		
		GoodsInfoDao goodsInfoDao = new GoodsInfoDao();
		List<Map<String , String>> list = goodsInfoDao.findByPage(tid, Integer.parseInt(page), Integer.parseInt(rows));
		
		
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		out.print(gson.toJson(list));
		out.flush();
		
	}
	
	
	

	/**添加商品
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void addGood(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		FileUploadUtil fuu = new FileUploadUtil();

		PageContext pageContext = JspFactory
				.getDefaultFactory()
				.getPageContext(this, request, response, null, true, 2048, true);
		int result = 0;

		Map<String, String> map;
		try {
			map = fuu.uploads(pageContext);
			GoodsInfoDao goodsInfoDao = new GoodsInfoDao();
			result = goodsInfoDao.add(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
	}

	/**
	 * 处理后台管理员登录的方法
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void upload(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		FileUploadUtil fuu = new FileUploadUtil();

		PageContext pageContext = JspFactory
				.getDefaultFactory()
				.getPageContext(this, request, response, null, true, 2048, true);

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, String> map = fuu.upload(pageContext);
			result.put("fileName", map.get("fileName"));
			result.put("uploaded", 1);
			result.put("url", "../../" + map.getOrDefault("upload", ""));

		} catch (Exception e) {
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		out.print(gson.toJson(result));
		out.flush();

	}

}
