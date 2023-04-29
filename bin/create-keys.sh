keytool -genkeypair -alias springboot -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore springboot.p12 -validity 3650

keytool -genkeypair -alias springboot -keyalg RSA -keysize 2048 -keystore springboot.jks -validity 3650

keytool -importkeystore -srckeystore springboot.jks -destkeystore springboot.p12 -deststoretype pkcs12