#include "ConfigParserImplTests.h"
#include "ConfigParserImpl.h"

using testing::Eq;
using testing::Return;

TEST_F(ConfigParserImplTests, testParseConfig)
{
    // GIVEN
    ConfigParserImpl configParser;

    // EXPECTATIONS
    EXPECT_CALL(
            *m_mockDependencyInjector.m_anomalyDetectionRulesParserMock,
            parse
    )
            .Times(1)
            .WillOnce(Return(m_anomalyDetectionRules));

    EXPECT_CALL(
            *m_mockDependencyInjector.m_measurementDataSourcesParserMock,
            parse
    )
            .Times(1)
            .WillOnce(Return(m_measurementDataSources));

    EXPECT_CALL(
            *m_mockDependencyInjector.m_configFactoryMock,
            createConfig(Eq(m_anomalyDetectionRules))
    )
            .Times(1)
            .WillOnce(Return(m_configMock));



    // WHEN
    const auto configuration = configParser.parse();

    // THEN
    ASSERT_EQ(configuration, m_configMock);
}