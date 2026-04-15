package com.app.worktest.pro.config;


import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>PURPOSE:
 * <p>DESCRIPTION:
 * <p>CALLED BY:
 * <p>CREATE DATE: 2023/1/17
 * <p>UPDATE DATE: 2023/1/17
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author xkq
 * @version 1.0
 * @see
 * @since java 1.8
 */
@Configuration
public class RestTemplateConfig {

    @Value("${hsa.restTemplate.connectTimeout:30000}")
    private Integer connectTimeout;

    @Value("${hsa.restTemplate.readTimeout:30000}")
    private Integer readTimeout;

	@Value("${hsa.restTemplate.maxConnTotal:200}")
	private Integer maxConnTotal;

	@Value("${hsa.restTemplate.maxConnPerRoute:100}")
	private Integer maxConnPerRoute;

	@Bean
	public RestTemplate restTemplate() {
		HttpClient httpClient = HttpClientBuilder.create()
				.setMaxConnTotal(maxConnTotal)  // 最大连接数
				.setMaxConnPerRoute(maxConnPerRoute) // 每个路由的最大连接数
				.build();
	
		HttpComponentsClientHttpRequestFactory factory =
				new HttpComponentsClientHttpRequestFactory(httpClient);
		factory.setConnectTimeout(connectTimeout);  // 连接超时时间 (ms)
		factory.setReadTimeout(readTimeout);    // 读取超时时间 (ms)
		factory.setConnectionRequestTimeout(2000); // 从连接池获取连接的超时时间
	
		return new RestTemplate(factory);
	}
	/**
	 * 构建支持忽略自签名证书的Restemplate的bean
	 * @return 支持发起https请求的RestTemplate对象
	 * @throws KeyStoreException 证书异常
	 * @throws NoSuchAlgorithmException 加密算法不可用异常
	 * @throws KeyManagementException 密钥管理异常
	 */
	/*
	 * 这个bean暂时注释掉，因为代码混淆后使用@Autowired注入restTemplate会有问题
	 * @Bean("httpsTemplate") public RestTemplate createHttpsRestTemplate() throws
	 * KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
	 * TrustStrategy acceptingTrustStrategy = (x509Certificates, authType) -> true;
	 * SSLContext sslContext = SSLContexts.custom() .loadTrustMaterial(null,
	 * acceptingTrustStrategy) .build(); SSLConnectionSocketFactory sslFactory = new
	 * SSLConnectionSocketFactory( sslContext, new NoopHostnameVerifier());
	 *
	 * CloseableHttpClient httpClient = HttpClients.custom()
	 * .setSSLSocketFactory(sslFactory) .build();
	 *
	 * HttpComponentsClientHttpRequestFactory factory = new
	 * HttpComponentsClientHttpRequestFactory(); factory.setConnectTimeout(3600000);
	 * factory.setReadTimeout(3600000);
	 *
	 * factory.setHttpClient(httpClient);
	 *
	 * return new RestTemplate(factory); }
	 */
}
