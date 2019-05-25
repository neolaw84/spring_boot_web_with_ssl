package space.qbpo.secured.web.one;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import space.qbpo.secured.web.one.SwoController.SwoIntegerObject;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers= {SwoController.class})
@Import(value= {SwoService.class})
public class SwoControllerTests {
	@Autowired private MockMvc mvc;
	@Autowired SwoService sWOService; 
	@Autowired ObjectMapper objectMapper;
	
	@Test
	public void addTwoAndFive () throws Exception {
		MvcResult result = mvc.perform(get("/integer/add_two/")
				.param("a", "2")
				.param("b", "5")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				)
		.andExpect(status().isOk())
		//.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andReturn();
		
		SwoIntegerObject myResult = objectMapper.readValue(result.getResponse().getContentAsByteArray(), 
				SwoIntegerObject.class);
		assert(myResult.getInteger() == 7);
		assert("ok".equals(myResult.getStatus()));
	}
	
	
}
