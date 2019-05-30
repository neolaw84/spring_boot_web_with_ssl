package space.qbpo.secured.web.one;

import javax.net.ssl.SSLContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import space.qbpo.secured.web.one.SwoController.SwoIntegerObject;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles= {"client"})
public class SwoControllerClientAuthViaRestTests {
	
	@Autowired RestTemplateBuilder restTemplateBuilder;
	
	@Value(value="${server.ssl.key-store}") Resource keyStore = null;
	@Value(value="${server.ssl.trust-store}") Resource trustStore = null;
	
    public RestTemplate restTemplate(RestTemplateBuilder builder) throws Exception {

        SSLContext sslContext = SSLContextBuilder
                .create()
                .loadKeyMaterial(keyStore.getFile(), "abcd1234".toCharArray(), "abcd1234".toCharArray())
                .loadTrustMaterial(trustStore.getFile(), "abcd1234".toCharArray())
                .build();

        HttpClient client = HttpClients.custom()
                .setSSLContext(sslContext)
                .build();
        
        return builder
        		.requestFactory(() -> new HttpComponentsClientHttpRequestFactory(client))
        		.build();
    }
	
	/**
	 * This test case requires the followings to be put as VM Arguments:
	 * -Djavax.net.ssl.trustStore="full/path/to/cacerts" -Djavax.net.ssl.trustStorePassword="password"
	 * the "node2.jks" in git repo has certificate for 'localhost' with password 'changeit'
	 * @throws Exception 
	 * @throws RestClientException 
	 */
	@Test
	public void addTwoAndFive() throws RestClientException, Exception {
		UriComponentsBuilder uriComponentBuilder = UriComponentsBuilder.fromHttpUrl(
				"https://localhost:8443/integer/add_two/"
				)
				.queryParam("a", new Integer(2))
				.queryParam("b", new Integer(5));
		//RestTemplate restTemplate = restTemplateBuilder.build();
		String uriString = uriComponentBuilder.toUriString();
		System.out.println(uriString);
		SwoIntegerObject result = restTemplate(restTemplateBuilder)
				.getForObject(
			uriString, SwoIntegerObject.class
		);
		assert(result.getInteger() == 7);
	}
}
