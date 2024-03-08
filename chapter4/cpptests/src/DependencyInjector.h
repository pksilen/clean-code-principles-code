#pragma once

#include "AnomalyDetectionRulesParserImpl.h"
#include "ConfigFactoryImpl.h"
#include "MeasurementDataSourcesParserImpl.h"

class DependencyInjector final
{
public:
    static void injectDependencies()
    {
        AnomalyDetectionRulesParser::setInstance(
                std::make_shared<AnomalyDetectionRulesParserImpl>()
        );

        ConfigFactory::setInstance(
                std::make_shared<ConfigFactoryImpl>()
        );

        MeasurementDataSourcesParser::setInstance(
                std::make_shared<MeasurementDataSourcesParserImpl>()
        );
    }

private:
    DependencyInjector() = default;
};