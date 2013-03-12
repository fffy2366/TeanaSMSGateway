EDM：修改CodeDaoImpl.java 直接运行 
功能：模拟post ，调api发邮件

SMS：
1.导出jar 放到test目录下，test目录下新建lib ，把MASMClient.jar 和mysql-connector-java-5.0.7-bin.jar放进去
2.修改MANIFEST.MF
3.linux下执行：java -jar sms.jar >>sms.log &
功能：IP限制，放到服务器执行命令批量发送短信