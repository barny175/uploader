package com.barnas.uploadapp.storage.amazon;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 05/11/2020.
 */
@TestConfiguration
public class AwsTestConfig {
    @Bean
    public AmazonS3 amazonS3(LocalStackContainer localStackContainer) {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(localStackContainer.getDefaultCredentialsProvider())
                .withEndpointConfiguration(localStackContainer.getEndpointConfiguration(LocalStackContainer.Service.S3))
//                .withRegion(localStackContainer.getRegion())
                .build();
    }

    @Bean
    public LocalStackContainer localStack(@Value("${s3.bucket}") String bucket) throws IOException, InterruptedException {
        LocalStackContainer localStackContainer = new LocalStackContainer()
                .withServices(LocalStackContainer.Service.S3);
        localStackContainer.start();
        localStackContainer.execInContainer("awslocal", "s3", "mb", "s3://" + bucket);
        return localStackContainer;
    }

    @Bean
    public MySQLContainer dbContainer() {
        MySQLContainer mySQLContainer = new MySQLContainer("mysql")
                .withDatabaseName("uploader")
                .withPassword("password")
                .withUsername("admin")
                .withDatabaseName("uploader");
        mySQLContainer.start();
        return mySQLContainer;
    }

    @Bean
    public S3Storage testS3Storage(LocalStackContainer localStack, ResourceLoader resourceLoader, @Value("${s3.bucket") String bucket) {
        return new S3Storage(resourceLoader,
                AmazonS3ClientBuilder.standard()
                        .withCredentials(localStack.getDefaultCredentialsProvider())
                        .withEndpointConfiguration(localStack.getEndpointConfiguration(LocalStackContainer.Service.S3))
                        .build(),
                bucket);
    }

    @Bean
    public DataSource dataSource(MySQLContainer dbContainer) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setPort(dbContainer.getFirstMappedPort());
        mysqlDataSource.setDatabaseName(dbContainer.getDatabaseName());
        mysqlDataSource.setPassword(dbContainer.getPassword());
        mysqlDataSource.setUser(dbContainer.getUsername());
        return mysqlDataSource;
    }
}
