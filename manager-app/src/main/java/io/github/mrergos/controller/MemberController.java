package io.github.mrergos.controller;

import io.github.mrergos.client.MembersRestClient;
import io.github.mrergos.client.exception.ProblemDetailException;
import io.github.mrergos.entity.MemberNkso;
import io.github.mrergos.util.Pair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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
        model.addAttribute("fields", membersRestClient.getAllEmptyFields());
        model.addAttribute("errors", null);
        return "create_member";
    }

    @PostMapping("/create")
    public String createMember(MemberNkso member) {
        MemberNkso savedMember = membersRestClient.create(member);
        return "redirect:/members/" + savedMember.registryNum();
    }

    @ExceptionHandler(ProblemDetailException.class)
    public String handleIllegalArgumentExceptionWithProblemDetail(ProblemDetailException exception, Model model) {
        model.addAttribute("fields", membersRestClient.getAllFields(exception.getMember()));

        Map<String, String> errors = new HashMap<>();
        if (exception.getProblemDetail().getProperties().get("errors") instanceof List list) {
            list.forEach(stringStringLinkedHashMap -> {
                if (stringStringLinkedHashMap instanceof LinkedHashMap map) {
                    errors.put((String) map.get("first"), (String) map.get("second"));
                }
            });
        }
        model.addAttribute("errors", errors);
        return "create_member";
    }
}
