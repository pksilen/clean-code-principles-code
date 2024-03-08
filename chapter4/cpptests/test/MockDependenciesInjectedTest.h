#include <gtest/gtest.h>
#include "MockDependencyInjector.h"

class MockDependenciesInjectedTest :
        public testing::Test
{
protected:
    void SetUp() override
    {
        m_mockDependencyInjector.injectMockDependencies();
    }

    void TearDown() override
    {
        m_mockDependencyInjector.removeMockDependencies();
    }

    MockDependencyInjector m_mockDependencyInjector;
};