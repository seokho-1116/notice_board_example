package com.example.noticeboardexample.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.noticeboardexample.config.RestControllerAuthTestConfiguration;
import com.example.noticeboardexample.controller.request.SignUpRequest;
import com.example.noticeboardexample.controller.request.SignInRequest;
import com.example.noticeboardexample.controller.response.ResponseWrapper;
import com.example.noticeboardexample.repository.FakePostRepository;
import com.example.noticeboardexample.service.PostService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest({PostRestController.class, AuthRestController.class})
@Import({RestControllerAuthTestConfiguration.class})
class PostRestControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeAll
  static void init(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
    SignUpRequest request = new SignUpRequest("test", "12345678");
    mvc.perform(post("/api/v1/auth/sign-up")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)));
  }

  @Test
  void postsWhenUnauthenticatedThen401() throws Exception {
    this.mvc.perform(get("/"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void postsWhenAuthenticatedThen200() throws Exception {
    SignInRequest request = new SignInRequest("test", "12345678");
    MvcResult result = this.mvc.perform(post("/api/v1/auth/sign-in")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andReturn();

    ResponseWrapper<String> response = objectMapper.readValue(
        result.getResponse().getContentAsString(), new TypeReference<>() {
        });

    this.mvc.perform(get("/api/v1/posts")
            .header("Authorization", "Bearer " + response.data()))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser
  void postsWhenAuthenticatedThen200WithMockUser() throws Exception {
    this.mvc.perform(get("/api/v1/posts"))
        .andExpect(status().isOk());
  }

  @TestConfiguration
  static class PostRestControllerTestConfig {

    @Bean
    public PostService postService() {
      return new PostService(new FakePostRepository());
    }
  }
}