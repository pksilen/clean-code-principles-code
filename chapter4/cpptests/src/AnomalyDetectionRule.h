#pragma once

#include "AnomalyIndicator.h"

class AnomalyDetectionRule
{
public:
    virtual ~AnomalyDetectionRule() = default;

    virtual std::shared_ptr<AnomalyIndicators>
    detectAnomalies() = 0;
};

using AnomalyDetectionRules =
        std::vector<std::shared_ptr<AnomalyDetectionRule>>;