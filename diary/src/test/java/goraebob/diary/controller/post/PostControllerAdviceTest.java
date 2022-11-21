package goraebob.diary.controller.post;

import goraebob.diary.handler.ResponseHandler;
import goraebob.diary.service.post.PostService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
public class PostControllerAdviceTest {
    @InjectMocks
    PostController postController;
    @Mock
    PostService postService;
    @Mock
    ResponseHandler responseHandler;
    MockMvc mockMvc;
}
