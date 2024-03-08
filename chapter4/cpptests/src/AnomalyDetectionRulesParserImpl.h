#pragma once
#include "AnomalyDetectionRulesParser.h"

class AnomalyDetectionRulesParserImpl :
        public AnomalyDetectionRulesParser
{
public:
    std::shared_ptr<AnomalyDetectionRules> parse() override {
        return std::make_shared<AnomalyDetectionRules>();
    }
};