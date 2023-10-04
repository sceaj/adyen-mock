package sceaj.adyenmock

import java.util.function.Supplier

import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test")
@Testcontainers
class SpringContextSpecification extends Specification {
	
	static log = LoggerFactory.getLogger(SpringContextSpecification)

	@LocalServerPort
	def port

//	@Container
//	static mariadb = new MariaDBContainer<>("mariadb:11.1.2")
//			.withDatabaseName("adyenmock")
//
//	def setupSpec() {
//		mariadb.start()
//	}
//
//	def cleanupSpec() {
//		mariadb.stop()
//	}
//
//	@DynamicPropertySource
//	static configureProperties(DynamicPropertyRegistry registry) {
//		registry.add("spring.datasource.url", new Supplier<String>() {
//			String get() {
//				log.info("Docker JDBC URL: {}", mariadb.jdbcUrl)
//				return mariadb.jdbcUrl
//			}
//		})
//		registry.add("spring.datasource.username", new Supplier<String>() {
//			String get() {
//				log.info("Docker DB Username: {}", mariadb.username)
//				return mariadb.username
//			}
//		})
//		registry.add("spring.datasource.password", new Supplier<String>() {
//			String get() {
//				log.info("Docker DB Password: {}", mariadb.password)
//				return mariadb.password
//			}
//		})
//	}
	
	def "SpringBoot Context Loads"() {
		expect:
		true
	}
}
