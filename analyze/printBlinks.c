#include <stdio.h>
#include "ThinkGearStreamParser.h"

void printBlinks( unsigned char extendedCodeLevel,
                  unsigned char code,
                  unsigned char valueLength,
                  const unsigned char *value,
                  void *customData ) {
    if ( extendedCodeLevel == 0 ) {
        switch ( code ) {
            case ( 0x16 ):
                printf( "BLINK: %d\n", value[0] & 0xFF );
                break;
            case ( 0x04 ):
                printf( "ATTENTION: %d\n", value[0] & 0xFF );
                break;
            case ( 0x02 ):
                printf( "POOR_SIGNAL: %d\n", value[0] & 0xFF );
                break;
            case ( 0x80 ):
                printf( "RAW: %d\n", (short) (( value[0] << 8 ) | value[1]) );
                break;
            default:
                break;
        }
    }
}

int main( int argc, char **argv ) {
    ThinkGearStreamParser parser;
    THINKGEAR_initParser( &parser, PARSER_TYPE_PACKETS,
            printBlinks, NULL );
    FILE *stream = fopen( "/dev/tty.MindSet-DevB", "r" );

    unsigned char streamByte;
    while ( 1 ) {
        fread( &streamByte, 1, 1, stream );
        THINKGEAR_parseByte( &parser, streamByte );
    }
}
