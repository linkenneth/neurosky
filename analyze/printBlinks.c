#include <stdio.h>
#include "ThinkGearStreamParser.h"

void printBlinks( unsigned char extendedCodeLevel,
                  unsigned char code,
                  unsigned char valueLength,
                  const unsigned char *value,
                  void *customData ) {
    if ( extendedCodeLevel == 0 ) {
        switch ( code ) {
            /* Blink Strength event (0-255) */
            case ( 0x16 ):
                printf( "Blink Strength: %d\n", value[0] & 0xFF );
                break;
            case ( 0x04 ):
                printf( "Attention level: %d\n", value[0] & 0xFF );
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
