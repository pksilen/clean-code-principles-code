#include <gmock/gmock.h>
#include "AnomalyDetectionRulesParser.h"

class AnomalyDetectionRulesParserMock :
        public AnomalyDetectionRulesParser
{
public:
    MOCK_METHOD(std::shared_ptr<AnomalyDetectionRules>, parse, ());
};