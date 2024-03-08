#include "AnomalyDetectionRulesParserMock.h"
#include "ConfigFactoryMock.h"
#include "MeasurementDataSourcesParserMock.h"

class MockDependencyInjector final
{
public:
    std::shared_ptr<AnomalyDetectionRulesParserMock> m_anomalyDetectionRulesParserMock{
            std::make_shared<AnomalyDetectionRulesParserMock>()
    };

    std::shared_ptr<ConfigFactoryMock> m_configFactoryMock{
            new ConfigFactoryMock()
    };

    std::shared_ptr<MeasurementDataSourcesParserMock> m_measurementDataSourcesParserMock{
            new MeasurementDataSourcesParserMock()
    };

    void injectMockDependencies() const
    {
        AnomalyDetectionRulesParser::setInstance(
                m_anomalyDetectionRulesParserMock
        );

        ConfigFactory::setInstance(
                m_configFactoryMock
        );

        MeasurementDataSourcesParser::setInstance(
                m_measurementDataSourcesParserMock
        );
    }

    void removeMockDependencies() const {
        AnomalyDetectionRulesParser::setInstance({nullptr});
        ConfigFactory::setInstance({nullptr});
        MeasurementDataSourcesParser::setInstance({nullptr});
    }
};