package animalShop.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import animalShop.AbstractTest;
import sk.pga.animalshop.model.enums.AnimalCategory;
import sk.pga.animalshop.model.projection.ProductFull;
import sk.pga.animalshop.service.ProductService;

@RunWith(SpringRunner.class)
public class ProductControllerTest extends AbstractTest {
	
	@MockBean
	private ProductService productService;
	
	@Test
	public void testFindProduct() throws Exception {
		
		class GalleryViewImpl implements ProductFull.GalleryView {
			
			@Override
			public Integer getId() {
				return 1;
			}
			
			@Override
			public String getUrl() {
				return "http://localhost:8080/image.jpg";
			}
			
		}
		
		class ProductFullImpl implements ProductFull {
			
			@Override
			public Integer getId() {
				return 1;
			}
			
			@Override
			public String getName() {
				return "product";
			}
			
			@Override
			public BigDecimal getPrice() {
				return BigDecimal.TEN;
			}
			
			@Override
			public AnimalCategory getAnimalCategory() {
				return AnimalCategory.DOG;
			}
			
			@Override
			public String getDescription() {
				return "description";
			}
			
			@Override
			public List<ProductFull.GalleryView> getGallery() {
				return List.of(new GalleryViewImpl());
			}
			
		}
		
		ProductFull testProduct = new ProductFullImpl();
		
		when(productService.findAllById(1)).thenReturn(testProduct);
		
		ResultActions result = mockMvc.perform(get("/rest/getProduct").param("productId", "1")
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andDo(MockMvcResultHandlers.print());
		result.andExpect(status().isOk());
	}
}
