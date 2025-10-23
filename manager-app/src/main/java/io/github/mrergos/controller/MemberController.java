package io.github.mrergos.controller;

import io.github.mrergos.client.MembersRestClient;
import io.github.mrergos.client.PageResponse;
import io.github.mrergos.client.exception.ProblemDetailException;
import io.github.mrergos.controller.payload.request.UpdateMemberNksoPayload;
import io.github.mrergos.entity.MemberNkso;
import io.github.mrergos.util.FieldHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping(path = "members")
@RequiredArgsConstructor
public class MemberController {
    private final MembersRestClient membersRestClient;
    private final MessageSource messageSource;


    @GetMapping
    public String getMemberList(@RequestParam(value = "filter", required = false, defaultValue = "") String filter,
                                @RequestParam(value = "page", defaultValue = "0") int page,
                                Model model) {
        PageResponse<MemberNkso> membersPage = membersRestClient.getMembersPageByFilter(filter, page);

        model.addAttribute("membersPage", membersPage);
        model.addAttribute("filter", filter);
        return "members";
    }

    @GetMapping("/{registryNum}")
    public String getMemberById(@PathVariable("registryNum") String registryNum,
            Model model) {
        MemberNkso member = membersRestClient.findMemberById(registryNum);
        model.addAttribute("member", member);
        return "member";
    }

    @GetMapping("/{registryNum}/edit")
    public String getMemberEdit(@PathVariable("registryNum") String registryNum,
            Model model) {
        MemberNkso member = membersRestClient.findMemberById(registryNum);
        model.addAttribute("member", member);
        model.addAttribute("saveIsSuccessful", false);
        return "edit_member";
    }

    @PostMapping("/{registryNum}/edit")
    public String editMemberEdit(@PathVariable("registryNum") String registryNum, @ModelAttribute("member") UpdateMemberNksoPayload payload, BindingResult bindingResult, Model model) {
        MemberNkso member = payload.toMemberNkso();
        member.setRegistryNum(registryNum);

        try {
            MemberNkso updatedMember = membersRestClient.update(member);
            model.addAttribute("member", updatedMember);
            model.addAttribute("saveIsSuccessful", true);
            return "member";
        } catch (ProblemDetailException exception) {
            Map<String, String> errors = this.extractErrors(exception.getProblemDetail());
            errors.forEach((fieldName, message) -> {
                bindingResult.addError(new FieldError("member",fieldName,FieldHelper.getFieldValue(payload, fieldName),false, null, null,  message));
            });
            model.addAttribute("member", member);
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "member", bindingResult);
            model.addAttribute("saveIsSuccessful", false);
            return "edit_member";
        }
    }

    @GetMapping("/create")
    public String createMember(Model model) {
        model.addAttribute("member", new MemberNkso());
        return "create_member";
    }

    @PostMapping("/create")
    public String createMember(@ModelAttribute("member") MemberNkso member,BindingResult bindingResult, Model model) {
        try {
            MemberNkso savedMember = membersRestClient.create(member);
            return "redirect:/members/" + savedMember.getRegistryNum();
        } catch (ProblemDetailException exception) {
            Map<String, String> errors = this.extractErrors(exception.getProblemDetail());
            errors.forEach((fieldName, message) -> {
                bindingResult.addError(new FieldError("member",fieldName,FieldHelper.getFieldValue(member, fieldName),false, null, null,  message));
            });
            model.addAttribute("member", member);
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "member", bindingResult);
            return "create_member";
        }
    }

    private Map<String, String> extractErrors(ProblemDetail problemDetail) {
        if (problemDetail.getProperties().get("errors") instanceof List<?> list) {
            return list.stream()
                    .filter(Map.class::isInstance)
                    .map(Map.class::cast)
                    .collect(Collectors.toMap(
                            map -> map.get("field").toString(),
                            map -> map.get("message").toString()
                    ));
        } else {
            return Map.of();
        }
    }
}
