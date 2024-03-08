#include <gmock/gmock.h>
#include "../src/AnomalyIndicator.h"

class AnomalyIndicatorMock : public AnomalyIndicator
{
public:
    MOCK_METHOD(void, publish, ());
};