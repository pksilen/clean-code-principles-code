#pragma once

#include <memory>
#include <vector>

class AnomalyIndicator
{
public:
    virtual ~AnomalyIndicator() = default;

    virtual void publish() = 0;
};

using AnomalyIndicators =
        std::vector<std::shared_ptr<AnomalyIndicator>>;