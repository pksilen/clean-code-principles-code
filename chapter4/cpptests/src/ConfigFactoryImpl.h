#pragma once
#include "ConfigFactory.h"
#include "ConfigurationImpl.h"

class ConfigFactoryImpl : public ConfigFactory
{
public:
    std::shared_ptr<Configuration>
    createConfig(
            const std::shared_ptr<AnomalyDetectionRules>& rules
    ) override {
        return std::make_shared<ConfigurationImpl>();
    }
};