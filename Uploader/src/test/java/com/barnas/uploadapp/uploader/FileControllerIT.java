package com.barnas.uploadapp.uploader;

import com.barnas.uploadapp.storage.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import javax.inject.Inject;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 21/10/2020.
 */
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WebMvcTest
@ContextConfiguration(classes = { FileController.class })
class FileControllerIT {

    @Inject
    private MockMvc mockMvc;

    @MockBean
    private FileService fileService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        when(fileService.store(anyString(), any(), anyString()))
                .thenReturn(321L);
    }

    @Test
    public void testUpload() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload")
                .file(new MockMultipartFile("file", "test-file", "application/text", "content of the file".getBytes()))
                .param("description", "description of the file"))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(status().is(200))
                .andExpect(jsonPath("fileName").value("test-file"))
                .andExpect(jsonPath("id").value("321"))
                .andReturn();
    }
}