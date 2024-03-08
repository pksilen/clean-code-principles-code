#include <gmock/gmock.h>
#include "ConfigFactory.h"

class ConfigFactoryMock : public ConfigFactory
{
public:
    MOCK_METHOD(
            std::shared_ptr<Configuration>,
            createConfig,
            (const std::shared_ptr<AnomalyDetectionRules>& rules)
    );
};