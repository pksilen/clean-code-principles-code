#include <gmock/gmock.h>
#include "../src/AnomalyDetectionRule.h"

class AnomalyDetectionRuleMock : public AnomalyDetectionRule
{
public:
    MOCK_METHOD(std::shared_ptr<AnomalyIndicators>,
                detectAnomalies, ());
};