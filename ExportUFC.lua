-- Connects to the server and waits until the player ID is defined


local LogFile = nil
LogFile = io.open(lfs.writedir().."/Logs/ExportUFCConnect.log", "w")
LogFile:write("Start of test ExportUFC\n");

local _myID
local MySocket

ExportUFCConnect = 
{
	SelectAircraftType=function(self)
		LogFile:write("Selecting aircraft: ")
		
		_my_airplane = LoGetSelfData()	
		
		LogFile:write( _my_airplane.Name .. "\n")
		if MySocket then
			socket.try(MySocket:send(_my_airplane.Name .. "\n"))
		end
		if _my_airplane.Name == "FA-18C_hornet" then		-- FA-18C_hornet is current aircraft
			dofile(lfs.writedir()..'Scripts/FA-18C_hornet_UFC.lua')
		end
			
	end,

	Start=function(self) 
		
		package.path  = package.path..";"..lfs.currentdir().."/LuaSocket/?.lua"
		package.cpath = package.cpath..";"..lfs.currentdir().."/LuaSocket/?.dll"

		socket = require("socket")
		IPAddress = "192.168.0.13"
		IPAddress = "127.0.0.1" -- Local test
		Port = 61528
		
		MySocket = socket.try(socket.connect(IPAddress, Port))
		MySocket:setoption("tcp-nodelay",true) 
		MySocket:settimeout(.01) -- set the timeout for reading the socket 
		
		_myID = LoGetPlayerPlaneId()
		if _myID then ExportUFCConnect:SelectAircraftType() end
	end,
	
	
	BeforeNextFrame=function(self)
		-- Read commands from socket and send them to the aircraft
		-- Works just before every simulation frame.
		
		if _myID then
			-- Read data
			local inpst, status, res = MySocket:receive()
			if string.len(res) > 0 then
			
				-- LogFile:write("Received "..res.."\n");
				
				t = {}
				
				for k, v in string.gmatch(res, "(%w+)=([+-]?[%w]?[.]?%w+)") do
					command = tonumber(k)
					value = tonumber(v)
					t[command] = value
					-- LogFile:write(string.format("Element %i %f\r\n", command, value))
					
					AircraftName:Command(command,value)
				end
			else
				-- LogFile:write("Nothing received: Status " .. status .. "\n");
			end
			
		else
			LogFile:write("Trying to get aircraft ID\n");
			_myID = LoGetPlayerPlaneId()
			if _myID then ExportUFCConnect:SelectAircraftType() end
		end
	end,
	
	AfterNextFrame=function(self)
		_data = AircraftName:CollectData()
		-- LogFile:write("Sending data socket " .. _data .. "\n");
		
		my_send = socket.protect(function()
			if MySocket then
				socket.try(MySocket:send(_data))
			end
		end)
		my_send()
				
	end,
	
	
	Stop=function(self)
	
		AircraftName:Stop()
	
		if MySocket then 
			socket.try(MySocket:send("exit"))
			MySocket:close()
		end
		if LogFile then
			LogFile:write("Export stopped\n");
			io.close(LogFile)
		end
	end
}


-- =============
-- Overload
-- =============
do
	local PrevLuaExportStart=LuaExportStart
	LuaExportStart=function()
		ExportUFCConnect:Start()
		if PrevLuaExportStart then
			PrevLuaExportStart()
		end
	end
end


do
	local PrevLuaExportBeforeNextFrame=LuaExportBeforeNextFrame
	LuaExportBeforeNextFrame=function()
		ExportUFCConnect:BeforeNextFrame()
		if PrevLuaExportBeforeNextFrame then
			PrevLuaExportBeforeNextFrame()
		end
	end
end

do
	local PrevLuaExportAfterNextFrame=LuaExportAfterNextFrame
	LuaExportAfterNextFrame=function()
		ExportUFCConnect:AfterNextFrame()
		if PrevLuaExportAfterNextFrame then
			PrevLuaExportAfterNextFrame()
		end
	end
end

do
	local PrevLuaExportStop=LuaExportStop
	LuaExportStop=function()
		ExportUFCConnect:Stop()
		if PrevLuaExportStop then
			PrevLuaExportStop()
		end
	end
end


