#pragma once

#include "MeasurementDataSourcesParser.h"

class MeasurementDataSourcesParserImpl :
        public MeasurementDataSourcesParser
{
public:
    std::shared_ptr<MeasurementDataSources> parse() override {
        return std::make_shared<MeasurementDataSources>();
    }
};