package io.github.mrergos.controller;

import io.github.mrergos.client.MembersRestClient;
import io.github.mrergos.client.exception.ProblemDetailException;
import io.github.mrergos.entity.MemberNkso;
import io.github.mrergos.util.FieldHelper;
import io.github.mrergos.util.Pair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Controller
@RequestMapping(path = "members")
@RequiredArgsConstructor
public class MemberController {
    private final MembersRestClient membersRestClient;

    @GetMapping
    public String getMemberList(@RequestParam(value = "filter", required = false, defaultValue = "") String filter,
                                @RequestParam(value = "page", defaultValue = "0") int page,
                                Model model) {
        model.addAttribute("filter", filter);
        Pair totalPagesAndMembers = membersRestClient.getMembersPageByFilter(filter, page);
        int totalPages = totalPagesAndMembers.getFirst();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("members", totalPagesAndMembers.getSecond());
        if (totalPages > 1) {
            Set<Integer> pageNumbers = new HashSet<>();
            IntStream.range(1, totalPages-1)
                    .takeWhile(pageNumber -> ((pageNumber > page - 3) && (pageNumber < page + 3)))
                    .forEach(pageNumber -> pageNumbers.add(pageNumber+1));
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "members";
    }

    @GetMapping("/{registryNum}")
    public String getMemberById(@PathVariable("registryNum") String registryNum,
            Model model) {
        MemberNkso member = membersRestClient.findMemberById(registryNum);
        model.addAttribute("fields", membersRestClient.getAllFields(member));
        model.addAttribute("member", member);
        return "member";
    }

    @GetMapping("/{registryNum}/edit")
    public String getMemberEdit(@PathVariable("registryNum") String registryNum,
            Model model) {
        MemberNkso member = membersRestClient.findMemberById(registryNum);
        model.addAttribute("fields", membersRestClient.getAllFields(member));
        model.addAttribute("member", member);
        model.addAttribute("saveIsSuccessful", false);
        return "member_edit";
    }

    @PostMapping("/{registryNum}/edit")
    public String editMemberEdit(MemberNkso member, Model model) {
        MemberNkso updatedMember = membersRestClient.update(member);
        model.addAttribute("member", updatedMember);
        model.addAttribute("fields", membersRestClient.getAllFields(updatedMember));
        model.addAttribute("saveIsSuccessful", true);
        return "member_edit";
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
