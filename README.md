# FAIEP-Vesna

# 1. Maven 打包
mvn clean package -DskipTests

# 2. 启动（需本机 MySQL 已启动，账号密码见 application.yml）
java -jar target/garbage-platform-1.0.0.jar
