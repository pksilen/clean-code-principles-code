cmake_minimum_required(VERSION 3.10)
set(CMAKE_CXX_STANDARD 20)

#set linker flags
set(CMAKE_CXX_FLAGS_DEBUG "${CMAKE_CXX_FLAGS_DEBUG} -std=c++20 -Wall -Wno-nonnull -g -O0")
set(CMAKE_CXX_FLAGS_RELEASE "${CMAKE_CXX_FLAGS_RELEASE} -std=c++20 -Wall -Wno-nonnull -O3")

#Create project
project(cpp-example)

#After cmake 2.8.1 this sets that link_directories paths are transformed from relative to absolute
cmake_policy(SET CMP0015 NEW)

#custom options
option(BUILD_TESTS "Build the unit test program" ON)

enable_testing()
include_directories(src test)
add_subdirectory(src)
add_subdirectory(test)