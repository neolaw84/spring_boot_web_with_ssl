package space.qbpo.secured.web.one;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import space.qbpo.secured.web.one.SwoController.SwoIntegerObject;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles= {"default"})
public class SwoControllerViaRestTests {
	
	@Autowired RestTemplateBuilder restTemplateBuilder;
	
	/**
	 * This test case requires the followings to be put as VM Arguments:
	 * -Djavax.net.ssl.trustStore="full/path/to/cacerts" -Djavax.net.ssl.trustStorePassword="password"
	 * the one in git repo (src/main/resources/certs3/node22.jks has certificate for 'localhost' with password 'abcd1234'
	 */
	@Test
	public void addTwoAndFive() {
		UriComponentsBuilder uriComponentBuilder = UriComponentsBuilder.fromHttpUrl(
				"https://localhost:8080/integer/add_two/"
				)
				.queryParam("a", new Integer(2))
				.queryParam("b", new Integer(5));
		RestTemplate restTemplate = restTemplateBuilder.build();
		String uriString = uriComponentBuilder.toUriString();
		System.out.println(uriString);
		SwoIntegerObject result = restTemplate.getForObject(
			uriString, SwoIntegerObject.class
		);
		assert(result.getInteger() == 7);
	}
}
