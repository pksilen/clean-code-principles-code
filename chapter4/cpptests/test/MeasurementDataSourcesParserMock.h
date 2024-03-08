#include <gmock/gmock.h>
#include "MeasurementDataSourcesParser.h"

class MeasurementDataSourcesParserMock :
        public MeasurementDataSourcesParser
{
public:
    MOCK_METHOD(std::shared_ptr<MeasurementDataSources>, parse, ());
};