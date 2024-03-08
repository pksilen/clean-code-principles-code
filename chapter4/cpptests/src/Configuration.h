#pragma once

#include <memory>
#include <vector>
#include "AnomalyDetectionRule.h"

class Configuration
{
public:
    virtual ~Configuration() = default;

    virtual std::shared_ptr<AnomalyDetectionRules>
    getAnomalyDetectionRules() const = 0;
};