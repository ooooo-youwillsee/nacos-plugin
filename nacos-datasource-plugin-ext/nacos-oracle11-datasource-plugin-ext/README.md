## 提供nacos 对 oracle11 的支持

> nacos 的插件体系还在完善中，所以可能需要对代码重新改动
> 当前提供 2.3 版本的插件
> 配置管理，public namespace 支持有bug

1. `driver/ojdbc6-11.2.0.3.jar`, oracle11 的驱动包
2. `driver/orai18n-11.2.0.3.jar`，如果数据库是utf8字符集，则不需要
3. `driver/nacos-oracle11-datasource-plugin-ext-xxx.jar`, nacos的插件
4. `resources/schema/nacos-oracle.sql`, oracle 的脚本, 需要自己创建用户和表空间


application.properties 配置示例

```properties
#*************** Config Module Related Configurations ***************#
### Deprecated configuration property, it is recommended to use `spring.sql.init.platform` replaced.
# spring.datasource.platform=mysql
# nacos.plugin.datasource.log.enabled=true
#spring.sql.init.platform=mysql
spring.sql.init.platform=oracle
### Count of DB:
db.num=1

### Connect URL of DB:
#db.url=jdbc:mysql://127.0.0.1:3306/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC
#db.user=root
#db.password=root
db.url=jdbc:oracle:thin:@172.16.1.104:1521:orcl
db.user=xxx
db.password=xxx
db.pool.config.driverClassName=oracle.jdbc.driver.OracleDriver
db.pool.config.connectionTestQuery=select '1' from dual
```

