package ru.job4j.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.job4j.dto.FileDto;
import ru.job4j.service.FileService;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FileControllerTest {
    @Mock
    private FileService fileService;
    @InjectMocks
    private FileController fileController;
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(fileController).build();
    }

    @DisplayName("Get File id 12 - ok")
    @Test
    void whenGetFileThenOk() throws Exception {
        int expectedId = 12;
        byte[] expectedBytes = "Test content".getBytes(StandardCharsets.UTF_8);
        FileDto fileDto = new FileDto("file.txt", expectedBytes);
        Mockito.doReturn(Optional.of(fileDto)).when(fileService).getFileById(expectedId);

        mvc.perform(get("/files/" + expectedId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/octet-stream"))
                .andExpect(content().bytes(expectedBytes));

        Mockito.verify(fileService, Mockito.times(1)).getFileById(expectedId);
    }

    @DisplayName("Get File - NotFound")
    @Test
    void whenGetFileThenNotFound() throws Exception {
        Mockito.doReturn(Optional.empty()).when(fileService).getFileById(Mockito.anyInt());

        mvc.perform(get("/files/12"))
                .andExpect(status().isNotFound());

        Mockito.verify(fileService, Mockito.times(1)).getFileById(Mockito.anyInt());
    }
}