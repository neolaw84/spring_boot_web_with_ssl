# A Demo Project to Showcase SSL capability of Spring Boot

## Battery Included

For testing in localhost, all certificates, java-key-stores (jks) and PKCS12 key-stores
as well as the trust-stores are included. 

Note: need to make changes to the application-<profile>.properties though. 

## Generating the certs

Go to src/main/resources/certs3 and run ./gen_certs_pairs.sh (for SSL with client-auth).

## Unit-Tests

Just run SwoControllerTests as JUnit Test Suite.

### For SSL (https) API Service without Client Authentication (X509)

1. **Set-up** : Change the properties accordingly in application-noclientauth.properties (especially the path to src/main/resources/qbpo_java.p12).

2. **Prepare** : Run the whole app with spring profiles "default" and "noclientauth" first. 

3. **Test** : Then, run SwoControllerViaRestTests as JUnit Test Suite.
	
### For SSL (https) API Service WITH Client Authentication (X509)

1. **Set-up** : Change the properties accordingly in application-clientauth.properties (especially the path to src/main/resources/certs3/node1.p12 and src/main/resources/certs/node11.jks)

2. **Prepare** : run the whole app with spring profiles "default" and "clientauth" first.

3. **Set-up** : Change the properties accordingly in application-client.properties (especially the path to src/main/resources/certs3/node2.p12 and src/main/resources/certs3/node22.jks).

4. **Test** : Then, run SwoControllerClientAuthViaRestTests as JUnit Test Suite.

