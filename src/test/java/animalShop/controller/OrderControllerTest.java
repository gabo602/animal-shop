package animalShop.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;

import animalShop.AbstractTest;
import sk.pga.animalshop.model.dto.OrderItemDto;

@RunWith(SpringRunner.class)
public class OrderControllerTest extends AbstractTest {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	@WithUserDetails("mojemeno")
	public void testPlaceOrder() throws Exception {
		
		List<OrderItemDto> orderItems = List.of(new OrderItemDto(1, 3), new OrderItemDto(1, 1));
		
		RequestBuilder requestBuilder = post("/rest/placeOrder")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(orderItems));
		
		ResultActions result = mockMvc.perform(requestBuilder);
		
		result.andDo(MockMvcResultHandlers.print());
		result.andExpect(status().isCreated());
		
		result = mockMvc.perform(get("/rest/getMyOrders"));
		
		result.andDo(MockMvcResultHandlers.print());
		result.andExpect(status().isOk());
	}
}
