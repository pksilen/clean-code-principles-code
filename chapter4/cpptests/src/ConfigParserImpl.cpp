#include "AnomalyDetectionRulesParser.h"
#include "Configuration.h"
#include "ConfigFactory.h"
#include "ConfigParserImpl.h"
#include "MeasurementDataSourcesParser.h"

std::shared_ptr<Configuration>
ConfigParserImpl::parse()
{
    const auto measurementDataSources =
            MeasurementDataSourcesParser::getInstance()->parse();

    const auto anomalyDetectionRules =
            AnomalyDetectionRulesParser::getInstance()->parse();

    return ConfigFactory::getInstance()
            ->createConfig(anomalyDetectionRules);
}