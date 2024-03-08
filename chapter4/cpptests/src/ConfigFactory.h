#pragma once

#include "Singleton.h"
#include "Configuration.h"

class ConfigFactory :
        public Singleton<ConfigFactory>
{
public:
    virtual std::shared_ptr<Configuration>
    createConfig(
            const std::shared_ptr<AnomalyDetectionRules>& rules
    ) = 0;
};