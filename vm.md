## Setting up a linux virtual machine for GTF

1. Install your preferred version of Linux in a virtual machine.
2. Install Qt Creator or Visual Studio Code. (optional) 
3. Install git 
	- ``` sudo apt-get install git ```
4. Set up your ssh key.
5. Make a folder, navigate to it open it in terminal and checkout guide.
	- ```git clone git@gitext.elektrobitautomotive.com:EB-GUIDE/guide.git```
6. Install build essentials.
	- ```sudo apt-get install build-essential```
7. Install GCC ( preferable version is 4.9.0, but this might require aditional steps)
	-  ```sudo apt-get install build-essential```
7. Install cmake.
	- ```sudo apt-get install cmake```
	- ```sudo apt-get install cmake-gui-qt``` (optional)
8. Install EGL/OpenGL dev libraries.
	- ```sudo apt-get install libglfw3-dev libgles2-mesa-dev```
9. Install XCB dependencies.
	- ```sudo apt-get install libxcb-util0-dev libxcb-icccm4-dev libxcb-image0-dev libxcb-keysyms1-dev```
10. Install PkgConfig.
	- ```sudo apt-get install pkg-config```

11. For memory analysis, install valgrind. (optional)
	- ```sudo apt-get install valgrind```

### Building from command line

TBD
### Building with Visual Studio Code

TBD

### Building with Qt Creator

1. Select Tools -> Options -> Build -> Kits and make sure CMake, GCC, Valgrind, etc are set correctly.
2. Make a new kit, name it as you wish. A quick way to do is cloning the default "Desktop" kit.
3. Create a `toolchain.cmake` file. (note: the example below is for GCC 7.4.0, but might work for different versions)
    ```set(EB_PLATFORM_OS_FAMILY POSIX)
    set(EB_PLATFORM_OS Linux)
    set(EB_PLATFORM_ARCH x86_64)

    set(COMMON_FLAGS "-m64 -mtune=generic -mfpmath=sse -msse -pthread")
    set(SILENCE_WARNINGS "-Wno-error=address -Wno-error=ignored-qualifiers -Wno-error=implicit-fallthrough")

    set(CMAKE_CXX_FLAGS "${COMMON_FLAGS} ${SILENCE_WARNINGS}")
    set(CMAKE_C_FLAGS   "${COMMON_FLAGS} ${SILENCE_WARNINGS}")
    set(CMAKE_EXE_LINKER_FLAGS    "-Wl,-Bsymbolic")
    set(CMAKE_MODULE_LINKER_FLAGS "-Wl,-Bsymbolic")
    set(CMAKE_SHARED_LINKER_FLAGS "-Wl,-Bsymbolic")

    set(CMAKE_FIND_ROOT_PATH_MODE_PROGRAM NEVER)
    set(CMAKE_FIND_ROOT_PATH_MODE_LIBRARY ONLY)
    set(CMAKE_FIND_ROOT_PATH_MODE_INCLUDE ONLY)```
4. Scroll down to CMake Configuration and input the following:
 	 ```CMAKE_BUILD_TYPE:INTERNAL=Debug
      CMAKE_CXX_COMPILER:STRING=%{Compiler:Executable:Cxx}
      CMAKE_C_COMPILER:STRING=%{Compiler:Executable:C}
      CMAKE_INSTALL_PREFIX:INTERNAL=<path/to/install/folder>
      CMAKE_PREFIX_PATH:STRING=%{Qt:QT_INSTALL_PREFIX}
      CMAKE_TOOLCHAIN_FILE:FILEPATH=<path/to/your/toolchain/file>
      GTF_INSTALL_TYPE:INTERNAL=All
      GTF_USE_X11:INTERNAL=ON
      GUIDE_BUILD_MONITOR:INTERNAL=OFF
      QT_QMAKE_EXECUTABLE:STRING=%{Qt:qmakeExecutable} ```
5. Set `<path/to/install/folder>` to your desired folder and `<path/to/your/toolchain/file>` to where you saved your `toolchain.cmake` file.
6. Select Open Folder and navigate to your checkout's src folder, then select CMakeLists.txt
7. You may need to run CMake a few times before the build is successful.