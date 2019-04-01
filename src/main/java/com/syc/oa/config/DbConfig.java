package com.syc.oa.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 在SpringBoot中不提倡使用xml文件,所以xml对应的配置可以交给配置类来完成.
 */
@Configuration
@MapperScan(basePackages = "com.syc.oa.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class DbConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbConfig.class);

    //@Value注解的作用:引用配置文件中的属性值

    @Value("${spring.datasource.druid.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.druid.username}")
    private String username;

    @Value("${spring.datasource.druid.password}")
    private String password;

    @Value("${spring.datasource.druid.driver-class-name}")
    private String driverClassName;

    /*@Value("${spring.datasource.type}")
    private String dbType;

    @Value("${spring.datasource.druid.initial-size}")
    private int initialSize;

    @Value("${spring.datasource.druid.min-idle}")
    private int minIdle;

    @Value("${spring.datasource.druid.max-active}")
    private int maxActive;

    @Value("${spring.datasource.druid.max-wait}")
    private int maxWait;

    @Value("${spring.datasource.druid.time-between-eviction-runs-millis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.druid.min-evictable-idle-time-millis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.druid.validation-query}")
    private String validationQuery;

    @Value("${spring.datasource.druid.test-while-idle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.druid.test-on-borrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.druid.test-on-return}")
    private boolean testOnReturn;

    @Value("${spring.datasource.druid.pool-prepared-statements}")
    private boolean poolPreparedStatements;

    @Value("${spring.datasource.druid.max-pool-prepared-statement-per-connection-size}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${spring.datasource.druid.filters}")
    private String filters;

    @Value("${spring.datasource.druid.connection-properties}")
    private String connectionProperties;

    @Value("${spring.datasource.druid.password-callback}")
    private String passwordCallbackClassName;*/

    //@Qualifier和@Autowired

    //<bean id="sqlSessionFactory",class="xxx"
    // init-method="xxx",destroy-method="xxx">
    //  <property name="dataSource" value="dataSourceRef"/>
    //</bean>
    @Bean("sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        //mybatis.type-aliases-package=com.syc.oa.model
        sqlSessionFactoryBean.setTypeAliasesPackage("com.syc.oa.model");
        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean("dataSource")
    @Primary
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);

        /*dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        dataSource.setDbType(dbType);
        //密码连接属性
        dataSource.setConnectionProperties(connectionProperties);
        try {
            dataSource.setPasswordCallbackClassName(passwordCallbackClassName);
        } catch (Exception e) {
            LOGGER.error("druid configuration initialization passwordCallbackClassName", e);
        }
        try {
            dataSource.setFilters(filters);
        } catch (SQLException e) {
            LOGGER.error("druid configuration initialization filter", e);
        }*/
        return dataSource;
    }

    @Bean
    @Primary
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    //配置Druid的后台监控界面
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //设置druid登陆界面的用户名和密码
        Map<String, String> params = new HashMap<>();
        params.put("loginUsername", "admin");
        params.put("loginPassword", "123");
        params.put("allow", "");
        bean.setInitParameters(params);
        return bean;
    }

    //配置Druid的后台监控界面的过滤器
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String, String> params = new HashMap<>();
        params.put("exclusions", "*.js,*.css,/druid/*");
        bean.setInitParameters(params);
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }
}
