package com.suho.book.springboot.web;

import com.suho.book.springboot.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexContorller {

    private final PostsService postsService;


    @GetMapping("/")
    public String index(Model model) { // 뷰에 객체를 전달하기 위한 모델
        model.addAttribute("posts", postsService.findAllDesc());

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

}
