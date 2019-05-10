connect -url tcp:127.0.0.1:3121
source D:/Users/Project_GPS/Proiect_GPS_ACL/Proiect_GPS_ACL.sdk/design_1_wrapper_hw_platform_0/ps7_init.tcl
targets -set -nocase -filter {name =~"APU*" && jtag_cable_name =~ "Digilent Arty Z7 003017A70203A"} -index 0
loadhw D:/Users/Project_GPS/Proiect_GPS_ACL/Proiect_GPS_ACL.sdk/design_1_wrapper_hw_platform_0/system.hdf
targets -set -nocase -filter {name =~"APU*" && jtag_cable_name =~ "Digilent Arty Z7 003017A70203A"} -index 0
stop
ps7_init
ps7_post_config
targets -set -nocase -filter {name =~ "ARM*#0" && jtag_cable_name =~ "Digilent Arty Z7 003017A70203A"} -index 0
rst -processor
targets -set -nocase -filter {name =~ "ARM*#0" && jtag_cable_name =~ "Digilent Arty Z7 003017A70203A"} -index 0
dow D:/Users/Project_GPS/Proiect_GPS_ACL/Proiect_GPS_ACL.sdk/proiect_c/Debug/proiect_c.elf
targets -set -nocase -filter {name =~ "ARM*#0" && jtag_cable_name =~ "Digilent Arty Z7 003017A70203A"} -index 0
con
