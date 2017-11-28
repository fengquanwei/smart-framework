package com.kilogate.framework;

import com.kilogate.framework.bean.Data;
import com.kilogate.framework.bean.Handler;
import com.kilogate.framework.bean.Param;
import com.kilogate.framework.bean.View;
import com.kilogate.framework.helper.BeanHelper;
import com.kilogate.framework.helper.ConfigHelper;
import com.kilogate.framework.helper.ControllerHelper;
import com.kilogate.framework.util.CodecUtil;
import com.kilogate.framework.util.JsonUtil;
import com.kilogate.framework.util.ReflectionUtil;
import com.kilogate.framework.util.StreamUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求转发器
 *
 * @author fengquanwei
 * @create 2017/11/17 20:49
 **/
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        // 初始化相关的 Helper 类
        HelperLoader.init();

        // 获取 ServletContext 对象（用于注册 Servlet）
        ServletContext servletContext = servletConfig.getServletContext();

        // 注册处理 JSP 的 Servlet TODO 干嘛用的？
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");

        // 注册处理静态资源的默认 Servlet TODO 干嘛用的？
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求方法与请求路径
        String requestMethod = request.getMethod().toLowerCase();
        String requestPath = request.getPathInfo();

        // 获取 Action 处理器
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if (handler != null) {
            // 获取 Controller 类及其 Bean 实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);

            // 创建请求参数对象
            Map<String, Object> paramMap = new HashMap<>();
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String paramValue = request.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }

            String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
            if (StringUtils.isNotBlank(body)) {
                String[] params = body.split("&");
                if (params != null && params.length > 0) {
                    for (String param : params) {
                        String[] array = param.split("=");
                        if (array != null && array.length == 2) {
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramName, paramValue);
                        }
                    }
                }
            }

            Param param = new Param(paramMap);

            // 调用 Action 方法
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);

            // 处理 Action 方法的返回值
            if (result instanceof View) {
                // 返回 JSP 页面
                View view = (View) result;
                String path = view.getPath();
                if (StringUtils.isNotBlank(path)) {
                    if (path.startsWith("/")) {
                        response.sendRedirect(request.getContextPath() + path);
                    } else {
                        Map<String, Object> model = view.getModel();
                        for (Map.Entry<String, Object> entry : model.entrySet()) {
                            request.setAttribute(entry.getKey(), entry.getValue());
                        }
                        request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
                    }
                }
            } else if (result instanceof Data) {
                // 返回 JSON 数据
                Data data = (Data) result;
                Object model = data.getModel();
                if (model != null) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter writer = response.getWriter();
                    String json = JsonUtil.toJson(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }
        }
    }
}
