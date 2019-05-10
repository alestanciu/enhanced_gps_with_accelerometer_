/*
 * Empty C++ Application
 */
#include "acc.h"
#include "wifi.h"
#include "GPS.h"

#include "sleep.h"
#include "string"



using namespace std;

int GPSLineCounter = 0;

int main()
{

	Init_GPS();
	GPSLineCounter = 0;
	int SecureLines = 0;
	char ptr[255];
	char bff[6];
	char * recv_data = (char*) malloc(sizeof(char*) * 1024);
	char send_data[1024];
	char send_data2[4096];
	char data[1024];
	int start = 0;
	strcpy(ptr,"");
	strcpy(send_data,"");


	RunWifi();
	InitACL();
			while(state != CONNECTED) { Wifi_Connect(); DEIPcK::periodicTasks(); }
			xil_printf("Connected to wifi!\n");
			while(state != SERVERCONNECTED1) { Wifi_TCPConnect(); DEIPcK::periodicTasks(); }
			xil_printf("Connected to server!\n");
			while(1)
			{
				xil_printf("Sending data...\r\n");
				while(true)
				{

				strcpy(send_data,"");
				strcat(send_data,"acl:");
				strcat(send_data,GetDataFromACL());
				Wifi_SendServer((char*)send_data);
				strcpy(send_data,"");
				printf("Send ACL data!\n");

				strcpy(data,RecieveGPSData());
							for(int i=1;i<6;i++) bff[i-1] = data[i];
							bff[5]='\0';
							if(strcmp(bff,"GPGGA") == 0) { start = 1; SecureLines++;}
							if((start == 1) && (SecureLines > 6))
							{
								strcat(ptr,bff);
								strcat(ptr," ");
								strcat(send_data2,data);
								strcat(send_data2,"\n");
								if ( GPSLineCounter == 4 )
								{
									GPSLineCounter = 0;
									start = 0;
									SecureLines = 7;
									if(strcmp(ptr,"GPGGA GPGSA GPGSV GPRMC GPVTG ") == 0) { printf("Send GPS data!\n"); Wifi_SendServer((char*)send_data2); strcpy(ptr,"");  break;}
									else { strcpy(ptr,""); strcpy(send_data2,""); }
								}
								else GPSLineCounter++;
							}
				DEIPcK::periodicTasks();
				}
				strcpy(send_data2,"");
				DEIPcK::periodicTasks();
			}



	return 0;
}
