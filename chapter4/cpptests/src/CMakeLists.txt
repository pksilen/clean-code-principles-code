
#add library which unit test can also use
add_library( ${CMAKE_PROJECT_NAME}-lib STATIC
			AnomalyDetectionEngine.h
			AnomalyDetectionEngineImpl.h
			AnomalyDetectionEngineImpl.cpp
			AnomalyDetectionRule.h
			Configuration.h
			AnomalyIndicator.h
			Singleton.h
			ConfigParserImpl.h
			ConfigParserImpl.cpp
			MeasurementDataSource.h
			MeasurementDataSourcesParser.h
			MeasurementDataSourcesParserImpl.h
			AnomalyDetectionRulesParser.h
			AnomalyDetectionRulesParserImpl.h
			ConfigFactory.h
			ConfigFactoryImpl.h
			DependencyInjector.h
		)

###############################################################################
# Add executable which uses library
###############################################################################
add_executable(${CMAKE_PROJECT_NAME} main.cpp)

target_include_directories(${CMAKE_PROJECT_NAME} PUBLIC "/usr/include" "/usr/local/include")
target_link_libraries(${CMAKE_PROJECT_NAME} ${CMAKE_PROJECT_NAME}-lib ssl z crypto curl pthread rdkafka rdkafka++ stdc++fs prometheus-cpp-core prometheus-cpp-pull pulsar)

###############################################################################
#installation
###############################################################################

#install executable
install(TARGETS ${CMAKE_PROJECT_NAME} DESTINATION ${INSTALL_PATH})




