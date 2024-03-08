#pragma once

#include "Singleton.h"
#include "AnomalyDetectionRule.h"

class AnomalyDetectionRulesParser :
        public Singleton<AnomalyDetectionRulesParser>
{
public:
    virtual std::shared_ptr<AnomalyDetectionRules> parse() = 0;
};