#include "MockDependenciesInjectedTest.h"
#include "ConfigurationMock.h"

class ConfigParserImplTests :
        public MockDependenciesInjectedTest
{
protected:
    std::shared_ptr<MeasurementDataSources> m_measurementDataSources{std::make_shared<MeasurementDataSources>()};
    std::shared_ptr<AnomalyDetectionRules> m_anomalyDetectionRules{std::make_shared<AnomalyDetectionRules>()};
    std::shared_ptr<ConfigurationMock> m_configMock{std::make_shared<ConfigurationMock>()};
};