package com.example.noticeboardexample.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.noticeboardexample.config.SecurityConfig;
import com.example.noticeboardexample.repository.FakePostRepository;
import com.example.noticeboardexample.service.PostService;
import com.example.noticeboardexample.service.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest({PostRestController.class, AuthController.class})
@Import({SecurityConfig.class, TokenService.class, PostService.class, FakePostRepository.class})
class PostRestControllerTest {

  @Autowired
  private MockMvc mvc;

  @Test
  void postsWhenUnauthenticatedThen401() throws Exception {
    this.mvc.perform(get("/"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void postsWhenAuthenticatedThen200() throws Exception {
    MvcResult result = this.mvc.perform(post("/api/v1/auth/token")
            .with(httpBasic("seokho", "password")))
        .andExpect(status().isOk())
        .andReturn();

    String token = result.getResponse().getContentAsString();

    this.mvc.perform(get("/api/v1/posts")
            .header("Authorization", "Bearer " + token))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser
  void postsWhenAuthenticatedThen200WithMockUser() throws Exception {
    this.mvc.perform(get("/api/v1/posts"))
        .andExpect(status().isOk());
  }
}