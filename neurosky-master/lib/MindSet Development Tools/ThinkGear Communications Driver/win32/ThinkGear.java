/*
 * @(#)Thinkgear.java    4.1    Jun 26, 2009
 * 
 * Copyright (c) 2008-2009 NeuroSky, Inc. All Rights Reserved
 * NEUROSKY PRORIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import java.io.*;

/**
 * @file ThinkGear.java
 * 
 * The ThinkGear Communications Driver (TGCD) is a set of library functions
 * which allows applications to communicate with ThinkGear modules.  The
 * TGCD functions are implemented as a shared library (.dll on Windows,
 * .bundle on Mac OS X).
 * <p>
 * This ThinkGear class for Java is simply a thin interface layer which uses 
 * Java Native Interface to provide access to the API functions in the TGCD
 * library, allowing Java programs to use the TGCD to communicate with
 * ThinkGear modules.
 * <p>
 * Because the class is merely a thin interface to the underlying shared
 * TGCD library, it is entirely dependent on the underlying TGCD library
 * to function, and is therefore only supported on platforms which can
 * run the TGCD itself (i.e. Windows and Mac OS X platforms).
 * <p>
 * There are three "tiers" of functions in this API, where functions
 * of a lower "tier" should not be called until an appropriate function
 * in the "tier" above it is called first:
 *
 * @code
 *
 * Tier 1:  Call anytime
 *     GetDriverVersion()
 *     GetNewConnectionId()
 *
 * Tier 2:  Requires GetNewConnectionId()
 *     SetStreamLog()
 *     SetDataLog()
 *     Connect()
 *     FreeConnection()
 *
 * Tier 3:  Requires Connect()
 *     ReadPackets()
 *     GetValue()
 *     SendByte()
 *     SetBaudrate()
 *     SetDataFormat()
 *     Disconnect()
 *
 * @endcode
 *
 * The contents of this file are generated from thinkgear.h, with the following
 * text manipulations:
 *   - "#define TG_"   => "public static final int "
 *   - "x ///<"        => "= x; ///<"
 *   - "THINKGEAR_API" => "public static native "
 *   - "const char *"  => "String "
 *   - "float"         => "double"
 *   - "TG_"           => ""
 *   - "@c x"          => "{@code x}"
 *     (in Visual Studio 2005, use: [@]c {:i+} => {@code \1})
 *   - tab indent
 * 
 * @author  Kelvin Soo
 * @author  Horace Ko
 * @version 4.1 Jun 26, 2009 Kelvin Soo
 *   - Updated name of library from ThinkGear Connection API to
 *     ThinkGear Communications Driver (TGCD).  The library still
 *     uses ThinkGear COnnection objects and IDs to communicate.
 * @version 1.0 Aug 07, 2008
 */
public class ThinkGear {
    
    /* Load DLL library */
    static { System.loadLibrary("thinkgear"); }

    private ThinkGear() {} /* Prevent instantiation of a ThinkGear instance */

    /**
     * Maximum number of Connections that can be requested before being
     * required to free one.
     */
    public static final int MAX_CONNECTION_HANDLES = 128;

    /**
     * Baud rate for use with Connect() and SetBaudrate().
     */
    public static final int BAUD_1200   =   1200; ///< Baud rate for use with Connect() and SetBaudrate().
    public static final int BAUD_2400   =   2400; ///< Baud rate for use with Connect() and SetBaudrate().
    public static final int BAUD_4800   =   4800; ///< Baud rate for use with Connect() and SetBaudrate().
    public static final int BAUD_9600   =   9600; ///< Baud rate for use with Connect() and SetBaudrate().
    public static final int BAUD_57600  =  57600; ///< Baud rate for use with Connect() and SetBaudrate().
    public static final int BAUD_115200 = 115200; ///< Baud rate for use with Connect() and SetBaudrate().

    /**
     * Data format for use with Connect() and SetDataFormat().
     */
    public static final int STREAM_PACKETS      = 0; ///< Data format for use with Connect() and SetDataFormat().
    public static final int STREAM_5VRAW        = 1; ///< Data format for use with Connect() and SetDataFormat().
    public static final int STREAM_FILE_PACKETS = 2; ///< Data format for use with TG_Connect() and TG_SetDataFormat().

