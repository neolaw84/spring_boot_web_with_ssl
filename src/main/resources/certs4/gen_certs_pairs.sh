#!/bin/bash

echo "generating key of ca"
openssl genrsa -des3 -out ca.key 2048

echo "generating ca's certificate (.pem)"
openssl req -x509 -new -nodes -key ca.key -sha256 -days 1825 -out ca.pem

echo "converting ca.pem to pkcs12"
openssl pkcs12 -export -out ca.p12 -in ca.pem -inkey ca.key

echo "preparing an empty keystore"
keytool -genkey -keyalg RSA -alias blar -keystore my_trust_store.ks
keytool -delete -alias blar -keystore my_trust_store.ks

echo "creating the truststore (this command needs an existing jks)"
keytool -import -v -trustcacerts -alias ca -file ca.pem -keystore my_trust_store.ks

echo "generating key 1" 
openssl genrsa -out node1.key 2048

echo "generating key 2"
openssl genrsa -out node2.key 2048

echo "generating csr 1"
openssl req -new -key node1.key -out node1.csr

echo "generating csr 2"
openssl req -new -key node2.key -out node2.csr

echo "generating certificate 1 signed by ca key"
openssl x509 -req -days 1800 -in node1.csr -CA ca.pem -CAkey ca.key -CAcreateserial -out node1.crt -sha256 -extfile node1.ext

echo "generating certificate 2 signed by ca key"
openssl x509 -req -days 1800 -in node2.csr -CA ca.pem -CAkey ca.key -CAcreateserial -out node2.crt -sha256 -extfile node2.ext

echo "generating key-store 1 containing certificate 1 signed by ca key"
openssl pkcs12 -export -in node1.crt -inkey node1.key -name "node1" -out node1.p12

echo "generating key-store 2 containing certificate 2 signed by ca key"
openssl pkcs12 -export -in node2.crt -inkey node2.key -name "node2" -out node2.p12

