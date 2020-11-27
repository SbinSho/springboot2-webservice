package com.suho.book.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class) // JUnit에 내장된 실행자 외에 다른 실행자를 실행 시킨다. ( SpringRunner.class )
@WebMvcTest(controllers = HelloContorller.class) // Web(Spring MVC)에 집중할 수 있는 어노테이션 이다.
public class HelloControllerTest {

    @Autowired // 빈 자동 주입.
    private MockMvc mvc; // 웹API를 테스트할 때 사용 한다.

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello")).andExpect(status().isOk()).andExpect(content().string(hello));
    }

    @Test
    public void helooDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))
                // MockMvc를 통해 /hello 주소로 HTTP GET 요청, 체이닝 지원 여러 검증 기능 이어서 선언 가능.
                .andExpect(status().isOk()) // 결과 검증
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));

    }


}