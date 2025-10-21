package io.github.mrergos.controller;

import io.github.mrergos.client.MembersRestClient;
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
        model.addAttribute("member", membersRestClient.findMemberById(registryNum));
        return "member";
    }
}
