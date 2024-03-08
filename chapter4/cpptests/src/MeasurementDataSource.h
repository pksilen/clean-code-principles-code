#pragma once

#include <memory>
#include <vector>

class MeasurementDataSource {
    // ...
};

using MeasurementDataSources =
        std::vector<std::shared_ptr<MeasurementDataSource>>;