    /**
     * Data type that can be requested from GetValue().
     */
    public static final int DATA_BATTERY     = 0; ///< Data type that can be requested from GetValue().
    public static final int DATA_POOR_SIGNAL = 1; ///< Data type that can be requested from GetValue().
    public static final int DATA_ATTENTION   = 2; ///< Data type that can be requested from GetValue().
    public static final int DATA_MEDITATION  = 3; ///< Data type that can be requested from GetValue().
    public static final int DATA_RAW         = 4; ///< Data type that can be requested from GetValue().
    public static final int DATA_DELTA       = 5; ///< Data type that can be requested from GetValue().
    public static final int DATA_THETA       = 6; ///< Data type that can be requested from GetValue().
    public static final int DATA_ALPHA1      = 7; ///< Data type that can be requested from GetValue().
    public static final int DATA_ALPHA2      = 8; ///< Data type that can be requested from GetValue().
    public static final int DATA_BETA1       = 9; ///< Data type that can be requested from GetValue().
    public static final int DATA_BETA2       = 10; ///< Data type that can be requested from GetValue().
    public static final int DATA_GAMMA1      = 11; ///< Data type that can be requested from GetValue().
    public static final int DATA_GAMMA2      = 12; ///< Data type that can be requested from GetValue().

    
    /**
     * Returns a number indicating the version of the ThinkGear Communications
     * Driver (TGCD) library accessed by this API.  Useful for debugging
     * version-related issues.
     *
     * @return The TGCD library's version number.
     */
    public static native int
    GetDriverVersion();


    /**
     * Returns an ID handle (an int) to a newly-allocated ThinkGear Connection
     * object.  The Connection is used to perform all other operations of this
     * API, so the ID handle is passed as the first argument to all functions
     * of this API.
     *
     * When the ThinkGear Connection is no longer needed, be sure to call
     * FreeConnection() on the ID handle to free its resources.  No more
     * than MAX_CONNECTION_HANDLES Connection handles may exist
     * simultaneously without being freed.
     *
     * @return -1 if too many Connections have been created without being freed
     *         by FreeConnection().
     * 
     * @return -2 if there is not enough free memory to allocate to a new
     *         ThinkGear Connection.
     * 
     * @return The ID handle of a newly-allocated ThinkGear Connection.
     */
    public static native int
    GetNewConnectionId();


    /**
     * As a ThinkGear Connection reads bytes from its serial stream, it may
     * automatically log those bytes into a log file.  This is useful primarily
     * for debugging purposes.  Calling this function with a valid {@code filename}
     * will turn this feature on.  Calling this function with an invalid
     * {@code filename}, or with {@code filename} set to NULL, will turn this feature
     * off.  This function may be called at any time for either purpose on a
     * ThinkGear Connection.
     *
     * @param connectionId The ID of the ThinkGear Connection to enable stream
     *                     logging for, as obtained from GetNewConnectionId().
     * @param filename     The name of the file to use for stream logging.
     *                     Any existing contents of the file will be erased.
     *                     Set to NULL to disable stream logging by the
     *                     ThinkGear Connection.
     *
     * @return -1 if {@code connectionId} does not refer to a valid ThinkGear
     *         Connection ID handle.
     * 
     * @return -2 if {@code filename} could not be opened for writing.  You may
     *         check errno for the reason.
     * 
     * @return 0 on success.
     */
    public static native int
    SetStreamLog( int connectionId, String filename );


    /**
     * As a ThinkGear Connection reads and parses Packets of data from its
     * serial stream, it may log the parsed data into a log file.  This is
     * useful primarily for debugging purposes.  Calling this function with
     * a valid {@code filename} will turn this feature on.  Calling this function
     * with an invalid {@code filename}, or with {@code filename} set to NULL, will turn
     * this feature off.  This function may be called at any time for either
     * purpose on a ThinkGear Connection.
     *
     * @param connectionId The ID of the ThinkGear Connection to enable data
     *                     logging for, as obtained from GetNewConnectionId().
     * @param filename     The name of the file to use for data logging.
     *                     Any existing contents of the file will be erased.
     *                     Set to NULL to disable stream logging by the
     *                     ThinkGear Connection.
     *
     * @return -1 if {@code connectionId} does not refer to a valid ThinkGear
     *         Connection ID handle.
     * 
     * @return -2 if {@code filename} could not be opened for writing.  You may
     *         check errno for the reason.
     * 
     * @return 0 on success.
     */
    public static native int
    SetDataLog( int connectionId, String filename );


