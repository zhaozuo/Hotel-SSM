package cn.sdut.interceptor.home;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
/**
 * ǰ̨��¼������
 *
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3) {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		String requestURI = request.getRequestURI();
		Object account = request.getSession().getAttribute("account");
		if(account == null){
			//��ʾδ��¼���ߵ�¼ʧЧ
			String header = request.getHeader("X-Requested-With");
			//�ж��Ƿ���ajax����
			if("XMLHttpRequest".equals(header)){
				//��ʾ��ajax����
				Map<String, String> ret = new HashMap<>();
				ret.put("type", "error");
				ret.put("msg", "��¼�Ự��ʱ��δ��¼�������µ�¼!");
				response.getWriter().write(JSONObject.fromObject(ret).toString());
				return false;
			}
			//��ʾ����ͨ������ת��ֱ���ض��򵽵�¼ҳ��
			response.sendRedirect(request.getServletContext().getContextPath() + "/home/login");
			return false;
		}
		
		return true;
	}

}
