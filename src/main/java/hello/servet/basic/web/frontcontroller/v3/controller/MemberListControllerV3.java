package hello.servet.basic.web.frontcontroller.v3.controller;

import hello.servet.basic.web.frontcontroller.ModelView;
import hello.servet.basic.web.frontcontroller.v3.ControllerV3;
import hello.servet.domain.member.Member;
import hello.servet.domain.member.MemberRepository;

import java.util.List;
import java.util.Map;

public class MemberListControllerV3 implements ControllerV3 {
    private MemberRepository memberRepository = MemberRepository.getInstance();
    @Override
    public ModelView process(Map<String, String> paramMap) {
        List<Member> members = memberRepository.findAll();
        ModelView mv = new ModelView("members");
        mv.getModel().put("members", members);
        return mv;
    }
}
