package com.mediastore.audio.controller;

import java.net.URI;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(SpringJUnit4ClassRunner.class)
public class AudioControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private AudioController audioController;
	
	@Test
	public void testAudioUpload()
	{
//		MockMultipartFile mockMultipartFile = new MockMultipartFile("", null); 
//		MOck
//		
//		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(new URI("http://localhost:8080/audio"))).andReturn();
		//Assert.assertEquals(true, true);

	}
	
}