    /**
     * Connects a ThinkGear Connection, given by {@code connectionId}, to a serial
     * communication (COM) port in order to communicate with a ThinkGear module.
     * It is important to check the return value of this function before
     * attempting to use the Connection further for other functions in this API.
     *
     * @param connectionId     The ID of the ThinkGear Connection to connect, as
     *                         obtained from GetNewConnectionId().
     * @param serialPortName   The name of the serial communication (COM) stream
     *                         port.  COM ports on PC Windows systems are named
     *                         like '\\.\COM4' (remember that backslashes in 
     *                         strings in most programming languages need to be 
     *                         escaped), while COM ports on Windows Mobile
     *                         systems are named like 'COM4:' (note the colon at
     *                         the end).  Linux COM ports may be named like
     *                         '/dev/ttys0'.  Refer to the documentation for 
     *                         your particular platform to determine the
     *                         available COM port names on your system.
     * @param serialBaudrate   The baudrate to use to attempt to communicate
     *                         on the serial communication port.  Select from
     *                         one of the BAUD_* constants defined above,
     *                         such as BAUD_9600 or BAUD_57600.
     *                         BAUD_9600 is the typical default baud rate
     *                         for standard ThinkGear modules.
     * @param serialDataFormat The type of ThinkGear data stream.  Select from
     *                         one of the STREAM_* constants defined above.
     *                         Most applications should use STREAM_PACKETS
     *                         (the data format for Embedded ThinkGear modules).
     *                         STREAM_5VRAW is supported only for legacy
     *                         non-embedded purposes.
     *
     * @return -1 if {@code connectionId} does not refer to a valid ThinkGear
     *         Connection ID handle.
     * 
     * @return -2 if {@code serialPortName} could not be opened as a serial
     *         communication port for any reason.  Check that the name
     *         is a valid COM port on your system.
     * 
     * @return -3 if {@code serialBaudrate} is not a valid BAUD_* value.
     * 
     * @return -4 if {@code serialDataFormat} is not a valid STREAM_* type.
     * 
     * @return 0 on success.
     */
    public static native int
    Connect( int connectionId, String serialPortName, int serialBaudrate,
                int serialDataFormat );


    /**
     * Attempts to use the ThinkGear Connection, given by {@code connectionId},
     * to read {@code numPackets} of data from the serial stream.  The Connection
     * will (internally) "remember" the most recent value it has seen of
     * each possible ThinkGear data type, so that any subsequent call to
     * {@code GetValue}() will return the most recently seen values.
     *
     * Set {@code numPackets} to -1 to attempt to read all Packets of data that
     * may be currently available on the serial stream.
     *
     * @param connectionId The ID of the ThinkGear Connection which should
     *                     read packets from its serial communication stream,
     *                     as obtained from GetNewConnectionId().
     * @param numPackets   The number of data Packets to attempt to read from
     *                     the ThinkGear Connection.  Only the most recently
     *                     read value of each data type will be "remembered"
     *                     by the ThinkGear Connection.  Setting this parameter
     *                     to -1 will attempt to read all currently available
     *                     Packets that are on the data stream.
     *
     * @return -1 if {@code connectionId} does not refer to a valid ThinkGear
     *         Connection ID handle.
     * 
     * @return -2 if there were not even any bytes available to be read from
     *         the Connection's serial communication stream.
     * 
     * @return -3 if an I/O error occurs attempting to read from the Connection's
     *         serial communication stream.
     * 
     * @return The number of Packets that were successfully read and parsed
     *         from the Connection.
     */
    public static native int
    ReadPackets( int connectionId, int numPackets );


    /**
     * Returns the most recently read value of the given {@code dataType}, which
     * is one of the DATA_* constants defined above.  Use {@code ReadPackets}()
     * to read more Packets in order to obtain updated values.  Afterwards, use
     * {@code GetValueStatus}() to check if a call to {@code ReadPackets}() actually
     * updated a particular {@code dataType}.
     *
     * NOTE: This function will terminate the program with a message printed
     * to stderr if {@code connectionId} is not a valid ThinkGear Connection, or
     * if {@code dataType} is not a valid DATA_* constant.
     *
     * @param connectionId The ID of the ThinkGear Connection to get a data
     *                     value from, as obtained from GetNewConnectionId().
     * @param dataType     The type of data value desired.  Select from one of
     *                     the DATA_* constants defined above.  Refer to the
     *                     documentation of each constant for details of how
     *                     to interpret its value.
     *
     * @return The most recent value of the requested {@code dataType}.
     */
    public static native double
    GetValue( int connectionId, int dataType );


    /**
     * Returns Non-zero if the {@code dataType} was updated by the most recent call 
     * to GetValue().  Returns 0 otherwise.
     *
     * @param connectionId The ID of the ThinkGear Connection to get a data
     *                     value from, as obtained from GetNewConnectionId().
     * @param dataType     The type of data value desired.  Select from one of
     *                     the DATA_* constants defined above.  
     *
     * NOTE: This function will terminate the program with a message printed
     * to stderr if {@code connectionId} is not a valid ThinkGear Connection, or
     * if {@code dataType} is not a valid DATA_* constant.
     *
     * @return Non-zero if the {@code dataType} was updated by the most recent call
     * to GetValue().  Returns 0 otherwise.
     */
    public static native int
    GetValueStatus( int connectionId, int dataType );


