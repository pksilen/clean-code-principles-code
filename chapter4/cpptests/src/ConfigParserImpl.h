#pragma once

#include <memory>
#include "Configuration.h"

class ConfigParserImpl {
public:
    std::shared_ptr<Configuration> parse();
};