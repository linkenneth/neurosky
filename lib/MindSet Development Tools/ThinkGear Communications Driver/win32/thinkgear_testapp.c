#include <stdlib.h>
#include <stdio.h>

#include "thinkgear.h"

/**
 * Prompts and waits for the user to press ENTER.
 */
void
wait() {
    printf( "\n" );
    printf( "Press the ENTER key...\n" );
    fflush( stdout );
    getc( stdin );
}

/**
 * Program which prints ThinkGear EEG_POWERS values to stdout.
 */
int
main( void ) {

    char *comPortName = NULL;
    int   dllVersion = 0;
    int   connectionId = 0;
    int   packetsRead = 0;
    int   errCode = 0;

    /* Print driver version number */
    dllVersion = TG_GetDriverVersion();
    printf( "ThinkGear DLL version: %d\n", dllVersion );

    /* Get a connection ID handle to ThinkGear */
    connectionId = TG_GetNewConnectionId();
    if( connectionId < 0 ) {
        fprintf( stderr, "ERROR: TG_GetNewConnectionId() returned %d.\n", 
                 connectionId );
        wait();
        exit( EXIT_FAILURE );
    }

    /* Set/open stream (raw bytes) log file for connection */
    errCode = TG_SetStreamLog( connectionId, "streamLog.txt" );
    if( errCode < 0 ) {
        fprintf( stderr, "ERROR: TG_SetStreamLog() returned %d.\n", errCode );
        wait();
        exit( EXIT_FAILURE );
    }

    /* Set/open data (ThinkGear values) log file for connection */
    errCode = TG_SetDataLog( connectionId, "dataLog.txt" );
    if( errCode < 0 ) {
        fprintf( stderr, "ERROR: TG_SetDataLog() returned %d.\n", errCode );
        wait();
        exit( EXIT_FAILURE );
    }

    /* Attempt to connect the connection ID handle to serial port "COM5" */
    comPortName = "\\\\.\\COM5";
    errCode = TG_Connect( connectionId, 
                          comPortName, 
                          TG_BAUD_9600, 
                          TG_STREAM_PACKETS );
    if( errCode < 0 ) {
        fprintf( stderr, "ERROR: TG_Connect() returned %d.\n", errCode );
        wait();
        exit( EXIT_FAILURE );
    }

    /* Read 10 ThinkGear Packets from the connection, 1 Packet at a time */
    packetsRead = 0;
    while( packetsRead < 10 ) {

        /* Attempt to read a Packet of data from the connection */
        errCode = TG_ReadPackets( connectionId, 1 );

        /* If TG_ReadPackets() was able to read a complete Packet of data... */
        if( errCode == 1 ) {
            packetsRead++;

            /* If attention value has been updated by TG_ReadPackets()... */
            if( TG_GetValueStatus(connectionId, TG_DATA_ATTENTION) != 0 ) {

                /* Get and print out the updated attention value */
                fprintf( stdout, "New attention value: %d\n",
                         TG_GetValue(connectionId, TG_DATA_ATTENTION) );
                fflush( stdout );

            } /* end "If attention value has been updated..." */

        } /* end "If a Packet of data was read..." */

    } /* end "Read 10 Packets of data from connection..." */

    /* Clean up */
    TG_FreeConnection( connectionId );

    /* End program */
    wait();
    return( EXIT_SUCCESS );
}