#!/bin/bash

echo "generating key 1" 
openssl genrsa -des3 -out node1.key 1024

echo "generating key 2"
openssl genrsa -des3 -out node2.key 1024

echo "generating csr 1"
openssl req -new -key node1.key -out node1.csr

echo "generating csr 2"
openssl req -new -key node2.key -out node2.csr

echo "generating certificate 1 signed by key 2"
openssl x509 -req -days 3650 -in node1.csr -signkey node2.key -out node1.crt

echo "generating certificate 2 signed by key 1"
openssl x509 -req -days 3650 -in node2.csr -signkey node1.key -out node2.crt

echo "generating certificate 1 signed by key 1"
openssl x509 -req -days 3650 -in node1.csr -signkey node1.key -out node11.crt

echo "generating certificate 2 signed by key 2"
openssl x509 -req -days 3650 -in node2.csr -signkey node2.key -out node22.crt

echo "generating trust-store 2 containing certificate 1 signed by key 2"
keytool -import -file node11.crt -alias node11 -keystore node22.jks

echo "generating trust-store 1 containing certificate 2 signed by key 1"
keytool -import -file node22.crt -alias node22 -keystore node11.jks

echo "generating key-store 1 containing certificate 1 signed by key 1"
openssl pkcs12 -export -in node11.crt -inkey node1.key -name "node1" -out node1.p12

echo "generating key-store 2 containing certificate 2 signed by key 2"
openssl pkcs12 -export -in node22.crt -inkey node2.key -name "node2" -out node2.p12

