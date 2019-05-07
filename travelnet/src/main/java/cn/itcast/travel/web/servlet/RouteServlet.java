package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService=new RouteServiceImpl();
    /*
    分页查询
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");
        //2.处理参数
        int cid=0;//类别id
        if (cidStr!=null && cidStr.length()>0){
            cid=Integer.parseInt(cidStr);
        }

        int currentPage=0;
        if (currentPageStr!=null && currentPageStr.length()>0){
            currentPage=Integer.parseInt(currentPageStr);
        }else {
            currentPage=1;
        }

        int pageSize=0;//每页显示条数,如果不传递,
        if (pageSizeStr!=null && pageSizeStr.length()>0){
            pageSize=Integer.parseInt("pageSizeStr");
        }else {
            pageSize=5;
        }
        //3.调用service查询pageBean对象
        PageBean<Route> pb = routeService.pageQuery(cid, currentPage, pageSize);

       // System.out.println(pb);

        //4.将pageBean对象序列化为json返回
        ObjectMapper mapper=new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getWriter(),pb);
    }

}
