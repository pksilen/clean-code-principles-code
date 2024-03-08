#pragma once

#include "Singleton.h"
#include "MeasurementDataSource.h"

class MeasurementDataSourcesParser :
        public Singleton<MeasurementDataSourcesParser>
{
public:
    virtual std::shared_ptr<MeasurementDataSources> parse() = 0;
};