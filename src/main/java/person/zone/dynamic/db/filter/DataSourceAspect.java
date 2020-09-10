package person.zone.dynamic.db.filter;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import person.zone.dynamic.db.config.db.dynamic.DataSourceHolder;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect // 注解的方式配置aop
@Configuration
public class DataSourceAspect {
	@Pointcut("execution(* person.zone.dynamic.db.controller..*.*(..))")
	private void anyMethod() {
	}// 定义一个切入点

	@Before("anyMethod()")
	public void dataSourceChange() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String tenantType = request.getHeader("tenantType");

		if (StringUtils.isEmpty(tenantType)) {
			tenantType = request.getParameter("tenantType").toString();
		}
		log.info("tenantType is " + tenantType);
		DataSourceHolder.setDataSource(tenantType);
	}
}