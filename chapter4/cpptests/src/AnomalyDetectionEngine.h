#pragma once

class AnomalyDetectionEngine
{
public:
    virtual ~AnomalyDetectionEngine() = default;

    virtual void detectAnomalies() = 0;
};