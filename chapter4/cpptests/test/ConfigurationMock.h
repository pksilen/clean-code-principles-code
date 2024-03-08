#include <gmock/gmock.h>
#include "Configuration.h"

class ConfigurationMock : public Configuration
{
public:
    MOCK_METHOD(std::shared_ptr<AnomalyDetectionRules>,
                getAnomalyDetectionRules, (), (const)
    );
};