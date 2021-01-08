package com.barnas.uploadapp.uploader;

import com.barnas.uploadapp.storage.FileDescriptor;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 21/10/2020.
 */
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

        when(fileService.list())
                .thenReturn(List.of(
                        new FileDescriptor("elephant", 33, "big animal", 1L),
                        new FileDescriptor("tiger", 66, "dangerous animal", 2L)
                ));
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

    @Test
    public void uploadFileWithoutDescriptionThrowsException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload")
                .file(new MockMultipartFile("file", "test-file", "application/text", "content of the file".getBytes())))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/list"))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(status().is(200))
                .andExpect(jsonPath("files[0].filename").value("elephant"))
                .andExpect(jsonPath("files[1].filename").value("tiger"))
                .andReturn();
    }

    @Test
    public void testListWithDescriptionFilter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/list").param("description", "dang"))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(status().is(200))
                .andExpect(jsonPath("files[0].filename").value("tiger"))
                .andReturn();
    }

    @Test
    public void testListWithNameFilter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/list").param("name", "pha"))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(status().is(200))
                .andExpect(jsonPath("files[0].filename").value("elephant"))
                .andReturn();
    }

    @Test
    public void testListWithNameAndDescriptionFilter() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/list")
                        .param("description", "anger")
                        .param("name", "ige"))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(status().is(200))
                .andExpect(jsonPath("files[0].filename").value("tiger"))
                .andReturn();
    }

    @Test
    public void testListWithSizeFilter() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/list")
                        .param("size", "66"))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(status().is(200))
                .andExpect(jsonPath("files[0].filename").value("tiger"))
                .andReturn();
    }

    @Test
    public void testListWithSizeFilterNoResult() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/list")
                        .param("size", "123"))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(status().is(200))
                .andExpect(jsonPath("files").isEmpty())
                .andReturn();
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/1"))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        verify(fileService, times(1)).delete(1);
    }
}