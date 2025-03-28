package capstone.backend.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource ;
@Configuration
@PropertySource("classpath:/application.properties")
public class DatabaseConfig {
    
    @Autowired
    private ApplicationContext context ;


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    } 
    @Bean
    public DataSource datasource() {
        return new HikariDataSource(hikariConfig()) ; 
    }

    @Bean
   public SqlSessionFactory sqlSessionFactory() throws Exception {
    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
    factoryBean.setDataSource(datasource());
    factoryBean.setMapperLocations(context.getResources("classpath:/mappers/**/*Mapper.xml")); // Mapper XML 파일 설정
    //factoryBean.setConfigLocation(context.getResource("classpath:/mybatis-config.xml")); // mybatis-config.xml 설정
    return factoryBean.getObject();
}


    @Bean
    public SqlSessionTemplate sqlSession() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }    
}
