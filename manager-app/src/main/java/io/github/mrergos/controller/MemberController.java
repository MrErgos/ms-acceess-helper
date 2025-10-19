package io.github.mrergos.controller;

import io.github.mrergos.client.MembersRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "members/")
@RequiredArgsConstructor
public class MemberController {
    private final MembersRestClient membersRestClient;

    @GetMapping
    public String getMemberList(Model model) {
        model.addAttribute("members", membersRestClient.findAllMembers());
        return "members";
    }

    @GetMapping("/{registryNum}")
    public String getMemberById(@PathVariable("registryNum") String registryNum,
            Model model) {
        model.addAttribute("member", membersRestClient.findMemberById(registryNum));
        return "member";
    }
}
