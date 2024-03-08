#pragma once

#include <memory>
#include "AnomalyDetectionEngine.h"
#include "Configuration.h"

class AnomalyDetectionEngineImpl :
        public AnomalyDetectionEngine
{
public:
    explicit AnomalyDetectionEngineImpl(
            std::shared_ptr<Configuration> configuration
    );

    void detectAnomalies() override;

private:
    void detectAnomalies(
            const std::shared_ptr<AnomalyDetectionRule>& anomalyDetectionRule
    );

    std::shared_ptr<Configuration> m_configuration;
};