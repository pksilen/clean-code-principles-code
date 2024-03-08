include(GoogleTest)

add_executable(${CMAKE_PROJECT_NAME}-test
        ConfigurationMock.h
        AnomalyDetectionRuleMock.h
        AnomalyIndicatorMock.h
        ConfigParserImplTests.h
        ConfigParserImplTests.cpp
        MockDependenciesInjectedTest.h
        AnomalyDetectionRulesParserMock.h
        main.cpp
        ConfigFactoryMock.h
        MockDependencyInjector.h
        MeasurementDataSourcesParserMock.h
        )

target_compile_options(${CMAKE_PROJECT_NAME}-test PRIVATE)
target_compile_definitions(${CMAKE_PROJECT_NAME}-test PUBLIC _SILENCE_TR1_NAMESPACE_DEPRECATION_WARNING)
target_include_directories(${CMAKE_PROJECT_NAME}-test PUBLIC "/usr/include" "/usr/local/include")
target_link_libraries(${CMAKE_PROJECT_NAME}-test ${CMAKE_PROJECT_NAME}-lib gcov gtest gmock)
target_compile_definitions(${CMAKE_PROJECT_NAME}-test PRIVATE RUN_AS_GTEST)
gtest_discover_tests(${CMAKE_PROJECT_NAME}-test DISCOVERY_TIMEOUT 300)
install(TARGETS ${CMAKE_PROJECT_NAME}-test DESTINATION "${INSTALL_PATH}/test")
