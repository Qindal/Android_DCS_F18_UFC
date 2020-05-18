# Android_DCS_F18_UFC

Simple app that replicates the UFC of the F/A-18C from DCS World.
Import and export to/from the simulator is via a TCP socket.

To make it work the user has to install the lua files in \Users\[USERNAME]\Saved Games\DCS.openbeta\Scripts\
and add th following line at the end of Export.lua
    local Exportlfs=require('lfs')
    dofile(Exportlfs.writedir()..'Scripts/ExportUFC.lua')
    
The app should be running before starting a mission
