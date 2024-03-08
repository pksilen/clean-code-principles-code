#include <algorithm>
#include <execution>
#include <iostream>
#include "AnomalyDetectionEngineImpl.h"

AnomalyDetectionEngineImpl::AnomalyDetectionEngineImpl(
        std::shared_ptr<Configuration> configuration
) : m_configuration(std::move(configuration))
{}

void AnomalyDetectionEngineImpl::detectAnomalies()
{
    const auto anomalyDetectionRules =
            m_configuration->getAnomalyDetectionRules();

    std::for_each(std::execution::par,
                  anomalyDetectionRules->cbegin(),
                  anomalyDetectionRules->cend(),
                  [this](const auto& anomalyDetectionRule)
                  {
                      detectAnomalies(anomalyDetectionRule);
                  });
}

void AnomalyDetectionEngineImpl::detectAnomalies(
        const std::shared_ptr<AnomalyDetectionRule>& anomalyDetectionRule
)
{
    const auto anomalyIndicators = anomalyDetectionRule->detectAnomalies();

    std::ranges::for_each(*anomalyIndicators,
                          [](const auto& anomalyIndicator)
                          {
                              anomalyIndicator->publish();
                          });
}