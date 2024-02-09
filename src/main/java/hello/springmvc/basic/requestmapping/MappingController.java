package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {
    private Logger log = LoggerFactory.getLogger(getClass());
    /**
     * 기본 요청
     * 둘다 허용 /hello-basic, /hello-basic/
     * HTTP 메서드 모두 허용 GET, HEAD, POST, PUT, PATCH, DELETE
     */
    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        log.info("mappingGetV1");
        return "ok";
    }
    /**
     * 편리한 축약 애노테이션 (코드보기)
     * @GetMapping
     * @PostMapping
     * @PutMapping
     * @DeleteMapping
     * @PatchMapping
     */
    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mapping-get-v2");
        return "ok";
    }
    /**
     * PathVariable 사용
     * 변수명이 같으면 생략 가능
     * @PathVariable("userId") String userId -> @PathVariable String userId
     */
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data) {
        // @PathVariable 의 이름과 파라미터 이름이 같으면 생략할 수 있다.
        log.info("mappingPath userId={}", data);
        return "ok";
    }
    /**
     * PathVariable 사용 다중
     */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long
            orderId) {
        log.info("mappingPath userId={}, orderId={}", userId, orderId);
        return "ok";
    }
    /**
     * 파라미터로 추가 매핑
     * params="mode",
     * params="!mode"
     * params="mode=debug"
     * params="mode!=debug" (! = )
     * params = {"mode=debug","data=good"}
     */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
        // 특정 파라미터가 있거나 없는 조건을 추가할 수 있다. 잘 사용하지는 않는다.
        log.info("mappingParam");
        return "ok";
    }
    /**
     * 특정 헤더로 추가 매핑
     * headers="mode",
     * headers="!mode"
     * headers="mode=debug"
     * headers="mode!=debug" (! = )
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok";
    }
    /**
     * Content-Type 헤더 기반 추가 매핑 Media Type
     * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     */
    @PostMapping(value = "/mapping-consume", consumes = "application/json")
    public String mappingConsumes() {
        // HTTP 요청의 Content-Type 헤더를 기반으로 미디어 타입으로 매핑한다.
        // 만약 맞지 않으면 HTTP 415 상태코드(Unsupported Media Type)을 반환한다.
        /*consumes = "text/plain"
        consumes = {"text/plain", "application/*"}
        consumes = MediaType.TEXT_PLAIN_VALUE*/
        log.info("mappingConsumes");
        return "ok";
    }
    /**
     * Accept 헤더 기반 Media Type
     * produces = "text/html"
     * produces = "!text/html"
     * produces = "text/*"
     * produces = "*\/*"
     */
    @PostMapping(value = "/mapping-produce", produces = "text/html")
    public String mappingProduces() {
        /*
        HTTP 요청의 Accept 헤더를 기반으로 미디어 타입으로 매핑한다.
        만약 맞지 않으면 HTTP 406 상태코드(Not Acceptable)을 반환한다.
        produces = "text/plain"
        produces = {"text/plain", "application/*"}
        produces = MediaType.TEXT_PLAIN_VALUE
        produces = "text/plain;charset=UTF-8"*/
        log.info("mappingProduces");
        return "ok";
    }
}
