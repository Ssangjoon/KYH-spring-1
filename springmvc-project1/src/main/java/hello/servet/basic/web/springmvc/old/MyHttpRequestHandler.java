package hello.servet.basic.web.springmvc.old;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

import java.io.IOException;

@Component("/springmvc/request-handler")
public class MyHttpRequestHandler implements HttpRequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        System.out.println("MyHttpRequestHandler.handleRequest");
    }
//    1. 핸들러 매핑으로 핸들러 조회
//          - HandlerMapping 을 순서대로 실행해서, 핸들러를 찾는다.
//          - 이 경우 빈 이름으로 핸들러를 찾아야 하기 때문에 이름 그대로 빈 이름으로 핸들러를 찾아주는
//          BeanNameUrlHandlerMapping 가 실행에 성공하고 핸들러인 MyHttpRequestHandler 를 반환한다.
//    2. 핸들러 어댑터 조회
//          - HandlerAdapter 의 supports() 를 순서대로 호출한다.
//          - HttpRequestHandlerAdapter 가 HttpRequestHandler 인터페이스를 지원하므로 대상이 된다.
//    3. 핸들러 어댑터 실행
//          - 디스패처 서블릿이 조회한 HttpRequestHandlerAdapter 를 실행하면서 핸들러 정보도 함께 넘겨준다.
//          - HttpRequestHandlerAdapter 는 핸들러인 MyHttpRequestHandler 를 내부에서 실행하고, 그 결과를반환한다.
}
