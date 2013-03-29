TO RECREATE THE thinkgear_testapp PROJECT:

1. Open Visual Studio 2005*.
2. Create a new project:
    - Use the "Visual C/C++" > "Win32" > "Win32 Console Application" template
    - Name the project "thinkgear_testapp"
    - Put the new project in an existing Solution or a new Solution.
    - Click OK.
3. When the Win32 Application Wizard pops up, select "Application Settings":
    - Under Application type, make sure "Console application" is selected
    - Under Additional options, make sure only "Empty project" is selected
    - Under common header files, make sure nothing is selected
    - Click Finish
4. Use Windows Explorer to copy the "thinkgear.h", "thinkgear.lib", and
   "thinkgear_testapp.c" files we provided to the new thinkgear_testapp
   project directory.
5. In Visual Studio, right-click on the "Source Files" folder:
    - Select "Add" > "Existing Item..."
    - Select the "thinkgear_testapp.c" file in the thinkgear_testapp folder
6. Right-click on the "Header Files" folder:
    - Select "Add" > "Existing Item..."
    - Select the "thinkgear.h" file in the thinkgear_testapp folder
7. Right-click on the "thinkgear_testapp" project itself:
    - Select "Add" > "Existing Item..."
    - Select the "thinkgear.lib" file in the thinkgear_testapp folder
      (you may have to change "Files of type" at the bottom to
      "All Files", and then ignore any prompts to create rules)
8. Build the project (The "thinkgear_testapp.exe" file should appear in
   Windows Explorer in the "SOLUTION\Debug\" folder)
9. Use Windows Explorer to copy the "thinkgear.dll" file we provided into
   the same folder as the "thinkgear_testapp.exe" ("SOLUTION\Debug\"), or
   into any folder on your system PATH.
10. Make sure your ThinkGear headset is connected to the computer on COM5**.
11. Run the "thinkgear_testapp.exe" file and watch the EEG brainwave values.
12. Read, understand, and adapt the code in thinkgear_testapp.cpp to your
    project to obtain data from ThinkGear Headsets.  Refer to the API
    documentation for details about the ThinkGear API functions.


*: Any version of Visual Studio or Visual C++ should work.
**: Use Device Manager to change the ThinkGear COM port to 5 if necessary.
