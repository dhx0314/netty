//package com.nettydome.nettydemo.config;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
///**
// * Druid配置文件
// * Created by 上海德咸软件有限公司 on 2018/1/22.
// */
//@Configuration
//@ConditionalOnClass(DruidDataSource.class)
//@ConditionalOnProperty(name = "spring.datasource.type", havingValue = "com.alibaba.druid.pool.DruidDataSource", matchIfMissing = true)
//public class DruidDataSourceConfiguration {
//    @Value("${spring.datasource.druid.initialSize}")
//    private int initialSize;
//
//    @Value("${spring.datasource.druid.minIdle}")
//    private int minIdle;
//
//    @Value("${spring.datasource.druid.maxActive}")
//    private int maxActive;
//
//    @Value("${spring.datasource.druid.maxWait}")
//    private long maxWait;
//
//    @Value("${spring.datasource.druid.timeBetweenEvictionRunsMillis}")
//    private long timeBetweenEvictionRunsMillis;
//
//    @Value("${spring.datasource.druid.minEvictableIdleTimeMillis}")
//    private long minEvictableIdleTimeMillis;
//
//    @Value("${spring.datasource.druid.validationQuery}")
//    private String validationQuery;
//
//    @Value("${spring.datasource.druid.testWhileIdle}")
//    private boolean testWhileIdle;
//
//    @Value("${spring.datasource.druid.testOnBorrow}")
//    private boolean testOnBorrow;
//
//    @Value("${spring.datasource.druid.testOnReturn}")
//    private boolean testOnReturn;
//
//
//    @SuppressWarnings("unchecked")
//    protected <T> T createDataSource(DataSourceProperties properties,
//                                     Class<? extends DataSource> type) {
//        return (T) properties.initializeDataSourceBuilder().type(type).build();
//    }
//
//    /**
//     * @see
//     * @param properties 读入的配置
//     * @return DruidDataSource
//     */
//    @Bean
//    @ConfigurationProperties("spring.datasource.druid")
//    public DruidDataSource dataSource(DataSourceProperties properties) {
//        DruidDataSource dataSource = createDataSource(properties, DruidDataSource.class);
//
//        dataSource.setDriverClassName(properties.getDriverClassName());
//        dataSource.setUsername(properties.getUsername());
//        dataSource.setPassword(properties.getPassword());
//        dataSource.setUrl(properties.getUrl());
//        dataSource.setInitialSize(initialSize);
//        dataSource.setMinIdle(minIdle);
//        dataSource.setMaxActive(maxActive);
//        dataSource.setMaxWait(maxWait);
//        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
//        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
//
//        if (validationQuery != null && !"".equals(validationQuery)) {
//            dataSource.setTestOnBorrow(testOnBorrow);
//            dataSource.setValidationQuery(validationQuery);
//            dataSource.setTestWhileIdle(testWhileIdle);
//            dataSource.setTestOnReturn(testOnReturn);
//        }
//
//        return dataSource;
//    }
//}
