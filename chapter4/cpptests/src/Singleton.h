#pragma once
#include <memory>

template<typename T>
class Singleton
{
public:
    Singleton() = default;

    virtual ~Singleton()
    {
        m_instance.reset();
    };

    static inline std::shared_ptr<T>& getInstance()
    {
        return m_instance;
    }

    static void setInstance(const std::shared_ptr<T>& instance)
    {
        m_instance = instance;
    }

private:
    static inline std::shared_ptr<T> m_instance;
};