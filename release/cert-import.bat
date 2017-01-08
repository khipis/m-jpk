%~dp0\jre\32bit\jre7\bin\keytool -import -file resources\test-e-dokumenty.mf.gov.pl_ssl.cer -keystore jre\32bit\jre7\lib\security\cacerts -trustcacerts -alias "test-e-dokumenty"
%~dp0\jre\64bit\jre7\bin\keytool -import -file resources\test-e-dokumenty.mf.gov.pl_ssl.cer -keystore jre\64bit\jre7\lib\security\cacerts -trustcacerts -alias "test-e-dokumenty"
%~dp0\jre\32bit\jre8\bin\keytool -import -file resources\test-e-dokumenty.mf.gov.pl_ssl.cer -keystore jre\32bit\jre8\lib\security\cacerts -trustcacerts -alias "test-e-dokumenty"
%~dp0\jre\64bit\jre8\bin\keytool -import -file resources\test-e-dokumenty.mf.gov.pl_ssl.cer -keystore jre\64bit\jre8\lib\security\cacerts -trustcacerts -alias "test-e-dokumenty"

%~dp0\jre\32bit\jre7\bin\keytool -import -file resources\3af5843ae11db6d94edf0ea502b5cd1a.cer -keystore jre\32bit\jre7\lib\security\cacerts -trustcacerts -alias "e-dokumenty"
%~dp0\jre\64bit\jre7\bin\keytool -import -file resources\3af5843ae11db6d94edf0ea502b5cd1a.cer -keystore jre\64bit\jre7\lib\security\cacerts -trustcacerts -alias "e-dokumenty"
%~dp0\jre\32bit\jre8\bin\keytool -import -file resources\3af5843ae11db6d94edf0ea502b5cd1a.cer -keystore jre\32bit\jre8\lib\security\cacerts -trustcacerts -alias "e-dokumenty"
%~dp0\jre\64bit\jre8\bin\keytool -import -file resources\3af5843ae11db6d94edf0ea502b5cd1a.cer -keystore jre\64bit\jre8\lib\security\cacerts -trustcacerts -alias "e-dokumenty"