    /**
     * Sends a byte through the ThinkGear Connection (presumably to a ThinkGear
     * module).  This function is intended for advanced ThinkGear Command Byte
     * operations.
     *
     * WARNING: Always make sure at least one valid Packet has been read (i.e.
     *          through the {@code ReadPackets}() function) at some point BEFORE
     *          calling this function.  This is to ENSURE the Connection is
     *          communicating at the right baud rate.  Sending Command Byte
     *          at the wrong baud rate may put a ThinkGear module into an
     *          indeterminate and inoperable state until it is reset by power 
     *          cycling (turning it off and then on again).
     *
     * NOTE: After sending a Command Byte that changes a ThinkGear baud rate,
     *       you will need to call {@code SetBaudrate}() to change the baud rate 
     *       of your {@code connectionId} as well.  After such a baud rate change, 
     *       it is important to check for a valid Packet to be received by
     *       {@code ReadPacket}() before attempting to send any other Command
     *       Bytes, for the same reasons as describe in the WARNING above.
     *
     * @param connectionId The ID of the ThinkGear Connection to send a byte
     *                     through, as obtained from GetNewConnectionId().
     * @param b            The byte to send through.  Note that only the lowest
     *                     8-bits of the value will actually be sent through.
     *
     * @return -1 if {@code connectionId} does not refer to a valid ThinkGear
     *         Connection ID handle.
     * 
     * @return 0 on success.
     */
    public static native int
    SendByte( int connectionId, int b );


    /**
     * Attempts to change the baud rate of the ThinkGear Connection, given by
     * {@code connectionId}, to {@code serialBaudrate}.  This function does not typically
     * need to be called, except after calling {@code SendByte}() to send a
     * Command Byte that changes the ThinkGear module's baud rate.  See 
     * SendByte() for details and NOTE.
     *
     * @param connectionId   The ID of the ThinkGear Connection to send a byte
     *                       through, as obtained from GetNewConnectionId().
     * @param serialBaudrate The baudrate to use to attempt to communicate
     *                       on the serial communication port.  Select from
     *                       one of the BAUD_* constants defined above,
     *                       such as BAUD_9600 or BAUD_57600.
     *                       BAUD_9600 is the typical default baud rate
     *                       for standard ThinkGear modules.
     *
     * @return -1 if {@code connectionId} does not refer to a valid ThinkGear
     *         Connection ID handle.
     * 
     * @return -2 if {@code serialBaudrate} is not a valid BAUD_* value.
     * 
     * @return 0 on success.
     */
    public static native int
    SetBaudrate( int connectionId, int serialBaudrate );


    /**
     * Attempts to change the data Packet parsing format used by the ThinkGear
     * Connection, given by {@code connectionId}, to {@code serialDataFormat}.  This
     * function does not typically need to be called, and is provided only
     * for special testing purposes.
     *
     * @param connectionId     The ID of the ThinkGear Connection to send a byte
     *                         through, as obtained from GetNewConnectionId().
     * @param serialDataFormat The type of ThinkGear data stream.  Select from
     *                         one of the STREAM_* constants defined above.
     *                         Most applications should use STREAM_PACKETS
     *                         (the data format for Embedded ThinkGear modules).
     *                         STREAM_5VRAW is supported only for legacy
     *                         non-embedded purposes.
     *
     * @return -1 if {@code connectionId} does not refer to a valid ThinkGear
     *         Connection ID handle.
     * 
     * @return -2 if {@code serialDataFormat} is not a valid STREAM_* type.
     * 
     * @return 0 on success.
     */
    public static native int
    SetDataFormat( int connectionId, int serialDataFormat );


    /**
     * Disconnects the ThinkGear Connection, given by {@code connectionId}, from
     * its serial communication (COM) port.  Note that after this call, the
     * Connection will not be valid to use with any of the API functions
     * that require a valid ThinkGear Connection, except SetStreamLog(),
     * SetDataLog(), Connect(), and FreeConnection().
     *
     * Note that FreeConnection() will automatically disconnect a
     * Connection as well, so it is not necessary to call this function
     * unless you wish to reuse the {@code connectionId} to call Connect()
     * again.
     *
     * @param connectionId The ID of the ThinkGear Connection to disconnect, as
     *                     obtained from GetNewConnectionId().
     */
    public static native void
    Disconnect( int connectionId );


    /**
     * Frees all memory associated with the given ThinkGear Connection.
     *
     * Note that this function will automatically call Disconnect() to
     * disconnect the Connection first, if appropriate, so that it is not
     * necessary to explicitly call Disconnect() before calling this
     * function, unless you wish to reuse the {@code connectionId} without freeing
     * it first.
     *
     * @param connectionId The ID of the ThinkGear Connection to disconnect, as
     *                     obtained from GetNewConnectionId().
     */
    public static native void
    FreeConnection( int connectionId );


}