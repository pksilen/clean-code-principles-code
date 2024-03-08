#pragma once
#include "Configuration.h"

class ConfigurationImpl : public Configuration
{
public:
    virtual std::shared_ptr<AnomalyDetectionRules>
    getAnomalyDetectionRules() const override {
        return std::make_shared<AnomalyDetectionRules>();
    }
};